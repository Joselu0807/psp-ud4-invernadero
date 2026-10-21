import java.util.concurrent.ThreadLocalRandom;

public class Temperatura extends Sensor {
    public Temperatura() {
        // nombre, unidad, periodo, numLecturas, umbralMin, umbralMax
        super("Temperatura", "°C", 400, 5, 18.0, 30.0);
    }

    @Override
    public double medir() {
        // Genera valores entre 15.0 y 35.0
        return ThreadLocalRandom.current().nextDouble(15.0, 35.0);
    }
}