package Presenter;

import AbstractClasses.AbstractAnimal;
import Classes.*;
import Interfaces.SimulationChangeListener;
import Interfaces.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

;

public class SimulationPresenter implements SimulationChangeListener {


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
    @FXML private TextField spawnOwlBear;
    @FXML private TextField ageIsHeavyBurden;

    @FXML private GridPane mapGrid;
    @FXML private GridPane inputGridPane;
    @FXML private VBox infoBox;
    @FXML private Label totalAnimalsLabel;
    @FXML private Label averageAgeLabel;
    @FXML private Label totalGrassLabel;
    @FXML private Label totalKidsLabel;
    @FXML private Label totalEnergyofLiving;

    private boolean isStopped = false;
    private boolean showAnimalStats = false;

    int mapMaxWidth = 600;
    int mapMaxHeight = 400;

    private Simulation sim;

    public void setMap(WorldMap map) {
        this.map = map;
    }

    private void clearGrid() {
        mapGrid.getChildren().clear();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawMap() {
        clearGrid(); // Clear the grid before drawing

        int width = map.getUpperRight().getX() + 1;
        int height = map.getUpperRight().getY() + 1;

        // Get the window size and calculate the maximum cell size
        double windowWidth = mapGrid.getScene().getWindow().getWidth();
        double windowHeight = mapGrid.getScene().getWindow().getHeight();
        double maxGridWidth = windowWidth * 0.5;
        double maxGridHeight = windowHeight * 0.5;
        double cellWidth = Math.min(maxGridWidth / width, maxGridHeight / height);
        double cellHeight = Math.min(maxGridWidth / width, maxGridHeight / height);

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
        mapGrid.setPadding(new Insets(10, 10, 10, 10));
        mapGrid.setHgap(5);
        mapGrid.setVgap(5);

        // Draw the grid with animals, grass, and empty cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Vector2D pos = new Vector2D(col, row);
                int animalCount = map.NumberOfAnimalsAt(pos);
                Label cellLabel = new Label();
                cellLabel.getStyleClass().add("grid-cell");
                GridPane.setHalignment(cellLabel, HPos.CENTER);

                if (map.isGrassOn(pos)) {
                    cellLabel.setText("*");
                    cellLabel.getStyleClass().add("grass-cell");
                }
                if (animalCount > 0) {
                    if (this.isStopped) {
                        Button animalButton = new Button(Integer.toString(animalCount));
                        animalButton.setOnAction(e -> showAnimalData(pos));
                        mapGrid.add(animalButton, col, row);
                    } else {
                        cellLabel.setText(Integer.toString(animalCount));
                        cellLabel.getStyleClass().add("animal-cell");
                        mapGrid.add(cellLabel, col, row);
                    }
                } else {
                    mapGrid.add(cellLabel, col, row);
                }
                if (map instanceof EarthWithOwlBear && ((EarthWithOwlBear) map).getOwlBearPosition().equals(pos)) {
                    cellLabel.setText("X");
                    cellLabel.getStyleClass().add("red-text");
                }
            }
        }

        // Set grid lines visible
        mapGrid.setGridLinesVisible(true);
    }

    private void showAnimalData(Vector2D pos) {
        map.setChosenOne( (Animal)map.getAnimalMap().get(pos).get(0) );
        updateChosenAnimalInfo();
        chosenOneInfo.setVisible(true);
    }


    private void updateChosenAnimalInfo(){
        map.setChosenGenes();
        map.setChosenKids();
        map.setChosenEnergy();
        map.setChosenCurrentGene();
        map.setChosenGrassConsumed();
        map.setChosenAge();
        map.setChosenDeathDay(-1);
        chosenGenome.setText("Genome: " + map.getChosenGenes());
        chosenCurrentGene.setText("Current gene: " + map.getChosenCurrentGene());
        chosenEnergy.setText("Energy: " + map.getChosenEnergy());
        chosenGrassEaten.setText("Grass eaten:" + map.getChosenGrassConsumed());
        chosenDescendants.setText("Descendants: " + map.getChosenDescendantNumber());
        chosenAge.setText("Age: " + map.getChosenAge());
        chosenDeathDay.setText("Death Day: " + map.getChosenDeathAge());
    }

    public void validateData() {
        if (Integer.parseInt(mapHeight.getText()) <= 0)
            throw new IllegalArgumentException("Height must be greater than zero");
        if (Integer.parseInt(mapWidth.getText()) <= 0)
            throw new IllegalArgumentException("Width must be greater than zero");
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
        });
    }

    private void updateInfoBox(Simulation simulation) {
        totalAnimalsLabel.setText("Total Animals: " + simulation.getAnimalCount());
        averageAgeLabel.setText("Average Age: " + simulation.getAverageDeathAge());
        totalGrassLabel.setText("Total Grass: " + simulation.getGrassCount());
        totalEnergyofLiving.setText("Total Energy of living Animals: " + simulation.getEnergyOfLiving());
        totalKidsLabel.setText("Total Kids: " + simulation.getTotalKids());
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
            boolean spawnOBear = Boolean.parseBoolean(spawnOwlBear.getText());
            boolean ageIsBurden = Boolean.parseBoolean(ageIsHeavyBurden.getText());

            Simulation simulation = new Simulation(height, width, nAnimals,
                    nGrasses, nGenes, defEnergy, Age, pace, grassE, grassDGrowth,
                    minE, minEu, minMut, maxMut, gap, spawnOBear, ageIsBurden
            );

            simulation.registerObserver(this);

            // Hide input GridPane and show mapGrid and infoBox
            inputGridPane.setVisible(false);
            mapGrid.setVisible(true);
            infoBox.setVisible(true);

            // Set constraints for mapGrid to occupy half of the window
            mapGrid.setMaxWidth(Double.MAX_VALUE);
            mapGrid.setMaxHeight(Double.MAX_VALUE);
            mapGrid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            mapGrid.setPrefWidth(Region.USE_COMPUTED_SIZE * 0.5); // Half of the window width
            mapGrid.setPrefHeight(Region.USE_COMPUTED_SIZE * 0.5); // Half of the window height

            // Draw the map
            setMap(simulation.getWorldMap());
            drawMap();

            this.sim = simulation;

            // Start the simulation
            SimulationEngine engine = new SimulationEngine();
            engine.addSimulation(simulation);


        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
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
        System.out.println(sim.getFlag());
        PauseSimulation.setVisible(false);
        PlaySimulation.setVisible(true);
        this.isStopped = true;
        drawMap();
    }

    public void playSimulation() {
        sim.setFlag();
        System.out.println(sim.getFlag());
        PauseSimulation.setVisible(true);
        PlaySimulation.setVisible(false);
        this.isStopped = false;
    }
}