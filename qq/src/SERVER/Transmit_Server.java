package SERVER;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import QQ.*;

//服务端

public class Transmit_Server  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String option;
	public Admin admin;
	public MyFriend[] friend_list;
	public Message message;
	public int delete_id;
	public int add_id;
	public File file;
	
	//可以使用构造方法，也可以直接改变实例化的成员
	
	//获得登录信息返回给用户时使用
	Transmit_Server(Admin admin, MyFriend[] friend_list) {
		this.admin = admin;
		this.friend_list = friend_list;
		this.option = "login";
		System.out.println("option: "+option);
	}
	
	//发送和接受信息时使用
	Transmit_Server(Message message,MyFriend friend,Admin me) {
		this.message = message;
		this.friend_list[0] = friend;
		this.admin = me;
		this.option = "sendMessage";
	}
	
	//添加或删除好友时使用,让客户端收到新的好友列表
	Transmit_Server(MyFriend[] friend_list) {
		this.friend_list = friend_list;
		this.option = "getFriends";
	}
	
	//传输文件时使用，不能大于1M（暂定
	Transmit_Server(File file) {
		this.file = file;
		this.option = "sendFile";
	}

	public Transmit_Server(Message message) {
		this.message = message;
	}

	//发送到指定ip的客户端
	public void send_to(String IP)	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			System.out.println("+++++++++++++++++");
			System.out.println(this.getOption());
			System.out.println("++++++++++++");
			send.writeObject(this);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//通过DatagramSocket对象创建udp服务
			//确定好数据后，并把数据封装成数据包
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName(IP),6951);//发送至指定IP，端口6951
			ds.send(dp);//通过send方法将数据包发送出去
			ds.close();//关闭资源
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}


	public String getOption() {
		return option;
	}
}