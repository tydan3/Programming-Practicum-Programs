package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_HEADERLIST;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.AbstractMessage;

/**
 * 
 * @author Dan
 *
 */
public class Leaderboard extends JPanel implements PropertyChangeListener{
    /** */
    private static final Dimension BOARD_SIZE = new Dimension(200, 400);
    /** */
    private static final Dimension LABEL_SIZE = new Dimension(200, 20);

    
    /** */
    private static final Color[] PARTICIPANT_COLORS = {Color.red, Color.blue, Color.yellow,
                                                       Color.green, Color.orange, Color.pink, 
                                                       Color.magenta, Color.cyan, Color.gray,
                                                       Color.white};
    /** */
    private List<ArrayList<AbstractMessage>> myRaceList;
    
    /** */
    private Map<String, Participant> myParticipants;
    
    /** */
    private ArrayList<JLabel> myLabels;
    
    /**
     * 
     */
    public Leaderboard() {
        // TODO Auto-generated constructor stub
        super();
        setPreferredSize(BOARD_SIZE);
        setBackground(Color.white);
        setOpaque(true);      
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            final int newTime = (Integer) theEvent.getNewValue();
            final int oldTime = (Integer) theEvent.getOldValue();
            
            if (newTime > oldTime) {
                if (newTime < myRaceList.size()) {
                    for (int i = oldTime; i <= newTime; i++) {
                        final ArrayList<AbstractMessage> messageList = myRaceList.get(i);
                        for (AbstractMessage message: messageList) {
                            final String[] splitMessage = message.toString().split(":");
                            if (splitMessage[0].equals("$L")) {
                                final ArrayList<String> sortedId = new ArrayList<String>();
                                for (int j = 2; j < splitMessage.length; j++) {
                                    final String id = splitMessage[j];
                                    sortedId.add(id);
                                }
                                for (int j = 0; j < sortedId.size(); j++) {
                                    final Participant nextParticipant =
                                                    myParticipants.get(sortedId.get(j));
                                    myLabels.get(j).setText(nextParticipant.getMyId() + ": "
                                                                + nextParticipant.getMyName());
                                    myLabels.get(j).setBackground
                                        (nextParticipant.getMyColor());
                                }
                            }
                        }   
                    }
                }
            } else if (newTime < oldTime) {            
                final ArrayList<AbstractMessage> messageList = myRaceList.get(newTime);
                for (AbstractMessage message: messageList) {
                    final String[] splitMessage = message.toString().split(":");
                    if (splitMessage[0].equals("$L")) {
                        final ArrayList<String> sortedId = new ArrayList<String>();
                        for (int i = 2; i < splitMessage.length; i++) {
                            final String id = splitMessage[i];
                            sortedId.add(id);
                        }
                        for (int i = 0; i < sortedId.size(); i++) {
                            final Participant nextParticipant = myParticipants.get
                                                                (sortedId.get(i));
                            myLabels.get(i).setText(nextParticipant.getMyId() + ": " 
                                                        + nextParticipant.getMyName());
                            myLabels.get(i).setBackground(nextParticipant.getMyColor());
                        }
                    }
                }
            }
        }
        if (PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
            myRaceList = (List<ArrayList<AbstractMessage>>) theEvent.getNewValue();
            this.removeAll();
        }
        if (PROPERTY_HEADERLIST.equals(theEvent.getPropertyName())) {
            final ArrayList<String> header = (ArrayList<String>) theEvent.getNewValue();
            myParticipants = new TreeMap<String, Participant>();
            myLabels = new ArrayList<JLabel>();
            for (int i = 7; i < header.size(); i++) {
                final String[] splitMsg = header.get(i).split(":");
                final String id =  splitMsg[0].substring(1);
                final String name = splitMsg[1];
                final double distance = Double.parseDouble(splitMsg[2]);
                final Color color = PARTICIPANT_COLORS[i - 7];
                final Participant participant = new Participant(id, name, distance, color);
                
                myParticipants.put(id, participant);
                
                final JLabel label = new JLabel();
                label.setPreferredSize(LABEL_SIZE);
                label.setBackground(Color.white);
                label.setOpaque(true);
                myLabels.add(label);                
            }
            
            final ArrayList<Participant> tempList = new ArrayList<Participant>();
            for (Participant p: myParticipants.values()) {
                tempList.add(p);   
            }
            
            final ArrayList<Participant> sortedList = new ArrayList<Participant>();
            while (!tempList.isEmpty()) {
                final Participant farthest = findFarthest(tempList);
                sortedList.add(farthest);
                tempList.remove(farthest);
            }

            for (int i = 0; i < sortedList.size(); i++) {
                final Participant nextParticipant = sortedList.get(i);
                myLabels.get(i).setText(nextParticipant.getMyId() + ": " 
                                            + nextParticipant.getMyName());
                myLabels.get(i).setBackground(nextParticipant.getMyColor());
                this.add(myLabels.get(i));
            }
        }
    }
    
    /**
     * 
     * @param theList
     * @return
     */
    private Participant findFarthest(final ArrayList<Participant> theList) {
        Participant result = null;
        double max = theList.get(0).getMyDistance();
        for (int i = 0; i < theList.size(); i++) {
            if (theList.get(i).getMyDistance() >= max) {
                max = theList.get(i).getMyDistance();
                result = theList.get(i);
            }
        }
        return result;
    }

}
