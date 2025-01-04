package Interfaces;

import Classes.Vector2D;

public interface PositionChangeObserver {
    boolean positionChanged(Vector2D oldPosition, Vector2D newPosition, Object o);
}
