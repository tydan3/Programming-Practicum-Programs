/**
 * TCSS 305 - Autumn 2019
 * Assignment 4(a) - Raceday
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    /**
     * Defines behaviors that may be performed on the race object.
     * 
     * @author Daniel Ty
     * @version Nov 15 2019
     */
public class Race implements PropertyChangeEnabledRaceControls {
    /** The default starting time. */
    public static final int DEFAULT_START_TIME = 0;
    
    /** An error message for illegal arguments. */
    private static final String ERROR_MESSAGE = "Time may not be less than 0.";
    
    /** Proper header for races. */
    private static final String[] HEADER = {"#RACE:", "#TRACK:", "#WIDTH:", "#HEIGHT:",
                                            "#DISTANCE:", "#TIME:", "#PARTICIPANTS:"};
    
    /** Stores this object's time. */
    private int myTime;
    
    /** */
    private List<String> myHeaderList;
        
    /** */
    private List<ArrayList<AbstractMessage>> myRaceList;
    
    /**
     * Manager for Property Change Listeners. 
     */
    private final PropertyChangeSupport myPcs;
    
    /**
     * 
     */
    public Race() {
        myTime = 0;
        myPcs = new PropertyChangeSupport(this);
    }

    @Override
    public void advance() {
        advance(1);
    }

    @Override
    public void advance(final int theMillisecond) {
        if (theMillisecond < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        changeTime(myTime + theMillisecond);
    }
    
    /**
     * Helper method to change the value of time and notify observers. 
     * Functional decomposition. 
     * @param theMillisecond the time to change to
     */
    private void changeTime(final int theMillisecond) {
        final int old = myTime;
        myTime = theMillisecond;
        myPcs.firePropertyChange(PROPERTY_TIME, old, myTime);
    }

    @Override
    public void moveTo(final int theMillisecond) {
        if (theMillisecond < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        changeTime(theMillisecond);
    }

    @Override
    public void toggleParticipant(final int theParticpantID, final boolean theToggle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadRace(final File theRaceFile) throws IOException {
        int i = 0;
        int participants = 0;
        int time = 0;
        myHeaderList = new ArrayList<String>();
        myRaceList = new ArrayList<ArrayList<AbstractMessage>>();
        ArrayList<AbstractMessage> messageList = new ArrayList<AbstractMessage>();
        final ArrayList<AbstractMessage> emptyList = new ArrayList<AbstractMessage>();
        final Scanner scanner = new Scanner(theRaceFile);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            final String[] splitLine = line.split(":");
            
            if (i < HEADER.length && !line.contains(HEADER[i])) {
                throw new IOException();
            } else if (i < HEADER.length && line.contains(HEADER[i])) {
                myHeaderList.add(line);
            }
            if (i == HEADER.length - 1) {
                participants = Integer.parseInt(line.substring(HEADER[i].length()));
            }            
            //messages past header
            if (i == HEADER.length + participants && !splitLine[0].equals("$L")) {
                throw new IOException();
            }
            if (i >= HEADER.length && i < HEADER.length + participants) {
                myHeaderList.add(line);
            }
            if (i >= HEADER.length + participants) {
                //construct message from line
                final AbstractMessage message;
                if (splitLine[0].equals("$T")) {
                    message = new TelemetryMessage(line);
                } else if (splitLine[0].equals("$L")) {
                    message = new LeaderboardMessage(line);
                } else if (splitLine[0].equals("$C")) {
                    message = new LineCrossingMessage(line);
                } else {
                    throw new IOException();
                }
                
                //add message to a list of messages
                if (message.getMyTimeStamp() == time) {
                    messageList.add(message);
                } else {
                    //add message list to race list
                    myRaceList.add(messageList);
                    while (time != message.getMyTimeStamp() - 1) {
                        myRaceList.add(emptyList);
                        time++;
                    }
                    messageList = new ArrayList<AbstractMessage>();
                    messageList.add(message);
                    time = message.getMyTimeStamp();
                }
            }
            i++;
        }
        myRaceList.add(messageList);        
        scanner.close();
        myPcs.firePropertyChange(PROPERTY_LOAD, null, myRaceList);
        myPcs.firePropertyChange(PROPERTY_HEADERLIST, emptyList, myHeaderList);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }
    

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }
    
    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);       
    }

}