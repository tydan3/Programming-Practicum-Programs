/**
 * TCSS 305 - Autumn 2019
 * Assignment 4(a) - Raceday
 */
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_HEADERLIST;

import application.Main;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.AbstractMessage;
import model.PropertyChangeEnabledRaceControls;
import model.Race;
import view.Leaderboard;
import view.StatusBar;
import view.Track;
import view.Utilities;
import view.ViewPanel;

/**
 * Defines controls for the race panel.
 * 
 * @author Charles Bryan
 * @author Daniel Ty
 * @version Nov 15 2019
 */
public class ControllerPanel extends JPanel implements PropertyChangeListener {

    /** The serialization ID. */
    private static final long serialVersionUID = -6759410572845422202L;

    /** Amount in Pixels for the Horizontal margin. */
    private static final int OUTPUT_HORIZONTAL = 50; 
    
    /** Amount in Pixels for the Vertical margin. */
    private static final int OUTPUT_VERTICAL = 10; 
    
    /** Amount in Pixels for the Border margin. */
    private static final int BORDER_FIVE = 5; 
    
    /** Amount in Pixels for the Border margin. */
    private static final int BORDER_TEN = 10; 
    
    /** Amount in Pixels for the Border margin. */
    private static final int BORDER_TWENTYFIVE = 25;
    
    /** Amount of milliseconds between each call to the timer. */
    private static final int TIMER_FREQUENCY = 31;    
    
    /** A reference to the backing Race Model. */
    private final PropertyChangeEnabledRaceControls myRace;

    /** Display of messages coming from the Race Model. */
    private final JTextArea myOutputArea;

    /** Panel to display CheckBoxs for each race Participant. */
    private final JPanel myParticipantPanel;

    /** A view on the race model  that displays the current race time. */
    private final JLabel myTimeLabel;

    /** A controller and view of the Race Model. */
    private final JSlider myTimeSlider;
        
    /** The list of javax.swing.Actions that make up the ToolBar (Controls) buttons. */
    private final List<Action> myControlActions;

    /** The timer that advances the Race Model. */
    private final Timer myTimer;

    /** Container to hold the different output areas. */
    private final JTabbedPane myTabbedPane;    
    
    /** The time multiplier. */
    private int myMultiplier;
    
    /** */
    private int myTime;
    
    /** */
    private int myMaxTime;
    
    /** */
    private List<String> myHeaderList;
    
    /** */
    private List<ArrayList<AbstractMessage>> myRaceList;
    
    /** */
    private JMenuItem myInfoItem;
    
    /** */
    private AbstractSimpleAction myPlay;
    
    /** */
    private boolean myIsLooping;
    
    /**
     * Construct a ControllerPanel.
     * 
     * @param theRace the backing race model
     */
    public ControllerPanel(final PropertyChangeEnabledRaceControls theRace) {
        super();
        myOutputArea = new JTextArea(OUTPUT_VERTICAL, OUTPUT_HORIZONTAL);
        myTimeLabel = new JLabel(Utilities.formatTime(0));
        myRace = theRace;
        myTimeSlider = new JSlider(0, 0, 0);
        myControlActions = new ArrayList<>();
        myTabbedPane = new JTabbedPane();
        myParticipantPanel = new JPanel();        
        myTimer = new Timer(TIMER_FREQUENCY, e -> myRace.advance(TIMER_FREQUENCY 
                                                                 * myMultiplier));
//                            new ActionListener() {
//
//            @Override
//            public void actionPerformed(final ActionEvent theEvent) {
//                myRace.advance(TIMER_FREQUENCY * myMultiplier);
//            }
//            
//        });
        myMultiplier = 1;

        addListeners();
        setUpComponents();
    }
        
