package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.TrafficLight;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import java.util.concurrent.Semaphore;

public class TrafficLightController extends Thread {
    private final Semaphore semaphoreFeu1;
    private final Semaphore semaphoreFeu2;
    private final int dureeFeu;
    private final TilePanel[][] grid;
    private final TrafficLight l1;
    private final TrafficLight l2;

    public TrafficLightController(Semaphore semaphoreFeu1, Semaphore semaphoreFeu2, int dureeFeu, TilePanel[][] grid, TrafficLight l1, TrafficLight l2) {
        this.semaphoreFeu1 = semaphoreFeu1;
        this.semaphoreFeu2 = semaphoreFeu2;
        this.dureeFeu = dureeFeu;
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
            System.out.println("Les feux de circulation sont allumés!");
            System.out.println("La position en X de l1 :" + l1.getX());
            System.out.println("La position en Y de l1 :" + l1.getY());
            System.out.println("------------------------------------");
            System.out.println("La position en X de l2 :" + l2.getX());
            System.out.println("La position en Y de l2 :" + l2.getY());

            while (true) {
                changeTrafficLight();

                // Gérer l'état du feu 1
                if (l1.isGreen()) {
                    semaphoreFeu1.release(); // Permettre aux voitures de passer si le feu est vert
                } else {
                    semaphoreFeu1.drainPermits(); // Empêcher les nouvelles voitures de passer si le feu est rouge
                }

                Thread.sleep(dureeFeu);

                changeTrafficLight(); // Changer l'état des feux

                // Gérer l'état du feu 2
                if (l2.isGreen()) {
                    semaphoreFeu2.release(); // Permettre aux voitures de passer si le feu est vert
                } else {
                    semaphoreFeu2.drainPermits(); // Empêcher les nouvelles voitures de passer si le feu est rouge
                }

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
