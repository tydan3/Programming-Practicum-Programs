package view;

import static model.PropertyChangeEnabledTimeControls.PROPERTY_TIME;

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
import javax.swing.JPanel;
import track.VisibleRaceTrack;

/**
 * A demo of PropertyChangeListener and track.jar. 
 * 
 * @author Charles Bryan
 * @version Autumn 2015
 */
public class TrackPanel extends JPanel implements PropertyChangeListener {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8385732728740430466L;
    
    /** The size of the Race Track Panel. */
    private static final Dimension TRACK_SIZE = new Dimension(750, 600);
    
    /** The number of seconds in a minute. */
    private static final int MILLIS_PER_MIN = 60_000;
    
    /** The x and y location of the Track. */
    private static final int OFF_SET = 40;

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 25;

    /** The size of participants moving around the track. */
    private static final int OVAL_SIZE = 20;
    
    /** The visible track. */
    private VisibleRaceTrack myTrack;
    
    /** The circle moving around the track. */
    private Ellipse2D myCircle;
    
    /**
     * Construct a Track Panel. 
     */
    public TrackPanel() {
        super();
        setPreferredSize(TRACK_SIZE);
        setupComponents();
    }
    
    /**
     * Setup and layout components. 
     */
    private void setupComponents() {
        //perform calculations to decide how big to make the track.
        //this track will be a 5x2 track. 
        final int width = (int) TRACK_SIZE.getWidth() - (OFF_SET * 2);
        final int height =
                        ((int) TRACK_SIZE.getWidth()  - 2 * OFF_SET) / 5 * 2;
        final int x = OFF_SET;
        final int y = (int) TRACK_SIZE.getHeight()  / 2 - height / 2;

        myTrack = new VisibleRaceTrack(x, y, width, height, MILLIS_PER_MIN / 2);
        final Point2D start = myTrack.getPointAtDistance(0.0);
        myCircle = new Ellipse2D.Double(start.getX() - OVAL_SIZE / 2,
                                        start.getY() - OVAL_SIZE / 2,
                                        OVAL_SIZE,
                                        OVAL_SIZE);
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
        
        g2d.setPaint(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.fill(myCircle);
    }
    


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            //Perform calculations to determine the current distance around
            // the track. We are basing distance as a linear progression in time.
            // The circle will travel around the track in exactly 30 seconds because
            // the track length is 500 and the circle moves 1 unit in distance for 
            // every millisecond of time. 
            final int time = (Integer) theEvent.getNewValue();
            final Point2D current = 
                            myTrack.getPointAtDistance(time % myTrack.getTrackLength());
            myCircle.setFrame(current.getX() - OVAL_SIZE / 2, 
                              current.getY() - OVAL_SIZE / 2, 
                              OVAL_SIZE, 
                              OVAL_SIZE);
            repaint();
        }
    }
}

/*
 * Add the follow statements to createAndShowGui method in TimeController. 
 * Uncomment them for a demo of track.jar.   
 */
//final TrackPanel trackPanel = new TrackPanel();
//time.addPropertyChangeListener(model.PropertyChangeEnabledTimeControls.PROPERTY_TIME,
//                               trackPanel);
//
//javax.swing.SwingUtilities.invokeLater(new Runnable() {
//    public void run() {
//        final JFrame timeViewFrame = new JFrame("Keep on movin'...");
//        timeViewFrame.setContentPane(trackPanel);
//        timeViewFrame.pack();
//        timeViewFrame.setVisible(true);
//    }
//});
