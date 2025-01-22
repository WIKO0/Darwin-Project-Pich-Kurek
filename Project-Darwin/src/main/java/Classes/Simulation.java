package Classes;

import AbstractClasses.AbstractAnimal;
import Interfaces.SimulationChangeListener;
import Interfaces.WorldMap;

import java.util.*;

import static java.util.Arrays.sort;

public class Simulation implements Runnable {
    final private WorldMap map;
    private List<Animal> animalList = new ArrayList<>();
    private List<SimulationChangeListener> observers = new ArrayList<>();
    private int animalCount;
    private int grassCount;
    /// private int freeFieldCount == wszystkie pola - zajete pola (height*width - animalMap.size()) // zakladajac że wolne pole oznacza pole bez zwierzaka
    ///private Genes - lista najpopularniejszych genotypów
    /// liczby wolnych pól,
    /// najpopularniejszych genotypów,(top3???) - lub array z rankingiem
    /// średniego poziomu energii dla żyjących zwierzaków,
    /// średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    /// średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
    final private int numberOfGenes;
    final private int startingEnergyLevel;
    final private int startingAge;
    final private int paceOfAging;
    final private int grassEnergyValue;
    final private int grassDailyGrowth;
    final private int minEnergyToMate;
    final private int energyUsedToMate;
    final private boolean isOwlBearPresent;
    final private boolean ageIsAHeavyBurden; // important when moving an animal
    final private int minimalNumberOfMutations;
    final private int maximalNumberOfMutations;
    final private int gapTime;
    final private UUID uuid;

    public Simulation(int height, int width, int numberOfAnimals, int numberOfGrass, int numberOfGenes,
                      int defaultEnergyLevel, int defaultAge, int paceOfAging, int grassEnergyValue,
                      int grassDailyGrowth, int minEnergyToMate, int energyUsedToMate, int minMutations,
                      int maxMutations, int gapTime, boolean spawnOwlBear,
                      boolean ageIsAHeavyBurden) { // numberOfGenes == N
        this.uuid = UUID.randomUUID();

        // time between the iterations of the simulation [ms]
        if (gapTime < 1) {
            this.gapTime = 100;
        }
        else {
            this.gapTime = gapTime;
        }
        if (spawnOwlBear) { // map with owlBear
            this.map = new EarthWithOwlBear(height, width, numberOfGenes);
        }
        else { // map without owlBear
            this.map = new Earth(height, width);
        }

        // simulation parameters
        this.numberOfGenes = numberOfGenes;
        this.startingEnergyLevel = defaultEnergyLevel;
        this.startingAge = defaultAge;
        this.paceOfAging = paceOfAging;
        this.isOwlBearPresent = spawnOwlBear;
        this.grassEnergyValue = grassEnergyValue;
        this.grassDailyGrowth = grassDailyGrowth;
        this.minEnergyToMate = minEnergyToMate;
        this.energyUsedToMate = energyUsedToMate;
        this.ageIsAHeavyBurden = ageIsAHeavyBurden;
        this.minimalNumberOfMutations = minMutations;
        this.maximalNumberOfMutations = maxMutations;


        // placing animals
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2D position = this.map.getRandomPosition();
            Animal animal = new Animal(position, numberOfGenes, defaultEnergyLevel, defaultAge, paceOfAging);
            map.place(animal, position);
            animalList.add(animal);
            animal.setBorder(this.map.getUpperRight(),this.map.getLowerLeft());
        }
        this.animalCount = numberOfAnimals;


