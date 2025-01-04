package Classes;

import java.util.Objects;

public class Vector2D {
    private int x;
    private int y;
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int new_x) {
        x = new_x;
    }

    public void setY(int new_y) {
        y = new_y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2D other) {
        return x <= other.x && y <= other.y;
    }
    public boolean follows(Vector2D other) {
        return x >= other.x && y >= other.y;
    }
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }
    public Vector2D upperRight(Vector2D other) {
        int new_x = Math.max(x, other.x);
        int new_y = Math.max(y, other.y);
        return new Vector2D(new_x, new_y);
    }
    public Vector2D lowerLeft(Vector2D other) {
        int new_x = Math.min(x, other.x);
        int new_y = Math.min(y, other.y);
        return new Vector2D(new_x, new_y);
    }

    public Vector2D opposite() {
        Vector2D other = this;
        return new Vector2D(-other.x, -other.y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
