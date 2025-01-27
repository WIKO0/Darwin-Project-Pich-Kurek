package AbstractClasses;

import Classes.Animal;
import Classes.Earth;
import Classes.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractMapTest {

    @Test
    void movingAnAnimal() {
        Vector2D position = new Vector2D(1,1);
        Vector2D destination = new Vector2D(2,2);
        Earth map = new Earth(5,5);

        Animal animal = new Animal(position,3,10,1,1,1,3,4,1);
        map.move(animal, position, destination);
        assertEquals(animal.getPosition(), destination);
    }
}