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
			
			DatagramSocket ds = new DatagramSocket();//ͨ��DatagramSocket���󴴽�udp����
			//ȷ�������ݺ󣬲������ݷ�װ�����ݰ�
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.1.235"),6666);//������ָ��IP��ָ���˿�
			ds.send(dp);//ͨ��send���������ݰ����ͳ�ȥ
			ds.close();//�ر���Դ
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}
