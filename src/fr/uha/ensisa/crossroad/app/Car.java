package fr.uha.ensisa.crossroad.app;

import java.awt.image.BufferedImage;

public class Car {
    private final BufferedImage image;
    private final int direction; // 0: vers la droite, 1: vers le haut, 2: vers la gauche, 3: vers le bas
    private int x;
    private int y;
    private boolean hasCrossedLight;

    public Car(int x, int y, int direction, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.image = image;
        this.hasCrossedLight = false;
    }

    // Cette méthode doit être appelée lorsque la voiture passe le feu
    public void crossTrafficLight() {
        this.hasCrossedLight = true;
    }

    public void move() {
        switch (direction) {
            case 0:
                this.y += 1;
                break; // Se déplace vers la droite
            case 1:
                this.x -= 1;
                break; // Se déplace vers le haut
            case 2:
                this.y -= 1;
                break; // se déplace vers la gauche
            case 3:
                this.x += 1;
                break; // se déplace vers le bas
        }
    }

    public boolean hasCrossedLight() {
        return hasCrossedLight;
    }

    public int getDirection() {
        return direction;
    }

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

