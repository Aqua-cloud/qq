package QQ;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//用户端

public class Transmit  implements Serializable{
	
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
	
	//客户端用，发送登陆信息时使用
	public Transmit(Admin admin) {
		this.admin = admin;
		this.option = "login";
	}
	
	//服务器用,收到正确的登录信息
	public Transmit(Admin admin, MyFriend[] friend_list) {
		this.admin = admin;
		this.friend_list = friend_list;
		this.option = "login";
	}
	
	//服务器用,收到错误的登录信息
	public Transmit(String error) {
		this.option = "loginError";
	}
	
	//客户端服务器共用，发送和接受信息时使用
	public Transmit(Message message) {
		this.message = message;
		this.option = "sendMessage";
	}
	
	//客户端用，添加或删除好友时使用,0删除，1添加
	public Transmit(Admin admin, int option, int id) {
		if(option==1) {
			this.add_id = id;
			this.admin = admin;
			this.option = "add friend";
		}
		if(option==0) {
			this.delete_id = id;
			this.admin = admin;
			this.option = "delete friend";
		}
		
	}
	
	//服务器用，添加或删除好友时使用,让客户端收到新的好友列表
	public Transmit(MyFriend[] friend_list, String option) {
		this.friend_list = friend_list;
			this.option = option;
			//option = addFriend、deleteFriend、addError
	}
		
	//传输文件时使用，不能大于1M（暂定
	public Transmit(File file) {
		this.file = file;
		this.option = "sendFile";
	}
	
	//发送到服务器
	public void send()	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			send.writeObject(this);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//通过DatagramSocket对象创建udp服务
			//确定好数据后，并把数据封装成数据包
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.1.140"),6950);//发送至指定IP，指定端口
			ds.send(dp);//通过send方法将数据包发送出去
			ds.close();//关闭资源
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	//发送到指定ip的客户端
	public void send_to_client(String IP)	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			send.writeObject(this);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//通过DatagramSocket对象创建udp服务
			//确定好数据后，并把数据封装成数据包
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName(IP),6667);//发送至指定IP，端口6667
			ds.send(dp);//通过send方法将数据包发送出去
			ds.close();//关闭资源
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
}
