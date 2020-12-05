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

        //������½ҳ��
        LoginPage loginPage = new LoginPage();
        DatagramSocket ds = null;
        //���������������˿�
        while (true) {
            try {
                ds = new DatagramSocket(6951);//���ն˼���ָ���˿�
                System.out.println("�ͻ������ڼ���6951�˿ڡ���");
                while (true) {
                    //�������ݰ�,���ڴ洢����
                    byte[] buf = new byte[1024 * 1024];
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    ds.receive(dp);//ͨ�������receive�������յ����ݴ������ݰ���,receive()Ϊ����ʽ����
                    //ͨ�����ݰ��ķ�����ȡ���е�����
                    ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
                    ObjectInputStream in = new ObjectInputStream(bais);
                    Transmit_Server transmit = (Transmit_Server) in.readObject();
                    System.out.println("������:" + transmit.option);

                    switch (transmit.option) {
                        case "login": {
                            login(transmit.admin, transmit.friend_list);
                            break;
                        }
                        case "loginError": {
                            JOptionPane.showMessageDialog(null, "�˺Ż��������", "����", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        case "addError": {
                            JOptionPane.showMessageDialog(null, "û�������", "����", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        case "addFriend": {
                            reflashFriendPanel(transmit.friend_list);
                            JOptionPane.showMessageDialog(null, "��ӳɹ�", "", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                        case "deleFriend": {
                            reflashFriendPanel(transmit.friend_list);
                            JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "", JOptionPane.INFORMATION_MESSAGE);
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
        //�������������
        if (page != null) {
            page.reflashChatPanel(reveivedMessage);
        }
        //����������û�д����һ����ʾ��
        else {
            new MessageTips(message);
        }
    }

    public static void getFile() {
        //ûͷ��
    }
}
