package model;

/**
 * 
 * @author Dan
 *
 */
public class TelemetryMessage extends AbstractMessage {
    /** */
    private final String myRacerId;
    /** */
    private final String myDistance;
    /** */
    private final String myLap;
    
    /**
     * 
     */
    public TelemetryMessage(String theMessage) {
        super(theMessage);
        final String[] splitMsg = super.getMySplitMsg();
        myRacerId = splitMsg[2];
        myDistance = splitMsg[3];
        myLap = splitMsg[4];
    }

    /**
     * @return the myRacerId
     */
    public String getMyRacerId() {
        return myRacerId;
    }

    /**
     * @return the myDistance
     */
    public String getMyDistance() {
        return myDistance;
    }

    /**
     * @return the myLap
     */
    public String getMyLap() {
        return myLap;
    }

}
