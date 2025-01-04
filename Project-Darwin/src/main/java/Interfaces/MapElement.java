package Interfaces;

import Classes.Vector2D;

import java.util.Observer;

public interface MapElement {
    Vector2D getPosition();
    boolean canMove();
    void addObserver(PositionChangeObserver observer);

}
