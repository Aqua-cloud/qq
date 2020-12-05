package QQ;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

class MakeMessagePanel extends JPanel{

	public MakeMessagePanel(String myName, String senderName, String message, String date) {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel nameJLabel = new JLabel(senderName+"£º");
		JLabel messageJLabel = new JLabel(message);
		JLabel dateJLabel = new JLabel(date);
		dateJLabel.setForeground(new Color(150,150,150));
		
		add(nameJLabel);
		add(messageJLabel);
		add(dateJLabel);
		
		setOpaque(true);
		setPreferredSize(new Dimension(200,60));
		
		springLayout.putConstraint(SpringLayout.EAST, messageJLabel, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.WEST, messageJLabel, 10, SpringLayout.WEST, this);
		
		if(senderName==myName) {
			messageJLabel.setHorizontalAlignment(JLabel.RIGHT);
			springLayout.putConstraint(SpringLayout.NORTH, nameJLabel, 5, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, nameJLabel, -10, SpringLayout.EAST, this);
			springLayout.putConstraint(SpringLayout.NORTH, messageJLabel, 5, SpringLayout.SOUTH, nameJLabel);
			springLayout.putConstraint(SpringLayout.WEST, dateJLabel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.NORTH, dateJLabel, 10, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.NORTH, nameJLabel, 5, SpringLayout.NORTH, this);
		}
		else {
			messageJLabel.setHorizontalAlignment(JLabel.LEFT);
			springLayout.putConstraint(SpringLayout.NORTH, nameJLabel, 5, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, nameJLabel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.NORTH, messageJLabel, 5, SpringLayout.SOUTH, nameJLabel);
			springLayout.putConstraint(SpringLayout.EAST, dateJLabel, -5, SpringLayout.EAST, this);
			springLayout.putConstraint(SpringLayout.NORTH, dateJLabel, 10, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.NORTH, nameJLabel, 10, SpringLayout.NORTH, this);
		}
	}
}	