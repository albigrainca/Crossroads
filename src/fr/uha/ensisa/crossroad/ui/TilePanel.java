package fr.uha.ensisa.crossroad.ui;

import fr.uha.ensisa.crossroad.app.Car;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class TilePanel extends JPanel {
    private int type;
    private BufferedImage image;
    private Car car;

    public TilePanel(int type) {
        this.type = type;
        loadImage();
        setPreferredSize(new Dimension(50, 50)); // assuming each tile is 50x50 pixels
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private void loadImage() {
        switch (type) {
            case 1:
                image = ImageLoader.loadImage("ground.jpg");
                break;
            case 2:
                image = ImageLoader.loadImage("road.jpg");
                break;
            case 3:
                image = ImageLoader.rotateImage(ImageLoader.loadImage("road.jpg"), 90);
                break;
            case 4:
                image = ImageLoader.rotateImage(ImageLoader.loadImage("road.jpg"), 180);
                break;
            case 5:
                image = ImageLoader.rotateImage(ImageLoader.loadImage("road.jpg"), 270);
                break;
            case 6:
                image = ImageLoader.loadImage("road2.jpg");
                break;
            case 7:
                image = ImageLoader.rotateImage(ImageLoader.loadImage("road2.jpg"), 90);
                break;
            case 8:
                image = ImageLoader.rotateImage(ImageLoader.loadImage("road2.jpg"), 180);
                break;
            case 9:
                image = ImageLoader.rotateImage(ImageLoader.loadImage("road2.jpg"), 270);
                break;
            case 10:
                image = ImageLoader.loadImage("red.jpg");
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }

        if (car != null) {
            g.drawImage(car.getImage(), car.getX(), car.getY(), this);
        }
    }
}

