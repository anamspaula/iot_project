public class Main {
    public static void main(String[] args) {
        SerialReader reader = new SerialReader();
        String portName = "COM3"; // <-- Porta COM do arduino

        if (reader.connect(portName)) {
            System.out.println("Conectado à porta " + portName + ". Lendo dados...");
            reader.readLoop();
        } else {
            System.out.println("Erro ao conectar à porta " + portName);
        }
    }
}
