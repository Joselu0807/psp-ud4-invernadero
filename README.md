# Invernadero con hilos
**Alumno:** Jose Luis Martin Blanco
**Módulo:** Programación de Servicios y Procesos (DAM)

## Qué hace
He desarrollado un prototipo para automatizar el control de un invernadero. El sistema lee datos de tres sensores (temperatura, humedad y luz) y controla la válvula de un programador de riego. He implementado una versión secuencial y una concurrente (usando `Thread` y `Runnable`) para demostrar cómo la concurrencia reduce drásticamente el tiempo de ejecución.

## Cómo ejecutarlo
1. Clona este repositorio en tu equipo.
2. Abre el proyecto con IntelliJ IDEA asegurándote de usar el **JDK 21**.
3. Ejecuta los métodos `main` de las clases `Fase1_Secuencial`, `Fase2_Concurrente`, `Fase3_Experimentos` y `Fase4_Vigilante`.

## Captura
*(Nota: Borra este texto en cursiva y pon aquí la ruta de la captura de tu Fase 2, por ejemplo: `![Captura Fase 2](captura.png)`)*

## Resultados

| Versión | Tiempo total aproximado |
| :--- | :--- |
| **Fase 1 (Secuencial)** | ~ 11.000 ms |
| **Fase 2 (Concurrente)** | ~ 3.500 ms |

## Análisis

### ¿Por qué compensa usar hilos en el invernadero?
Me compensa totalmente usar hilos porque, en mi versión secuencial, el tiempo total es la **suma** de los tiempos de todos los dispositivos (unos 11 segundos), dejando el invernadero desatendido mientras un sensor espera. En cambio, en mi versión concurrente, todos trabajan a la vez, por lo que el tiempo total se reduce al tiempo del dispositivo más lento (mi riego, que tarda unos 3.500 ms). Así vigilo todo simultáneamente.

### Experimentos A, B y C
- **Experimento A**: Cuando imprimí mi informe sin esperar, me salieron 0 lecturas porque el hilo principal no le dio tiempo al hilo del sensor a medir nada. Al poner el `.join()`, obligué a mi hilo principal a esperar, y entonces sí salieron las 5 lecturas.
- **Experimento B**: Al llamar a `run()` en lugar de `start()`, el código se ejecutó secuencialmente dentro de mi hilo `main`. Como nunca llegué a usar `start()`, mi hilo del riego nunca arrancó como hilo independiente y su estado se quedó en `NEW`.
- **Experimento C**: Al hacer un doble `start()` sobre un hilo que ya había muerto, me saltó la excepción `IllegalThreadStateException`, demostrando que no puedo resucitar o reiniciar un hilo que ya ha terminado.

### Estados observados
| Estado | Cuándo lo observé | Qué línea lo provoca |
| :--- | :--- | :--- |
| **NEW** | Antes de arrancar mis hilos. | Justo después de instanciar: `new Thread(...)` o `new ProgramadorRiego()`. |
| **RUNNABLE** | Mientras los dispositivos trabajan. | Cuando ejecutan operaciones o imprimen por consola (ej. `System.out.println()`). |
| **TIMED_WAITING** | Cuando mis sensores o riego pausan. | Provocado por la línea `Thread.sleep(periodo);`. |
| **TERMINATED** | Al finalizar el trabajo. | Tras salir del método `run()` y ser esperados por `join()`. |

**¿Por qué termina el programa si el vigilante sigue en su bucle?**
Termina solo porque convertí a mi vigilante en un hilo de servicio llamando a `miVigilante.setDaemon(true)` antes de iniciarlo. Esto hace que la máquina virtual de Java (JVM) cierre el programa en cuanto terminan mis hilos de trabajo normales, sin quedarse bloqueada esperando al bucle infinito del vigilante.

### Thread frente a Runnable

| Aspecto a comparar | extends Thread | implements Runnable |
| :--- | :--- | :--- |
| **Qué representa** | El objeto es directamente el hilo. | El objeto es la tarea que ejecutará un hilo. |
| **Herencia** | Gasto mi única herencia (ya no puedo heredar de otra clase). | Me deja libre la herencia para heredar de otra clase si lo necesito. |
| **Cómo obtengo el nombre** | Directamente con `this.getName()`. | Usando `Thread.currentThread().getName()`. |
| **Cómo se arranca** | Directamente invocando `miObjeto.start()`. | Tengo que pasarlo a un hilo: `new Thread(miObjeto).start()`. |

**¿Por qué los sensores SOLO pueden ser Runnable en tu diseño?**
Porque en mi diseño los sensores ya heredan de mi clase abstracta `Sensor`. Como Java no permite la herencia múltiple, si `Sensor` o sus clases hijas heredasen de `Thread`, ya no podrían formar esta jerarquía. Usando `Runnable`, mantengo mi diseño y simplemente paso la tarea a un hilo.

**¿Qué forma elegirías para el riego y por qué?**
Elegí `extends Thread` porque el programador de riego es un elemento único en mi diseño que no necesita heredar de ninguna otra clase base. Su única función es ser un proceso independiente, por lo que me resulta más directo modelarlo como un hilo por sí mismo.