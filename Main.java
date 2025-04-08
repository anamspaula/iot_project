public class Main {
    public static void main(String[] args) {
        SerialReader reader = new SerialReader();
        String portName = "COM3"; // Porta do Arduino

        GetRequestNativo get = new GetRequestNativo();

        // Obtém os dados da API antes de iniciar a leitura
        PropArduino propArduino = get.buscarDados("linkDaApi"); 

        if (propArduino != null) { 
            if (reader.connect(portName)) {
                System.out.println("Conectado à porta " + portName + ". Enviando valores de referência...");
                
                // Envia os valores de referência para o sensor
                reader.enviarValoresReferencia(propArduino);

                System.out.println("Iniciando leitura dos dados...");
                // Coleta os dados do sensor
                PropArduino dadosColetados = reader.readLoop(propArduino.horas);

                // Após a leitura, envia os dados via PUT
                PutRequestNativo.enviarDados(
                    "linkDaApi",
                    dadosColetados.temp,
                    dadosColetados.umidade.intValue(),
                    dadosColetados.horas.toString(),
                    dadosColetados.luminosidade.intValue()
                );

            } else {
                System.out.println("Erro ao conectar à porta " + portName);
            }
        } else {
            System.out.println("Erro ao obter dados da API.");
        }
    }
}
