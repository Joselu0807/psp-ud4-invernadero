public class Fase3_Experimentos {
    public static void main(String[] args) {
        System.out.println("=== MIS TRES EXPERIMENTOS (FASE 3) ===");

        // --- EXPERIMENTO A: Imprimir informe sin esperar ---
        System.out.println("\n--- Experimento A ---");
        Temperatura miTareaTemp = new Temperatura();
        Thread miHiloTemp = new Thread(miTareaTemp, "sensor-TempExpA");

        miHiloTemp.start();
        // Imprimo mi informe inmediatamente
        System.out.println("Mi informe sin esperar: " + miTareaTemp.informe());

        try {
            miHiloTemp.join(); // Ahora sí espero a que termine mi hilo
        } catch (InterruptedException e) {
            System.out.println("Fui interrumpido.");
        }
        // Vuelvo a imprimir (ahora sí tendrá mis 5 lecturas)
        System.out.println("Mi informe tras esperar: " + miTareaTemp.informe());

        // --- EXPERIMENTO B: run() vs start() ---
        System.out.println("\n--- Experimento B ---");
        ProgramadorRiego miRiegoExpB = new ProgramadorRiego(); // Nota: usará mis 5 ciclos por defecto

        long miInicioB = System.currentTimeMillis();

        miRiegoExpB.run();
        long miFinB = System.currentTimeMillis();

        System.out.println("Mi tiempo de ejecución (secuencial): " + (miFinB - miInicioB) + " ms");
        // Imprimo mi estado. Saldrá "NEW" porque nunca usé start()
        System.out.println("Estado de mi hilo tras usar run(): " + miRiegoExpB.getState());

        // --- EXPERIMENTO C: Doble start() ---
        System.out.println("\n--- Experimento C ---");
        ProgramadorRiego miRiegoExpC = new ProgramadorRiego();
        miRiegoExpC.start();

        try {
            miRiegoExpC.join(); // Espero a que mi hilo muera tranquilamente
        } catch (InterruptedException e) {
            System.out.println("Fui interrumpido.");
        }

        try {
            System.out.println("Intento arrancar mi hilo por segunda vez...");
            // Esto es lo que lanza mi excepción según el CheatSheet
            miRiegoExpC.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("¡Capturado! Excepción: " + e.toString());
        }
    }
}