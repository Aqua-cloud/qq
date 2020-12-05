package SERVER;

import java.io.*;
import java.net.*;

import QQ.Transmit;

public class UDPClient {

	public static void send(Transmit transmit)
	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			send.writeObject(transmit);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//通过DatagramSocket对象创建udp服务
			//确定好数据后，并把数据封装成数据包
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.1.235"),6666);//发送至指定IP，指定端口
			ds.send(dp);//通过send方法将数据包发送出去
			ds.close();//关闭资源
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}
