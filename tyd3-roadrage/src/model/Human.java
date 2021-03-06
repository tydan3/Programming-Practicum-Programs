/*
* TCSS 305 – Autumn 2019
* Assignment 2 – Roadrage
*/
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a human.
 * @author Daniel Ty
 * @version Oct 25, 2019
 */ 
public class Human extends AbstractVehicle {
    /** Total time spent each death. */
    private static final int DEATH_TIME = 45;

    /**
     * Constructs a Human given an x coordinate, y coordinate, and direction.
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDir the direction
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain.equals(Terrain.GRASS) || theTerrain.equals(Terrain.CROSSWALK)
                        && !theLight.equals(Light.GREEN)) {
            result = true;
        }
        return result;
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> possibleDir = listPossible(theNeighbors);
        Direction chosenDir;
        if (crosswalkDir(theNeighbors) == Direction.NORTH
                        || crosswalkDir(theNeighbors) == Direction.EAST
                        || crosswalkDir(theNeighbors) == Direction.SOUTH
                        || crosswalkDir(theNeighbors) == Direction.WEST) {
            chosenDir = crosswalkDir(theNeighbors);
        } else if (possibleDir.size() == 1 && possibleDir.contains(getDirection().reverse())) {
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
        if (theNeighbors.get(Direction.NORTH).equals(Terrain.GRASS)
                        || theNeighbors.get(Direction.NORTH).equals(Terrain.CROSSWALK)) {
            result.add(Direction.NORTH);
        }
        if (theNeighbors.get(Direction.EAST).equals(Terrain.GRASS)
                        || theNeighbors.get(Direction.EAST).equals(Terrain.CROSSWALK)) {
            result.add(Direction.EAST);

        }
        if (theNeighbors.get(Direction.SOUTH).equals(Terrain.GRASS)
                        || theNeighbors.get(Direction.SOUTH).equals(Terrain.CROSSWALK)) {
            result.add(Direction.SOUTH);

        }
        if (theNeighbors.get(Direction.WEST).equals(Terrain.GRASS)
                        || theNeighbors.get(Direction.WEST).equals(Terrain.CROSSWALK)) {
            result.add(Direction.WEST);
        }
        return result;
    }

    /**
     * Returns a neighboring crosswalk direction given a direction and its neighboring terrain.
     * If multiple neighboring terrains are crosswalk, the straight direction is prioritized,
     * then left, then right.
     * @param theNeighbors Map of neighboring terrain
     * @return Direction of a crosswalk
     */
    private Direction crosswalkDir(final Map<Direction, Terrain> theNeighbors) {
        Direction result = null;
        if (theNeighbors.get(getDirection()).equals(Terrain.CROSSWALK)) {
            result = getDirection();
        } else if (theNeighbors.get(getDirection().left()).equals(Terrain.CROSSWALK)) {
            result = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()).equals(Terrain.CROSSWALK)) {
            result = getDirection().right();
        }
        return result;
    }
}
