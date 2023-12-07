package fr.uha.ensisa.crossroad.tests;

import fr.uha.ensisa.crossroad.app.TrafficLight;
import fr.uha.ensisa.crossroad.ui.ImageLoader;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {

    BufferedImage greenLight = ImageLoader.resizeImage(ImageLoader.loadImage("green.jpg"), 56, 56);
    BufferedImage redLight = ImageLoader.resizeImage(ImageLoader.loadImage("red.jpg"), 56, 56);

    @Test
    void testTrafficLightChange() {
        TrafficLight trafficLight = new TrafficLight(6, 3, redLight, greenLight);
        trafficLight.setGreen(true);
        assertTrue(trafficLight.isGreen(), "Le feu doit être vert");
        trafficLight.setGreen(false);
        assertFalse(trafficLight.isGreen(), "Le feu doit être rouge");
    }

    @Test
    void testTrafficLightPosition() {
        TrafficLight trafficLight = new TrafficLight(6, 3, redLight, greenLight);
        assertEquals(6, trafficLight.getX(), "La position X doit être 6");
        assertEquals(3, trafficLight.getY(), "La position Y doit être 3");
    }

    @Test
    void testTrafficLightImages() {
        TrafficLight trafficLight = new TrafficLight(6, 3, redLight, greenLight);
        trafficLight.setGreen(true);
        assertSame(greenLight, trafficLight.getCurrentImage(), "L'image doit être celle du feu vert");

        trafficLight.setGreen(false);
        assertSame(redLight, trafficLight.getCurrentImage(), "L'image doit être celle du feu rouge");
    }

    @Test
    void testImageLoading() {
        assertNotNull(greenLight, "L'image du feu vert ne doit pas être nulle");
        assertNotNull(redLight, "L'image du feu rouge ne doit pas être nulle");
    }
}
