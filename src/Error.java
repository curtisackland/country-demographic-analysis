import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Error extends JFrame implements ActionListener {
	/**
	 * This class creates a pop-up window displaying the error that occurred
	 * @param message Error message to be displayed
	 */
	public Error(String message) {
		
		super("Error");
		
		JPanel err = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 10, 5, 10);
		
		JLabel errorMessage = new JLabel(message);
		JButton closeButton = new JButton("close");
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		err.add(errorMessage, constraints);
		constraints.gridy = 2;
		err.add(closeButton, constraints);
		
		closeButton.addActionListener(this);
		
		add(err);
		pack();
		setLocationRelativeTo(null);
	    setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	/**
	 * When the close button is pressed the error window is deleted
	 * @param e Action event caused by pressing the close button
	 */
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
	}
}
