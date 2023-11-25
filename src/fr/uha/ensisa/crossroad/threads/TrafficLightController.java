package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.TrafficLight;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import java.util.concurrent.Semaphore;

public class TrafficLightController extends Thread {
    private Semaphore semaphoreFeu1;
    private Semaphore semaphoreFeu2;
    private int dureeFeu;
    private TilePanel[][] grid;
    private TrafficLight l1;
    private TrafficLight l2;

    public TrafficLightController(Semaphore semaphoreFeu1, Semaphore semaphoreFeu2, int dureeFeu, TilePanel[][] grid, TrafficLight l1, TrafficLight l2) {
        this.semaphoreFeu1 = semaphoreFeu1;
        this.semaphoreFeu2 = semaphoreFeu2;
        this.dureeFeu = dureeFeu;
        this.grid = grid;
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphoreFeu1.release(); // Feu 1 vert
                semaphoreFeu2.acquire(); // Feu 2 rouge
                changeTrafficLight();

                Thread.sleep(dureeFeu);

                semaphoreFeu1.acquire(); // Feu 1 rouge
                semaphoreFeu2.release(); // Feu 2 vert
                changeTrafficLight();

                Thread.sleep(dureeFeu);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void changeTrafficLight() {
        l1.setGreen(!l1.isGreen());
        grid[l1.getX()][l1.getY()].setLight1(l1);
        grid[l1.getX()][l1.getY()].repaint();
        l2.setGreen(!l2.isGreen());
        grid[l2.getX()][l2.getY()].setLight1(l2);
        grid[l2.getX()][l2.getY()].repaint();
    }


}
