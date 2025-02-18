package Classes;

import AbstractClasses.AbstractAnimal;
import Interfaces.SimulationChangeListener;
import Interfaces.WorldMap;
import javafx.application.Platform;

import java.util.*;

import static java.util.Arrays.sort;

public class Simulation implements Runnable {
    private WorldMap map;
    private List<Animal> animalList = new ArrayList<>();
    private List<SimulationChangeListener> observers = new ArrayList<>();
    private HashMap<Genes, Integer> genesRanking = new HashMap<Genes, Integer>();
    private int animalCount;
    private int grassCount;
    private int countDead;
    private int countDeadAge;
    private int unOccupied;
    private int mostPopularGenome;
    private int totalKids;
    private int[] popularGenomes;
    private int numberOfGenome;
    private int EnergyOfLiving;

    private boolean flag;

    //DNI ktorych jeszcze nie znamy
    private int Days;

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
    private boolean territoryFlag;
    private boolean jungleFlag;
    private boolean highlightFlag;

    public Simulation(int height, int width, int numberOfAnimals, int numberOfGrass, int numberOfGenes,
                      int defaultEnergyLevel, int defaultAge, int paceOfAging, int grassEnergyValue,
                      int grassDailyGrowth, int minEnergyToMate, int energyUsedToMate, int minMutations,
                      int maxMutations, int gapTime, boolean spawnOwlBear,
                      boolean ageIsAHeavyBurden) { // numberOfGenes == N
        this.uuid = UUID.randomUUID();

        this.territoryFlag = false;
        this.jungleFlag = false;
        this.highlightFlag = false;

        // time between the iterations of the simulation [ms]
        if (gapTime < 1) {
            this.gapTime = 100;
        }
        else {
            this.gapTime = gapTime;
        }
        this.map = spawnOwlBear ? new EarthWithOwlBear(height, width, numberOfGenes) : new Earth(height, width);
        if (this.map == null) {
            throw new IllegalStateException("WorldMap is not initialized correctly.");
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

        this.countDead = 0;
        this.countDeadAge = 0;
        this.unOccupied = 0;
        this.mostPopularGenome = 0;
        this.totalKids = 0;
        this.popularGenomes = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        this.numberOfGenome = 0;
        this.EnergyOfLiving = 0;
        this.flag = true;
        this.Days = 0;

        // placing animals
        for (int i = 0; i < numberOfAnimals; i++) {
            //System.out.println("kladzenie");
            Vector2D position = this.map.getRandomPosition();
            Animal animal = new Animal(position, numberOfGenes, defaultEnergyLevel, defaultAge, paceOfAging,minMutations, maxMutations, minEnergyToMate,energyUsedToMate);
            map.place(animal, position);
            animalList.add(animal);
            addToGenesRanking(animal);
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
        while(true){
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(flag) {
                ArrayList<Vector2D> keysToDelete = new ArrayList<>(); // when no one stands on the field
                this.Days ++;
                this.EnergyOfLiving = 0;
                this.popularGenomes = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
                this.totalKids = 0;
                this.numberOfGenome = 0;
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
                    this.totalKids += animal.getChildren();
                    animal.getGenes().updateMostPopularGenome(this.popularGenomes,1);
                    this.EnergyOfLiving += animal.getEnergy();
                }


//            System.out.println("zmiany w genach");
                for(int i = 0; i < this.popularGenomes.length; i++) {
                    if(this.numberOfGenome < this.popularGenomes[i]) {
                        this.mostPopularGenome = i;
                        this.numberOfGenome=this.popularGenomes[i];
                    }
                }

//            System.out.println("owlbear");
                // owlBear part
                if (this.isOwlBearPresent) {
                    // moving owlBear
                    EarthWithOwlBear earth = (EarthWithOwlBear) this.map;
//                System.out.println(earth.getOwlBear().getPosition());
                    OwlBear owlBear = earth.getOwlBear();
                    owlBear.move();
                    earth.setOwlBear(owlBear);
//                System.out.println(earth.getOwlBear().getPosition());
//                    System.out.println(owlBear.getGenes().getGenes());
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
                    keysToDelete.add(owlBearPosition);
                    earth.killAll(owlBearPosition);

                    this.animalCount -= animalsListOnPositionNumber;
                    for(int i = 0; i < animalsListOnPositionNumber; i++) {
                        Animal deadAnimal = (Animal) animalsListOnPosition.get(i);
                        removeFromGenesRanking(deadAnimal);
                        this.countDeadAge += deadAnimal.getAge();
                        this.animalList.remove(deadAnimal);
                        this.countDead ++;

                    }

                    // not needed because of killAll: earth.setAnimalMap(animalMap);
                }

//            System.out.println("eating");
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
                        dominator.incrementGrassEaten();

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

//            System.out.println("mating");
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
                                    addToGenesRanking(child);
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

//            System.out.println("growth");
                // grass growth
                for (int i = 0; i < grassDailyGrowth; i++) {
                    Vector2D grassPosition = this.map.getRandomGrassPosition();
                    if (!this.map.isGrassOn(grassPosition)) {
                        grassMap.put(grassPosition, new Grass(grassPosition));
                        grassCount++;
                    }
                }
                this.map.setGrassMap(grassMap);

//            System.out.println("handling");
                // handling animals that starved to death



                animalMap.forEach( (key, value) -> {
                    ArrayList<AbstractAnimal> positionAnimalList = animalMap.get(key);
                    int animalNumber = positionAnimalList.size();
                    positionAnimalList.sort(comparator);
                    for(int i = animalNumber - 1; i >= 0; i--) {
                        Animal animal = (Animal) positionAnimalList.get(i);
                        if(animal.getEnergy() <= 0) {
                            this.countDeadAge += animal.getAge();
                            positionAnimalList.remove(animal);
                            animalMap.put(key, positionAnimalList);
                            this.animalList.remove(animal);
                            animalCount--;
                            this.countDead ++;
                            animal.setIsDead(true);
                            removeFromGenesRanking(animal);
                            if (positionAnimalList.isEmpty()) {
                                toDelete.add(key);
                            }
                        }
                        else {
                            break;
                        }
                    }
                });
                // deleting keys
                for (int i = 0; i < keysToDelete.size(); i++) {
                    animalMap.remove(i);
                }



                this.map.setAnimalMap(animalMap); // update map
                //System.out.println(map.getAnimalMap());

                // Break before next iteration
                simulationChanged("");
//            System.out.println("Simulation finished.");

                try {
                    Thread.sleep(this.gapTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        Platform.runLater(() -> {
            int observersSize = observers.size();
            for (int i = 0; i < observersSize; i++) {
                observers.get(i).simulationChanged(this, message);
            }
        });
    }

    public UUID getID () {
        return this.uuid;
    }

    public Boolean isOwlBearPresent(){return this.isOwlBearPresent;}
    public WorldMap getWorldMap() {
        if (this.map == null) {
            throw new IllegalStateException("WorldMap is not initialized!");
        }
        return this.map;
    }

    public int getAverageDeathAge(){
        if(this.countDead == 0){
            return 0;
        }
        return this.countDeadAge/this.countDead;
    }

    public int getAnimalCount(){
        return this.animalCount;
    }

    public int getGrassCount(){
        return this.grassCount;
    }

    public int getMostPopularGenome() {
        return mostPopularGenome;
    }
    public double getTotalKids(){
        if(this.animalCount == 0){
            return 0.0;
        }
        return Math.round((double) this.totalKids / this.animalCount * 100.0) / 100.0;
    }

    public int getEnergyOfLiving(){
        if(this.animalCount == 0){
            return 0;
        }
        return this.EnergyOfLiving/this.animalCount;
    }

    public Boolean getFlag(){
        return this.flag;
    }

    public void setFlag(){
        if(this.flag){
            this.flag = false;
        }
        else{
            this.flag = true;
        }
    }

    public int getDays(){
        return this.Days;
    }

    public int getStartingEnergyLevel() {
        return this.startingEnergyLevel;
    }


    public boolean setJungleFlag(){
        if(this.jungleFlag){
            this.jungleFlag = false;
        }
        else{
            this.jungleFlag = true;
        }
        return this.jungleFlag;
    }

    public boolean setTerritoryFlag(){
        if(this.territoryFlag){
            this.territoryFlag = false;
        }
        else{
            this.territoryFlag = true;
        }
        return this.territoryFlag;
    }

    public Genes getMostPopularGenes() {
        if (genesRanking.size() == 0) {
            //System.out.println("r");
            return new Genes();
        }
        else {
            //System.out.println("s");
            Genes mostPopularGenes = new Genes();
            int mostPopluarGenesNumber = 0;

            Iterator it = genesRanking.entrySet().iterator();
            while (it.hasNext()) {
                //System.out.println("sss");
                Map.Entry pair = (Map.Entry)it.next();
                if ((int) pair.getValue() > mostPopluarGenesNumber) {
                    mostPopularGenes = (Genes) pair.getKey();
                    mostPopluarGenesNumber = (int) pair.getValue();
                }
                //it.remove();
            }
            return mostPopularGenes;
        }
    }

    void addToGenesRanking(Animal animal) {
        Genes genes = animal.getGenes();
        int genesNumber = 1;
        if (genesRanking.get(genes) != null) {
            genesNumber += genesRanking.get(genes);
        }
        //System.out.println("pakowanie");
        //System.out.println(genesRanking);
        genesRanking.put(genes, genesNumber);
    }

    void removeFromGenesRanking(Animal animal) {
        Genes genes = animal.getGenes();
        int genesNumber = 0;
        if (genesRanking.get(genes) != null) {
            genesNumber = genesRanking.get(genes);
            genesNumber--;
        }
        genesRanking.put(genes, genesNumber);
    }

    public boolean setHighlightFlag(){
        if(this.highlightFlag){
            this.highlightFlag = false;
        }
        else{
            this.highlightFlag = true;
        }
        return this.highlightFlag;
    }
}