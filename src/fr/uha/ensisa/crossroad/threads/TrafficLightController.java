package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.TrafficLight;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import java.util.concurrent.Semaphore;

public class TrafficLightController extends Thread {
    private final Semaphore horizontalLight;
    private final Semaphore verticalLight;
    private final int beamtime;
    private final TilePanel[][] grid;
    private final TrafficLight l1;
    private final TrafficLight l2;
    private final TrafficLight l3;
    private final TrafficLight l4;

    public TrafficLightController(Semaphore horizontalLight, Semaphore verticalLight, int dureeFeu, TilePanel[][] grid, TrafficLight l1, TrafficLight l2, TrafficLight l3, TrafficLight l4) {
        this.horizontalLight = horizontalLight;
        this.verticalLight = verticalLight;
        this.beamtime = dureeFeu;
        this.grid = grid;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
    }

    public TrafficLight getTrafficLight1() {
        return this.l1;
    }

    public TrafficLight getTrafficLight2() {
        return this.l2;
    }

    public TrafficLight getTrafficLight3() {
        return this.l3;
    }

    public TrafficLight getTrafficLight4() {
        return this.l4;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Changer les premiers feux horizontales au rouge et les verticales au vert après un délai
                l1.setGreen(false);
                updateTrafficLight(l1);
                l3.setGreen(false);
                updateTrafficLight(l3);
                Thread.sleep(1000); // Délai de sécurité d'une seconde
                l2.setGreen(true);
                updateTrafficLight(l2);
                l4.setGreen(true);
                updateTrafficLight(l4);

                // Gestion des sémaphores et délai
                handleSemaphores();
                Thread.sleep(beamtime);

                // Inverser le processus pour les feux
                l2.setGreen(false);
                updateTrafficLight(l2);
                l4.setGreen(false);
                updateTrafficLight(l4);
                Thread.sleep(1000); // Délai de sécurité d'une seconde
                l1.setGreen(true);
                updateTrafficLight(l1);
                l3.setGreen(true);
                updateTrafficLight(l3);

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
            horizontalLight.release();
            verticalLight.drainPermits();
        } else {
            verticalLight.release();
            horizontalLight.drainPermits();
        }
    }
}
