public class Fase2_Concurrente {
    public static void main(String[] args) {
        // 1. Instancio mis tareas (Runnable) y mi hilo de riego (Thread)
        Temperatura miTareaTemp = new Temperatura();
        Humedad miTareaHum = new Humedad();
        Luz miTareaLuz = new Luz();
        ProgramadorRiego miHiloRiego = new ProgramadorRiego();

        // 2. Meto cada uno de mis sensores en un Thread con el nombre que pide el PDF
        Thread hiloTemp = new Thread(miTareaTemp, "sensor-Temperatura");
        Thread hiloHum = new Thread(miTareaHum, "sensor-Humedad");
        Thread hiloLuz = new Thread(miTareaLuz, "sensor-Luz");

        System.out.println("=== INICIO FASE 2: CONCURRENTE ===");
        long miTiempoInicio = System.currentTimeMillis();

        // 3. Arranco mis cuatro hilos a la vez
        hiloTemp.start();
        hiloHum.start();
        hiloLuz.start();
        miHiloRiego.start();

        // 4. Obligo a mi programa principal a esperar a que terminen los 4 antes de imprimir el informe
        try {
            hiloTemp.join();
            hiloHum.join();
            hiloLuz.join();
            miHiloRiego.join();
        } catch (InterruptedException e) {
            System.out.println("Mi hilo principal fue interrumpido.");
        }

        long miTiempoFin = System.currentTimeMillis();

        // 5. Imprimo mi informe final y calculo mi tiempo total
        System.out.println("\n=== INFORMES ===");
        System.out.println(miTareaTemp.informe());
        System.out.println(miTareaHum.informe());
        System.out.println(miTareaLuz.informe());
        System.out.println(miHiloRiego.informe());

        long miTiempoTotal = miTiempoFin - miTiempoInicio;
        System.out.println("\nTiempo total concurrente: " + miTiempoTotal + " ms");
    }
}