    /**
     * Displays a simple JFrame.
     */
    private void setUpComponents() {
        setLayout(new BorderLayout());
        
        // JPanel is a useful container for organizing components inside a JFrame
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_TEN, BORDER_TEN,
                                                            BORDER_TEN, BORDER_TEN));

        mainPanel.add(buildSliderPanel(), BorderLayout.NORTH);

        myOutputArea.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(myOutputArea);
        scrollPane.
            setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        
        final JScrollPane participantScrollPane = new JScrollPane(myParticipantPanel);
        participantScrollPane.setPreferredSize(scrollPane.getSize());

        myTabbedPane.addTab("Data Output Stream", scrollPane);
        myTabbedPane.addTab("Race Participants", participantScrollPane);

        mainPanel.add(myTabbedPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        add(buildToolBar(), BorderLayout.SOUTH);

    }

    /**
     * Builds the panel with the time slider and time label.
     * 
     * @return the panel
     */
    private JPanel buildSliderPanel() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(BORDER_FIVE, BORDER_FIVE,
                                                        BORDER_TWENTYFIVE, BORDER_FIVE));
        
        myTimeSlider.setBorder(BorderFactory.createEmptyBorder(BORDER_FIVE, BORDER_FIVE,
                                                               BORDER_FIVE, BORDER_FIVE));
        
        panel.add(myTimeSlider, BorderLayout.CENTER);
        
        myTimeSlider.setEnabled(false);
        myTimeSlider.addChangeListener(e -> {myTimeLabel.setText(Utilities.formatTime(myTimeSlider.getValue()));
                                          myRace.moveTo(myTimeSlider.getValue());                 
                                       });
