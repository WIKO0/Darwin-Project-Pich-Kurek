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
        WorldMap map = simulation.getWorldMap(); // Upewniamy się, że mapa jest poprawnie zainicjalizowana
        if (map == null) {
            throw new IllegalArgumentException("WorldMap is null from simulation.");
        }

        System.out.println("WorldMap is not null. Proceeding with launching JavaFX simulation.");

        // Jeśli już jesteśmy na wątku JavaFX
        if (Platform.isFxApplicationThread()) {
            // Uruchom wizualizację na wątku JavaFX
            JavaFXSimulationWindow.launchSimulation(map);
        } else {
            // Jeśli nie jesteśmy na wątku JavaFX, uruchamiamy go za pomocą Platform.startup()
            Platform.startup(() -> {
                // Uruchamiamy symulację w osobnym wątku, aby nie blokować wątku GUI
                new Thread(simulation).start();

                // Następnie wywołujemy wizualizację na wątku JavaFX za pomocą Platform.runLater()
                Platform.runLater(() -> {
                    // Upewniamy się, że mapa nadal nie jest null
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
        // Uruchamiamy symulację w osobnym wątku
        new Thread(simulation).start();

        // Uruchamiamy wizualizację
        JavaFXSimulationWindow.launchSimulation(simulation.getWorldMap());
    }
}
