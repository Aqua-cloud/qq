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
     * 页面初始化模块
     */
	public LoginPage() {
		int w= Toolkit.getDefaultToolkit().getScreenSize().width/4;
	    int h= Toolkit.getDefaultToolkit().getScreenSize().height/3;
	    
	    JFrame frame = new JFrame("登录");
        frame.setLayout(null);
        
        JLabel nameStr = new JLabel("账号:");
        nameStr.setBounds(w*2/10, h*5/20, w/10, h/10);
        frame.add(nameStr);
        
        JLabel passwordStr = new JLabel("密码:");
        passwordStr.setBounds(w*2/10, h*4/10, w/10, h/10);
        frame.add(passwordStr);  
        
        JTextField userID = new JTextField();
        userID.setBounds(w*3/10, h*5/20, w*5/10, h/10);
        frame.add(userID);
        
        JPasswordField password = new JPasswordField();
        password.setBounds(w*3/10, h*4/10, w*5/10, h/10);
        frame.add(password);
        
        JButton buttonlogin = new JButton("登录");
        buttonlogin.setBounds(w*3/12, h*6/10,w/5, h/10);
        frame.add(buttonlogin);
        
        JButton buttonregister = new JButton("注册");
        buttonregister.setBounds(w*7/12, h*6/10, w/5, h/10);
        frame.add(buttonregister);  
        
        frame.setBounds(w*3/2, h, w, h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);



        /***
         * 监听器模块
         */
        //密码框监听
        password.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped (KeyEvent e) {
			     if(e.getKeyChar()==KeyEvent.VK_ENTER ) {
			    	 int ID = Integer.parseInt(userID.getText());
			    	 String passwd = new String (password.getPassword());
			    	 //创建一个Admin用户，把输入框中的用户名密码和提出来
                     Admin user = new Admin();
                     user.setID(ID);
                     user.setPassword(passwd);
                     //用user创建一个transmit对象向客户端传送数据
                     Transmit tr1 = new Transmit(user);
                     tr1.send();//发送数据

                     //登录
//                     Login login = new Login();
//                     login.setAdmin(user);
//		                try {
//		                    if((user = login.login(user))!=null) {
//		                        System.out.println(user.getName());
//		                        //弹出登录成功的窗口
//		                        JOptionPane.showMessageDialog(null, "登陆成功", "登陆成功", JOptionPane.NO_OPTION);
//		                        //点击确定后会跳转到主窗口
//		                        frame.setVisible(false);
//		                        new MainPage(user);//不在这里打开主页了，在linker打开
//		                    }
//		                    else {
//		                        //弹出账号或密码错误的窗口
//		                        JOptionPane.showMessageDialog(null, "账号或密码错误", "错误", JOptionPane.WARNING_MESSAGE);
//		                        //清除密码框中的信息
//		                        password.setText("");
//		                        //清除账号框中的信息
//		                        userID.setText("");
//
//		                        //System.out.println("登陆失败");
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
//                    //创建一个Admin用户，把输入框中的用户名密码和提出来
//                    Admin user = new Admin();
//                    user.setID(ID);
//                    user.setPassword(passwd);
//                    //登录
//                    Login login = new Login();
//                    login.setAdmin(user);
//                    try {
//                        if((user = login.login(user))!=null) {
//                            System.out.println(user.getName());
//                            //弹出登录成功的窗口
//                            JOptionPane.showMessageDialog(null, "登陆成功", "登陆成功", JOptionPane.NO_OPTION);
//                            //点击确定后会跳转到主窗口
//                            frame.setVisible(false);
//                            new MainPage(user);//不在这里打开主页了，在linker打开
//                        }
//                        else {
//                            //弹出账号或密码错误的窗口
//                            JOptionPane.showMessageDialog(null, "账号或密码错误", "错误", JOptionPane.WARNING_MESSAGE);
//                            //清除密码框中的信息
//                            password.setText("");
//                            //清除账号框中的信息
//                            userID.setText("");
//
//                            //System.out.println("登陆失败");
//
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        });


        //登录按钮监听
        buttonlogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ID = Integer.parseInt(userID.getText());
                String passwd = new String (password.getPassword());
                //创建一个Admin用户，把输入框中的用户名密码和提出来
                Admin user = new Admin();
                user.setID(ID);
                user.setPassword(passwd);
                //用user创建一个transmit对象向客户端传送数据
                Transmit tr1 = new Transmit(user);
                tr1.send();//发送数据

            }
        });



        //为注册按钮添加监听器
         buttonregister.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 //注册页面
                 JOptionPane.showMessageDialog(null, "暂未开放用户注册", "对不起", JOptionPane.WARNING_MESSAGE);
        	 }
         });
	}
}