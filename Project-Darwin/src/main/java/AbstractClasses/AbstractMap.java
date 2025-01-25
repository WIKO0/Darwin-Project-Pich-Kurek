package AbstractClasses;

import Classes.Animal;
import Classes.Genes;
import Classes.Grass;
import Classes.Vector2D;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;
import Interfaces.WorldMap;
import util.MapVisualizer;

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


    //Clicked Animal Stats
    private Animal chosenOne;
    private Genes chosenGenes;
    private int chosenCurrentGene;
    private int chosenEnergy;
    private int chosenGrassConsumed;
    private int chosenDescendantNumber;
    private int chosenAge;
    private int chosenDeathDay = -1;

    private boolean isChosenDead = false;

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
                if (animalsList == null) {
                    animalsList = new ArrayList<>();
                }
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

    public boolean isJungle(Vector2D position) {
        if (position.getY() >= this.jungleLowerBorder && position.getY() <= this.jungleUpperBorder) {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public void move(MapElement element, Vector2D position, Vector2D destination) {
        int x = destination.getX();
        int y = destination.getY();
        if (isInBoundaries(destination)) {
            if (element instanceof AbstractAnimal) { // element is an animal
                ArrayList<AbstractAnimal> animalsListOnPosition = animals.get(position);
                if (animalsListOnPosition == null) {
                    animalsListOnPosition = new ArrayList<>();
                }
                ArrayList<AbstractAnimal> animalsListOnDestination = animals.get(destination);
                if (animalsListOnDestination == null) {
                    animalsListOnDestination = new ArrayList<>();
                }
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
        int y = ThreadLocalRandom.current().nextInt(this.jungleLowerBorder, this.jungleUpperBorder + 1);
        return new Vector2D(x,y);
    }

    @Override
    public Vector2D getRandomGrassPosition() {
        int random = ThreadLocalRandom.current().nextInt(0, 10);
        if (random > 7) {
            if (random == 8) {
                Vector2D position = getRandomLowerSteppePosition();
                int attempts = 0;
                while (isGrassOn(position) && attempts < 10) {
                    position = getRandomLowerSteppePosition();
                    attempts++;
                }
                return position;
            }
            else { // random == 9
                Vector2D position = getRandomUpperSteppePosition();
                int attempts = 0;
                while (isGrassOn(position) && attempts < 10) {
                    position = getRandomUpperSteppePosition();
                    attempts++;
                }
                return position;
            }
        }
        else {
            Vector2D position = getRandomJunglePosition();
            int attempts = 0;
            while (isGrassOn(position) && attempts < 10) {
                position = getRandomJunglePosition();
                attempts++;
            }
            return position;
        }
    }

    @Override
    public boolean isGrassOn (Vector2D position) {
        return grasses.containsKey(position);
    }

    @Override
    public int NumberOfAnimalsAt(Vector2D position) {
        ArrayList<AbstractAnimal> animalsList = animals.get(position);
        if (animalsList == null) {
            return 0;
        }
        else {
            return animalsList.size();
        }
    }

    public Map<Vector2D, ArrayList<AbstractAnimal>> getAnimalMap() {
        return this.animals;
    }

    public void setAnimalMap(Map<Vector2D, ArrayList<AbstractAnimal>> map) {
        this.animals = map;
    }

    public Map<Vector2D, Grass> getGrassMap() {
        return this.grasses;
    }

    public void setGrassMap(Map<Vector2D, Grass> map) {
        this.grasses = map;
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

    public Vector2D getUpperRight() {
        return this.upperRight;
    }

    public Vector2D getLowerLeft() {
        return this.lowerLeft;
    }


    //staty dla zwierzola

    public void setChosenOne(Animal animal){
        this.chosenOne=animal;
    }

    public void setChosenGenes(){
        this.chosenGenes =  this.chosenOne.getGenes();
    }

    public void setChosenKids(){
        this.chosenDescendantNumber = this.chosenOne.getChildren();
    }

    public void setChosenEnergy(){
        this.chosenEnergy = this.chosenOne.getEnergy();
    }

    public void setChosenCurrentGene(){
        this.chosenCurrentGene = this.chosenOne.getGenes().getCurrentGene();
    }

    public void setChosenGrassConsumed(){
        this.chosenGrassConsumed = this.chosenOne.getGrassEaten();
    }

    public void setChosenAge(){
        this.chosenAge = this.chosenOne.getAge();
    }

    public void setChosenDeathDay(int age){
        this.chosenDeathDay = age;
    }

    // getery dla statow zwierzola

    public Animal getChosenOne(){
        return this.chosenOne;
    }

    public ArrayList<Integer> getChosenGenes(){
        return this.chosenGenes.getGenes();
    }

    public int getChosenDescendantNumber(){
        return this.chosenDescendantNumber;
    }

    public int getChosenEnergy(){
        return this.chosenEnergy;
    }

    public int getChosenCurrentGene(){
        return this.chosenCurrentGene;
    }

    public int getChosenGrassConsumed(){
        return this.chosenGrassConsumed;
    }

    public int getChosenAge(){
        return this.chosenAge;
    }

    public int getChosenDeathAge(){
        return this.chosenDeathDay;
    }

}


