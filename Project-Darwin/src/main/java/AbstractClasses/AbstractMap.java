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
    protected Map<Vector2D, Grass> grasses = new HashMap<>();
    protected Map<Vector2D, ArrayList<AbstractAnimal>> animals = new HashMap<>();
    protected final int mapHeight;
    protected final int mapWidth;
    protected final Vector2D lowerLeft;
    protected final Vector2D upperRight;
    protected final int jungleLowerBorder; // borders belong to jungle
    protected final int jungleUpperBorder;

    public AbstractMap(int mapHeight, int mapWidth) {
        if(mapHeight > 5 && mapWidth > 5) {
            this.mapHeight = mapHeight;
            this.mapWidth = mapWidth;
        }
        else {
            this.mapHeight = 10;
            this.mapWidth = 10;
        }
        this.lowerLeft = new Vector2D(0,0);
        this.upperRight = new Vector2D(this.mapWidth - 1, this.mapHeight - 1);


        // designating jungle borders
        int jungleHeight = (int) Math.floor((0.2 * (double) mapHeight));
        int steppeHeight = (int) Math.floor((0.4 * (double) mapHeight));

        while (jungleHeight + (2 * steppeHeight) < this.mapHeight) {
            jungleHeight++;
        }
        while (jungleHeight + (2 * steppeHeight) > this.mapHeight) {
            jungleHeight--;
        }
        this.jungleLowerBorder = steppeHeight;
        this.jungleUpperBorder = steppeHeight + jungleHeight - 1;
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

    /*
    public void placeAnimals(int numberOfAnimals, int N, int energy, int age, int paceOfAging) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2D position = new Vector2D(randomX(), randomY());
            Animal animal =  new Animal(position, N, energy, age, paceOfAging);
            place(animal, position);
        }
    }
    */


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
    public Vector2D getRandomPosition() {
        return new Vector2D(randomX(), randomY());
    }

    private Vector2D getRandomUpperSteppePosition() {
        int x = randomX();
        int y = ThreadLocalRandom.current().nextInt(this.jungleUpperBorder + 1, this.mapHeight);
        return new Vector2D(x,y);
    }

    private Vector2D getRandomLowerSteppePosition() {
        int x = randomX();
        int y = ThreadLocalRandom.current().nextInt(0, this.jungleLowerBorder);
        return new Vector2D(x,y);
    }

    private Vector2D getRandomJunglePosition() {
        int x = randomX();
        int y = ThreadLocalRandom.current().nextInt(this.jungleLowerBorder, this.jungleUpperBorder);
        return new Vector2D(x,y);
    }

    @Override
    public Vector2D getRandomGrassPosition() {
        int random = ThreadLocalRandom.current().nextInt(0, 10);
        if (random > 7) {
            if (random == 8) {
                return getRandomLowerSteppePosition();
            }
            else { // random == 9
                return getRandomUpperSteppePosition();
            }
        }
        else {
            return getRandomJunglePosition();
        }
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

    public Map<Vector2D, ArrayList<AbstractAnimal>> getAnimalMap() {
        return this.animals;
    }

    public void setAnimalMap(Map<Vector2D, ArrayList<AbstractAnimal>> map) {
        this.animals = map;
    }
}


