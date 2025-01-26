package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    ExecutorService executorService;

    public SimulationEngine () {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void addSimulation (Simulation simulation) {
        executorService.submit(simulation);
    }

    public void closeThreadPool() throws InterruptedException {
        this.executorService.shutdown();
        this.executorService.awaitTermination(10, TimeUnit.SECONDS);
    }


}