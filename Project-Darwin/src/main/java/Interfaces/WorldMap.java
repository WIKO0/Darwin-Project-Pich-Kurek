package Interfaces;

import Classes.Vector2D;

import java.util.ArrayList;

public interface WorldMap {

    // Places an animal or grass on the map on the given coordinates
    void place(MapElement element, Vector2D position);

    // Moves an object on the map
    void move(MapElement element);

    // Indicates if position is within boundaries, which means it is legal to place an animal or grass
    boolean isInBoundaries(Vector2D position);

    // Indicates if position is one of the poles, which means that if animal tries to step there,
    // it changes its direction 180 degrees
    boolean isUpperOrLowerBoundary(Vector2D position);

    // Indicates if position is one of the edges, which means that if animal tries to step there,
    // it teleports the animal to the opposite side
    boolean isLeftOrRightBoundary(Vector2D position);

    // Note that some positions (4 of them) are both on the pole and on the edge

    // Returns all animals and grass objects that are currently on the given coordinates
    ArrayList<Object> objectsAt(Vector2D position);

    // Returns all animals and grass objects that are currently on the map
    ArrayList<Object> getAllElements();

}
