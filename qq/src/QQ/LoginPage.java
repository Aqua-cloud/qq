package QQ;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
    /***
     * ҳ���ʼ��ģ��
     */
	public LoginPage() {
		int w= Toolkit.getDefaultToolkit().getScreenSize().width/4;
	    int h= Toolkit.getDefaultToolkit().getScreenSize().height/3;
	    
	    JFrame frame = new JFrame("��¼");
        frame.setLayout(null);
        
        JLabel nameStr = new JLabel("�˺�:");
        nameStr.setBounds(w*2/10, h*5/20, w/10, h/10);
        frame.add(nameStr);
        
        JLabel passwordStr = new JLabel("����:");
        passwordStr.setBounds(w*2/10, h*4/10, w/10, h/10);
        frame.add(passwordStr);  
        
        JTextField userID = new JTextField();
        userID.setBounds(w*3/10, h*5/20, w*5/10, h/10);
        frame.add(userID);
        
        JPasswordField password = new JPasswordField();
        password.setBounds(w*3/10, h*4/10, w*5/10, h/10);
        frame.add(password);
        
        JButton buttonlogin = new JButton("��¼");
        buttonlogin.setBounds(w*3/12, h*6/10,w/5, h/10);
        frame.add(buttonlogin);
        
        JButton buttonregister = new JButton("ע��");
        buttonregister.setBounds(w*7/12, h*6/10, w/5, h/10);
        frame.add(buttonregister);  
        
        frame.setBounds(w*3/2, h, w, h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);



        /***
         * ������ģ��
         */
        //��������
        password.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped (KeyEvent e) {
			     if(e.getKeyChar()==KeyEvent.VK_ENTER ) {
			    	 int ID = Integer.parseInt(userID.getText());
			    	 String passwd = new String (password.getPassword());
			    	 //����һ��Admin�û�����������е��û�������������
                     Admin user = new Admin();
                     user.setID(ID);
                     user.setPassword(passwd);
                     //��user����һ��transmit������ͻ��˴�������
                     Transmit tr1 = new Transmit(user);
                     tr1.send();//��������

                     //��¼
//                     Login login = new Login();
//                     login.setAdmin(user);
//		                try {
//		                    if((user = login.login(user))!=null) {
//		                        System.out.println(user.getName());
//		                        //������¼�ɹ��Ĵ���
//		                        JOptionPane.showMessageDialog(null, "��½�ɹ�", "��½�ɹ�", JOptionPane.NO_OPTION);
//		                        //���ȷ�������ת��������
//		                        frame.setVisible(false);
//		                        new MainPage(user);//�����������ҳ�ˣ���linker��
//		                    }
//		                    else {
//		                        //�����˺Ż��������Ĵ���
//		                        JOptionPane.showMessageDialog(null, "�˺Ż��������", "����", JOptionPane.WARNING_MESSAGE);
//		                        //���������е���Ϣ
//		                        password.setText("");
//		                        //����˺ſ��е���Ϣ
//		                        userID.setText("");
//
//		                        //System.out.println("��½ʧ��");
//
//		                    }
//		                } catch (Exception ex) {
//		                    ex.printStackTrace();
//		                }
			     }
			}
		});
//        password.addKeyListener(new KeyAdapter() {
//
//            @Override
//            public void keyTyped (KeyEvent e) {
//                if(e.getKeyChar()==KeyEvent.VK_ENTER ) {
//                    int ID = Integer.valueOf(userID.getText());
//                    String passwd = new String (password.getPassword());
//                    //����һ��Admin�û�����������е��û�������������
//                    Admin user = new Admin();
//                    user.setID(ID);
//                    user.setPassword(passwd);
//                    //��¼
//                    Login login = new Login();
//                    login.setAdmin(user);
//                    try {
//                        if((user = login.login(user))!=null) {
//                            System.out.println(user.getName());
//                            //������¼�ɹ��Ĵ���
//                            JOptionPane.showMessageDialog(null, "��½�ɹ�", "��½�ɹ�", JOptionPane.NO_OPTION);
//                            //���ȷ�������ת��������
//                            frame.setVisible(false);
//                            new MainPage(user);//�����������ҳ�ˣ���linker��
//                        }
//                        else {
//                            //�����˺Ż��������Ĵ���
//                            JOptionPane.showMessageDialog(null, "�˺Ż��������", "����", JOptionPane.WARNING_MESSAGE);
//                            //���������е���Ϣ
//                            password.setText("");
//                            //����˺ſ��е���Ϣ
//                            userID.setText("");
//
//                            //System.out.println("��½ʧ��");
//
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        });


        //��¼��ť����
        buttonlogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ID = Integer.parseInt(userID.getText());
                String passwd = new String (password.getPassword());
                //����һ��Admin�û�����������е��û�������������
                Admin user = new Admin();
                user.setID(ID);
                user.setPassword(passwd);
                //��user����һ��transmit������ͻ��˴�������
                Transmit tr1 = new Transmit(user);
                tr1.send();//��������

            }
        });



        //Ϊע�ᰴť��Ӽ�����
         buttonregister.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 //ע��ҳ��
                 JOptionPane.showMessageDialog(null, "��δ�����û�ע��", "�Բ���", JOptionPane.WARNING_MESSAGE);
        	 }
         });
	}
}