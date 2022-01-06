package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class LeaderboardMessage extends AbstractMessage {
    /** */
    private final List<String> myRacers;
    
    /**
     * 
     */
    public LeaderboardMessage(final String theMessage) {
        super(theMessage);
        final String[] splitMsg = super.getMySplitMsg();
        myRacers = new ArrayList<String>();
        for (int i = 2; i < splitMsg.length; i++) {
            myRacers.add(splitMsg[i]);
        }
    }
    
    /**
     * 
     * @return
     */
    public List<String> getMyRacers(){
        return new ArrayList<String>(myRacers);
        
    }
}
