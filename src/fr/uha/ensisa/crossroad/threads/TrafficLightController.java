package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.TrafficLight;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import java.util.concurrent.Semaphore;

public class TrafficLightController extends Thread {
    private final Semaphore semaphoreFeu1;
    private final Semaphore semaphoreFeu2;
    private final int beamtime;
    private final TilePanel[][] grid;
    private final TrafficLight l1;
    private final TrafficLight l2;

    public TrafficLightController(Semaphore semaphoreFeu1, Semaphore semaphoreFeu2, int dureeFeu, TilePanel[][] grid, TrafficLight l1, TrafficLight l2) {
        this.semaphoreFeu1 = semaphoreFeu1;
        this.semaphoreFeu2 = semaphoreFeu2;
        this.beamtime = dureeFeu;
        this.grid = grid;
        this.l1 = l1;
        this.l2 = l2;
    }

    public TrafficLight getTrafficLight1() {
        return this.l1;
    }

    public TrafficLight getTrafficLight2() {
        return this.l2;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Changer le premier feu au rouge et le second au vert après un délai
                l1.setGreen(false);
                updateTrafficLight(l1);
                Thread.sleep(1000); // Délai de sécurité d'une seconde
                l2.setGreen(true);
                updateTrafficLight(l2);

                // Gestion des sémaphores et délai
                handleSemaphores();
                Thread.sleep(beamtime);

                // Inverser le processus pour les feux
                l2.setGreen(false);
                updateTrafficLight(l2);
                Thread.sleep(1000); // Délai de sécurité d'une seconde
                l1.setGreen(true);
                updateTrafficLight(l1);

                // Gestion des sémaphores et délai
                handleSemaphores();
                Thread.sleep(beamtime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateTrafficLight(TrafficLight light) {
        grid[light.getX()][light.getY()].setLight1(light);
        grid[light.getX()][light.getY()].repaint();
    }

    private void handleSemaphores() {
        if (l1.isGreen()) {
            semaphoreFeu1.release();
            semaphoreFeu2.drainPermits();
        } else {
            semaphoreFeu2.release();
            semaphoreFeu1.drainPermits();
        }
    }
}
