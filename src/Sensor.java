public abstract class Sensor implements Runnable {
    protected String nombre;
    protected String unidad;
    protected int periodo; // en milisegundos
    protected int numLecturas;
    protected double umbralMin;
    protected double umbralMax;

    // Estadísticas
    protected int lecturasTotales = 0;
    protected double sumaValores = 0;
    protected double minValor = Double.MAX_VALUE;
    protected double maxValor = Double.MIN_VALUE;
    protected int alertas = 0;

    public Sensor(String nombre, String unidad, int periodo, int numLecturas, double umbralMin, double umbralMax) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.periodo = periodo;
        this.numLecturas = numLecturas;
        this.umbralMin = umbralMin;
        this.umbralMax = umbralMax;
    }

    public abstract double medir();

    @Override
    public void run() {
        for (int i = 0; i < numLecturas; i++) {
            double valor = medir();
            lecturasTotales++;
            sumaValores += valor;

            if (valor < minValor) minValor = valor;
            if (valor > maxValor) maxValor = valor;

            boolean fueraDeRango = (valor < umbralMin || valor > umbralMax);
            if (fueraDeRango) {
                alertas++;
            }

            String hora = java.time.LocalTime.now().toString();
            String nombreHilo = Thread.currentThread().getName();
            String alertaStr = fueraDeRango ? " -> ¡¡ALERTA!!" : "";

            System.out.println("[" + hora + "] [" + nombreHilo + "] " + nombre + ": " + String.format("%.2f", valor) + " " + unidad + alertaStr);

            try {
                Thread.sleep(periodo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public String informe() {
        double media = lecturasTotales > 0 ? sumaValores / lecturasTotales : 0;
        return String.format("Sensor: %s | Lecturas: %d | Media: %.2f %s | Mín: %.2f | Máx: %.2f | Alertas: %d",
                nombre, lecturasTotales, media, unidad, minValor, maxValor, alertas);
    }
}
