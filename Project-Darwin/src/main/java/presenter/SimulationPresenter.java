package presenter;

import Classes.EarthWithOwlBear;
import Classes.Simulation;
import Classes.SimulationEngine;
import Classes.Vector2D;
import Interfaces.SimulationChangeListener;
import Interfaces.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class SimulationPresenter implements SimulationChangeListener {
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

        int width = map.getUpperRight().getX();
        int height = map.getUpperRight().getY();

        // Set constraints for columns and rows
        for (int i = 0; i <= width; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / (width + 1));
            mapGrid.getColumnConstraints().add(col);
        }
        for (int i = 0; i <= height; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / (height + 1));
            mapGrid.getRowConstraints().add(row);
        }

        // Add column numbers
        for (int i = 0; i < width; i++) {
            Label label = new Label(Integer.toString(i + 1));
            label.getStyleClass().add("grid-label");
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }

        // Add row numbers
        for (int i = 0; i < height; i++) {
            Label label = new Label(Integer.toString(i + 1));
            label.getStyleClass().add("grid-label");
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, i + 1);
        }

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
                } else if (animalCount > 0) {
                    cellLabel.setText(Integer.toString(animalCount));
                } else if (map instanceof EarthWithOwlBear && ((EarthWithOwlBear) map).getOwlBearPosition().equals(pos)) {
                    cellLabel.setText("X");
                }

                mapGrid.add(cellLabel, col + 1, row + 1);
            }
        }

        // Set grid lines visible
        mapGrid.setGridLinesVisible(true);
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
        });
    }

    private void updateInfoBox(Simulation simulation) {
        totalAnimalsLabel.setText("Total Animals: " + simulation.getAnimalCount());
        averageAgeLabel.setText("Average Age: " + simulation.getAverageDeathAge());
        totalGrassLabel.setText("Total Grass: " + simulation.getGrassCount());
        // Dodaj dodatkowe aktualizacje, jeśli potrzebujesz więcej informacji
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

            // Draw the map
            setMap(simulation.getWorldMap());
            drawMap();

            // Start the simulation
            SimulationEngine engine = new SimulationEngine();
            engine.addSimulation(simulation);


        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }
}