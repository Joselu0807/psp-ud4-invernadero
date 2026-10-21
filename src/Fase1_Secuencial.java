public class Fase1_Secuencial {
    public static void main(String[] args) {
        //  Instancio los dispositivos
        Temperatura sensorTemp = new Temperatura();
        Humedad sensorHum = new Humedad();
        Luz sensorLuz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        System.out.println("=== INICIO FASE 1: SECUENCIAL ===");

        // Aqui captura la marca de tiempo inicial
        long tiempoInicio = System.currentTimeMillis();

        // Llamo a run() uno detrás de otro
        sensorTemp.run();
        sensorHum.run();
        sensorLuz.run();
        riego.run();

        // Obténgo la marca final
        long tiempoFin = System.currentTimeMillis();

        // Aqui imprimo el informe de todos
        System.out.println("\n=== INFORMES ===");
        System.out.println(sensorTemp.informe());
        System.out.println(sensorHum.informe());
        System.out.println(sensorLuz.informe());
        System.out.println(riego.informe());

        // Calcula la diferencia de tiempo
        long tiempoTotal = tiempoFin - tiempoInicio;
        System.out.println("\nTiempo total secuencial: " + tiempoTotal + " ms");
    }
}
