package QQ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import QQ.MainPage.DrawIcon;
import layout.AfColumnLayout;
import layout.AfRowLayout;

public class test {
	private Admin myself;
	public final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	JPanel messageTopJPanel = new JPanel();
	JScrollPane messegeJScrollPane = new JScrollPane(messageTopJPanel);
	
	public test(MyFriend friend,Admin myself) {
		//����
		this.myself = myself;
		JFrame chatFrame = new JFrame();
		chatFrame.setMinimumSize(new Dimension(300,600));
		
		//�ײ�����
		Container chatContainer = chatFrame.getContentPane();
		chatContainer.setLayout(new BorderLayout());

		//����Է���Ϣ
		JPanel info = new JPanel();
		JLabel opposite = new oppositeLabel(friend);
		info.add(opposite);
		
		SpringLayout springLayout = new SpringLayout();
		
		//��Ϣ�����
		JPanel top = new JPanel();
		top.setLayout(springLayout);
		
		messageTopJPanel.setLayout(new GridLayout(0,1,0,0));
		
		
		messageTopJPanel.doLayout();
		
		
		top.setMinimumSize(new Dimension(200,50));
		top.add(messegeJScrollPane);
		
		
		
		//��Ϣ����岼��
		springLayout.putConstraint(SpringLayout.NORTH, messegeJScrollPane, 5, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.WEST, messegeJScrollPane, 10, SpringLayout.WEST, top);
		springLayout.putConstraint(SpringLayout.EAST, messegeJScrollPane, -10, SpringLayout.EAST, top);
		springLayout.putConstraint(SpringLayout.SOUTH, messegeJScrollPane, -5, SpringLayout.SOUTH, top);

		//���������
		JPanel botton = new JPanel();
		botton.setMinimumSize(new Dimension(200,200));
		botton.setLayout(springLayout);
		
		//�ļ���ť
		JButton file = new JButton("�ļ�");
		file.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				int i = filechooser.showOpenDialog(chatContainer);
				if(i==JFileChooser.APPROVE_OPTION) {
					File selectedFile = filechooser.getSelectedFile();
					System.out.println("����Ӧ����һ�������ļ��ķ���  "+selectedFile.getPath());
				}
			}
		});
		
		//�����¼��ť
		JButton record = new JButton("�����¼");
		record.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//TODO ��ȡ�����¼������message�����б�
				//new ChatRecord(message, myself.getName());
				
			}
		});
		
		//�����ı���
		JScrollPane text = new JScrollPane();
		JTextArea input = new JTextArea();
		text.setViewportView(input);
		
		//���Ͱ�ť
		JButton send = new JButton("����");
		send.addActionListener(new ActionListener() {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel q = send_messege(myself,friend,input) ;
				reflashChatPanel(q);
				
			}
		});
		input.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped (KeyEvent e) {
			     if(e.getKeyChar()==KeyEvent.VK_ENTER )
			        { 
			    	 JPanel q = send_messege(myself,friend,input) ;
			    	 reflashChatPanel(q);
			        } 
			}
		});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		//��������岼��
		botton.add(file);
		springLayout.putConstraint(SpringLayout.NORTH, file, 5, SpringLayout.NORTH, botton);
		springLayout.putConstraint(SpringLayout.WEST, file, 10, SpringLayout.WEST, botton);
		botton.add(record);
		springLayout.putConstraint(SpringLayout.NORTH, record, 5, SpringLayout.NORTH, botton);
		springLayout.putConstraint(SpringLayout.EAST, record, -10, SpringLayout.EAST, botton);
		botton.add(text);
		springLayout.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.SOUTH, file);
		springLayout.putConstraint(SpringLayout.WEST, text, 10, SpringLayout.WEST, botton);
		springLayout.putConstraint(SpringLayout.EAST, text, -10, SpringLayout.EAST, botton);
		springLayout.putConstraint(SpringLayout.SOUTH, text, -40, SpringLayout.SOUTH, botton);
		botton.add(send);
		springLayout.putConstraint(SpringLayout.NORTH, send, 5, SpringLayout.SOUTH, text);
		springLayout.putConstraint(SpringLayout.SOUTH, send, -5, SpringLayout.SOUTH, botton);
		springLayout.putConstraint(SpringLayout.EAST, send, -10, SpringLayout.EAST, botton);
		
		//����Ϣ�����������Ž��������
		JSplitPane chat = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		chat.setDividerSize(2);
		chat.setBackground(Color.LIGHT_GRAY);
		chat.setTopComponent(top);
		chat.setBottomComponent(botton);
		chat.setDividerLocation(h/3);
		
		//���Է���Ϣ�����������붥������
		chatContainer.add(info,BorderLayout.NORTH);
		chatContainer.add(chat,BorderLayout.CENTER);
		
		//��ʼ������
		chatFrame.setBounds(w/4,h/6,w/3,h*2/3);
		chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		chatFrame.setVisible(true);
		listen();
	}
	
	//���ı�������ݸ�ʽ�����͸�������������Ӧ����������ˢ�µ����
	public JPanel send_messege(Admin myself, MyFriend friend, JTextArea input) {
		String send_text = input.getText();
	 	if(send_text.length()!=0&&!send_text.equals("\n")) {
	 		
	 		//������ǿ�����message
	 		Calendar time = Calendar.getInstance();
	 	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");  
	 	    String date = formatter.format(time.getTime());  
	 	    Message message = new Message(myself.getID(), friend.getID(), date, send_text);

	 		//����Ӧ����һ����������������
	 		Transmit t = new Transmit(message);
	 		t.send();
	 		
	 		MakeMessagePanel messagePanel = new MakeMessagePanel(myself.getName(), myself.getName(), send_text, date);
	 		
		 	input.setText("");//�ı����ÿ�
		 	return messagePanel;
	 	}
		return null;
	

	}
	
	//���һ��������Ϣ��ˢ���������
	public void reflashChatPanel(JPanel messagePanel) {
		if(messagePanel!=null) {
	   		 messageTopJPanel.add(messagePanel);
	   		 messegeJScrollPane.revalidate();
	   		 JScrollBar jscrollBar = messegeJScrollPane.getVerticalScrollBar();
	   		 jscrollBar.setValue(jscrollBar.getMaximum());
   	 	}
	}
	
	class oppositeLabel extends JLabel{
		public oppositeLabel(MyFriend i) {
			super(i.getName()+"("+i.getID()+")");
			DrawIcon icon;
			if(!i.getlog_status()) {
				icon = new DrawIcon(Color.gray);
				setForeground(Color.gray);
			}
			else {
				icon = new DrawIcon(Color.green);
				setForeground(new Color(0,100,0));
			}
			setIcon(icon);
		}
	}
	public class DrawIcon implements Icon {		
		
		int width = 10;		
		int height = 10;		
		Color color;
		
		public DrawIcon(Color color) {	
			this.color = color;
		}
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {	
			// TODO Auto-generated method stub
			g.setColor(color);
			g.fillOval(x, y, width, height);		
		}
	 
		@Override
		public int getIconWidth() {		
			// TODO Auto-generated method stub
			return this.width;
		}
	 
		@Override
		public int getIconHeight() {		
			// TODO Auto-generated method stub
			return this.height;
		}
	}

	public void listen() {
		while(true) {
			try {
				@SuppressWarnings("resource")
				DatagramSocket ds = new DatagramSocket(6667);//���ն˼���ָ���˿�
				System.out.println("�ͻ������ڼ���6667�˿ڡ���");
				while(true)
				{
					//�������ݰ�,���ڴ洢����
					byte[] buf = new byte[1024*1024];
					DatagramPacket dp = new DatagramPacket(buf,buf.length);
					ds.receive(dp);//ͨ�������receive�������յ����ݴ������ݰ���,receive()Ϊ����ʽ����
					//ͨ�����ݰ��ķ�����ȡ���е�����
					ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
					ObjectInputStream in = new ObjectInputStream(bais);
					Transmit transmit = (Transmit) in.readObject();
					System.out.println("������:"+transmit.option);
					if (transmit.option.equals("sendMessage")) {
						Message message = transmit.message;
						JPanel reveivedMessage = new MakeMessagePanel(myself.getName(), message.getSenderName(), message.getMessage(), message.getDate());
						reflashChatPanel(reveivedMessage);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
	
	public static void main(String[] args) {
		MyFriend f = new MyFriend();
		f.setID(45);
		f.setName("С��");
		f.setlog_status(true);
		f.setrecent3chat(new String[] {"������","zaima","qifei"});
		Admin a = new Admin();
		a.setName("���");
		a.setID(22);
		new test(f,a);
	}
	
}