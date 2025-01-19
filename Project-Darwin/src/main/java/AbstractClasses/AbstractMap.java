package AbstractClasses;

import Classes.Animal;
import Classes.Grass;
import Classes.Vector2D;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;
import Interfaces.WorldMap;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;
import static java.util.concurrent.ThreadLocalRandom.current;

public abstract class AbstractMap implements WorldMap {

    Map<Vector2D, Grass> grasses = new HashMap<>();
    Map<Vector2D, ArrayList<AbstractAnimal>> animals = new HashMap<>();
    int mapHeight;
    int mapWidth;

    public AbstractMap(int mapHeight, int mapWidth) {
        if(mapHeight > 5 && mapWidth > 5) {
            this.mapHeight = mapHeight;
            this.mapWidth = mapWidth;
        }
        else {
            this.mapHeight = 10;
            this.mapWidth = 10;
        }
    }

    @Override
    public void place(MapElement element, Vector2D position) {
        int x = position.getX();
        int y = position.getY();
        int width = this.mapWidth;
        int height = this.mapHeight;
        if (isInBoundaries(position)) {
            if (element instanceof AbstractAnimal) { // element is an animal
                ArrayList<AbstractAnimal> animalsList = animals.get(position);
                AbstractAnimal animal = (AbstractAnimal) element;
                animalsList.add(animal);
                animals.put(position, animalsList);
            }
            else { // element is a grass
                Grass grass = (Grass) element;
                grasses.put(position, grass);
            }
        }

    }


    public void placeAnimals(int numberOfAnimals, int N, int energy, int age, int paceOfAging) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2D position = new Vector2D(randomX(), randomY());
            Animal animal =  new Animal(position, N, energy, age, paceOfAging);
            place(animal, position);
        }
    }


    @Override
    public void move(MapElement element, Vector2D position, Vector2D destination) {
        int x = destination.getX();
        int y = destination.getY();
        if (isInBoundaries(destination)) {
            if (element instanceof AbstractAnimal) { // element is an animal
                ArrayList<AbstractAnimal> animalsListOnPosition = animals.get(position);
                ArrayList<AbstractAnimal> animalsListOnDestination = animals.get(destination);
                AbstractAnimal animal = (AbstractAnimal) element;
                animalsListOnPosition.remove(animal);
                animalsListOnDestination.add(animal);
                animals.put(destination, animalsListOnDestination);
            }
            else { // element is a grass
                // grass should not be moved
            /*
            Grass grass = (Grass) element;
            grasses.put(destination, grass);
             */
            }
        }
    }

    @Override
    public boolean isInBoundaries(Vector2D position) {
        int x = position.getX();
        int y = position.getY();
        if (x >= 0 && x < this.mapWidth && y >= 0 && y < this.mapHeight ) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isUpperOrLowerBoundary(Vector2D position) {
        int y = position.getY();
        if (y == 0 || y + 1 == this.mapHeight) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isLeftOrRightBoundary(Vector2D position) {
        int x = position.getX();
        if (x == 0 || x + 1 == this.mapWidth) {
            return true;
        }
        else {
            return false;
        }
    }

    private int randomX() {
        return ThreadLocalRandom.current().nextInt(0, this.mapWidth);
    }

    private int randomY() {
        return ThreadLocalRandom.current().nextInt(0, this.mapHeight);
    }

    @Override
    public boolean isGrassOn (Vector2D position) {
        return grasses.containsKey(position);
    }

    @Override
    public int NumberOfAnimalsAt(Vector2D position) {
        ArrayList<AbstractAnimal> animalsList = animals.get(position);
        return animalsList.size();
    }
}


