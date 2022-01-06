package view;

import java.awt.Color;

/**
 * 
 * @author Dan
 *
 */
public class Participant {
    
    /** */
    private double myDistance;
    /** */
    private String myId;
    /** */
    private String myName;
    /** */
    private Color myColor;
    
    /**
     * 
     * @param theDistance
     * @param theId
     * @param theName
     * @param theColor
     */
    public Participant(final String theId, final String theName, final double theDistance,
                       final Color theColor) {
        // TODO Auto-generated constructor stub
        myDistance = theDistance;
        myId = theId;
        myName = theName;
        myColor = theColor;
    }

    /**
     * @return the myCurrentDistance
     */
    public double getMyDistance() {
        return myDistance;
    }

    /**
     * @param theDistance the distance to set to
     */
    public void setMyDistance(final double theDistance) {
        this.myDistance = theDistance;
    }


    /**
     * @return the myName
     */
    public String getMyName() {
        return myName;
    }


    /**
     * @return the myId
     */
    public String getMyId() {
        return myId;
    }

    /**
     * @return the myColor
     */
    public Color getMyColor() {
        return myColor;
    }

}
