import Classes.*;
import View.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        try {
            SavedParameters save = new SavedParameters();
            Application.launch(SimulationApp.class, args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
/*
        Application.launch(App.class, args);
        try {
            Application.launch(App.class, args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }*/
        /*
        SimulationEngine engine = new SimulationEngine();
        Simulation simulation = new Simulation(10, 10, 20, 40, 100,
                40, 10,1,10, 5, 25,
                20, 0, 0, 1000,true, true);
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        simulation.registerObserver(consoleDisplay);
        engine.addSimulation(simulation);
        */
    }
}
