package Menu;

import Classes.ConsoleDisplay;
import Classes.MapSimulation;
import Classes.Simulation;
import Classes.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ActionListener {
    public static final int Height = 800;
    public static final int Width = 1000;

    //fieldy dla zmiennych
    private JTextField mapHeight;
    private JTextField mapWidth;
    private JTextField startGrass; //poczatkowa liczba roslin
    private JTextField energyGain; //energia z jedzenia
    private JTextField GrassDaily; //dzienna liczba roslin
    private JTextField startAnimals; //poczatkowa liczba zwierzat
    private JTextField animalsStartEnergy;
    private JTextField animalsFull; //liczba potrzebnej energi do bycia najedzonym
    private JTextField animalsUsedEnergy; //liczba energi potrzebnej w kopulacji
    private JTextField genLenght; //dlugosc genomu
    private JTextField includeOwlBear;
    private JTextField includeAging;
    private JTextField StartAge;
    private JTextField AgingTempo;
    private JTextField gapTime;
    private JTextField minMutation;
    private JTextField maxMutation;

    //labels dla powyzszeych rzeczy
    private JLabel mapHeightLabel;
    private JLabel mapWidthLabel;
    private JLabel startGrassLabel;
    private JLabel energyGainLabel;
    private JLabel GrassDailyLabel;
    private JLabel startAnimalsLabel;
    private JLabel animalsStartEnergyLabel;
    private JLabel animalsFullLabel;
    private JLabel animalsUsedEnergyLabel;
    private JLabel genLenghtLabel;
    private JLabel includeOwlBearLabel;
    private JLabel includeAgingLabel;
    private JLabel StartAgeLabel;
    private JLabel AgingTempoLabel;
    private JLabel gapTimeLabel;
    private JLabel minMutationLabel;
    private JLabel maxMutationLabel;


    //przycisk
    private JButton startButton;

    public SettingsPanel(Integer[] defaultSettings, Boolean[] upgrades) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(Width, Height));
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton);

        //Labels
        mapHeightLabel = new JLabel("MapHeight");
        mapWidthLabel = new JLabel("MapWidth");
        startGrassLabel = new JLabel("StartGrass");
        energyGainLabel = new JLabel("EnergyGain");
        GrassDailyLabel = new JLabel("GrassDaily");
        startAnimalsLabel = new JLabel("StartAnimals");
        animalsStartEnergyLabel = new JLabel("AnimalsStartEnergy");
        animalsFullLabel = new JLabel("AnimalsFull");
        animalsUsedEnergyLabel = new JLabel("AnimalsUsedEnergy");
        genLenghtLabel = new JLabel("GenLenght");
        includeOwlBearLabel = new JLabel("IncludeOwlBear");
        includeAgingLabel = new JLabel("IncludeAging");
        StartAgeLabel = new JLabel("StartAge");
        AgingTempoLabel = new JLabel("AgingTempo");
        gapTimeLabel = new JLabel("GapTime");
        minMutationLabel = new JLabel("MinMutation");
        maxMutationLabel = new JLabel("MaxMutation");


        //TextFields
        int a = 10;
        boolean b = true;

        mapHeight = new JTextField();
        mapHeight.setColumns(a);
        mapHeight.setText(String.valueOf(defaultSettings[0]));

        mapWidth = new JTextField();
        mapWidth.setColumns(a);
        mapWidth.setText(String.valueOf(defaultSettings[1]));


        startGrass = new JTextField();
        startGrass.setColumns(a);
        startGrass.setText(String.valueOf(defaultSettings[3]));

        energyGain = new JTextField();
        energyGain.setColumns(a);
        energyGain.setText(String.valueOf(defaultSettings[8]));
        GrassDaily = new JTextField();
        GrassDaily.setColumns(a);
        GrassDaily.setText(String.valueOf(defaultSettings[9]));

        startAnimals = new JTextField();
        startAnimals.setColumns(a);
        startAnimals.setText(String.valueOf(defaultSettings[2]));


        animalsStartEnergy = new JTextField();
        animalsStartEnergy.setColumns(a);
        animalsStartEnergy.setText(String.valueOf(defaultSettings[5]));

        animalsFull = new JTextField();
        animalsFull.setColumns(a);
        animalsFull.setText(String.valueOf(defaultSettings[10]));

        animalsUsedEnergy = new JTextField();
        animalsUsedEnergy.setColumns(a);
        animalsUsedEnergy.setText(String.valueOf(defaultSettings[11]));

        genLenght = new JTextField();
        genLenght.setColumns(a);
        genLenght.setText(String.valueOf(defaultSettings[4]));

        includeOwlBear = new JTextField();
        includeOwlBear.setColumns(a);
        includeOwlBear.setText(String.valueOf(upgrades[0]));

        includeAging = new JTextField();
        includeAging.setColumns(a);
        includeAging.setText(String.valueOf(upgrades[1]));

        StartAge = new JTextField();
        StartAge.setColumns(a);
        StartAge.setText(String.valueOf(defaultSettings[6]));

        AgingTempo = new JTextField();
        AgingTempo.setColumns(a);
        AgingTempo.setText(String.valueOf(defaultSettings[7]));

        gapTime = new JTextField();
        gapTime.setColumns(a);
        gapTime.setText(String.valueOf(defaultSettings[14]));

        minMutation = new JTextField();
        minMutation.setColumns(a);
        minMutation.setText(String.valueOf(defaultSettings[12]));

        maxMutation = new JTextField();
        maxMutation.setColumns(a);
        maxMutation.setText(String.valueOf(defaultSettings[13]));

        mapHeightLabel.setLabelFor(mapHeight);
        mapWidthLabel.setLabelFor(mapWidth);
        startGrassLabel.setLabelFor(startGrass);
        energyGainLabel.setLabelFor(energyGain);
        GrassDailyLabel.setLabelFor(GrassDaily);
        startAnimalsLabel.setLabelFor(startAnimals);
        animalsStartEnergyLabel.setLabelFor(animalsStartEnergy);
        animalsFullLabel.setLabelFor(animalsFull);
        animalsUsedEnergyLabel.setLabelFor(animalsUsedEnergy);
        genLenghtLabel.setLabelFor(genLenght);
        includeOwlBearLabel.setLabelFor(includeOwlBear);
        includeAgingLabel.setLabelFor(includeAging);
        StartAgeLabel.setLabelFor(StartAge);
        AgingTempoLabel.setLabelFor(AgingTempo);
        gapTimeLabel.setLabelFor(gapTime);
        minMutationLabel.setLabelFor(minMutation);
        maxMutationLabel.setLabelFor(maxMutation);

        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();
        JPanel l3 = new JPanel();
        JPanel l4 = new JPanel();
        JPanel l5 = new JPanel();
        JPanel l6 = new JPanel();
        JPanel l7 = new JPanel();
        JPanel l8 = new JPanel();
        JPanel l9 = new JPanel();
        JPanel l10 = new JPanel();
        JPanel l11 = new JPanel();
        JPanel l12 = new JPanel();
        JPanel l13 = new JPanel();
        JPanel l14 = new JPanel();
        JPanel l15 = new JPanel();
        JPanel l16 = new JPanel();
        JPanel l17 = new JPanel();

        l1.add(mapHeight);
        l2.add(mapWidth);
        l3.add(startGrass);
        l4.add(energyGain);
        l5.add(GrassDaily);
        l6.add(startAnimals);
        l7.add(animalsStartEnergy);
        l8.add(animalsFull);
        l9.add(genLenght);
        l10.add(includeOwlBear);
        l11.add(includeAging);
        l12.add(StartAge);
        l13.add(AgingTempo);
        l14.add(gapTime);
        l15.add(minMutation);
        l16.add(maxMutation);
        l17.add(animalsUsedEnergy);



        JPanel buttonPanel = new JPanel();
        add(new JLabel("Map Properties:"));
        add(l1);
        add(l2);

        add(new JLabel("Starting Simulation Properties"));
        add(l3);
        add(l6);

        add(new JLabel("Energy and Age Properties"));
        add(l7);
        add(l8);
        add(l4);
        add(l17);
        add(l15);
        add(l16);


        add(new JLabel("Animals Properties"));
        add(l12);
        add(l13);
        add(l9);

        add(new JLabel("Spawning Properties"));
        add(l5);

        add(new JLabel("Additional Properties"));
        add(l10);
        add(l11);

        add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Simulation simulation = new Simulation(
                Integer.parseInt(mapHeight.getText()),
                Integer.parseInt(mapWidth.getText()),
                Integer.parseInt(startAnimals.getText()),
                Integer.parseInt(startGrass.getText()),
                Integer.parseInt(genLenght.getText()),
                Integer.parseInt(animalsStartEnergy.getText()),
                Integer.parseInt(StartAge.getText()),
                Integer.parseInt(AgingTempo.getText()),
                Integer.parseInt(energyGain.getText()),
                Integer.parseInt(GrassDaily.getText()),
                Integer.parseInt(animalsFull.getText()),
                Integer.parseInt(animalsUsedEnergy.getText()),
                Integer.parseInt(minMutation.getText()),
                Integer.parseInt(maxMutation.getText()),
                Integer.parseInt(gapTime.getText()),
                Boolean.parseBoolean(includeOwlBear.getText()),
                Boolean.parseBoolean(includeAging.getText())
        );
//        SimulationEngine engine = new SimulationEngine();
//        ConsoleDisplay console = new ConsoleDisplay();
//        simulation.registerObserver(console);
//        engine.addSimulation(simulation);

        MapSimulation mapSim = new MapSimulation(simulation);
        mapSim.startVisualization();
    }
}
