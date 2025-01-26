package Classes;

import Interfaces.SimulationChangeListener;

public class ConsoleDisplay implements SimulationChangeListener {
    private int receivedUpdates;

    public ConsoleDisplay () {
        this.receivedUpdates = 0;
    }

    public synchronized void simulationChanged(Simulation simulation, String message) {
        receivedUpdates++;
        System.out.println();
        System.out.println("Simulation ID: " + simulation.getID());
        System.out.println(message);
        System.out.println();
        System.out.println(simulation.toString());
        System.out.println("Iteration: " + receivedUpdates);
        System.out.println();
    }
}
