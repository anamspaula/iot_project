import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutRequestNativo {
    public static void enviarDados(String apiUrl, double temperatura, int umidade, String horas, int luminosidade) {
        try {
            // Criar URL e abrir conexão
            URL url = new URL("linkDaApi");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            
            // Configurar método PUT e cabeçalhos
            conexao.setRequestMethod("PUT");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);

            // Criar JSON manualmente
            String json = "{"
                    + "\"temperatura\":" + temperatura + ","
                    + "\"umidade\":" + umidade + ","
                    + "\"horas\":\"" + horas + "\","
                    + "\"luminosidade\":" + luminosidade
                    + "}";

            // Enviar os dados
            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obter resposta
            int statusCode = conexao.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Dados enviados com sucesso!");
            } else {
                System.out.println("Erro ao enviar dados. Código: " + statusCode);
            }

            conexao.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}