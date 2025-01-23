package Menu;

import javax.swing.*;

public class MenuSettings {
    public JFrame frame;

    public MenuSettings() {
        frame = new JFrame("Menu Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 640);
        frame.setLocationRelativeTo(null);
    }

    public void startSimulation(Integer[] mapProperties, Boolean[] upgrades){
        frame.add(new SettingsPanel(mapProperties, upgrades));

        // Wywołanie metody w odpowiednim wątku, aby uruchomić okno menu
        javax.swing.SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

}
