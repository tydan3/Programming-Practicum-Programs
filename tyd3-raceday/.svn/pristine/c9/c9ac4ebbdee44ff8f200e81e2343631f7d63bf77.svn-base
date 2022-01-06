package model;

/**
 * 
 * @author Dan
 *
 */
public abstract class AbstractMessage {
    /** */
    private final String myMessage;
    /** */
    private final int myTimeStamp;
    /** */
    private final String[] mySplitMsg;

    /**
     * 
     * @param theMessage
     */
    public AbstractMessage(final String theMessage) {
        myMessage = theMessage;
        mySplitMsg = theMessage.split(":");
        myTimeStamp = Integer.parseInt(mySplitMsg[1]);
    }
    
    
    /**
     * 
     * @return
     */
    public String getMessage() {
        return myMessage;
    }

    /**
     * 
     * @return
     */
    public int getMyTimeStamp() {
        return myTimeStamp;
    }

    /**
     * 
     * @return
     */
    protected String[] getMySplitMsg() {
        return mySplitMsg;
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return myMessage;
        
    }
}
