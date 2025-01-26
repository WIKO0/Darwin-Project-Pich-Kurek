package util;

import Classes.EarthWithOwlBear;
import Classes.Vector2D;
import Interfaces.WorldMap;

/**
 * The map visualizer converts the {@link WorldMap} map into a string
 * representation.
 *
 * @author apohllo, idzik
 */
public class MapVisualizer {
    private static final String EMPTY_CELL = "   ";
    private static final String FRAME_SEGMENT = "---";
    private static final String CELL_SEGMENT = "|";
    private final WorldMap map;

    /**
     * Initializes the MapVisualizer with an instance of map to visualize.
     *
     * @param map
     */
    public MapVisualizer(WorldMap map) {
        this.map = map;
    }

    /**
     * Convert selected region of the map into a string. It is assumed that the
     * indices of the map will have no more than two characters (including the
     * sign).
     *
     * @param lowerLeft  The lower left corner of the region that is drawn.
     * @param upperRight The upper right corner of the region that is drawn.
     * @return String representation of the selected region of the map.
     */
    public String draw(Vector2D lowerLeft, Vector2D upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.getY() + 1; i >= lowerLeft.getY() - 1; i--) {
            if (i == upperRight.getY() + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.getX(); j <= upperRight.getX() + 1; j++) {
                if (i < lowerLeft.getY() || i > upperRight.getY()) {
                    builder.append(drawFrame(j <= upperRight.getX()));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.getX()) {
                        builder.append(drawObject(new Vector2D(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2D lowerLeft, Vector2D upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.getX(); j < upperRight.getX() + 1; j++) {
            if (j<10) {
                builder.append(String.format(" %2d ", j));
            }
            else if (j<100) {
                builder.append(String.format(" %2d", j));
            }
            else {
                builder.append(String.format("%2d", j));
            }

        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String drawObject(Vector2D currentPosition) {
        int numberOfAnimals = this.map.NumberOfAnimalsAt(currentPosition);
        Integer number = (Integer) numberOfAnimals;
        String positionString;
        if (this.map instanceof EarthWithOwlBear && ((EarthWithOwlBear) this.map).getOwlBearPosition().equals(currentPosition)) {
            positionString = " X";
            if(map.isGrassOn(currentPosition)) {
                positionString += "*";
            }
            else {
                positionString += " ";
            }
            return positionString;
        }
        else if(map.isGrassOn(currentPosition)) {
            if (numberOfAnimals > 0) {
                positionString = number.toString();
                if (numberOfAnimals < 10) {

                    return " " + positionString + "*";
                }
                else {
                    positionString += "*";
                    return positionString;
                }

            }
            else {
                positionString = " * ";
                return positionString;
            }
        }
        else {
            if (numberOfAnimals > 0) {
                positionString = number.toString();
                return " " + positionString + " ";
            }
            else {
                return EMPTY_CELL;
            }
        }
    }
}

