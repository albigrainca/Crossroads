package fr.uha.ensisa.crossroad.app;

import java.awt.image.BufferedImage;

public class Car {
    private int x;
    private int y;
    private BufferedImage image;
    private static int GRID_MAX = 9;
    private static int GRID_MIN = 0;
    private int direction; // 0: vers la gauche, 1: vers le haut

    public Car(int x, int y, int direction, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.image = image;
    }

    public void move() {
        switch (direction) {
            case 0: this.y += 1; break; // Se déplace vers la gauche
            case 1: this.x -= 1; break; // Se déplace vers le haut
        }

        if (this.y >= GRID_MAX) {
            this.y = 0;
        }

        if (this.x <= GRID_MIN) {
            this.x = 9;
        }
    }

    // Getters et setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage() {
        return image;
    }

}

