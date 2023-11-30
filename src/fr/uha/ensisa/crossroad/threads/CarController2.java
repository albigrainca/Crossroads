package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.Car;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class CarController2 extends Thread {
    private List<Car> cars;
    private Semaphore semaphore;
    private TilePanel[][] grid;
    private BufferedImage carImage;

    public CarController2(List<Car> cars, Semaphore semaphore, TilePanel[][] grid) {
        this.cars = cars;
        this.semaphore = semaphore;
        this.grid = grid;
        this.carImage = cars.get(0).getImage();
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphore.acquire();

                List<Car> carsToRemove = new ArrayList<>();
                for (Car car : cars) {
                    int oldX = car.getX();
                    int oldY = car.getY();

                    car.move();
                    int newX = car.getX();
                    int newY = car.getY();

                    if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid.length) {
                        grid[oldX][oldY].setCar(null);
                        grid[oldX][oldY].repaint();
                        carsToRemove.add(car);
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            grid[newX][newY].setCar(car);
                            grid[newX][newY].repaint();

                            grid[oldX][oldY].setCar(null);
                            grid[oldX][oldY].repaint();
                        });
                    }

                }

                cars.removeAll(carsToRemove);
                for (Car car : carsToRemove) {
                    Car newCar = createNewCar(); // Méthode à implémenter pour créer une nouvelle voiture
                    cars.add(newCar);
                }

                semaphore.release(); // Relâcher le sémaphore après le passage des voitures
                Thread.sleep(100);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Car createNewCar() {
        int startX = 9;
        int startY = 5;
        int direction = 1; // de bas en haut
        return new Car(startX, startY, direction, carImage);
    }

}


