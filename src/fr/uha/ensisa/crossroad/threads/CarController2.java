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
                int yFeu0 = trafficLightController.getTrafficLight1().getY() + 1;
                int xFeu1 = trafficLightController.getTrafficLight2().getX() - 1;

                List<Car> carsToRemove = new ArrayList<>();
                for (Car car : cars) {
                    int oldX = car.getX();
                    int oldY = car.getY();

                    // Déterminer si la voiture est juste devant le feu
                    boolean isJustBeforeFeu0 = (car.getDirection() == 0 && oldY + 1 == yFeu0);
                    boolean isJustBeforeFeu1 = (car.getDirection() == 1 && oldX - 1 == xFeu1);

                    // Si la voiture n'a pas encore franchi le feu et est juste devant le feu
                    if (!car.hasCrossedLight() && (isJustBeforeFeu0 || isJustBeforeFeu1)) {
                        // Si le feu est rouge, la voiture s'arrête
                        if ((car.getDirection() == 0 && !trafficLightController.getTrafficLight1().isGreen()) ||
                                (car.getDirection() == 1 && !trafficLightController.getTrafficLight2().isGreen())) {
                            continue; // Passer à la voiture suivante sans bouger celle-ci
                        }
                    }

                    // Si la voiture n'est pas juste devant le feu ou le feu est vert, ou la voiture a déjà franchi le feu
                    if (!isJustBeforeFeu0 && !isJustBeforeFeu1 || car.hasCrossedLight() ||
                            (car.getDirection() == 0 && trafficLightController.getTrafficLight1().isGreen()) ||
                            (car.getDirection() == 1 && trafficLightController.getTrafficLight2().isGreen())) {
                        car.move(); // Déplacer la voiture
                    }

                    // Si la voiture est maintenant au niveau du feu, la marquer comme ayant franchi le feu
                    if ((car.getDirection() == 0 && car.getY() == yFeu0) ||
                            (car.getDirection() == 1 && car.getX() == xFeu1)) {
                        car.crossTrafficLight();
                    }

                    // Mettre à jour la grille avec la nouvelle position de la voiture
                    int newX = car.getX();
                    int newY = car.getY();
                    updateGridWithNewCarPosition(oldX, oldY, newX, newY, car);

                    // Supprimer les voitures qui ont quitté la grille
                    removeCarsOutsideGrid(oldX, oldY, car, carsToRemove);
                }

                // Ajouter de nouvelles voitures si nécessaire
                addNewCars(carsToRemove);

                Thread.sleep(100); // Attendre avant le prochain cycle de déplacement
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


