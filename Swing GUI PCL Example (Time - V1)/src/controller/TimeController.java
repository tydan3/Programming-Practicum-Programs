package controller;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Time;
import model.TimeControls;
import view.TimePanel;

/**
 *  A controller for a Time object.
 * 
 *  @author Charles Bryan
 *  @version Autumn 2015 
 */
public class TimeController extends JPanel {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8452917670991316606L;
    
 
    /** Amount in Pixels for the Horizontal margin. */
    private static final int HORIZONATAL_MARGIN = 75; 
    
    /** Amount in Pixels for the Vertical margin. */
    private static final int VERTICALL_MARGIN = 75; 
    
    /** Amount of milliseconds between each call to the timer. */
    private static final int TIMER_FREQUENCY = 31; 
    
    /** Start text for the start/stop button. */
    private static final String BUTTON_ICON_START = "./images/ic_start.png"; 
    
    /** Stop text for the start/stop button. */
    private static final String BUTTON_ICON_STOP = "./images/ic_stop.png"; 
    
    /** The Color object this class controls. */
    private final TimeControls myTime;
    
    /** The timer to control how often to advance the Time object. */ 
    private final Timer myTimer;
   
    /**
     * Constructs a ColorSlider.
     * 
     * @param theTime the color object this class controls
     */
    public TimeController(final TimeControls theTime) {
        super(new BorderLayout());
        
        myTime = theTime;
        myTimer = new Timer(TIMER_FREQUENCY, this::handleTimer);

        layoutComponents();
    }
    
    /**
     * Lay out the components.
     */
    private void layoutComponents() {
        final JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(VERTICALL_MARGIN, 
                                                          HORIZONATAL_MARGIN, 
                                                          VERTICALL_MARGIN, 
                                                          HORIZONATAL_MARGIN));
        
        final JButton butt = new JButton();
        setIcon(BUTTON_ICON_START, butt);
        butt.addActionListener(this::handleButtonPress);
        
        content.add(butt);
        add(content, BorderLayout.CENTER);
    }
    
    /**
     * Helper to size and set the Icon for the button. 
     * @param theFile the icon file
     * @param theButton the button needing an icon set
     */
    private void setIcon(final String theFile, final JButton theButton) {
        final ImageIcon icon = new ImageIcon(theFile);
        final Image largeImage =
            icon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        theButton.setIcon(largeIcon);
    }
    
    /**
     * Event handler for the single button.
     * @param theEvent the fired event which includes the originating button press
     */
    private void handleButtonPress(final ActionEvent theEvent) { //NOPMD
        if (myTimer.isRunning()) {
            myTimer.stop();
            setIcon(BUTTON_ICON_START, (JButton) theEvent.getSource());
        } else {
            myTimer.start();
            setIcon(BUTTON_ICON_STOP, (JButton) theEvent.getSource());
        }
    }
    
    /**
     * Event handler for the timer. 
     * @param theEvent the fired event
     */
    private void handleTimer(final ActionEvent theEvent) { //NOPMD
        myTime.advance(TIMER_FREQUENCY);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * 
     * NOTE: This is the place where all of the parts and pieces of this project are in 
     *      the same place. This is where we should instantiate our MOdel and add it to the
     *      controller and view.  
     */
    public static void createAndShowGUI() {
        // Create and set up the window.
        final JFrame frame = new JFrame("Observer Design Pattern Demo (Time - V1)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // Instantiate the Time model.
        final Time time = new Time();
        
        // Create and set up the content pane for the Controller Window.
        final TimeController pane = new TimeController(time);
        pane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(pane);
            
        // Create a time panel to listen to and demonstrate our 
        // Time.
        final TimePanel timePanel = new TimePanel();
        time.addPropertyChangeListener(model.PropertyChangeEnabledTimeControls.PROPERTY_TIME,
                                       timePanel);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame timeViewFrame = new JFrame("The clock ticks...");
                timeViewFrame.setContentPane(timePanel);
                timeViewFrame.pack();
                timeViewFrame.setVisible(true);
            }
        });
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}