//                                       new ChangeListener() {
//
//            @Override
//            public void stateChanged(final ChangeEvent theEvent) {
//                myTimeLabel.setText(Utilities.formatTime(myTimeSlider.getValue()));
//                myRace.moveTo(myTimeSlider.getValue());
//            }
//            
//        });

        myTimeLabel.setBorder(BorderFactory.
                              createCompoundBorder(BorderFactory.createEtchedBorder(),
                                              BorderFactory.createEmptyBorder(BORDER_FIVE,
                                                                              BORDER_FIVE,
                                                                              BORDER_FIVE,
                                                                              BORDER_FIVE)));
        final JPanel padding = new JPanel();
        padding.add(myTimeLabel);
        panel.add(padding, BorderLayout.EAST);

        return panel;
    }
    
    /**
     * Constructs a JMenuBar for the Frame.
     * @return the Menu Bar
     */
    private JMenuBar buildMenuBar() {
        final JMenuBar bar = new JMenuBar();
        bar.add(buildFileMenu());
        bar.add(buildControlsMenu(myControlActions));
        bar.add(buildHelpMenu());
        return bar;
    }
    
    /**
     * Builds the file menu for the menu bar.
     * 
     * @return the File menu
     */
    private JMenu buildFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem load = new JMenuItem("Load Race...");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final FileLoader loader = new FileLoader();
                loader.doInBackground();
            }            
        });
        fileMenu.add(load);
        fileMenu.addSeparator();
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        return fileMenu;
    }
    
    /**
     * Build the Controls JMenu.
     * 
     * @param theActions the Actions needed to add/create the items in this menu
     * @return the Controls JMenu
     */
    private JMenu buildControlsMenu(final List<Action> theActions) {
        final JMenu controlsMenu = new JMenu("Controls");

        for (final Action a : theActions) {
            controlsMenu.add(a);
        }

        return controlsMenu;
    }
    
    /**
     * Build the Help JMenu.
     * 
     * @return the Help JMenu
     */
    private JMenu buildHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");

        myInfoItem = new JMenuItem("Race Info...");
        myInfoItem.setEnabled(false);
        myInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final StringBuilder info = new StringBuilder();
                info.append(myHeaderList.get(0).substring(6) + "\n");
                info.append("Track type: " + myHeaderList.get(1).substring(7) + "\n");
                final int totalTime = Integer.parseInt(myHeaderList.get(5).substring(6));
                info.append("Total time: " + Utilities.formatTime(totalTime)  + "\n");
                info.append("Lap distance: " + myHeaderList.get(4).substring(10));
                
                JOptionPane.showMessageDialog(ControllerPanel.this, info.toString(),
                                              "Race Information",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(myInfoItem);

        final JMenuItem aboutItem = new JMenuItem("About...");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                ImageIcon img = (ImageIcon) new ImageIcon(Main.class.getResource
                                                                ("/race_icon.png"));
                img = new ImageIcon(img.getImage().getScaledInstance(24, -1,
                                                       java.awt.Image.SCALE_SMOOTH));
                JOptionPane.showMessageDialog(ControllerPanel.this,
                                            "Daniel Ty\nWinter 2019\nTCSS 305",
                                            "About", JOptionPane.INFORMATION_MESSAGE, img);
            }
            
        });
        helpMenu.add(aboutItem);
        return helpMenu;
    }

    /**
     * Build the toolbar from the Actions list.
     * 
     * @return the toolbar with buttons for all of the Actions in the list
     */
    private JToolBar buildToolBar() {
        final JToolBar toolBar = new JToolBar();
        for (final Action a : myControlActions) {
            final JButton b = new JButton(a);
            b.setHideActionText(true);
            toolBar.add(b);
        }
        return toolBar;
    }
    
    /**
     * Add actionListeners to the buttons. 
     */
    private void addListeners() {
        buildActions();
        myRace.addPropertyChangeListener(PROPERTY_LOAD, this);
        myRace.addPropertyChangeListener(PROPERTY_TIME, this);
        myRace.addPropertyChangeListener(PROPERTY_HEADERLIST, this);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Property change
        if (PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
            myRaceList = (List<ArrayList<AbstractMessage>>) theEvent.getNewValue();
            ControllerPanel.this.setCursor(Cursor.getDefaultCursor());
            myTimeSlider.setEnabled(true);
            myTimeSlider.setValue(0);
            for (final Action x: myControlActions) {
                x.setEnabled(true);
            }
            myOutputArea.setText("File loaded.\n");
        }
        
        if (PROPERTY_HEADERLIST.equals(theEvent.getPropertyName())) {
            myHeaderList = (List<String>) theEvent.getNewValue();
            myInfoItem.setEnabled(true);            
            for (int i = 7; i < myHeaderList.size(); i++) {
                final String[] msg = myHeaderList.get(i).split(":");
                final int id = Integer.parseInt(msg[0].substring(1));
                final JCheckBox participant = new JCheckBox(msg[1]);
                
                participant.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent theEvent) {
                        myRace.toggleParticipant(id, participant.isSelected());
                    }                    
                });
                
                myParticipantPanel.add(participant, true);     
            }
            myMaxTime = (Integer) Integer.parseInt(myHeaderList.get(5).substring(6));
            myTimeSlider.setMaximum(myMaxTime);
            myTimeSlider.setMajorTickSpacing(60000);
            myTimeSlider.setMinorTickSpacing(10000);
            myTimeSlider.setPaintTicks(true);
        }
        
        
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            //TODO
            final int newTime = (Integer) theEvent.getNewValue();
            final int oldTime = (Integer) theEvent.getOldValue();
            if (newTime >= myMaxTime) {
                myRace.moveTo(myMaxTime);
                myTimer.stop();
                myPlay.setEnabled(false);
                myPlay.putValue(Action.NAME, "Play");
                myPlay.setIcon(new ImageIcon(Main.class.getResource("/ic_play.png")));
                myTimeSlider.setEnabled(true);
            } else {
                myPlay.setEnabled(true);
            }
            if (newTime > oldTime) {
                if (newTime < myRaceList.size()) {
                    for (int i = oldTime; i <= newTime; i++) {
                        final StringBuilder result = new StringBuilder();
                        for (AbstractMessage message 
                            : myRaceList.get(i)) {
                            result.append(message + "\n");
                        }
                        myOutputArea.append(result.toString());
                    }
                } else if (newTime >= myRaceList.size()) {
                    for (int i = oldTime; i < myRaceList.size(); i++) {
                        final StringBuilder result = new StringBuilder();
                        for (AbstractMessage message 
                            : myRaceList.get(i)) {
                            result.append(message + "\n");
                        }
                        myOutputArea.append(result.toString());
                    }
                }
            } else if (newTime < oldTime) {
                final StringBuilder result = new StringBuilder();
                for (AbstractMessage message: myRaceList.get(newTime)) {
                    result.append(message + "\n");
                }
                myOutputArea.append(result.toString());
            }
            myTimeLabel.setText(Utilities.formatTime(newTime));
            myTimeSlider.setValue(newTime);
        }
    }
    
    /**
     * Instantiate and add the Actions.
     */
    private void buildActions() {
        final AbstractSimpleAction restart = new RestartAction("Restart", "/ic_restart.png");
        myPlay = new PlayAction("Play", "/ic_play.png");
        final AbstractSimpleAction times = new TimesAction("Times One", "/ic_one_times.png");
        final AbstractSimpleAction singleRace =
                        new LoopAction("Single Race", "/ic_repeat.png");
        final AbstractSimpleAction clear = new ClearAction("Clear", "/ic_clear.png");
        myControlActions.add(restart);
        myControlActions.add(myPlay);
        myControlActions.add(times);
        myControlActions.add(singleRace);
        myControlActions.add(clear);
        for (final Action x: myControlActions) {
            x.setEnabled(false);
        }
    }
    
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        final JFrame frame = new JFrame("Race Track");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final ImageIcon img = (ImageIcon) new ImageIcon(Main.class.getResource
                                                        ("/race_icon.png"));
        frame.setIconImage(img.getImage().getScaledInstance(16, -1,
                                                            java.awt.Image.SCALE_SMOOTH));
        final PropertyChangeEnabledRaceControls race = new Race();
        
        //Create and set up the content pane.
        final ControllerPanel pane = new ControllerPanel(race);
        
        //Add the JMenuBar to the frame:
        frame.setJMenuBar(pane.buildMenuBar());
        
        pane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(pane);
        
        //Display race track
        final Track track = new Track();
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                       track);
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.
                                       PROPERTY_LOAD,
                                       track);
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.
                                       PROPERTY_HEADERLIST,
                                       track);
        
        final Leaderboard leaderboard = new Leaderboard();
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                       leaderboard);
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.
                                       PROPERTY_LOAD,
                                       leaderboard);
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.
                                       PROPERTY_HEADERLIST,
                                       leaderboard);
        
        final StatusBar statusBar = new StatusBar();      
        race.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                       statusBar);
        
        final ViewPanel viewPanel = new ViewPanel();
        viewPanel.add(track);
        viewPanel.add(leaderboard);
        viewPanel.add(statusBar);
      
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame viewFrame = new JFrame("Track Panel");
                viewFrame.setContentPane(viewPanel);
                viewPanel.setOpaque(true); //content panes must be opaque
                viewFrame.pack();
                viewFrame.setVisible(true);
            }
        });
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    

    /**
     * This is a simple implementation of an Action.
     * You will most likely not use this implementation in your final solution. Either
     * create your own Actions or alter this to suit the requirements for this assignment. 
     * 
     * @author Charles Bryan
     * @version Autumn 2019
     */
    private abstract class AbstractSimpleAction extends AbstractAction {
        /** The serialization ID. */
        private static final long serialVersionUID = -3160383376683650991L;
        
        /**
         * Constructs a SimpleAction.
         * 
         * @param theText the text to display on this Action
         * @param theIcon the icon to display on this Action
         */
        AbstractSimpleAction(final String theText, final String theIcon) {
            super(theText);
            
            setIcon(new ImageIcon(getRes(theIcon)));

        }
        /**
         * Wrapper method to get a system resource.
         * 
         * @param theResource the name of the resource to retrieve
         * @return the resource
         */
        protected URL getRes(final String theResource) {
            return Main.class.getResource(theResource);
        }
        /**
         * Helper to set the Icon to both the Large and Small Icon values. 
         * @param theIcon the icon to set for this Action 
         */
        protected void setIcon(final ImageIcon theIcon) {
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
        @Override
        public abstract void actionPerformed(ActionEvent theEvent);
    }
    
    /**
     * 
     * @author Dan
     *
     */
    private class RestartAction extends AbstractSimpleAction {
        /** The serialization ID. */
        private static final long serialVersionUID = -2978577729462704256L;
        
        /**
         * Constructs a AbstractSimpleAction.
         * 
         * @param theText the text to display on this Action
         * @param theIcon the icon to display on this Action
         */
        RestartAction(final String theText, final String theIcon) {
            super(theText, theIcon);           
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myRace.moveTo(0);
        }
    }

    /**
     * 
     * @author Dan
     *
     */
    private class PlayAction extends AbstractSimpleAction {
        /** The serialization ID. */
        private static final long serialVersionUID = -2978577729462704256L;
        /**
         * Constructs a SimpleAction.
         * 
         * @param theText the text to display on this Action
         * @param theIcon the icon to display on this Action
         */
        PlayAction(final String theText, final String theIcon) {
            super(theText, theIcon);
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myTimer.isRunning()) {
                myTimer.stop();
                putValue(Action.NAME, "Play");
                setIcon(new ImageIcon(getRes("/ic_play.png")));
                myTimeSlider.setEnabled(true);
            } else {
                myTimer.start();
                putValue(Action.NAME, "Pause");
                setIcon(new ImageIcon(getRes("/ic_pause.png")));
                myTimeSlider.setEnabled(false);
            }
        }
    }
    
    /**
     * 
     * @author Dan
     *
     */
    private class TimesAction extends AbstractSimpleAction {
        /** The serialization ID. */
        private static final long serialVersionUID = -2978577729462704256L;
        
        /**
         * Constructs a SimpleAction.
         * 
         * @param theText the text to display on this Action
         * @param theIcon the icon to display on this Action
         */
        TimesAction(final String theText, final String theIcon) {
            super(theText, theIcon);
           
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myMultiplier == 1) {
                myMultiplier = 4;
                putValue(Action.NAME, "Times Four");
                setIcon(new ImageIcon(getRes("/ic_four_times.png")));
            } else {
                myMultiplier = 1;
                putValue(Action.NAME, "Times One");
                setIcon(new ImageIcon(getRes("/ic_one_times.png")));
            }
        }
    }
    
    /**
     * 
     * @author Dan
     *
     */
    private class LoopAction extends AbstractSimpleAction {
        /** The serialization ID. */
        private static final long serialVersionUID = -2978577729462704256L;       
        
        /**
         * Constructs a SimpleAction.
         * 
         * @param theText the text to display on this Action
         * @param theIcon the icon to display on this Action
         */
        LoopAction(final String theText, final String theIcon) {
            super(theText, theIcon);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            //TODO
            if (myIsLooping) {
                myIsLooping = false;
                putValue(Action.NAME, "Single Race");
                setIcon(new ImageIcon(getRes("/ic_repeat.png")));
            } else {
                myIsLooping = true;
                putValue(Action.NAME, "Loop Race");
                setIcon(new ImageIcon(getRes("/ic_repeat_color.png")));
            }
        }
    }
    
    /**
     * 
     * @author Dan
     *
     */
    private class ClearAction extends AbstractSimpleAction {
        /** The serialization ID. */
        private static final long serialVersionUID = -2978577729462704256L;
        
        /**
         * Constructs a SimpleAction.
         * 
         * @param theText the text to display on this Action
         * @param theIcon the icon to display on this Action
         */
        ClearAction(final String theText, final String theIcon) {
            super(theText, theIcon);           
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myOutputArea.setText("");
        }
    }  

    /**
     * A worker thread to load the files.
     * 
     * @author Charles Bryan
     * @version Autumn 2019
     */
    private class FileLoader extends SwingWorker<Boolean, Void> {

        @Override
        public Boolean doInBackground() {
            boolean result = true;
            ControllerPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ControllerPanel.this.setCursor(Cursor.getDefaultCursor());
            myTimeSlider.setEnabled(false);
            for (final Action x: myControlActions) {
                x.setEnabled(false);
            }
            final JFileChooser jfc = new JFileChooser(".");
            final int choice = jfc.showOpenDialog(ControllerPanel.this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                try {
                    myRace.loadRace(jfc.getSelectedFile());
                } catch (final IOException exception) {
                    System.out.println("Error");
                    JOptionPane.showMessageDialog(ControllerPanel.this, "Error loading file.",
                                                  "Error!", JOptionPane.ERROR_MESSAGE);
                    result = false;
                }
            }
            return result;
        }

//        @Override
//        public void done() {
//            ControllerPanel.this.setCursor(Cursor.getDefaultCursor());
//            try {
//                final boolean resultOfFileLoad = get();
//                
//                /*
//                 * Do something with the result of reading the file. 
//                 */
//                System.out.println("hello");
//                
//            } catch (final InterruptedException ex1) {
//                ex1.printStackTrace();
//            } catch (final ExecutionException ex2) {
//                ex2.printStackTrace();
//            }
//
//        }

    }
    
}