        // placing grass
        for (int i = 0; i < numberOfGrass; i++) {
            Vector2D position = this.map.getRandomGrassPosition();
            if (this.map.isGrassOn(position) == false) {
                Grass grass = new Grass(position);
                map.place(grass, position);
            }
            else {
                i--;
            }
        }
        this.grassCount = numberOfGrass;
    }

    public List<Animal> getAnimalList() {
        return this.animalList;
    }

    @Override
    public void run() {
        while(true) {
            //System.out.println("moving");
            // moving animals
            int numberOfAnimals = this.animalList.size();
            for(int i = 0; i < numberOfAnimals; i++) {
                Animal animal = this.animalList.get(i);
                Vector2D position = animal.getPosition();
                if(this.ageIsAHeavyBurden) {
                    if(animal.canMove()) { // if true, canMove() changes the position of the animal; always changes the energy
                        this.map.move(animal, position, animal.getPosition()); // (animal, oldPosition, newPosition)
                    }
                }
                else {
                    animal.move();
                    this.map.move(animal, position, animal.getPosition());
                    int energy = animal.getEnergy();
                    energy--;
                    animal.setEnergy(energy);
                }
            }

            //System.out.println("owlbear");
            // owlBear part
            if (this.isOwlBearPresent) {
                // moving owlBear
                EarthWithOwlBear earth = (EarthWithOwlBear) this.map;
                OwlBear owlBear = earth.getOwlBear();
                owlBear.move();
                earth.setOwlBear(owlBear);
                // eating animals
                Vector2D owlBearPosition = owlBear.getPosition();
                Map<Vector2D, ArrayList<AbstractAnimal>> animalMap = earth.getAnimalMap();
                ArrayList<AbstractAnimal> animalsListOnPosition = animalMap.get(owlBearPosition);
                int animalsListOnPositionNumber;
                if (animalsListOnPosition == null) {
                    animalsListOnPositionNumber = 0;
                }
                else {
                    animalsListOnPositionNumber = animalsListOnPosition.size();
                }
                earth.killAll(owlBearPosition);
                this.animalCount -= animalsListOnPositionNumber;
                for(int i = 0; i < animalsListOnPositionNumber; i++) {
                    Animal deadAnimal = (Animal) animalsListOnPosition.get(i);
                    this.animalList.remove(deadAnimal);
                }
                // not needed because of killAll: earth.setAnimalMap(animalMap);
            }

            //System.out.println("eating");
            // eating grass
            Map<Vector2D, Grass> grassMap = this.map.getGrassMap();
            Map<Vector2D, ArrayList<AbstractAnimal>> animalMap = this.map.getAnimalMap(); // used later too
            ArrayList<Vector2D> toDelete = new ArrayList<>();
            grassMap.forEach( (key, value) -> {
                ArrayList<AbstractAnimal> PositionAnimalList = animalMap.get(key);
                int animalNumber;
                if (PositionAnimalList == null) {
                    animalNumber = 0;
                }
                else {
                    animalNumber = PositionAnimalList.size();
                }
                if (animalNumber > 0) {
                    Animal animal1 = (Animal) PositionAnimalList.get(0);
                    Animal dominator = animal1;
                    for(int i = 1; i < animalNumber; i++) {
                        Animal animal2 = (Animal) PositionAnimalList.get(i);
                        if (animal2.dominates(animal1)) {
                            dominator = animal2;
                        }
                        animal1 = animal2;
                    }
                    dominator.incrementEnergy(this.grassEnergyValue); // does this work ???
                    toDelete.add(key);
                    grassCount--;
                    // this.map.setAnimalMap(animalMap); not needed??
                }
            });
            int numberOfDeletedGrasses = toDelete.size();
            for (int i = 0; i < numberOfDeletedGrasses; i++) {
                grassMap.remove(toDelete.get(i));
            }
            this.map.setGrassMap(grassMap);

            //System.out.println("mating");
            // mating
            CompareAnimals comparator = new CompareAnimals(); // also used in section: handling animals that starved to death
            animalMap.forEach( (key, value) -> {
                ArrayList<AbstractAnimal> positionAnimalList = animalMap.get(key);
                int animalNumber = positionAnimalList.size();
                if (animalNumber > 1) {
                    positionAnimalList.sort(comparator);
                    for(int i = 0; i < animalNumber; i += 2) {
                        if (animalNumber - i > 1) { // indicates if there is still a pair
                            Animal animal1 = (Animal) positionAnimalList.get(i);
                            Animal animal2 = (Animal) positionAnimalList.get(i+1);
                            if(animal1.canMate() && animal2.canMate()) {
                                Animal child = animal1.copulate(animal2);
                                positionAnimalList.add(child);
                                animalMap.put(key, positionAnimalList);
                                this.animalList.add(child);
                                animalCount++;
                                child.setBorder(this.map.getUpperRight(),this.map.getLowerLeft());
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
            });
            this.map.setAnimalMap(animalMap); // update map

            //System.out.println("growth");
            // grass growth
            for (int i = 0; i < grassDailyGrowth; i++) {
                Vector2D grassPosition = this.map.getRandomGrassPosition();
                if (!this.map.isGrassOn(grassPosition)) {
                    grassMap.put(grassPosition, new Grass(grassPosition));
                    grassCount++;
                }
            }
            this.map.setGrassMap(grassMap);

            //System.out.println("handling");
            // handling animals that starved to death
            animalMap.forEach( (key, value) -> {
                ArrayList<AbstractAnimal> positionAnimalList = animalMap.get(key);
                int animalNumber = positionAnimalList.size();
                positionAnimalList.sort(comparator);
                for(int i = animalNumber - 1; i >= 0; i--) {
                    Animal animal = (Animal) positionAnimalList.get(i);
                    if(animal.getEnergy() <= 0) {
                        positionAnimalList.remove(animal);
                        animalMap.put(key, positionAnimalList);
                        this.animalList.remove(animal);
                        animalCount--;
                    }
                    else {
                        break;
                    }
                }
            });
            this.map.setAnimalMap(animalMap); // update map

            // Break before next iteration
            simulationChanged("");

            try {
                Thread.sleep(this.gapTime);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

    public void registerObserver(SimulationChangeListener newObserver) {
        this.observers.add(newObserver);
    }

    public void deleteObserver(SimulationChangeListener observer) {
        this.observers.remove(observer);
    }

    public void simulationChanged(String message) {
        int observersSize = observers.size();
        for (int i = 0; i < observersSize; i++) {
            observers.get(i).simulationChanged(this, message);
        }
    }

    public UUID getID () {
        return this.uuid;
    }
}
