package fr.uha.ensisa.crossroad.ui;

import fr.uha.ensisa.crossroad.app.Car;
import fr.uha.ensisa.crossroad.app.TrafficLight;
import fr.uha.ensisa.crossroad.threads.CarController;
import fr.uha.ensisa.crossroad.threads.CarController2;
import fr.uha.ensisa.crossroad.threads.TrafficLightController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class CrossroadsFrame extends JFrame {
    private static final int GRID_SIZE = 10;
    private final TilePanel[][] grid = new TilePanel[GRID_SIZE][GRID_SIZE];
    private final int[][] layout = {
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {3, 3, 3, 3, 6, 7, 3, 3, 3, 3},
            {5, 5, 5, 5, 9, 8, 5, 5, 5, 5},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
    };
    private List<Car> cars1;
    private List<Car> cars2;
    private TrafficLight road1Light;
    private TrafficLight road2Light;
    private TrafficLight road3Light;
    private TrafficLight road4Light;

    public CrossroadsFrame() {
        setTitle("Crossroads Simulation");
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        initializeGrid();
        initializeCars();
        initializeTrafficsLight();
        grid[road1Light.getX()][road1Light.getY()].setLight1(road1Light);
        grid[road2Light.getX()][road2Light.getY()].setLight1(road2Light);
        grid[road3Light.getX()][road3Light.getY()].setLight1(road3Light);
        grid[road4Light.getX()][road4Light.getY()].setLight1(road4Light);
        Semaphore horizontalLight = new Semaphore(0); // Feux horizontales initialement vert
        Semaphore verticalLight = new Semaphore(1); // Feux verticales initialement rouge

        // Créer et démarrer les threads
        int TIME_FEU = 5000;
        TrafficLightController lightController = new TrafficLightController(horizontalLight, verticalLight, TIME_FEU, grid, road1Light, road2Light, road3Light, road4Light);
        lightController.start();

        CarController carControllerFeu1 = new CarController(cars1, grid, lightController);
        carControllerFeu1.start();

        CarController2 carControllerFeu2 = new CarController2(cars2, grid, lightController);
        carControllerFeu2.start();
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = new TilePanel(layout[i][j]);
                add(grid[i][j]);
            }
        }
    }

    private void initializeCars() {
        cars1 = new ArrayList<>();
        cars2 = new ArrayList<>();
        int directionGauche = 0;
        int directionHaut = 1;
        int directionDroite = 2;
        int directionBas = 3;
        BufferedImage carImage = ImageLoader.resizeImage(ImageLoader.loadImage("car.png"), 55, 55);
        BufferedImage rotateCarImage = ImageLoader.rotateImage(carImage, 90);
        BufferedImage rotateCarImage2 = ImageLoader.rotateImage(carImage, 270);
        BufferedImage rotateCarImage3 = ImageLoader.rotateImage(carImage, 180);
        cars1.add(new Car(5, 0, directionGauche, rotateCarImage));
        cars1.add(new Car(4, 9, directionDroite, rotateCarImage2));
        cars2.add(new Car(9, 5, directionHaut, carImage));
        cars2.add(new Car(0, 4, directionBas, rotateCarImage3));
    }

    private void initializeTrafficsLight() {
        BufferedImage greenLight = ImageLoader.resizeImage(ImageLoader.loadImage("green.jpg"), 56, 56);
        BufferedImage redLight = ImageLoader.resizeImage(ImageLoader.loadImage("red.jpg"), 56, 56);
        BufferedImage rotateGreenLight = ImageLoader.rotateImage(greenLight, 90);
        BufferedImage rotateRedLight = ImageLoader.rotateImage(redLight, 90);
        BufferedImage rotateGreenLight2 = ImageLoader.rotateImage(greenLight, 270);
        BufferedImage rotateRedLight2 = ImageLoader.rotateImage(redLight, 270);
        BufferedImage rotateGreenLight3 = ImageLoader.rotateImage(greenLight, 180);
        BufferedImage rotateRedLight3 = ImageLoader.rotateImage(redLight, 180);

        road1Light = new TrafficLight(6, 3, rotateRedLight, rotateGreenLight);
        road2Light = new TrafficLight(6, 6, redLight, greenLight);
        road3Light = new TrafficLight(3, 6, rotateRedLight2, rotateGreenLight2);
        road4Light = new TrafficLight(3, 3, rotateRedLight3, rotateGreenLight3);
        road2Light.setGreen(true);
        road4Light.setGreen(true);
    }
}
