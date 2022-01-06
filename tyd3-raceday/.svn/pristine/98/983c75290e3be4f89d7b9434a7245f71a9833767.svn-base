package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_HEADERLIST;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.AbstractMessage;
import track.VisibleRaceTrack;

/**
 * This is the Track panel for the race.
 * 
 * @author Daniel Ty
 * @version Autumn 2019
 */
public class Track extends JPanel implements PropertyChangeListener {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8385732728740430466L;
    
    /** The size of the Race Track Panel. */
    private static final Dimension TRACK_SIZE = new Dimension(500, 400);
    
    /** The number of seconds in a minute. */
    private static final int MILLIS_PER_MIN = 60_000;
    
    /** The x and y location of the Track. */
    private static final int OFF_SET = 40;

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 25;

    /** The size of participants moving around the track. */
    private static final int OVAL_SIZE = 20;
    
    /** */
    private static final Color[] PARTICIPANT_COLORS = {Color.red, Color.blue, Color.yellow,
                                                       Color.green, Color.orange, Color.pink, 
                                                       Color.magenta, Color.cyan, Color.gray,
                                                       Color.white};
    
    /** The visible track. */
    private VisibleRaceTrack myTrack;
    
    /** */
    private Map<String, Ellipse2D> myParticipants;
    
    /** */
    private List<ArrayList<AbstractMessage>> myRaceList;
    
    
    /**
     * Construct a Track Panel. 
     */
    public Track() {
        super();
        setPreferredSize(TRACK_SIZE);
        setBackground(Color.white);
        setOpaque(true);
        final Border border = BorderFactory.createLineBorder(Color.blue);
        setBorder(BorderFactory.createTitledBorder(border, "Race Track"));
    }
    

    
    /**
     * Paints the VisibleTrack with a single ellipse moving around it.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        if (myTrack != null) {
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            g2d.draw(myTrack);
        }
        if (myParticipants != null) {
            int i = 0;
            for (Ellipse2D circle: myParticipants.values()) {
                g2d.setPaint(PARTICIPANT_COLORS[i]);
                i++;
                g2d.setStroke(new BasicStroke(1));
                g2d.fill(circle);
            }
        }
//        g2d.setPaint(Color.RED);
//        g2d.setStroke(new BasicStroke(1));
//        g2d.fill(myCircle);
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
                            if (splitMessage[0].equals("$T")) {
                                final String id = splitMessage[2];
                                final double distance = Double.parseDouble(splitMessage[3]);
                                final Point2D current = 
                                                myTrack.
                                                getPointAtDistance
                                                (distance % myTrack.getTrackLength());
                                myParticipants.get(id).setFrame(current.getX()
                                                                - OVAL_SIZE / 2, 
                                          current.getY() - OVAL_SIZE / 2, 
                                          OVAL_SIZE, 
                                          OVAL_SIZE);
                            }
                            repaint();
                        }  
                    }
                }
            } else if (newTime < oldTime) {            
                final ArrayList<AbstractMessage> messageList = myRaceList.get(newTime);
                for (AbstractMessage message: messageList) {
                    final String[] splitMessage = message.toString().split(":");
                    if (splitMessage[0].equals("$T")) {
                        final String id = splitMessage[2];
                        final double distance = Double.parseDouble(splitMessage[3]);
                        final Point2D current = 
                                        myTrack.
                                        getPointAtDistance
                                        (distance % myTrack.getTrackLength());
                        myParticipants.get(id).setFrame(current.getX() - OVAL_SIZE / 2, 
                                  current.getY() - OVAL_SIZE / 2, 
                                  OVAL_SIZE, 
                                  OVAL_SIZE);
                    }
                }
                repaint();
            }
        }
        if (PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
            myRaceList = (List<ArrayList<AbstractMessage>>) theEvent.getNewValue();
        }
        if (PROPERTY_HEADERLIST.equals(theEvent.getPropertyName())) {
            final ArrayList<String> header = (ArrayList<String>) theEvent.getNewValue();
            
            final int dimensionW = parseDimension(header.get(2));
            final int dimensionH = parseDimension(header.get(3));
            final int widthOne = ((int) TRACK_SIZE.getWidth() - (OFF_SET * 2)) / 5;
            final int heightOne = ((int) TRACK_SIZE.getWidth()  - 2 * OFF_SET) / 5;
            final int x = OFF_SET;
            final int y = (int) TRACK_SIZE.getHeight()  / 2 - (heightOne * dimensionH / 2);
            myTrack = new VisibleRaceTrack(x, y, widthOne * dimensionW,
                                           heightOne * dimensionH, MILLIS_PER_MIN / 2);
            
            myParticipants = new TreeMap<String, Ellipse2D>();
            for (int i = 7; i < header.size(); i++) {
                final String id = parseParticipantId(header.get(i));
                final double distance = parseParticipantStart(header.get(i));
                final Point2D start = myTrack.getPointAtDistance(distance);
                myParticipants.put(id, new Ellipse2D.Double(start.getX() - OVAL_SIZE / 2,
                                                          start.getY() - OVAL_SIZE / 2,
                                                          OVAL_SIZE,
                                                          OVAL_SIZE));                
            }
            repaint();
        }
    }
    
    /**
     * 
     * @param theMessage
     * @return
     */
    private int parseDimension(final String theMessage) {
        final String[] split = theMessage.split(":");
        return Integer.parseInt(split[split.length - 1]);
    }
    
    /**
     * 
     * @param theMessage
     * @return
     */
    private double parseParticipantStart(final String theMessage) {
        final String[] split = theMessage.split(":");
        return Double.parseDouble(split[2]);
    }
    
    /**
     * 
     * @param theMessage
     * @return
     */
    private String parseParticipantId(final String theMessage) {
        final String[] split = theMessage.split(":");
        return split[0].substring(1);
    }
    
}
