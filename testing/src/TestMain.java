import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("Hello");
				frame.setVisible(true);
				frame.setSize(500, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
			}
			
		});
	}

}
