package Presenter;


import CSV.CSVWriter;
import Classes.*;
import Interfaces.SimulationChangeListener;
import Interfaces.WorldMap;
import View.SimulationApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationPresenter implements SimulationChangeListener {

    @FXML private CheckBox checkBoxSaveToCSVC;

    @FXML private Label chosenGenome;
    @FXML private Label chosenCurrentGene;
    @FXML private Label chosenEnergy;
    @FXML private Label chosenGrassEaten;
    @FXML private Label chosenDescendants;
    @FXML private Label chosenAge;
    @FXML private Label chosenDeathDay;
    @FXML private VBox chosenOneInfo;
    @FXML private Button PlaySimulation;
    @FXML private Button PauseSimulation;

    private WorldMap map;

    // Opcje w menu
    @FXML private TextField mapHeight;
    @FXML private TextField mapWidth;
    @FXML private TextField numberOfAnimals;
    @FXML private TextField numberOfGrasses;
    @FXML private TextField numberOfGenes;
    @FXML private TextField defaultEnergy;
    @FXML private TextField defaultAge;
    @FXML private TextField paceOfAging;
    @FXML private TextField grassEnergyGiven;
    @FXML private TextField grassDailyGrowth;
    @FXML private TextField minEnergyForCopulation;
    @FXML private TextField energyUsedToCopulate;
    @FXML private TextField minMutations;
    @FXML private TextField maxMutations;
    @FXML private TextField gapTime;
    @FXML private CheckBox spawnOwlBear;
    @FXML private CheckBox ageIsHeavyBurden;

    @FXML private GridPane mapGrid;
    @FXML private VBox inputBox;
    @FXML private VBox infoBox;
    @FXML private Label totalAnimalsLabel;
    @FXML private Label averageAgeLabel;
    @FXML private Label totalGrassLabel;
    @FXML private Label totalKidsLabel;
    @FXML private Label totalEnergyofLiving;
    @FXML private Label topGenes;
    @FXML private Label unoccupiedFields;
    @FXML private Label simulationDay;

    @FXML private Label errorMessage;

    private boolean isStopped = false;
    private boolean showAnimalStats = false;
    private Animal chosenAnimal;
    private boolean isChosenALive = true;

    private boolean jungleFlag;
    private boolean territoryFlag;
    private boolean highlightFlag;

    //private SavedParameters save = ;

    int mapMaxWidth = 600;
    int mapMaxHeight = 400;

    private CSVWriter csvWriter;
    private boolean saveToCSV = false;

    private Simulation sim;


    public void setMap(WorldMap map) {
        this.map = map;
    }

    private void clearGrid() {
        mapGrid.getChildren().clear();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private synchronized void drawMap() {
        clearGrid(); // Clear the grid before drawing

        int width = map.getUpperRight().getX() + 1;
        int height = map.getUpperRight().getY() + 1;

        // Get the window size and calculate the maximum cell size
        double windowWidth = mapGrid.getScene().getWindow().getWidth();
        double windowHeight = mapGrid.getScene().getWindow().getHeight();
        double maxGridWidth = 600.0; //windowWidth * 0.6;
        double maxGridHeight = 600.0; //windowHeight * 0.6;
        double cellWidth = (maxGridWidth / width); //Math.min(maxGridWidth / width, maxGridHeight / height);
        double cellHeight = (maxGridHeight / height); //Math.min(maxGridWidth / width, maxGridHeight / height);
        //System.out.println(cellHeight);
        //System.out.println(cellWidth);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));

        for (int i = 0; i <= width; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPrefWidth(cellWidth);
            col.setMaxWidth(cellWidth);
            mapGrid.getColumnConstraints().add(col);
        }
        for (int i = 0; i <= height; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(cellHeight);
            row.setMaxHeight(cellHeight);
            mapGrid.getRowConstraints().add(row);
        }

        // Set grid padding and spacing
//        mapGrid.setPadding(new Insets(10, 10, 10, 10));
//        mapGrid.setHgap(5);
//        mapGrid.setVgap(5);

        // Draw the grid with animals, grass, and empty cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Vector2D pos = new Vector2D(col, row);
                int animalCount = map.NumberOfAnimalsAt(pos);
                Label cellLabel = new Label();
                cellLabel.setStyle(
                        "-fx-min-width: " + cellWidth + ";\n"
                                + "-fx-min-height: " + cellHeight + ";\n"
                                + "-fx-max-width: " + cellWidth + ";\n"
                                + "-fx-max-height: " + cellHeight + ";\n"
                                + "-fx-font-size: " + (cellHeight)*(3.0/5.0) + ";");
                cellLabel.getStyleClass().add("grid-cell");


                GridPane.setHalignment(cellLabel, HPos.CENTER);

                if (map.isGrassOn(pos)) {
                    if (animalCount > 0) {
                        cellLabel.setText(Integer.toString(animalCount));

                        double energy = (0.2 + (((Animal) map.getAnimalMap().get(pos).get(0)).getEnergy()) / (float) this.sim.getStartingEnergyLevel() );
                        if(energy < 0.2) {
                            energy = 0.2;
                        }
                        else if(energy > 1.0) {
                            energy = 0.999;
                        }
                        cellLabel.setStyle(
                                "-fx-min-width: " + cellWidth + ";\n"
                                        + "-fx-min-height: " + cellHeight + ";\n"
                                        + "-fx-max-width: " + cellWidth + ";\n"
                                        + "-fx-max-height: " + cellHeight + ";\n"
                                        + "-fx-font-size: " + (cellHeight)*(3.0/5.0) + ";\n"
                                        + "-fx-text-fill: rgba(0,0,221," + energy +");");

                        cellLabel.getStyleClass().add("animal-grass-cell");

                        mapGrid.add(cellLabel, col, row);
                        if (this.isStopped) {
                            Button animalButton = new Button(); //Integer.toString(animalCount)
                            animalButton.setOnAction(e -> {
                                showAnimalData(pos);
                            });
                            animalButton.setOpacity(0);
                            animalButton.setMaxWidth(cellWidth);
                            animalButton.setMaxHeight(cellHeight);

                            mapGrid.add(animalButton, col, row);
                        }
                    }
                    else {
                        cellLabel.setText("*");
                        cellLabel.getStyleClass().add("grass-cell");
                        cellLabel.setStyle(
                                "-fx-min-width: " + cellWidth + ";\n"
                                        + "-fx-min-height: " + cellHeight + ";\n"
                                        + "-fx-max-width: " + cellWidth + ";\n"
                                        + "-fx-max-height: " + cellHeight + ";\n"
                                        + "-fx-font-size: " + cellHeight + ";");
                        if (map instanceof EarthWithOwlBear && ((EarthWithOwlBear) map).getOwlBearPosition().equals(pos)) {
                            cellLabel.setText("X");
                            cellLabel.getStyleClass().add("owl-bear-grass-cell");
                            cellLabel.setStyle(
                                    "-fx-min-width: " + cellWidth + ";\n"
                                            + "-fx-min-height: " + cellHeight + ";\n"
                                            + "-fx-max-width: " + cellWidth + ";\n"
                                            + "-fx-max-height: " + cellHeight + ";\n"
                                            + "-fx-font-size: " + cellHeight*(3.0/5.0) + ";");

                        }
                        mapGrid.add(cellLabel, col, row);
                    }
                }
                else {
                    if (animalCount > 0) {
                        cellLabel.setText(Integer.toString(animalCount));

                        double energy = (0.2 + (((Animal) map.getAnimalMap().get(pos).get(0)).getEnergy()) / (float) this.sim.getStartingEnergyLevel() );
                        if(energy < 0.2) {
                            energy = 0.2;
                        }
                        else if(energy > 1.0) {
                            energy = 0.999;
                        }
                        cellLabel.setStyle(
                                "-fx-min-width: " + cellWidth + ";\n"
                                        + "-fx-min-height: " + cellHeight + ";\n"
                                        + "-fx-max-width: " + cellWidth + ";\n"
                                        + "-fx-max-height: " + cellHeight + ";\n"
                                        + "-fx-font-size: " + (cellHeight)*(3.0/5.0) + ";\n"
                                        + "-fx-text-fill: rgba(0,0,221," + energy +");");

                        cellLabel.getStyleClass().add("animal-cell");
                        mapGrid.add(cellLabel, col, row);
                        if (this.isStopped) {
                            Button animalButton = new Button(); //Integer.toString(animalCount)
                            animalButton.setOnAction(e -> {
                                showAnimalData(pos);
                            });
                            animalButton.setOpacity(0);
                            animalButton.setMaxWidth(cellWidth);
                            animalButton.setMaxHeight(cellHeight);
                            mapGrid.add(animalButton, col, row);
                        }
                        else {

                        }
                    }
                    else if (map instanceof EarthWithOwlBear && ((EarthWithOwlBear) map).getOwlBearPosition().equals(pos)) {
                        cellLabel.setText("X");
                        cellLabel.getStyleClass().add("owl-bear-cell");
                        mapGrid.add(cellLabel, col, row);
                    }
                    else {
                        mapGrid.add(cellLabel, col, row);
                    }
                }

                if (jungleFlag && territoryFlag) {
                    if  (pos.follows(((EarthWithOwlBear)map).getOwlBearLowerLeft()) &&
                        pos.precedes(((EarthWithOwlBear)map).getOwlBearUpperRight()) &&
                        (pos.follows(map.getJungleLowerLeft()) &&
                        pos.precedes(map.getJungleUpperRight()))
                    ) {
                        cellLabel.getStyleClass().add("jungle-territory-cell");
                    }
                    else if (pos.follows(((EarthWithOwlBear)map).getOwlBearLowerLeft()) &&
                            pos.precedes(((EarthWithOwlBear)map).getOwlBearUpperRight())) {
                        cellLabel.getStyleClass().add("territory-cell");
                    }
                    else if ((pos.follows(map.getJungleLowerLeft()) &&
                            pos.precedes(map.getJungleUpperRight()))) {
                        cellLabel.getStyleClass().add("jungle-cell");
                    }

                }
                else if (jungleFlag) {
                    if ((pos.follows(map.getJungleLowerLeft()) &&
                            pos.precedes(map.getJungleUpperRight()))) {
                        cellLabel.getStyleClass().add("jungle-cell");
                    }
                }
                else if (territoryFlag) {
                    if (pos.follows(((EarthWithOwlBear)map).getOwlBearLowerLeft()) &&
                            pos.precedes(((EarthWithOwlBear)map).getOwlBearUpperRight())) {
                        cellLabel.getStyleClass().add("territory-cell");
                    }
                }

                if (animalCount > 0) {
                    if(highlightFlag) {
                        for (int i = 0; i < animalCount; i++) {
                            if( ((Animal) map.getAnimalMap().get(pos).get(i)).getGenes().equals(sim.getMostPopularGenes()) ) {
                                cellLabel.getStyleClass().add("gene-highlighted-cell");
                                break;
                            }
                        }
                    }
                }


            }
        }
        // Set grid lines visible
        //mapGrid.setGridLinesVisible(true);
    }

    private void showAnimalData(Vector2D pos) {
        this.chosenAnimal = ((Animal)map.getAnimalMap().get(pos).get(0));
        map.setChosenOne( (Animal)map.getAnimalMap().get(pos).get(0) );
        updateChosenAnimalInfo();
        chosenOneInfo.setVisible(true);
        this.showAnimalStats = true;
    }

    private void updateChosenAnimalInfo(){
        map.setChosenGenes();
        map.setChosenKids();
        map.setChosenEnergy();
        map.setChosenCurrentGene();
        map.setChosenGrassConsumed();
        map.setChosenAge();
        if(this.isChosenALive)
        {
            //System.out.println("Animal is dead:" + this.chosenAnimal.getIsDead());
            if (this.chosenAnimal.getIsDead()) {
                map.setChosenDeathDay(this.sim.getDays());
                this.isChosenALive = false;
            } else {
                map.setChosenDeathDay(-1);
            }
        }
        chosenGenome.setText("" + map.getChosenGenes());
        chosenCurrentGene.setText("" + map.getChosenCurrentGene());
        chosenEnergy.setText("" + map.getChosenEnergy());
        chosenGrassEaten.setText("" + map.getChosenGrassConsumed());
        chosenDescendants.setText("" + map.getChosenDescendantNumber());
        chosenAge.setText("" + map.getChosenAge());
        if(map.getChosenDeathAge() == -1) {
            chosenDeathDay.setText("Animal is alive");
        }
        else {
            chosenDeathDay.setText("" + map.getChosenDeathAge());
        }

    }

    public void validateData() {
        if (Integer.parseInt(mapHeight.getText()) <= 2)
            throw new IllegalArgumentException("Height must be greater than 2");
        if (Integer.parseInt(mapWidth.getText()) <= 2)
            throw new IllegalArgumentException("Width must be greater than 2");
        if (Integer.parseInt(numberOfAnimals.getText()) <= 0)
            throw new IllegalArgumentException("Number of animals must be greater than zero");
        if (Integer.parseInt(numberOfGrasses.getText()) < 0)
            throw new IllegalArgumentException("Number of grasses must not be negative");
        if (Integer.parseInt(numberOfGenes.getText()) <= 0)
            throw new IllegalArgumentException("Number of genes must be greater than zero");
        if (Integer.parseInt(defaultEnergy.getText()) <= 0)
            throw new IllegalArgumentException("Default energy must be greater than zero");
        if (Integer.parseInt(defaultAge.getText()) < 0)
            throw new IllegalArgumentException("Default age must be greater than or equal to zero");
        if (Integer.parseInt(paceOfAging.getText()) <= 0)
            throw new IllegalArgumentException("Pace of aging must be greater than zero");
        if (Integer.parseInt(grassEnergyGiven.getText()) <= 0)
            throw new IllegalArgumentException("Energy gained from consuming grass must be greater than zero");
        if (Integer.parseInt(grassDailyGrowth.getText()) < 0)
            throw new IllegalArgumentException("Number of daily spawnings of grass fields must not be negative");
        if (Integer.parseInt(minEnergyForCopulation.getText()) < 0)
            throw new IllegalArgumentException("Energy needed for copulation must be non-negative");
        if (Integer.parseInt(energyUsedToCopulate.getText()) < 0)
            throw new IllegalArgumentException("Energy used by copulation must be non-negative");
        if (Integer.parseInt(minMutations.getText()) < 0)
            throw new IllegalArgumentException("Minimum number of mutations must be non-negative");
        if (Integer.parseInt(maxMutations.getText()) < Integer.parseInt(minMutations.getText()))
            throw new IllegalArgumentException("Maximum number of mutations must be greater than or equal to minimum number of mutations");
        if (Integer.parseInt(gapTime.getText()) < 0)
            throw new IllegalArgumentException("Refresh rate must be non-negative");
    }

    @Override
    public void simulationChanged(Simulation simulation, String message) {
        Platform.runLater(() -> {
            drawMap();
            updateInfoBox(simulation);
            if(showAnimalStats){
                updateChosenAnimalInfo();
            }
            if(this.saveToCSV) {
                try {
                    csvWriter.writeStats(this.sim.getDays(),
                            this.sim.getAnimalCount(),
                            this.sim.getAverageDeathAge(),
                            this.sim.getGrassCount(),
                            this.sim.getTotalKids(),
                            this.sim.getEnergyOfLiving());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void updateInfoBox(Simulation simulation) {
        simulationDay.setText("" + simulation.getDays());
        totalAnimalsLabel.setText("" + simulation.getAnimalCount());
        averageAgeLabel.setText("" + simulation.getAverageDeathAge());
        totalGrassLabel.setText("" + simulation.getGrassCount());
        totalEnergyofLiving.setText("" + simulation.getEnergyOfLiving());
        totalKidsLabel.setText("" + simulation.getTotalKids());
        topGenes.setText("" + simulation.getMostPopularGenes());
        unoccupiedFields.setText("" + map.getNumberOfUnoccupiedFields());
    }

    public void startSimulation() {
        try {
            validateData();
            int height = Integer.parseInt(mapHeight.getText());
            int width = Integer.parseInt(mapWidth.getText());
            int nAnimals = Integer.parseInt(numberOfAnimals.getText());
            int nGrasses = Integer.parseInt(numberOfGrasses.getText());
            int nGenes = Integer.parseInt(numberOfGenes.getText());
            int defEnergy = Integer.parseInt(defaultEnergy.getText());
            int Age = Integer.parseInt(defaultAge.getText());
            int pace = Integer.parseInt(paceOfAging.getText());
            int grassE = Integer.parseInt(grassEnergyGiven.getText());
            int grassDGrowth = Integer.parseInt(grassDailyGrowth.getText());
            int minE = Integer.parseInt(minEnergyForCopulation.getText());
            int minEu = Integer.parseInt(energyUsedToCopulate.getText());
            int minMut = Integer.parseInt(minMutations.getText());
            int maxMut = Integer.parseInt(maxMutations.getText());
            int gap = Integer.parseInt(gapTime.getText());
            boolean spawnOBear = spawnOwlBear.isSelected();
            boolean ageIsBurden = ageIsHeavyBurden.isSelected();

            Simulation simulation = new Simulation(height, width, nAnimals,
                    nGrasses, nGenes, defEnergy, Age, pace, grassE, grassDGrowth,
                    minE, minEu, minMut, maxMut, gap, spawnOBear, ageIsBurden
            );

            simulation.registerObserver(this);
            this.saveToCSV = checkBoxSaveToCSVC.isSelected();
            if(this.saveToCSV){
                setSaveToCSV();
            }

            // Hide input GridPane and show mapGrid and infoBox
            inputBox.setVisible(false);
            mapGrid.setVisible(true);
            infoBox.setVisible(true);

            // Set constraints for mapGrid to occupy half of the window
//            mapGrid.setMaxWidth(Double.MAX_VALUE);
//            mapGrid.setMaxHeight(Double.MAX_VALUE);
//            mapGrid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
//            mapGrid.setPrefWidth(Region.USE_COMPUTED_SIZE * 0.5); // Half of the window width
//            mapGrid.setPrefHeight(Region.USE_COMPUTED_SIZE * 0.5); // Half of the window height
//            mapGrid.setMaxWidth(700.0);
//            mapGrid.setMaxHeight(700.0);

            this.errorMessage.setVisible(false);
            // Draw the map
            setMap(simulation.getWorldMap());
            this.sim = simulation; // order is important !!!
            drawMap();



            // Start the simulation
            SimulationEngine engine = new SimulationEngine();
            engine.addSimulation(simulation);


        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            showValidationError("Invalid parameter value: " + e.getMessage());
        }
    }

    public void showValidationError(String message) {
        this.errorMessage.setText(message);
        this.errorMessage.setVisible(true);
    }

    public void startNewSimulation() {

        SimulationApp simulationApp = new SimulationApp();
        try {
            simulationApp.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void pauseSimulation() {
        sim.setFlag();
        PauseSimulation.setVisible(false);
        PlaySimulation.setVisible(true);
        this.isStopped = true;
        drawMap();
        try {
            if (csvWriter != null) {
                csvWriter.close();
                csvWriter = null; // Ustawienie na null, aby wskazywać, że jest zamknięty
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSimulation() {

        sim.setFlag();
        PauseSimulation.setVisible(true);
        PlaySimulation.setVisible(false);
        this.isStopped = false;

        if (csvWriter == null && saveToCSV) {
            setSaveToCSV(); // Ponowne otwarcie pliku
        }
    }

    public void setSaveToCSV() {
        try {
            // Get the user's home directory
            String userHome = System.getProperty("user.home");
            // Create a default path to the Desktop
            String defaultPath = userHome + "/Desktop/simulation_stats.csv";

            this.csvWriter = new CSVWriter(defaultPath);

            System.out.println("File saved to: " + defaultPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showJungle() {
        this.jungleFlag = sim.setJungleFlag();
        drawMap();

    }

    public void showTerritory() {
        this.territoryFlag = sim.setTerritoryFlag();
        drawMap();
    }

    public void showGene() {
        this.highlightFlag = sim.setHighlightFlag();
        drawMap();
    }

    public void setParameters1() {
        this.mapHeight.setText("20");
        this.mapWidth.setText("20");
        this.numberOfAnimals.setText("150");
        this.numberOfGrasses.setText("10");
        this.numberOfGenes.setText("6");
        this.defaultEnergy.setText("50");
        this.defaultAge.setText("1");
        this.paceOfAging.setText("1");
        this.grassEnergyGiven.setText("30");
        this.grassDailyGrowth.setText("10");
        this.minEnergyForCopulation.setText("25");
        this.energyUsedToCopulate.setText("15");
        this.minMutations.setText("0");
        this.maxMutations.setText("1");
        this.gapTime.setText("500");
        this.spawnOwlBear.setSelected(true);
        this.ageIsHeavyBurden.setSelected(true);
    }

    public void setParameters2() {
        this.mapHeight.setText("15");
        this.mapWidth.setText("15");
        this.numberOfAnimals.setText("100");
        this.numberOfGrasses.setText("100");
        this.numberOfGenes.setText("4");
        this.defaultEnergy.setText("15");
        this.defaultAge.setText("1");
        this.paceOfAging.setText("1");
        this.grassEnergyGiven.setText("15");
        this.grassDailyGrowth.setText("10");
        this.minEnergyForCopulation.setText("10");
        this.energyUsedToCopulate.setText("5");
        this.minMutations.setText("0");
        this.maxMutations.setText("1");
        this.gapTime.setText("500");
        this.spawnOwlBear.setSelected(true);
        this.ageIsHeavyBurden.setSelected(false);
    }

    public void setParameters3() {
        this.mapHeight.setText("10");
        this.mapWidth.setText("10");
        this.numberOfAnimals.setText("50");
        this.numberOfGrasses.setText("5");
        this.numberOfGenes.setText("3");
        this.defaultEnergy.setText("30");
        this.defaultAge.setText("1");
        this.paceOfAging.setText("1");
        this.grassEnergyGiven.setText("30");
        this.grassDailyGrowth.setText("5");
        this.minEnergyForCopulation.setText("10");
        this.energyUsedToCopulate.setText("5");
        this.minMutations.setText("0");
        this.maxMutations.setText("1");
        this.gapTime.setText("500");
        this.spawnOwlBear.setSelected(true);
        this.ageIsHeavyBurden.setSelected(false);
    }

    public void setParameters4() {
        this.mapHeight.setText("10");
        this.mapWidth.setText("10");
        this.numberOfAnimals.setText("250");
        this.numberOfGrasses.setText("50");
        this.numberOfGenes.setText("4");
        this.defaultEnergy.setText("30");
        this.defaultAge.setText("1");
        this.paceOfAging.setText("1");
        this.grassEnergyGiven.setText("30");
        this.grassDailyGrowth.setText("5");
        this.minEnergyForCopulation.setText("30");
        this.energyUsedToCopulate.setText("5");
        this.minMutations.setText("0");
        this.maxMutations.setText("1");
        this.gapTime.setText("500");
        this.spawnOwlBear.setSelected(true);
        this.ageIsHeavyBurden.setSelected(false);
    }


}