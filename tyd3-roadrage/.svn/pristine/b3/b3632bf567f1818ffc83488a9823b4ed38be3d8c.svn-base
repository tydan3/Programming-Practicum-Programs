/*
* TCSS 305 – Autumn 2019
* Assignment 2 – Roadrage
*/
package model;

import java.util.Locale;

/**
 * Represents a vehicle concept.
 * 
 * @author Daniel Ty
 * @version Oct 24, 2019
 */
public abstract class AbstractVehicle implements Vehicle {
    /** Initial x coordinate. */
    private final int myInitX;
    /** Initial y coordinate. */
    private final int myInitY;
    /** Initial direction. */
    private final Direction myInitDir;
    /** Total time spent each death. */
    private final int myDeathTime;
    /** Current x coordinate. */
    private int myX;
    /** Current y coordinate. */
    private int myY;
    /** Current direction. */
    private Direction myDir;
    /** Whether vehicle is alive. */
    private boolean myIsAlive;
    /** Current amount of time spent dead.*/
    private int myDeathCounter;
    
    /**
     * Constructs a Vehicle given an x coordinate, y coordinate, direction, and death time.
     * @param theX x coordinate
     * @param theY y coordinate
     * @param theDir direction
     * @param theDeathTime total time spent each death
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDir,
                              final int theDeathTime) {
        myInitX = theX;
        myInitY = theY;
        myInitDir = theDir;
        myX = theX;
        myY = theY;
        myDir = theDir;
        myIsAlive = true;
        myDeathTime = theDeathTime;
        myDeathCounter = theDeathTime;
    }

    @Override
    public void collide(final Vehicle theOther) {
        if (isAlive() && getDeathTime() > theOther.getDeathTime()) {
            myDeathCounter = 0;
            myIsAlive = false;
        }
    }

    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    @Override
    public String getImageFileName() {
        final StringBuilder result = new StringBuilder(getClass().getSimpleName().
                                                       toLowerCase(Locale.ENGLISH));
        if (isAlive()) {
            result.append(".gif");
        } else {
            result.append("_dead.gif");
        }
        return result.toString();
    }

    @Override
    public Direction getDirection() {
        return myDir;
    }

    @Override
    public int getX() {
        return myX;
    }

    @Override
    public int getY() {
        return myY;
    }

    @Override
    public boolean isAlive() {
        return myIsAlive;
    }

    @Override
    public void poke() {
        if (myDeathCounter < myDeathTime) {
            myDeathCounter++;
        } else {
            myIsAlive = true;
        }
    }

    @Override
    public void reset() {
        myX = myInitX;
        myY = myInitY;
        myDir = myInitDir;
    }

    @Override
    public void setDirection(final Direction theDir) {
        myDir = theDir;
    }

    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    @Override
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Returns a string representation of vehicle. Represented as "(x, y)" where
     * x and y are the x and y coordinates of the vehicle, respectively.
     * @return String representation of vehicle
     */
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

}
