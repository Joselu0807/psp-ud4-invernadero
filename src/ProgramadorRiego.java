public class ProgramadorRiego extends Thread {
    private int ciclos = 5;
    private double litrosGastados = 0.0;

    public ProgramadorRiego() {
        // El nombre riego al hilo usando el constructor de Thread
        super("riego");
    }

    @Override
    public void run() {
        for (int i = 1; i <= ciclos; i++) {
            String hora = java.time.LocalTime.now().toString();
            System.out.println("[" + hora + "] [" + getName() + "] Riego: ABIERTA");
            litrosGastados += 2.5;

            hora = java.time.LocalTime.now().toString();
            System.out.println("[" + hora + "] [" + getName() + "] Riego: cerrada");

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }

    public String informe() {
        return String.format("Riego: %d ciclos | Litros totales: %.2f L", ciclos, litrosGastados);
    }
}