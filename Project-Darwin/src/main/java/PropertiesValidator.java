import java.io.FileNotFoundException;



public class PropertiesValidator {
    private int mapHeight = 10;
    private int mapWidth = 10;
    private int nAnimals = 10;
    private int nGrass = 10;
    private int nGenes = 20;
    private int defaultEnergy = 50;
    private int defaultAge = 0;
    private int paceOfAging = 1;
    private int grassEnergyGiven = 10;
    private int grassDailyGrowth = 4;
    private int minEnergyForCopulation = 20;
    private int energyUsedToCopulate = 20;
    private int minMutations = 2;
    private int maxMutations = 5;
    private int gapTime = 100;
    private boolean spawnOwlBear = true;
    private boolean ageIsHeavyBurden = true;

    public PropertiesValidator(int mapHeight, int mapWidth, int nAnimals, int nGrass,
                               int nGenes, int defaultEnergy, int defaultAge,
                               int paceOfAging, int grassEnergyGiven, int grassDailyGrowth,
                               int minEnergyForCopulation, int energyUsedToCopulate,
                               int minMutations, int maxMutations, int gapTime,
                               boolean spawnOwlBear, boolean ageIsHeavyBurden) throws IllegalArgumentException {

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.nAnimals = nAnimals;
        this.nGrass = nGrass;
        this.nGenes = nGenes;
        this.defaultEnergy = defaultEnergy;
        this.defaultAge = defaultAge;
        this.paceOfAging = paceOfAging;
        this.grassEnergyGiven = grassEnergyGiven;
        this.grassDailyGrowth = grassDailyGrowth;
        this.minEnergyForCopulation = minEnergyForCopulation;
        this.energyUsedToCopulate = energyUsedToCopulate;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.gapTime = gapTime;
        this.spawnOwlBear = spawnOwlBear;
        this.ageIsHeavyBurden = ageIsHeavyBurden;

        validateData();
    }

    public PropertiesValidator(){
        validateData();
    }

    public void validateData() {
        if (this.mapHeight <= 0) throw new IllegalArgumentException("Height must be greater than zero");
        if (this.mapWidth <= 0) throw new IllegalArgumentException("Width must be greater than zero");
        if (this.nAnimals <= 0) throw new IllegalArgumentException("Number of starting animals must be greater than zero");
        if (this.nGrass < 0) throw new IllegalArgumentException("Number of starting Grass must be non-negative");
        if (this.nGenes <= 0) throw new IllegalArgumentException("nGenes must be greater than zero");
        if (this.defaultEnergy <= 0) throw new IllegalArgumentException("defaultEnergy must be greater than zero");
        if (this.minEnergyForCopulation < 0) throw new IllegalArgumentException("minEnergyForCopulation must be non-negative");
        if(this.minEnergyForCopulation > this.defaultEnergy) throw new IllegalArgumentException("Energy needed to copulate should be lower than default energy");
        if (this.energyUsedToCopulate < 0) throw new IllegalArgumentException("energyUsedToCopulate must be non-negative");
        if(this.energyUsedToCopulate > this.defaultEnergy) throw new IllegalArgumentException("Energy to copulate should be lower than default energy");
        if(this.minMutations < 0) throw new IllegalArgumentException("minMutations must be non-negative");
        if (this.minMutations > this.maxMutations) throw new IllegalArgumentException("minMutations cannot be greater than maxMutations");
        if (this.gapTime < 0) throw new IllegalArgumentException("gapTime must be non-negative");
    }

    public int getMapHeight() {
        return mapHeight;
    }
    public int getMapWidth() {
        return mapWidth;
    }
    public int getNAnimals() {
        return nAnimals;
    }
    public int getNGrass() {
        return nGrass;
    }
    public int getNGenes() {
        return nGenes;
    }
    public int getDefaultEnergy() {
        return defaultEnergy;
    }
    public int getDefaultAge() {
        return defaultAge;
    }
    public int getPaceOfAging() {
        return paceOfAging;
    }
    public int getGrassEnergyGiven() {
        return grassEnergyGiven;
    }
    public int getGrassDailyGrowth() {
        return grassDailyGrowth;
    }
    public int getMinEnergyForCopulation() {
        return minEnergyForCopulation;
    }

    public int getEnergyUsedToCopulate() {
        return energyUsedToCopulate;
    }
    public int getMinMutations() {
        return minMutations;
    }
    public int getMaxMutations() {
        return maxMutations;
    }
    public int getGapTime() {
        return gapTime;
    }
    public boolean isSpawnOwlBear() {
        return spawnOwlBear;
    }
    public boolean isAgeIsHeavyBurden() {
        return ageIsHeavyBurden;
    }
}