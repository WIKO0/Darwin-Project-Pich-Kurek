package Classes;

import EnumClasses.MoveDirections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void testConstructorAnimal1() {
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;

        Animal animal = new Animal(position,size,energy,age,pace);
        assertEquals(animal.getPosition(),position);
        assertEquals(animal.getGenes().getSize(),size);
        assertEquals(animal.getEnergy(),energy);
        assertEquals(animal.getAge(),age);
    }

    @Test
    void testConstructorAnimal2() {
        Vector2D position = new Vector2D(0,0);
        int energy = 200;
        int pace = 5;
        Genes genes = new Genes(10);
        Animal animal = new Animal(position,energy,genes,pace);
        assertEquals(animal.getPosition(),position);
        assertEquals(animal.getEnergy(),energy);
        assertEquals(animal.getGenes(),genes);
        assertEquals(animal.getAge(),0);
    }

    @Test
    void testIncrementChild() {
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;
        Animal animal = new Animal(position,size,energy,age,pace);

        assertEquals(0,animal.getChildren());
        animal.incrementChild();
        assertEquals(1,animal.getChildren());
    }

    @Test
    void testSetDirection(){
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;
        Animal animal = new Animal(position,size,energy,age,pace);
        MoveDirections dir = MoveDirections.RIGHT;
        animal.setDirection(dir);

        assertEquals(animal.getDirection(),dir);

        MoveDirections newDir =MoveDirections.LEFT;
        animal.setDirection(newDir);

        assertEquals(animal.getDirection(),newDir);

    }

    @Test
    void testAnimalAging(){
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;
        Animal animal = new Animal(position,size,energy,age,pace);

        for(int i = 0; i < 20; i ++){
            animal.incrementNumberOfMoves();
        }

        assertEquals(animal.getAge(),age + 4);
    }

    @Test
    void testIncrementEnergyLess(){
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;
        Animal animal = new Animal(position,size,energy,age,pace);

        animal.setEnergy(100);
        animal.incrementEnergy(50);
        assertEquals(animal.getEnergy(),150);
    }

    @Test
    void testIncrementEnergyEqual(){
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;
        Animal animal = new Animal(position,size,energy,age,pace);

        animal.setEnergy(100);
        animal.incrementEnergy(100);
        assertEquals(animal.getEnergy(),200);
    }

    @Test
    void testIncrementEnergyMore(){
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        int energy = 200;
        int age = 10;
        int pace = 5;
        Animal animal = new Animal(position,size,energy,age,pace);

        animal.setEnergy(100);
        animal.incrementEnergy(170);
        assertEquals(animal.getEnergy(),200);
    }

    @Test
    void testCanMate(){
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 10;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);

        Vector2D position2 = new Vector2D(0,0);
        int size2 = 10;
        int energy2 = 200;
        int age2 = 10;
        int pace2 = 5;

        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        animal2.setEnergy(50);

        assertTrue(animal1.canMate());
        assertFalse(animal2.canMate());
    }

    @Test
    void testDominates1(){
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 10;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);

        Vector2D position2 = new Vector2D(0,0);
        int size2 = 10;
        int energy2 = 100;
        int age2 = 10;
        int pace2 = 5;

        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        assertTrue(animal1.dominates(animal2));
        assertFalse(animal2.dominates(animal1));
    }

    @Test
    void testDominates2(){
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 10;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);

        Vector2D position2 = new Vector2D(0,0);
        int size2 = 10;
        int energy2 = 200;
        int age2 = 8;
        int pace2 = 5;

        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        assertTrue(animal1.dominates(animal2));
        assertFalse(animal2.dominates(animal1));
    }

    @Test
    void testDominates3(){
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 10;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);
        animal1.incrementChild();

        Vector2D position2 = new Vector2D(0,0);
        int size2 = 10;
        int energy2 = 200;
        int age2 = 10;
        int pace2 = 5;

        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        assertTrue(animal1.dominates(animal2));
        assertFalse(animal2.dominates(animal1));
    }

    @Test
    void testCopulate1(){
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 9;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);


        Vector2D position2 = new Vector2D(0,0);
        int size2 = 9;
        int energy2 = 100;
        int age2 = 10;
        int pace2 = 5;
        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        Animal kid = animal1.copulate(animal2);


    }



}