/*
* TCSS 305 – Autumn 2019
* Assignment 2 – Roadrage
*/
package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a bicycle.
 * @author Daniel Ty
 * @version Oct 25, 2019
 */ 
public class Bicycle extends AbstractVehicle {
    /** Total time spent each death. */
    private static final int DEATH_TIME = 35;

    /**
     * Constructs a Bicycle given an x coordinate, y coordinate, and direction.
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDir the direction
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain.equals(Terrain.STREET) || theTerrain.equals(Terrain.TRAIL)
            || (theTerrain.equals(Terrain.CROSSWALK) || theTerrain.equals(Terrain.LIGHT))
            && theLight.equals(Light.GREEN)) {
            result = true;
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> possibleDir = listPossible(theNeighbors);
        final Direction chosenDir;
        if (trailDir(theNeighbors) == Direction.NORTH
                        || trailDir(theNeighbors) == Direction.EAST
                        || trailDir(theNeighbors) == Direction.SOUTH
                        || trailDir(theNeighbors) == Direction.WEST) {
            chosenDir = trailDir(theNeighbors);
        } else if (possibleDir.contains(getDirection())) {
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
    
    /**
     * Returns a neighboring trail direction given a direction and its neighboring terrain.
     * If multiple neighboring terrains are trails, the straight direction is prioritized,
     * then left, then right.
     * @param theNeighbors Map of neighboring terrain
     * @return Direction of a trail
     */
    private Direction trailDir(final Map<Direction, Terrain> theNeighbors) {
        Direction result = null;
        if (theNeighbors.get(getDirection()).equals(Terrain.TRAIL)) {
            result = getDirection();
        } else if (theNeighbors.get(getDirection().left()).equals(Terrain.TRAIL)) {
            result = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()).equals(Terrain.TRAIL)) {
            result = getDirection().right();
        }
        return result;
    }

}
