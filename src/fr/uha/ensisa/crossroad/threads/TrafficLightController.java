package fr.uha.ensisa.crossroad.threads;

import java.util.concurrent.Semaphore;

public class TrafficLightController extends Thread {
    private Semaphore semaphoreFeu1;
    private Semaphore semaphoreFeu2;
    private int dureeFeu;

    public TrafficLightController(Semaphore semaphoreFeu1, Semaphore semaphoreFeu2, int dureeFeu) {
        this.semaphoreFeu1 = semaphoreFeu1;
        this.semaphoreFeu2 = semaphoreFeu2;
        this.dureeFeu = dureeFeu;
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphoreFeu1.release(); // Feu 1 vert
                semaphoreFeu2.acquire(); // Feu 2 rouge

                Thread.sleep(dureeFeu);

                semaphoreFeu1.acquire(); // Feu 1 rouge
                semaphoreFeu2.release(); // Feu 2 vert

                Thread.sleep(dureeFeu);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
