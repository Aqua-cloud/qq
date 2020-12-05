package SERVER;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import QQ.*;

//�����

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
	
	//����ʹ�ù��췽����Ҳ����ֱ�Ӹı�ʵ�����ĳ�Ա
	
	//��õ�¼��Ϣ���ظ��û�ʱʹ��
	Transmit_Server(Admin admin, MyFriend[] friend_list) {
		this.admin = admin;
		this.friend_list = friend_list;
		this.option = "login";
		System.out.println("option: "+option);
	}
	
	//���ͺͽ�����Ϣʱʹ��
	Transmit_Server(Message message,MyFriend friend,Admin me) {
		this.message = message;
		this.friend_list[0] = friend;
		this.admin = me;
		this.option = "sendMessage";
	}
	
	//��ӻ�ɾ������ʱʹ��,�ÿͻ����յ��µĺ����б�
	Transmit_Server(MyFriend[] friend_list) {
		this.friend_list = friend_list;
		this.option = "getFriends";
	}
	
	//�����ļ�ʱʹ�ã����ܴ���1M���ݶ�
	Transmit_Server(File file) {
		this.file = file;
		this.option = "sendFile";
	}

	public Transmit_Server(Message message) {
		this.message = message;
	}

	//���͵�ָ��ip�Ŀͻ���
	public void send_to(String IP)	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			System.out.println("+++++++++++++++++");
			System.out.println(this.getOption());
			System.out.println("++++++++++++");
			send.writeObject(this);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//ͨ��DatagramSocket���󴴽�udp����
			//ȷ�������ݺ󣬲������ݷ�װ�����ݰ�
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName(IP),6951);//������ָ��IP���˿�6951
			ds.send(dp);//ͨ��send���������ݰ����ͳ�ȥ
			ds.close();//�ر���Դ
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}


	public String getOption() {
		return option;
	}
}