package view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;

/**
 * 
 * @author Dan
 *
 */
public class StatusBar extends JLabel implements PropertyChangeListener {
    private final static Dimension BAR_SIZE = new Dimension(100, 10);
    private int myTime;

    public StatusBar() {
        super();
        myTime = 0;
        setPreferredSize(BAR_SIZE);
        setBackground(Color.lightGray);
        setOpaque(true);
        setText("Time:  " + Utilities.formatTime(myTime));
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        myTime = (Integer) theEvent.getNewValue();
        setText("Time:  " + Utilities.formatTime(myTime));
    }

}
