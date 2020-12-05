package QQ;

import SERVER.Transmit_Server;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Linker {

    public Linker() {

        //创建登陆页面
        LoginPage loginPage = new LoginPage();
        DatagramSocket ds = null;
        //持续监听服务器端口
        while (true) {
            try {
                ds = new DatagramSocket(6951);//接收端监听指定端口
                System.out.println("客户端正在监听6951端口……");
                while (true) {
                    //定义数据包,用于存储数据
                    byte[] buf = new byte[1024 * 1024];
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    ds.receive(dp);//通过服务的receive方法将收到数据存入数据包中,receive()为阻塞式方法
                    //通过数据包的方法获取其中的数据
                    ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
                    ObjectInputStream in = new ObjectInputStream(bais);
                    Transmit_Server transmit = (Transmit_Server) in.readObject();
                    System.out.println("服务器:" + transmit.option);

                    switch (transmit.option) {
                        case "login": {
                            login(transmit.admin, transmit.friend_list);
                            break;
                        }
                        case "loginError": {
                            JOptionPane.showMessageDialog(null, "账号或密码错误", "错误", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        case "addError": {
                            JOptionPane.showMessageDialog(null, "没有这个人", "错误", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        case "addFriend": {
                            reflashFriendPanel(transmit.friend_list);
                            JOptionPane.showMessageDialog(null, "添加成功", "", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                        case "deleFriend": {
                            reflashFriendPanel(transmit.friend_list);
                            JOptionPane.showMessageDialog(null, "删除成功", "", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                        case "sendMessage": {
                            reflashChatPanel(transmit.message);
                            break;
                        }
                        case "sendFile": {
                            getFile();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                Objects.requireNonNull(ds).close();
                e.printStackTrace();
            }
        }
    }


    MainPage mainPage;

    public void login(Admin user, MyFriend[] friend_list) {
        mainPage = new MainPage(user, friend_list);
    }

    public void reflashFriendPanel(MyFriend[] friends_list) {
        mainPage.reflashFriendPanel(friends_list);
    }

    public void reflashChatPanel(Message message) {
        JPanel reveivedMessage = new MakeMessagePanel(message.getReceiverName(),
                message.getSenderName(), message.getMessage(), message.getDate());
        int id = message.getSender();
        ChatPage page = new ChatPages().getPage(id);
        //若这个聊天框打开了
        if (page != null) {
            page.reflashChatPanel(reveivedMessage);
        }
        //若这个聊天框没有打开则打开一个提示框
        else {
            new MessageTips(message);
        }
    }

    public static void getFile() {
        //没头绪
    }
}
