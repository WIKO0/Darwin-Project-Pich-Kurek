package Interfaces;

import AbstractClasses.AbstractAnimal;
import Classes.Animal;
import Classes.Grass;
import Classes.OwlBear;
import Classes.Vector2D;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Map;

public interface WorldMap {

    // Places an animal or grass on the map on the given coordinates
    void place(MapElement element, Vector2D position);

    // Moves an object on the map to the given coordinates
    void move(MapElement element, Vector2D position, Vector2D destination);

    // Indicates if position is within boundaries, which means it is legal to place an animal or grass
    boolean isInBoundaries(Vector2D position);

    // Indicates if position is one of the poles, which means that if animal tries to step there,
    // it changes its direction 180 degrees
    boolean isUpperOrLowerBoundary(Vector2D position);

    // Indicates if position is one of the edges, which means that if animal tries to step there,
    // it teleports the animal to the opposite side
    boolean isLeftOrRightBoundary(Vector2D position);

    // Note that some positions (4 of them) are both on the pole and on the edge

    // Returns the number of all animals that are currently on the given coordinates
    int NumberOfAnimalsAt(Vector2D position);

    // Indicates if a grass is currently on the given coordinates
    boolean isGrassOn (Vector2D position);

    // Returns valid random position
    Vector2D getRandomPosition();

    Vector2D getRandomGrassPosition();

    Map<Vector2D, Grass> getGrassMap();

    Map<Vector2D, ArrayList<AbstractAnimal>> getAnimalMap();

    void setAnimalMap(Map<Vector2D, ArrayList<AbstractAnimal>> animalMap);

    void setGrassMap(Map<Vector2D, Grass> grassMap);

    Vector2D getUpperRight();

    Vector2D getLowerLeft();

    void setChosenOne(Animal animal);

    void setChosenGenes();

    void setChosenKids();

    void setChosenEnergy();

    void setChosenCurrentGene();

    void setChosenGrassConsumed();

    void setChosenAge();

    void setChosenDeathDay(int age);

    ArrayList<Integer> getChosenGenes();

    int getChosenCurrentGene();

    int getChosenEnergy();

    int getChosenGrassConsumed();

    int getChosenDescendantNumber();

    int getChosenAge();

    int getChosenDeathAge();

    Vector2D getJungleLowerLeft();

    Vector2D getJungleUpperRight();

    int getNumberOfUnoccupiedFields();











    /*
    // Returns all animals that are currently on the given coordinates
    ArrayList<Object> AnimalsAt(Vector2D position);

    // Returns all animals and grass objects that are currently on the map
    ArrayList<Object> getAllElements();
    */
}