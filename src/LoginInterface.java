/**
 * This class represents the Login Interface handling the sign in process to the MainUI
 * This includes taking in user input, and all visuals (text fields, buttons, and text)
 * @author Curtis Ackland
 */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginInterface extends JFrame implements ActionListener {

	private JTextField usernameField = new JTextField(20);
	private JPasswordField passwordField = new JPasswordField(20);
	private static volatile Boolean openMainUI = false;
	private static volatile Boolean incorrectCred = false;
	
	/**
	 * Constructor initializes and draws all visuals for the login menu
	 */
	public LoginInterface() {
		super("Login");

		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		JLabel usernameText = new JLabel("Username: ");
		JLabel passwordText = new JLabel("Password: ");

		JButton loginButton = new JButton("Login");

		constraints.gridx = 0;
		constraints.gridy = 0;
		loginPanel.add(usernameText, constraints);

		constraints.gridx = 1;
		loginPanel.add(usernameField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		loginPanel.add(passwordText, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		loginPanel.add(passwordField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		loginPanel.add(loginButton, constraints);

		loginPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Enter Credentials"));

		loginButton.addActionListener(this);
		loginButton.setActionCommand("Attempted Login");

		add(loginPanel);
		pack();
		setLocationRelativeTo(null);
		loginPanel.setBackground(new Color(220, 220, 220));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * When the login button is pressed this function is notified and checks
	 * the user's credentials
	 * @param ActionEvent is the command sent when the login Button is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		if ("Attempted Login".equals(e.getActionCommand())) {
			try {
				if (CredentialValidator.checkCredentials(usernameField.getText().toString(),
						passwordField.getText().toString())) {
					openMainUI = true;
				} else {
					if (usernameField.getText().equals("")) {
						new Error("Please enter your credentials to login");
					} else {
						incorrectCred = true;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		try {
			new FileAdapter();
		} catch (IOException e) {
			new Error("File could not be read");
			return;
		} catch (RuntimeException e) {
			new Error("File not found");
			return;
		}

		LoginInterface frame = new LoginInterface();
		while (openMainUI == false) {
			if (incorrectCred) {
				new Error("You have entered incorrect credentials. Goodbye.");
				break;
			}
		}
		frame.setVisible(false);
		frame.dispose();

		if (!incorrectCred) {
			MainUI userInterface = new MainUI();
		}
	}

}
