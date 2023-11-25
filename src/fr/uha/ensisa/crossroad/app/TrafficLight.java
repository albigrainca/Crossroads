package fr.uha.ensisa.crossroad.app;

public class TrafficLight {
    private boolean isGreen;

    public TrafficLight(boolean isGreen) {
        this.isGreen = isGreen;
    }

    public synchronized void changeLight() {
        this.isGreen = !this.isGreen;
    }

    public synchronized boolean isGreen() {
        return isGreen;
    }
}

