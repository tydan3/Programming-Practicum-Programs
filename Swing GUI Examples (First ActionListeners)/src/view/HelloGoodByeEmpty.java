package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Demonstrating ActionListeners.
 * 
 * @author Charles Bryan
 * @version Autumn 2016
 */
public class HelloGoodByeEmpty extends JPanel {
    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = -1155574959121887493L;
    
    /** A button to say hello. */
    private final JButton myHelloButton;
    
    /** A button to say goodbye. */
    private final JButton myGoodbyeButton;
    
    /** A button to say Wait, come back. */
    private final JButton myWaitButton;
    
    /** A button to say Lambda. */
    private final JButton myLambdaButton;

    /** A button to say Method References. */
    private final JButton myMethodReferenceButton;

    /** A label to display the message. */
    private final JLabel myMessageLabel;

    /**
     * Initializes all of the fields.
     */
    public HelloGoodByeEmpty() {
        super();
        
        myMessageLabel = new JLabel("Message");
        myMessageLabel.setOpaque(true);
        
        myHelloButton = new JButton("Say Hello");
        myGoodbyeButton = new JButton("Say Goodbye");
        myWaitButton = new JButton("Wait...!");
        myLambdaButton = new JButton("Lambda Style");
        myMethodReferenceButton = new JButton("Method Reference");
        
        setUpComponents();
        addListeners();
    }
    
    /**
     * Add Listeners to the components. 
     */
    private void addListeners() {
        //Add an action listener from an inner class. Page 329
        myHelloButton.addActionListener(new MyHelloListener());
        
        
    }
    
    /**
     * Lay out the components and makes this frame visible.
     */
    private void setUpComponents() {

        setLayout(new BorderLayout());
        
        final JPanel labelPanel = new JPanel();
        labelPanel.add(myMessageLabel);
        add(labelPanel, BorderLayout.SOUTH);
        
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(myHelloButton);
        buttonPanel.add(myGoodbyeButton);
        buttonPanel.add(myWaitButton);
        buttonPanel.add(myLambdaButton);
        buttonPanel.add(myMethodReferenceButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    
    /**
     * Creates a JFrame to demonstrate this panel.
     * It is OK, even typical to include a main method 
     * in the same class file as a GUI for testing purposes. 
     * 
     * @param theArgs Command line arguments, ignored.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final HelloGoodByeEmpty mainPanel = 
                                new HelloGoodByeEmpty();
                
                // A size for the JFrame.
                //final Dimension frameSize = new Dimension(400, 400);
                
                final JFrame window = new JFrame("A Message");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                //window.setSize(frameSize);
                window.pack();
                window.setVisible(true);
            }
        });
    }   
    
    /**
     * An Inner class that is an ActionListener for Button clicks. 
     * 
     * @author Charles Bryan
     * @version Autumn 2019
     */
    private class MyHelloListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myMessageLabel.setText("Hello");
            
        }
        
    }

}