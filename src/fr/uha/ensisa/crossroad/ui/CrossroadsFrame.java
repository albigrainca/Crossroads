package fr.uha.ensisa.crossroad.ui;

import fr.uha.ensisa.crossroad.app.Car;
import fr.uha.ensisa.crossroad.threads.CarController;
import fr.uha.ensisa.crossroad.threads.TrafficLightController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class CrossroadsFrame extends JFrame {
    private static final int GRID_SIZE = 10;
    private TilePanel[][] grid = new TilePanel[GRID_SIZE][GRID_SIZE];
    private List<Car> listDesVoitures1;
    private List<Car> listDesVoitures2;
    private Semaphore semaphoreFeu1;
    private Semaphore semaphoreFeu2;
    private final int TIME_FEU = 5000; // Durée du feu en millisecondes
    private int[][] layout = {
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {3, 3, 3, 3, 6, 7, 3, 3, 3, 3},
            {5, 5, 5, 5, 9, 8, 5, 5, 5, 5},
            {1, 1, 1, 10, 2, 4, 10, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 4, 1, 1, 1, 1},
    };

    public CrossroadsFrame() {
        setTitle("Crossroads Simulation");
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        initializeGrid();
        initializeCars();
        semaphoreFeu1 = new Semaphore(0); // Feu 1 initialement vert
        semaphoreFeu2 = new Semaphore(1); // Feu 2 initialement rouge

        // Créer et démarrer les threads
        TrafficLightController lightController = new TrafficLightController(semaphoreFeu1, semaphoreFeu2, TIME_FEU);
        lightController.start();

        CarController carControllerFeu1 = new CarController(listDesVoitures1, semaphoreFeu1, grid);
        carControllerFeu1.start();

        CarController carControllerFeu2 = new CarController(listDesVoitures2, semaphoreFeu2, grid);
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
        listDesVoitures1 = new ArrayList<>();
        listDesVoitures2 = new ArrayList<>();
        int directionGauche = 0;
        int directionHaut = 1;
        BufferedImage carImage = ImageLoader.resizeImage(ImageLoader.loadImage("car.png"), 40, 40);
        BufferedImage rotateCarImage = ImageLoader.rotateImage(carImage, 90);
        listDesVoitures1.add(new Car(5, 0, directionGauche, rotateCarImage));
        listDesVoitures2.add(new Car(9, 5, directionHaut, carImage ));
    }
}
