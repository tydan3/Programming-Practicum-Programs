package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dan
 *
 */
public class LineCrossingMessage extends AbstractMessage {
    /** */
    private final String myRacerId;
    /** */
    private final String myNewLap;
    /** */
    private final boolean myIsFinished;
    
    /**
     * 
     */
    public LineCrossingMessage(String theMessage) {
        super(theMessage);
        final String[] splitMsg = super.getMySplitMsg();
        myRacerId = splitMsg[2];
        myNewLap = splitMsg[3];
        myIsFinished = splitMsg[4].equals("true");
        
    }

    /**
     * 
     * @return
     */
    public String getMyRacerId() {
        return myRacerId;
    }

    /**
     * 
     * @return
     */
    public String getMyNewLap() {
        return myNewLap;
    }

    /**
     * @return the myIsFinished
     */
    public boolean isMyIsFinished() {
        return myIsFinished;
    }


}
