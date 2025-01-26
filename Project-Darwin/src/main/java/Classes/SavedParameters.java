package Classes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SavedParameters {

    private int mapHeight;
    private int mapWidth;
    private int numberOfAnimals;
    private int numberOfGrasses;
    private int numberOfGenes;
    private int startingEnergyLevel;
    private int startingAge;
    private int paceOfAging;
    private int grassEnergyValue;
    private int grassDailyGrowth;
    private int minEnergyToMate;
    private int energyUsedToMate;
    private int minimalNumberOfMutations;
    private int maximalNumberOfMutations;
    private int gapTime;
    private boolean isOwlBearPresent;
    private boolean ageIsAHeavyBurden;

    public void setParameters(
    int mapHeight,
    int mapWidth,
    int numberOfAnimals,
    int numberOfGrasses,
    int numberOfGenes,
    int startingEnergyLevel,
    int startingAge,
    int paceOfAging,
    int grassEnergyValue,
    int grassDailyGrowth,
    int minEnergyToMate,
    int energyUsedToMate,
    int minimalNumberOfMutations,
    int maximalNumberOfMutations,
    int gapTime,
    boolean isOwlBearPresent,
    boolean ageIsAHeavyBurden) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrasses = numberOfGrasses;
        this.numberOfGenes = numberOfGenes;
        this.startingEnergyLevel = startingEnergyLevel;
        this.startingAge = startingAge;
        this.paceOfAging = paceOfAging;
        this.isOwlBearPresent = isOwlBearPresent;
        this.grassEnergyValue = grassEnergyValue;
        this.grassDailyGrowth = grassDailyGrowth;
        this.minEnergyToMate = minEnergyToMate;
        this.energyUsedToMate = energyUsedToMate;
        this.ageIsAHeavyBurden = ageIsAHeavyBurden;
        this.minimalNumberOfMutations = minimalNumberOfMutations;
        this.maximalNumberOfMutations = maximalNumberOfMutations;
        this.gapTime = gapTime;
    }


    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfGrasses() {
        return numberOfGrasses;
    }

    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    public int getStartingEnergyLevel() {
        return startingEnergyLevel;
    }

    public int getStartingAge() {
        return startingAge;
    }

    public int getPaceOfAging() {
        return paceOfAging;
    }

    public int getGrassEnergyValue() {
        return grassEnergyValue;
    }

    public int getGrassDailyGrowth() {
        return grassDailyGrowth;
    }

    public int getMinEnergyToMate() {
        return minEnergyToMate;
    }

    public int getEnergyUsedToMate() {
        return energyUsedToMate;
    }

    public int getMinimalNumberOfMutations() {
        return minimalNumberOfMutations;
    }

    public int getMaximalNumberOfMutations() {
        return maximalNumberOfMutations;
    }

    public int getGapTime() {
        return gapTime;
    }

    public boolean isOwlBearPresent() {
        return isOwlBearPresent;
    }

    public boolean isAgeIsAHeavyBurden() {
        return ageIsAHeavyBurden;
    }
}
