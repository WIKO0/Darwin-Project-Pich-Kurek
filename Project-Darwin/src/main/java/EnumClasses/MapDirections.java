package EnumClasses;

import Classes.Vector2D;

public enum MapDirections {
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTHWEST,
    NORTHEAST,
    SOUTHWEST,
    SOUTHEAST;

    public Vector2D toVector(){
        switch (this) {
            case NORTH: return new Vector2D(0,1);
            case NORTHEAST: return new Vector2D(1,1);
            case NORTHWEST: return new Vector2D(-1,1);
            case EAST: return new Vector2D(1,0);
            case WEST: return new Vector2D(-1,0);
            case SOUTH: return new Vector2D(0,-1);
            case SOUTHEAST: return new Vector2D(1,-1);
            case SOUTHWEST: return new Vector2D(-1,-1);
            default: return null;
        }
    }

    public int toRadians(){
        switch (this) {
            case NORTH: return 0;
            case NORTHEAST: return 45;
            case NORTHWEST: return 315;
            case EAST: return 90;
            case WEST: return 270;
            case SOUTH: return 180;
            case SOUTHEAST: return 135;
            case SOUTHWEST: return 225;
            default: return 0;
        }
    }

    public static MapDirections fromRadians(int n){
        switch (n) {
            case 0: return NORTH;
            case 45: return NORTHEAST;
            case 90: return EAST;
            case 135: return SOUTHEAST;
            case 180: return SOUTH;
            case 225: return SOUTHWEST;
            case 270: return WEST;
            case 315: return NORTHWEST;
            default: return null;
        }
    }

    public MapDirections OppositeDirection (MapDirections direction) {
        switch (direction) {
            case SOUTH: return NORTH;
            case SOUTHWEST: return NORTHEAST;
            case WEST: return EAST;
            case NORTHWEST: return SOUTHEAST;
            case NORTH: return SOUTH;
            case NORTHEAST: return SOUTHWEST;
            case EAST: return WEST;
            case SOUTHEAST: return NORTHWEST;
            default: return null;
        }
    }
}
