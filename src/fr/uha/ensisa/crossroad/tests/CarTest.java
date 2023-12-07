package fr.uha.ensisa.crossroad.tests;

import fr.uha.ensisa.crossroad.app.Car;
import fr.uha.ensisa.crossroad.ui.ImageLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;
    private List<Car> carList;

    @BeforeEach
    void setUp() {
        BufferedImage carImage = ImageLoader.resizeImage(ImageLoader.loadImage("car.png"), 50, 50);
        car = new Car(0, 0, 0, carImage);
        carList = new ArrayList<>();
    }

    @Test
    void testCarCreation() {
        assertNotNull(car, "La voiture doit être créée");
        assertNotNull(car.getImage(), "L'image de la voiture ne doit pas être nulle");
    }

    @Test
    void testCarMovement() {
        int initialX = car.getX();
        int initialY = car.getY();
        car.move();
        assertTrue(car.getX() != initialX || car.getY() != initialY, "La voiture doit se déplacer");
    }

    @Test
    void testCarDirection() {
        assertEquals(0, car.getDirection(), "La direction de la voiture doit être correcte");
    }

    @Test
    void testCrossTrafficLight() {
        assertFalse(car.hasCrossedLight(), "La voiture ne doit pas avoir franchi le feu initialement");
        car.crossTrafficLight();
        assertTrue(car.hasCrossedLight(), "La voiture doit avoir franchi le feu après l'appel de crossTrafficLight()");
    }

    @Test
    void testCarAdditionAndRemovalFromList() {
        carList.add(car);
        assertEquals(1, carList.size(), "La liste de voitures doit contenir la voiture ajoutée");

        carList.remove(car);
        assertEquals(0, carList.size(), "La liste de voitures doit être vide après la suppression de la voiture");
    }
}
