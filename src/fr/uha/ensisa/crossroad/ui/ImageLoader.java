package fr.uha.ensisa.crossroad.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {

    public static BufferedImage loadImage(String fileName) {
        try {
            URL resource = ImageLoader.class.getResource("/" + fileName);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + fileName);
            }
            return ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Créer une nouvelle image qui est suffisamment grande pour contenir l'image pivotée
        // Pour simplifier, on utilise la même taille, mais pour des rotations non orthogonales (autres que 90, 180, 270),
        // il faudrait calculer une nouvelle taille.
        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Appliquer la transformation de rotation
        AffineTransform at = new AffineTransform();
        at.translate(width / 2.0, height / 2.0); // Déplace le centre de rotation au centre de l'image
        at.rotate(Math.toRadians(degrees)); // Rotation de l'image
        at.translate(-width / 2.0, -height / 2.0); // Remettre le centre de l'image à sa position originale
        g2d.setTransform(at);

        // Dessiner l'image originale sur la nouvelle image (qui est pivotée)
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();

        return resizedImage;
    }


}

