public class SensorData {
    private double temperatura;
    private double umidade;
    private int luminosidade;

    public SensorData(double temperatura, double umidade, int luminosidade) {
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.luminosidade = luminosidade;
    }

    @Override
    public String toString() {
        return String.format("Temperatura: %.1f °C | Umidade: %.1f %% | Luminosidade: %d lx",
                temperatura, umidade, luminosidade);
    }
}
