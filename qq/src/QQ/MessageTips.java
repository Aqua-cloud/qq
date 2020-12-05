package QQ;

import java.awt.*;

import javax.swing.*;

public class MessageTips extends JFrame{

	public MessageTips(Message message) {
		int w= Toolkit.getDefaultToolkit().getScreenSize().width/4;
	    int h= Toolkit.getDefaultToolkit().getScreenSize().height/3;
		setLayout(null);
		JLabel name = new JLabel(message.getSenderName()+"£º");
		name.setBounds(20, 20, 100, 40);
		JLabel string = new JLabel(message.getMessage());
		string.setBounds(20, 80, 180, 40);
		add(name);
		add(string);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
	}
	
}
