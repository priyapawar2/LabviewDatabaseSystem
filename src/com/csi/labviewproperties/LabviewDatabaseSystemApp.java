
package com.csi.labviewproperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * @author ppawar
 *
 */
/**
 * 
 * This class creates one Graphical user Interface, and prompt the user to select date of last modified file from "Date Modified" 
 * column of O:/Operations/LabVIEW Test Data/DSP II folder. Then user needs to select date of the current end up file i.e.up to which file user want to process.
 * Example: Last Modified Date 2013-12-02 
 *          Current End up Date 2013-12-05 
 * Then system will work on the files whose date modified from 2013-12-03 to 2013-12-05.
 * The user needs to choose date from DSP II folder because this is the one which updating everyday now! This same date will be applicable for all other folders.
 * If you entered any future date/dates, system will search for them in folder, there will not any folders for this date, then system will show you the same massage
 * i.e. "The process is completed. Please exit the system!"   
 * 
 *
 */
public class LabviewDatabaseSystemApp {

	public static void main(String[] args) {
		JFrame frame = new LabviewDatabaseSystemApplication();
		frame.pack();
		frame.setVisible(true);
	}

}

@SuppressWarnings("serial")
class LabviewDatabaseSystemApplication extends JFrame {

	public LabviewDatabaseSystemApplication() {
		setTitle("Labview Data System Application");
		setBounds(450, 150, 450, 150);
		centerWindow(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new LabviewStartDatePanel();
		this.add(panel);
		this.pack();
		setResizable(false);
	}
	public void centerWindow(Window w) {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setLocation((d.width-w.getWidth())/2, (d.height-w.getHeight())/2);
	}

	//Create main panel class
	class LabviewStartDatePanel extends JPanel implements ActionListener {
		//Last Modified Date label and textField
		JLabel label1 = new JLabel("Last Modified Date:");
		final JTextField text1 = new JTextField("Select date from Calendar...", 15);
		//	final JTextField text1 = new JTextField(20);
		JButton b1 = new JButton("Calendar"); 

		//Current EndupDate label and textField
		JLabel label2 = new JLabel("Current Endupdate:"); 
		final JTextField text2 = new JTextField("Select date from Calendar...", 15); 
		//	final JTextField text2 = new JTextField(20);
		JButton b2 = new JButton("Calendar");

		private JButton submitButton;
		private JButton exitButton;

		//Create panel method
		public LabviewStartDatePanel() {

			//Last Modified Date panel
			JPanel LastModifiedpanel = new JPanel(); 
			LastModifiedpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			LastModifiedpanel.add(label1); 
			LastModifiedpanel.add(text1); 
			LastModifiedpanel.add(b1); 

			final JFrame f1 = new JFrame(); 
			f1.getContentPane().add(LastModifiedpanel); 
			f1.pack(); 
			f1.setVisible(false);

			text1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					text1.setText("");
					text1.setEditable(false);
				}
			});

			//ActionListner to pickup the date from JCalendar1 
			b1.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 
					text1.setText(new JCalendar1(f1).setPickedDate()); 
				} 
			}); 

			//Current EndupDate panel
			JPanel CurrentDatePanel = new JPanel(); 
			CurrentDatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			CurrentDatePanel.add(label2); 
			CurrentDatePanel.add(text2); 
			CurrentDatePanel.add(b2); 
			final JFrame f2 = new JFrame(); 
			f2.getContentPane().add(CurrentDatePanel); 
			f2.pack(); 
			f2.setVisible(false); 

			text2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					text2.setText("");
					text2.setEditable(false);
				}
			}); 

			//ActionListner to pickup the date from JCalendar2
			b2.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent ae) { 
					text2.setText(new JCalendar2(f1).setPickedDate()); 
				} 
			});

			//button panel
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

			//submitButton button
			submitButton = new JButton("Submit");
			submitButton.addActionListener(this);
			buttonPanel.add(submitButton);

			//exitButton button
			exitButton = new JButton("Exit");
			exitButton.addActionListener(this);
			buttonPanel.add(exitButton);

			//add panels to main panel
			this.setLayout(new BorderLayout());
			this.add(LastModifiedpanel, BorderLayout.PAGE_START);
			this.add(CurrentDatePanel, BorderLayout.CENTER);
			this.add(buttonPanel, BorderLayout.SOUTH);

		}

		//Create ActionEvent on Submit button
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();

			//ActionPerformed for exitButton
			if(source == exitButton) {
				System.exit(0);
			}

			//ActionPerformed for submitButton
			else if(source == submitButton) {
				if("".equals(text1.getText().trim()) || "Select date from Calendar...".equals(text1.getText().trim())) {
					JOptionPane.showMessageDialog(null,"Please select the date","Error Massage", JOptionPane.ERROR_MESSAGE);
				}

				else if("".equals(text2.getText().trim()) || "Select date from Calendar...".equals(text2.getText().trim())) {
					JOptionPane.showMessageDialog(null,"Please select the date","Error Massage", JOptionPane.ERROR_MESSAGE);
				}
				else {

					String dateStart = text1.getText();
					String dateEnd = text2.getText();

					//call ProcessFiles class
					ProcessFiles processFiles = new ProcessFiles(dateStart, dateEnd);
					submitButton.setText("The process is completed. Please exit the system!");
					try {
						//Execute run() method of ProcessFiles class
						processFiles.run(dateStart, dateEnd);
						submitButton.setForeground(Color.blue);
						exitButton.setForeground(Color.red);
					}
					catch (IOException e1) {
						System.err.println(e1.getMessage());
						System.err.println(e1.toString());
					}
				}

			}
		}

	}

}

