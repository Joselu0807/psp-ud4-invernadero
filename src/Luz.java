import java.util.concurrent.ThreadLocalRandom;

public class Luz extends Sensor {
    public Luz() {
        super("Luz", "lux", 500, 5, 200.0, 900.0);
    }

    @Override
    public double medir() {
        // Genera valores entre 0 y 1000
        return ThreadLocalRandom.current().nextDouble(0.0, 1000.0);
    }
}