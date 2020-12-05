package QQ;

import java.awt.*;
import javax.swing.*;

public class ChatRecord{
	
	public final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	public ChatRecord(Message[] message,String myname) {
		JFrame record = new JFrame();
		Container c = record.getContentPane();
		JPanel messageTopJPanel = new JPanel();
		JScrollPane messegeJScrollPane = new JScrollPane(messageTopJPanel);
		messageTopJPanel.setLayout(new GridLayout(0,1,0,0));
		for (Message i:message) {
			MakeMessagePanel a = new MakeMessagePanel(myname, i.senderName, i.message, i.date);
			messageTopJPanel.add(a);
		}
		
		c.add(messegeJScrollPane);
		record.setBounds(w/3,h/6,w/3,h*2/3);
		record.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		record.setVisible(true);
	}
}
