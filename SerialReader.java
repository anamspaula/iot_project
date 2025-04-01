import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

public class SerialReader {
    private SerialPort port;

    public boolean connect(String portName) {
        port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);
        return port.openPort();
    }

    public void readLoop() {
        Scanner scanner = new Scanner(port.getInputStream());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            SensorData data = parseData(line);
            if (data != null) {
                System.out.println(data);
            }
        }
        scanner.close();
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
