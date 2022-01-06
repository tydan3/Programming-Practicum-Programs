package controller;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
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
import view.TrackPanel;

/**
 *  A controller for Time Model.
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
    
    /** Start text for the speed button. */
    private static final String BUTTON_TEXT_REGULAR = "Regular"; 
    
    /** Stop text for the speed button. */
    private static final String BUTTON_TEXT_FAST = "Fast"; 
    
    /** Start text for the start/stop button. */
    private static final String BUTTON_ICON_REGULAR = "./images/ic_happy.png"; 
    
    /** Stop text for the start/stop button. */
    private static final String BUTTON_ICON_FAST = "./images/ic_sad.png"; 
    
    /** Value for regular multiplier. */
    private static final int SPEED_REGULAR = 1;

    /** Value for fast multiplier. */
    private static final int SPEED_FAST = 10;
    
    /** The Color object this class controls. */
    private final TimeControls myTime;
    
    /** The timer to control how often to advance the Time object. */ 
    private final Timer mySwingTimer;
    
    /** The Actions for the ToolBar and File Menu. */
    private final List<Action> myActions;
    
    /** The time multiplier. */
    private int myMultiplier;
    
    /**
     * Constructs a TimeController.
     * 
     * @param theTime the color object this class controls
     */
    public TimeController(final TimeControls theTime) {
        super(new BorderLayout());
        
        myTime = theTime;
        mySwingTimer = new Timer(TIMER_FREQUENCY, this::handleTimer);
        
        myActions = new ArrayList<>();
        myActions.add(new StartAction(BUTTON_TEXT_START));
        myActions.add(new SpeedAction(BUTTON_TEXT_REGULAR));

        myMultiplier = 1;
        layoutComponents();
    }
    
    /**
     * Lay out the components.
     */
    private void layoutComponents() {
        final JPanel content = new JPanel();
        
        //A content panel
        content.setBorder(BorderFactory.createEmptyBorder(VERTICALL_MARGIN, 
                                                          HORIZONATAL_MARGIN, 
                                                          VERTICALL_MARGIN, 
                                                          HORIZONATAL_MARGIN));
        final JToolBar toolBar = new JToolBar();
        
        for (final Action a : myActions) {
            //add a button based on the action to the content panel
            content.add(new JButton(a));
            
            //add a button based on the action to the Tool Bar
            final JButton butt = new JButton(a);
            butt.setHideActionText(true); // <- look up in documentation
            toolBar.add(butt);
        }

        add(content, BorderLayout.CENTER);
        add(toolBar, BorderLayout.SOUTH);
    }
    
    /**
     * Build a menubar for this GUI.
     * @return the menubar
     */
    private JMenuBar createMenuBar() {
        final JMenuBar bar = new JMenuBar();
        final JMenu controls = new JMenu("Controls");
        
        for (final Action a : myActions) {
            controls.add(new JMenuItem(a));
        }
        bar.add(controls);
        
        return bar;
    }
    
    /**
     * Event handler for the timer. 
     * @param theEvent the fired event
     */
    private void handleTimer(final ActionEvent theEvent) { //NOPMD
        myTime.advance(TIMER_FREQUENCY * myMultiplier);
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
        //Create and set up the window.
        final JFrame frame = new JFrame("Observer Design Pattern Demo (Time - V2)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final Time time = new Time();
        
        //Create and set up the content pane.
        final TimeController pane = new TimeController(time);
        pane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(pane);
        frame.setJMenuBar(pane.createMenuBar());
            
        //Create a time panel to listen to and demonstrate our 
        //ObservableTime.
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
        
        final TrackPanel trackPanel = new TrackPanel();
        time.addPropertyChangeListener(model.PropertyChangeEnabledTimeControls.PROPERTY_TIME,
                                     trackPanel);
      
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame timeViewFrame = new JFrame("Keep on movin'...");
                timeViewFrame.setContentPane(trackPanel);
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
    class StartAction extends AbstractAction {

        /**  
         * A generated serial version UID for object Serialization. 
         * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
         */
        private static final long serialVersionUID = 1234567890L;

        /**
         * Construct a StartAction. 
         * @param theText the title for the action
         */
        StartAction(final String theText) {
            super(theText);
            
            setIcon(new ImageIcon(BUTTON_ICON_START));
            
            //Or set the text using the put method instead of super(theText)
//            putValue(Action.NAME, theText);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (mySwingTimer.isRunning()) {
                mySwingTimer.stop();
                putValue(Action.NAME, BUTTON_TEXT_START);
                setIcon(new ImageIcon(BUTTON_ICON_START));
            } else {
                mySwingTimer.start();
                putValue(Action.NAME, BUTTON_TEXT_STOP);
                setIcon(new ImageIcon(BUTTON_ICON_STOP));
            }
        }
        
        /**
         * Helper to set the Icon to both the Large and Small Icon values. 
         * @param theIcon the icon to set for this Action 
         */
        private void setIcon(final ImageIcon theIcon) {
            final ImageIcon icon = (ImageIcon) theIcon;
            final Image largeImage =
                icon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon largeIcon = new ImageIcon(largeImage);
            putValue(Action.LARGE_ICON_KEY, largeIcon);
            
            final Image smallImage =
                icon.getImage().getScaledInstance(12, -1, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon smallIcon = new ImageIcon(smallImage);
            putValue(Action.SMALL_ICON, smallIcon);
        }
    }
    
    
    /**
     * An action to encapsulate the Speed Button.
     * @author Charles Bryan
     * @version Autumn 2018
     */
    class SpeedAction extends AbstractAction {

        /**  
         * A generated serial version UID for object Serialization. 
         * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
         */
        private static final long serialVersionUID = 1234567890L;

        /**
         * Construct a StartAction. 
         * @param theText the title for the action
         */
        SpeedAction(final String theText) {
            super(theText);
            
            setIcon(new ImageIcon(BUTTON_ICON_REGULAR));
                        
            //Or set the text using the put method instead of super(theText)
//            putValue(Action.NAME, theText);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myMultiplier == SPEED_REGULAR) {
                myMultiplier = SPEED_FAST;
                putValue(Action.NAME, BUTTON_TEXT_FAST);
                setIcon(new ImageIcon(BUTTON_ICON_FAST));
            } else {
                myMultiplier = SPEED_REGULAR;
                putValue(Action.NAME, BUTTON_TEXT_REGULAR);
                setIcon(new ImageIcon(BUTTON_ICON_REGULAR));
            }
        }
        
        /**
         * Helper to set the Icon to both the Large and Small Icon values. 
         * @param theIcon the icon to set for this Action 
         */
        private void setIcon(final ImageIcon theIcon) {
            final ImageIcon icon = (ImageIcon) theIcon;
            final Image largeImage =
                icon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon largeIcon = new ImageIcon(largeImage);
            putValue(Action.LARGE_ICON_KEY, largeIcon);
            
            final Image smallImage =
                icon.getImage().getScaledInstance(12, -1, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon smallIcon = new ImageIcon(smallImage);
            putValue(Action.SMALL_ICON, smallIcon);
        }
    }
    
}