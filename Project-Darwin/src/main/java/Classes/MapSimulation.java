package Classes;

import Interfaces.WorldMap;
import javafx.application.Platform;

public class MapSimulation {

    private Simulation simulation;
    private JavaFXSimulationWindow simulationWindow;

    public MapSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void startVisualization() {
        WorldMap map = simulation.getWorldMap(); // Ensure this is initialized properly
        if (map == null) {
            throw new IllegalArgumentException("WorldMap is null from simulation.");
        }

        System.out.println("WorldMap is not null. Proceeding with launching JavaFX simulation.");

        if (Platform.isFxApplicationThread()) {
            // If we're already on the JavaFX thread, launch the window
            JavaFXSimulationWindow.launchSimulation(map);
        } else {
            // If we're not on the JavaFX thread, start the simulation and then launch the window
            Platform.startup(() -> {
                new Thread(simulation).start();  // Start simulation in a separate thread

                Platform.runLater(() -> {
                    // Double-check if map is still non-null at this stage
                    if (map != null) {
                        JavaFXSimulationWindow.launchSimulation(map);
                    } else {
                        System.out.println("ERROR: Map is null in Platform.runLater.");
                    }
                });
            });
        }
    }

    private void launchSimulation() {
        // Uruchamiamy symulację
        new Thread(simulation).start(); // Uruchamiamy symulację w osobnym wątku

        // Wywołanie JavaFXSimulationWindow, aby rozpocząć wizualizację
        JavaFXSimulationWindow.launchSimulation(simulation.getWorldMap());
    }
}
