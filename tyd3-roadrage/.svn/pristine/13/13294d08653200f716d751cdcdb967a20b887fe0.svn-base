/*
* TCSS 305 – Autumn 2019
* Assignment 2 – Roadrage
*/
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a atv.
 * @author Daniel Ty
 * @version Oct 25, 2019
 */ 
public class Atv extends AbstractVehicle {
    /** Total time spent each death. */
    private static final int DEATH_TIME = 25;
    
    /**
     * Constructs a Atv given an x coordinate, y coordinate, and direction.
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDir the direction
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return true;
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> possibleDir = listPossible(theNeighbors);
        Direction chosenDir;
        do {
            chosenDir = Direction.random();
        } while (!possibleDir.contains(chosenDir) 
                        || chosenDir.equals(getDirection().reverse()));
        return chosenDir;
    }
    
    /**
     * Returns a list of all possible directions given neighboring terrain.
     * @param theNeighbors Map of neighboring terrain
     * @return list of all possible directions
     */
    private List<Direction> listPossible(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> result = new ArrayList<Direction>();
        if (!theNeighbors.get(Direction.NORTH).equals(Terrain.WALL)) {
            result.add(Direction.NORTH);
        }
        if (!theNeighbors.get(Direction.EAST).equals(Terrain.WALL)) {
            result.add(Direction.EAST);

        }
        if (!theNeighbors.get(Direction.SOUTH).equals(Terrain.WALL)) {
            result.add(Direction.SOUTH);

        }
        if (!theNeighbors.get(Direction.WEST).equals(Terrain.WALL)) {
            result.add(Direction.WEST);
        }
        return result;
    }


}
