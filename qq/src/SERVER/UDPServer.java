package SERVER;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import QQ.*;

/***
 * @author gsy yyc
 * ��  * ��������
 */

public class UDPServer {
	
	public static void main(String[] args)
	{
		try {
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(6950);//���ն˼���ָ���˿�
			System.out.println("���������ڼ���6950�˿ڡ���");
			while(true)
			{
				//�������ݰ�,���ڴ洢����
				byte[] buf = new byte[1024*1024];
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				ds.receive(dp);//ͨ�������receive�������յ����ݴ������ݰ���,receive()Ϊ����ʽ����
				//ͨ�����ݰ��ķ�����ȡ���е�����
				String IP = dp.getAddress().getHostAddress();
				ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
				ObjectInputStream in = new ObjectInputStream(bais);
				Transmit transmit = (Transmit) in.readObject();
				System.out.println(IP+" ����:"+transmit.option);
				switch (transmit.option) {
				case "login":{
					login(transmit.admin,IP);
					break;
				}
				case "sendMessage": {
					sendMessage(transmit.message,IP);
					break;
				}
				case "add friend":{
					addFriend(transmit.admin,IP);
					break;
				}
				case "delete friend":{
					deleteFriend(transmit.admin,IP);
					break;
				}
				case "sendFile":{
					sendFile(transmit.file,IP);
					break;
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
	public static void login(Admin admin,String IP) {
		//��õ�admin����id��password���������ͷ�������Ϣ�Ƿ�ƥ��
		//��ƥ�����÷������и��û���admin��friend_list[]
		//�����transmit_server�з��͸��û�
		//transmit_server.option="login"

		Login login = new Login();
		Transmit_Server ts1 = null;
		try {
			if((admin = login.login(admin))!=null){
				String friends = admin.getFriends();
				MyFriend[] friendList = getFriendList(friends);
				ts1 = new Transmit_Server(admin,friendList);
			} else {
				ts1 = new Transmit_Server(admin,null);
				ts1.option = "loginError";
				System.out.println(IP+" ����:ʧ��");
			}
			System.out.println("======================");
			System.out.println(" ts1"+ts1);
			System.out.println("======================");
			ts1.send_to(IP);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	public static void sendMessage(Message message, String IP) {
		//TODO
		//�����¼���
		//�ҵ������ߣ��жϵ�½״̬����������ֱ��ת����
		//(�������������ü�¼δ�����Ǹ��裩��ת��)
		//transmit_server.option="sendMessage"
		Transmit_Server t = new Transmit_Server(message);
		t.send_to(IP);
	}
	
	public static void addFriend(Admin admin,String IP) {
		//�����ݿ�����Ӻ��ѣ�Ȼ����ͻ��˷����µĺ����б�
		//transmit_server.option="getFriends"
	}
	
	public static void deleteFriend(Admin admin,String IP) {
		//�����ݿ���ɾ�����ѣ�Ȼ����ͻ��˷����µĺ����б�
		//transmit_server.option="getFriends"
	}
	
	public static void sendFile(File file, String sender_IP) {
		
	}
	//������friendsList
	public static MyFriend[] getFriendList(String friends){
		String[] list = friends.split("\\?"); //���ѵ�id�б�
		//System.out.println(list.length);
		MyFriend[] friend_list = new MyFriend[list.length];
		String sql1 = "select * from user where account = ?";
		Connection conn;
		for (int i = 0; i < list.length; i++) {
			try {
				conn = JDBCUtils.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql1);
				ps.setInt(1, Integer.valueOf(list[i]));
				ResultSet rs = ps.executeQuery();
				rs.next();
				MyFriend friend = new MyFriend(rs.getInt("account"),
						rs.getString("name"), rs.getInt("status") > 0);
				friend_list[i] = friend;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return friend_list;
	}
	
}
