package Classes;

import EnumClasses.MoveDirections;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;

import java.util.Objects;

public class Grass implements MapElement {

    private Vector2D position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grass grass = (Grass) o;
        return Objects.equals(position, grass.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }

    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public void addObserver(PositionChangeObserver observer) {
        return;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean canMove() {
        return false;
    }

}
