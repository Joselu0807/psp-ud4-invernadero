public class Fase4_Vigilante {
    public static void main(String[] args) {
        // 1. Preparo mis tareas y mi hilo de riego
        Temperatura miTareaTemp = new Temperatura();
        Humedad miTareaHum = new Humedad();
        Luz miTareaLuz = new Luz();
        ProgramadorRiego miHiloRiego = new ProgramadorRiego();

        // 2. Meto mis sensores en sus hilos
        Thread hiloTemp = new Thread(miTareaTemp, "sensor-Temperatura");
        Thread hiloHum = new Thread(miTareaHum, "sensor-Humedad");
        Thread hiloLuz = new Thread(miTareaLuz, "sensor-Luz");

        // 3. Creo mi hilo vigilante con un bucle infinito
        Thread miVigilante = new Thread(() -> {
            while (true) {
                System.out.println("\n[Mi Vigilante] Estados de mis hilos:");
                System.out.println(" - Temp: " + hiloTemp.getState());
                System.out.println(" - Hum: " + hiloHum.getState());
                System.out.println(" - Luz: " + hiloLuz.getState());
                System.out.println(" - Riego: " + miHiloRiego.getState());

                try {
                    Thread.sleep(300); // Vigilo cada 300 ms como pide el PDF
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        // 4. Convierto a mi vigilante en demonio antes de arrancarlo
        miVigilante.setDaemon(true);

        // 5. Muestro mis estados antes de arrancar
        System.out.println("=== MIS ESTADOS ANTES DE ARRANCAR ===");
        System.out.println("Temp: " + hiloTemp.getState());
        System.out.println("Hum: " + hiloHum.getState());
        System.out.println("Luz: " + hiloLuz.getState());
        System.out.println("Riego: " + miHiloRiego.getState());

        System.out.println("\n=== ARRANCANDO MIS HILOS ===");
        miVigilante.start();
        hiloTemp.start();
        hiloHum.start();
        hiloLuz.start();
        miHiloRiego.start();

        // 6. Espero a que terminen mis 4 hilos de trabajo
        try {
            hiloTemp.join();
            hiloHum.join();
            hiloLuz.join();
            miHiloRiego.join();
        } catch (InterruptedException e) {
            System.out.println("Fui interrumpido.");
        }

        // 7. Muestro mis estado despues de esperar
        System.out.println("\n=== MIS ESTADOS DESPUÉS DE ESPERAR ===");
        System.out.println("Temp: " + hiloTemp.getState());
        System.out.println("Hum: " + hiloHum.getState());
        System.out.println("Luz: " + hiloLuz.getState());
        System.out.println("Riego: " + miHiloRiego.getState());
        System.out.println("\nFin de mi programa principal. Mi vigilante se apagará solo.");
    }
}