import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequestNativo {
    public static void buscarDados(String apiUrl) {
        try {
            // Criar URL e abrir conexão
            URL url = new URL("linkDaApi");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            
            // Configurar método GET
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Accept", "application/json");

            // Obter código de resposta
            int statusCode = conexao.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                // Ler a resposta
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "utf-8"));
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    resposta.append(linha);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(resposta.toString());

                // Ler quatro propriedades específicas e converter para Double
                Double umidade = jsonResponse.optDouble("umidade", 0.0);
                Double temp = jsonResponse.optDouble("temp", 0.0);
                Double horas = jsonResponse.optDouble("horas", 0.0);
                Double luminosidade = jsonResponse.optDouble("luminosidade", 0.0);

                // Criar objeto PropArduino
                PropArduino dados = new PropArduino(umidade, temp, horas, luminosidade);

                // Retornar o objeto para uso posterior
                return dados;

            } else {
                System.out.println("Erro ao buscar dados. Código: " + statusCode);
            }

            conexao.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}