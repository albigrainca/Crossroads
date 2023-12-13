package fr.uha.ensisa.crossroad.threads;

import fr.uha.ensisa.crossroad.app.Car;
import fr.uha.ensisa.crossroad.ui.ImageLoader;
import fr.uha.ensisa.crossroad.ui.TilePanel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarController2 extends Thread {
    private final List<Car> cars;
    private final TilePanel[][] grid;
    private final BufferedImage carImage;
    private final BufferedImage carImage2;
    private final TrafficLightController trafficLightController;
    private final Random random = new Random();
    private final List<Integer> availableStartPositions;
    private int rb = 0;

    public CarController2(List<Car> cars, TilePanel[][] grid, TrafficLightController trafficLightController) {
        this.cars = cars;
        this.grid = grid;
        this.carImage = cars.get(0).getImage();
        this.carImage2 = ImageLoader.rotateImage(carImage, 180);
        this.trafficLightController = trafficLightController;
        this.availableStartPositions = new ArrayList<>();
        for (int i = 0; i < grid[0].length; i++) { // Initialisez la liste pour l'axe Y
            availableStartPositions.add(i);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    moveCars();
                    createNewCarIfPossible();
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void moveCars() {
        List<Car> carsToRemove = new ArrayList<>();
        for (Car car : cars) {
            int oldX = car.getX();
            int oldY = car.getY();
            int nextX = oldX; // Initialiser nextX à la position actuelle
            int nextY = oldY; // Initialiser nextY à la position actuelle

            // Déterminer la prochaine position basée sur la direction
            switch (car.getDirection()) {
                case 0: // Droite
                    nextY += 1;
                    break;
                case 1: // Haut
                    nextX -= 1;
                    break;
                case 2: // Gauche
                    nextY -= 1;
                    break;
                case 3: // Bas
                    nextX += 1;
                    break;
            }

            // Vérifier si la voiture est devant le feu et si le feu est rouge
            boolean atRedLight = atRedLight(car, nextX, nextY);

            // Si la voiture n'est pas à un feu rouge et la prochaine position est libre, alors bouger la voiture
            if (!atRedLight && isPositionFree(nextX, nextY)) {
                car.move();
                updateGridWithNewCarPosition(oldX, oldY, car.getX(), car.getY(), car);
            }

            // Vérifier si la voiture a quitté la grille et la supprimer si nécessaire
            if (car.getX() < 0 || car.getX() >= grid.length || car.getY() < 0 || car.getY() >= grid[0].length) {
                carsToRemove.add(car);
                // Réintégrer la position de départ dans availableStartPositions
                availableStartPositions.add(car.getY());
            }
        }

        // Enfin, supprimer les voitures de la liste principale des voitures
        cars.removeAll(carsToRemove);
    }

    private void updateGridWithNewCarPosition(int oldX, int oldY, int newX, int newY, Car car) {
        SwingUtilities.invokeLater(() -> {
            if (oldX >= 0 && oldX < grid.length && oldY >= 0 && oldY < grid[0].length) {
                grid[oldX][oldY].setCar(null);
                grid[oldX][oldY].repaint();
            }
            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length) {
                grid[newX][newY].setCar(car);
                grid[newX][newY].repaint();
            }
        });
    }

    private synchronized void createNewCarIfPossible() {
        rb++;
        if (random.nextBoolean() && !availableStartPositions.isEmpty()) {
            int direction = random.nextBoolean() ? 1 : 3; // 1 pour bas à haut, 3 pour haut à bas
            int dirRoad = 0;
            for(Car car: cars){
                if(car.getDirection() == direction) dirRoad ++;
            }
            if(dirRoad <= 2 && rb % 5 == 0){
                int startPositionIndex = random.nextInt(availableStartPositions.size());
                int startY = availableStartPositions.get(startPositionIndex);

                if (isPositionFree(direction == 1 ? grid.length - 1 : 0, startY)) {
                    Car newCar = createNewCar(direction);
                    cars.add(newCar);
                    updateGridWithNewCarPosition(-1, -1, newCar.getX(), newCar.getY(), newCar);
                    availableStartPositions.remove(Integer.valueOf(startY)); // Retirer la position pour ne pas créer une autre voiture au même endroit
                }
            }
        }
    }

    private boolean atRedLight(Car car, int nextX, int nextY) {
        // Récupérer les positions des feux de circulation
        int yFeu0 = trafficLightController.getTrafficLight1().getY() + 1;
        int xFeu1 = trafficLightController.getTrafficLight2().getX() - 1;
        int yFeu2 = trafficLightController.getTrafficLight3().getY() - 1;
        int xFeu3 = trafficLightController.getTrafficLight4().getX() + 1;

        // Vérifier si la voiture est juste devant un feu rouge
        if ((car.getDirection() == 0 && nextY == yFeu0 && !trafficLightController.getTrafficLight1().isGreen()) || (car.getDirection() == 1 && nextX == xFeu1 && !trafficLightController.getTrafficLight2().isGreen()) || (car.getDirection() == 2 && nextY == yFeu2 && !trafficLightController.getTrafficLight3().isGreen()) || (car.getDirection() == 3 && nextX == xFeu3 && !trafficLightController.getTrafficLight4().isGreen())) {
            return true; // La voiture est devant un feu rouge
        }

        return false; // La voiture peut continuer à avancer
    }

    private boolean isPositionFree(int x, int y) {
        return cars.stream().noneMatch(c -> c.getX() == x && c.getY() == y);
    }

    private Car createNewCar(int direction) {
        int startX = (direction == 1) ? 9 : 0; // La position de départ change en fonction de la direction
        int startY = (direction == 3) ? 4 : 5;
        BufferedImage image = direction == 1 ? carImage : carImage2;
        return new Car(startX, startY, direction, image);
    }
}