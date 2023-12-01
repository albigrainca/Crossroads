package fr.uha.ensisa.crossroad.app;

import java.awt.image.BufferedImage;

public class TrafficLight {
    private boolean isGreen;
    private int x;
    private int y;
    private BufferedImage redLightImage;
    private BufferedImage greenLightImage;

    public TrafficLight(int x, int y, BufferedImage redLightImage, BufferedImage greenLightImage) {
        this.x = x;
        this.y = y;
        this.redLightImage = redLightImage;
        this.greenLightImage = greenLightImage;
        this.isGreen = false;
    }

    public void setGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getCurrentImage() {
        return isGreen ? greenLightImage : redLightImage;
    }
}


