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
    private TrafficLightController trafficLightController;

    public CarController2(List<Car> cars, Semaphore semaphore, TilePanel[][] grid, TrafficLightController trafficLightController) {
        this.cars = cars;
        this.semaphore = semaphore;
        this.grid = grid;
        this.carImage = cars.get(0).getImage();
        this.trafficLightController = trafficLightController;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int yFeu0 = trafficLightController.getTrafficLight1().getY(); // pour la direction 0
                int xFeu1 = trafficLightController.getTrafficLight2().getX();

                List<Car> carsToRemove = new ArrayList<>();
                for (Car car : cars) {
                    int oldX = car.getX();
                    int oldY = car.getY();

                    // Si la voiture est sur le point de passer le feu et que le feu est vert
                    if (!car.hasCrossedLight()) {
                        if (car.getDirection() == 0 && oldY == yFeu0 && trafficLightController.getTrafficLight1().isGreen()) {
                            semaphore.acquire();
                            car.crossTrafficLight(); // Marquer la voiture comme ayant franchi le feu
                        } else if (car.getDirection() == 1 && oldX == xFeu1 && trafficLightController.getTrafficLight2().isGreen()) {
                            semaphore.acquire();
                            car.crossTrafficLight(); // Marquer la voiture comme ayant franchi le feu
                        }
                    }

                    // Déplacer la voiture uniquement si elle a déjà passé le feu ou si le feu est vert.
                    if (car.hasCrossedLight() ||
                            (car.getDirection() == 0 && trafficLightController.getTrafficLight1().isGreen()) ||
                            (car.getDirection() == 1 && trafficLightController.getTrafficLight2().isGreen())) {
                        car.move();
                        int newX = car.getX();
                        int newY = car.getY();

                        // Mettez à jour la grille avec la nouvelle position de la voiture
                        updateGridWithNewCarPosition(oldX, oldY, newX, newY, car);
                    }

                    // Supprimez les voitures qui ont quitté la grille
                    removeCarsOutsideGrid(oldX, oldY, car, carsToRemove);

                    // Libérer le sémaphore si la voiture a franchi le feu
                    if (car.hasCrossedLight()) {
                        semaphore.release();
                    }
                }

                // Ajoutez de nouvelles voitures si nécessaire
                addNewCars(carsToRemove);

                Thread.sleep(450);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void updateGridWithNewCarPosition(int oldX, int oldY, int newX, int newY, Car car) {
        SwingUtilities.invokeLater(() -> {
            grid[oldX][oldY].setCar(null);
            grid[oldX][oldY].repaint();
            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid.length) {
                grid[newX][newY].setCar(car);
                grid[newX][newY].repaint();
            }
        });
    }

    private void removeCarsOutsideGrid(int oldX, int oldY, Car car, List<Car> carsToRemove) {
        int newX = car.getX();
        int newY = car.getY();
        if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid.length) {
            grid[oldX][oldY].setCar(null);
            grid[oldX][oldY].repaint();
            carsToRemove.add(car);
        }
    }

    private void addNewCars(List<Car> carsToRemove) {
        cars.removeAll(carsToRemove);
        for (Car car : carsToRemove) {
            Car newCar = createNewCar();
            cars.add(newCar);
        }
    }

    private Car createNewCar() {
        int startX = 9;
        int startY = 5;
        int direction = 1; // de bas en haut
        return new Car(startX, startY, direction, carImage);
    }

}


