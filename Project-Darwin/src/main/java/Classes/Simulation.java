package Classes;

import AbstractClasses.AbstractAnimal;
import Interfaces.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulation implements Runnable {
    final private WorldMap map;
    private List<Animal> animalList = new ArrayList<>();
    private int animalCount;
    private int grassCount;
    /// private int freeFieldCount
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

    public Simulation(int height, int width, int numberOfAnimals, int numberOfGrass, int numberOfGenes,
                      int defaultEnergyLevel, int defaultAge, int paceOfAging, int grassEnergyValue,
                      int grassDailyGrowth, int minEnergyToMate, int energyUsedToMate, boolean spawnOwlBear,
                      boolean ageIsAHeavyBurden) { // numberOfGenes == N
                        /// min i max liczba mutacji
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

        // placing animals
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2D position = this.map.getRandomPosition();
            Animal animal = new Animal(position, numberOfGenes, defaultEnergyLevel, defaultAge, paceOfAging);
            map.place(animal, position);
            animalList.add(animal);
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
        // moving animals
        List<Animal> animals = this.animalList;
        int numberOfAnimals = animals.size();
        for(int i = 0; i < numberOfAnimals; i++) {
            Animal animal = animals.get(i);
            Vector2D position = animal.getPosition();
            if(this.ageIsAHeavyBurden) {
                if(animal.canMove()) { // if true, canMove() changes the position of the animal
                    map.move(animal, position, animal.getPosition()); // (animal, oldPosition, newPosition)
                }
            }
            else {
                animal.move();
                map.move(animal, position, animal.getPosition());
            }
        }
        // owlBear part
        if (this.isOwlBearPresent) {
            // moving owlBear
            EarthWithOwlBear earth = (EarthWithOwlBear) map;
            OwlBear owlBear = earth.getOwlBear();
            owlBear.move();
            earth.setOwlBear(owlBear);
            // eating animals
            Vector2D owlBearPosition = owlBear.getPosition();
            Map<Vector2D, ArrayList<AbstractAnimal>> animalMap = earth.getAnimalMap();
            ArrayList<AbstractAnimal> animalsListOnPosition = animalMap.get(owlBearPosition);
            int animalsListOnPositionNumber = animalsListOnPosition.size();
            for(int i = 0; i < animalsListOnPositionNumber; i++) {
                Animal deadAnimal = (Animal) animalsListOnPosition.get(i);
                /// trzeba ustawic date zgonu, przydalaby sie lista zgonow
                killAnimal() // usuwa z mapy, usuwa z listy zwierzat, dodaje do listy zmarlych zwierzat itd
            }


            earth.setAnimalMap(anmialMap);
        }

    }

}
