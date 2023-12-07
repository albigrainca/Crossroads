package fr.uha.ensisa.crossroad.app;

import java.awt.image.BufferedImage;

public class TrafficLight {
    private final int x;
    private final int y;
    private final BufferedImage redLightImage;
    private final BufferedImage greenLightImage;
    private boolean isGreen;

    public TrafficLight(int x, int y, BufferedImage redLightImage, BufferedImage greenLightImage) {
        this.x = x;
        this.y = y;
        this.redLightImage = redLightImage;
        this.greenLightImage = greenLightImage;
        this.isGreen = false;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public void setGreen(boolean isGreen) {
        this.isGreen = isGreen;
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


