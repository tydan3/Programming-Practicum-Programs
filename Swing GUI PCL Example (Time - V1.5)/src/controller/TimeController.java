package controller;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
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
    private static final String BUTTON_TEXT_START = "Start"; 
    
    /** Stop text for the start/stop button. */
    private static final String BUTTON_TEXT_STOP = "Stop"; 
    
    /** Start text for the start/stop button. */
    private static final String BUTTON_ICON_START = "./images/ic_start.png"; 
    
    /** Stop text for the start/stop button. */
    private static final String BUTTON_ICON_STOP = "./images/ic_stop.png"; 
    
    /** The Color object this class controls. */
    private final TimeControls myTime;
    
    /** The timer to control how often to advance the Time object. */ 
    private final Timer myTimer;
    
    /** The action for the start stop button. */
    private final Action myStartStopAction;
   
    /**
     * Constructs a ColorSlider.
     * 
     * @param theTime the color object this class controls
     */
    public TimeController(final TimeControls theTime) {
        super(new BorderLayout());
        
        myTime = theTime;
        myTimer = new Timer(TIMER_FREQUENCY, this::handleTimer);

        myStartStopAction = new StartStopAction(BUTTON_TEXT_START, BUTTON_ICON_START);
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
        
        final JButton butt = new JButton(myStartStopAction);

        
        content.add(butt);
        add(content, BorderLayout.CENTER);
        
        final JToolBar toolbar = new JToolBar();
        final JButton toolButt = new JButton(myStartStopAction);
        toolButt.setHideActionText(true);
        
        toolbar.add(toolButt);
        add(toolbar, BorderLayout.SOUTH);
    }
    
    /**
     * Build a menubar for this GUI.
     * @return the menubar
     */
    private JMenuBar setupMenuBar() {
        final JMenuBar bar = new JMenuBar();
        final JMenu controls = new JMenu("Controls");
        final JMenuItem menuButt = new JMenuItem(myStartStopAction);
        
        controls.add(menuButt);
        bar.add(controls);
        return bar;
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
     *      the same place. This is where we should instantiate our Model and add it to the
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
        frame.setJMenuBar(pane.setupMenuBar());
            
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
    
    /**
     * An action to encapsulate the Start Button.
     * @author Charles Bryan
     * @version Autumn 2018
     */
    class StartStopAction extends AbstractAction {

        /**  
         * A generated serial version UID for object Serialization. 
         * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
         */
        private static final long serialVersionUID = 1234567890L;

        /**
         * Construct a StartAction. 
         * @param theText the title for the action
         * @param theFile the name of the image file 
         */
        StartStopAction(final String theText, final String theFile) {
            super(theText);
            
            setIcon(theFile);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myTimer.isRunning()) {
                myTimer.stop();
                setIcon(BUTTON_ICON_START);
                putValue(NAME, BUTTON_TEXT_START);
            } else {
                myTimer.start();
                setIcon(BUTTON_ICON_STOP);
                putValue(NAME, BUTTON_TEXT_STOP);
            }
            
        }
        
        /**
         * Helper to size and set the Icon for the button. 
         * @param theFile the icon file
         */
        private void setIcon(final String theFile) {
            final ImageIcon icon = new ImageIcon(theFile);
            final Image largeImage =
                icon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon largeIcon = new ImageIcon(largeImage);
            final Image smallImage =
                icon.getImage().getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon smallIcon = new ImageIcon(smallImage);
            putValue(LARGE_ICON_KEY, largeIcon);
            putValue(SMALL_ICON, smallIcon);
        }
        
    }
}