package SERVER;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import QQ.*;

/***
 * @author gsy yyc
 * 真  * 服务器端
 */

public class UDPServer {
	
	public static void main(String[] args)
	{
		try {
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(6950);//接收端监听指定端口
			System.out.println("服务器正在监听6950端口……");
			while(true)
			{
				//定义数据包,用于存储数据
				byte[] buf = new byte[1024*1024];
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				ds.receive(dp);//通过服务的receive方法将收到数据存入数据包中,receive()为阻塞式方法
				//通过数据包的方法获取其中的数据
				String IP = dp.getAddress().getHostAddress();
				ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
				ObjectInputStream in = new ObjectInputStream(bais);
				Transmit transmit = (Transmit) in.readObject();
				System.out.println(IP+" 请求:"+transmit.option);
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
		//获得的admin里有id和password，检测输入和服务器信息是否匹配
		//若匹配则获得服务器中该用户的admin和friend_list[]
		//打包在transmit_server中发送给用户
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
				System.out.println(IP+" 请求:失败");
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
		//聊天记录入库
		//找到接收者，判断登陆状态，若在线则直接转发，
		//(若不在线则设置记录未读（非刚需）不转发)
		//transmit_server.option="sendMessage"
		Transmit_Server t = new Transmit_Server(message);
		t.send_to(IP);
	}
	
	public static void addFriend(Admin admin,String IP) {
		//在数据库中添加好友，然后向客户端发送新的好友列表
		//transmit_server.option="getFriends"
	}
	
	public static void deleteFriend(Admin admin,String IP) {
		//在数据库中删除好友，然后向客户端发送新的好友列表
		//transmit_server.option="getFriends"
	}
	
	public static void sendFile(File file, String sender_IP) {
		
	}
	//分析出friendsList
	public static MyFriend[] getFriendList(String friends){
		String[] list = friends.split("\\?"); //好友的id列表
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
