package AbstractClasses;

import Classes.Animal;
import Classes.Grass;
import Classes.Vector2D;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;
import Interfaces.WorldMap;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

    public void place(MapElement element, Vector2D position) {
        int x = position.getX();
        int y = position.getY();
        int width = this.mapWidth;
        int height = this.mapHeight;

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

    public void placeAnimals(int numberOfAnimals) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Animal animal =  new Animal();
            Vector2D position = new Vector2D(randomX(), randomY());
            place(animal, position);
        }
    }

    private int randomX() {
        return ThreadLocalRandom.current().nextInt(0, this.mapWidth);
    }

    private int randomY() {
        return ThreadLocalRandom.current().nextInt(0, this.mapHeight);
    }

    public boolean isGrassOn (Vector2D position) {
        return grasses.containsKey(position);
    }


}


