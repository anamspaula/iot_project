import com.fazecast.jSerialComm.SerialPort;
import java.io.OutputStream;
import java.util.Scanner;

public class SerialReader {
    private SerialPort port;

    public boolean connect(String portName) {
        port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);
        return port.openPort();
    }

    public void enviarValoresReferencia(PropArduino prop) {
        try {
            OutputStream outputStream = port.getOutputStream();
            String referencia = prop.umidade + ";" + prop.temp + ";" + prop.horas + ";" + prop.luminosidade + "\n";
            outputStream.write(referencia.getBytes());
            outputStream.flush();
            System.out.println("Valores de referência enviados ao sensor: " + referencia);
        } catch (Exception e) {
            System.err.println("Erro ao enviar valores de referência: " + e.getMessage());
        }
    }

    public PropArduino readLoop(Double horas) {
        Scanner scanner = new Scanner(port.getInputStream());

        long startTime = System.currentTimeMillis();
        long duration = (long) (horas * 1000);

        double ultimaTemp = 0;
        double ultimaUmid = 0;
        int ultimaLuz = 0;

        while (System.currentTimeMillis() - startTime < duration) { 
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                SensorData data = parseData(line);
                if (data != null) {
                    ultimaTemp = data.getTemp();
                    ultimaUmid = data.getUmidade();
                    ultimaLuz = data.getLuminosidade();
                    System.out.println(data);
                }
            }
        }

        scanner.close();
        port.closePort();

        return new PropArduino(ultimaUmid, ultimaTemp, horas, ultimaLuz);
    }

    private SensorData parseData(String line) {
        try {
            String[] parts = line.split(";");
            double temp = Double.parseDouble(parts[0].split(":")[1]);
            double umid = Double.parseDouble(parts[1].split(":")[1]);
            int luz = Integer.parseInt(parts[2].split(":")[1]);
            return new SensorData(temp, umid, luz);
        } catch (Exception e) {
            System.err.println("Erro ao processar: " + line);
            return null;
        }
    }
}
