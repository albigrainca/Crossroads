package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.Car;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public class CarController extends Thread {
    private List<Car> cars;
    private Semaphore semaphore;
    private TilePanel[][] grid;

    public CarController(List<Car> cars, Semaphore semaphore, TilePanel[][] grid) {
        this.cars = cars;
        this.semaphore = semaphore;
        this.grid = grid;
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphore.acquire();

                for (Car car : cars) {
                    int oldX = car.getX();
                    int oldY = car.getY();

                    car.move();
                    int newX = car.getX();
                    int newY = car.getY();

                    SwingUtilities.invokeLater(() -> {
                        grid[oldX][oldY].setCar(null);
                        grid[oldX][oldY].repaint();

                        grid[newX][newY].setCar(car);
                        grid[newX][newY].repaint();
                    });

                }

                semaphore.release(); // Relâcher le sémaphore après le passage des voitures
                Thread.sleep(100);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


