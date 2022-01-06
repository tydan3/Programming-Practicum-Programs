/*
* TCSS 305 – Autumn 2019
* Assignment 2 – Roadrage
*/
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a truck.
 * @author Daniel Ty
 * @version Oct 25, 2019
 */
public class Truck extends AbstractVehicle {
    /** Total time spent each death. */
    private static final int DEATH_TIME = 0;

    /**
     * Constructs a Car given an x coordinate, y coordinate, and direction.
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDir the direction
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain.equals(Terrain.STREET) || theTerrain.equals(Terrain.LIGHT)
            || theTerrain.equals(Terrain.CROSSWALK) && !theLight.equals(Light.RED)) {
            result = true;
        }
        return result;
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> possibleDir = listPossible(theNeighbors);
        Direction chosenDir;
        if (possibleDir.size() == 1 && possibleDir.contains(getDirection().reverse())) {
            chosenDir = getDirection().reverse();
        } else {
            do {
                chosenDir = Direction.random();
            } while (!possibleDir.contains(chosenDir)
                            || chosenDir.equals(getDirection().reverse()));
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
