/*
* TCSS 305 – Autumn 2019
* Assignment 2 – Roadrage
*/
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a car.
 * @author Daniel Ty
 * @version Oct 25, 2019
 */
public class Car extends AbstractVehicle {
    /** Total time spent each death. */
    private static final int DEATH_TIME = 15;
    
    /**
     * Constructs a Car given an x coordinate, y coordinate, and direction.
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDir the direction
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain.equals(Terrain.STREET)
            || theTerrain.equals(Terrain.CROSSWALK) && theLight.equals(Light.GREEN)
            || theTerrain.equals(Terrain.LIGHT) && !theLight.equals(Light.RED)) {
            result = true;
        }
        return result;
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> possibleDir = listPossible(theNeighbors);
        final Direction chosenDir;
        if (possibleDir.contains(getDirection())) {
            chosenDir = getDirection();
        } else if (possibleDir.contains(getDirection().left())) {
            chosenDir = getDirection().left();
        } else if (possibleDir.contains(getDirection().right())) {
            chosenDir = getDirection().right();
        } else {
            chosenDir = getDirection().reverse();
        }
        return chosenDir;
    }
    
    /**
     * Returns a list of all possible directions given neighboring terrain.
     * @param theNeighbors Map of neighboring terrain
     * @return list of all possible directions
     */
    private List<Direction> listPossible(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> result = new ArrayList<Direction>();
        if (isPossible(theNeighbors, Direction.NORTH)) {
            result.add(Direction.NORTH);
        }
        if (isPossible(theNeighbors, Direction.EAST)) {
            result.add(Direction.EAST);

        }
        if (isPossible(theNeighbors, Direction.SOUTH)) {
            result.add(Direction.SOUTH);

        }
        if (isPossible(theNeighbors, Direction.WEST)) {
            result.add(Direction.WEST);
        }
        return result;
    }

    /**
     * Returns if a direction is possible given neighboring terrain and the direction itself.
     * @param theNeighbors Map of neighboring terrain
     * @param theDir direction to be tested
     * @return true if direction is possible; false otherwise.
     */
    private boolean isPossible(final Map<Direction, Terrain> theNeighbors,
                               final Direction theDir) {
        return theNeighbors.get(theDir).equals(Terrain.STREET)
                        || theNeighbors.get(theDir).equals(Terrain.CROSSWALK)
                        || theNeighbors.get(theDir).equals(Terrain.LIGHT);
    }
}
