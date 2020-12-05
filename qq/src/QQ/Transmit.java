package QQ;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//�û���

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
	
	//����ʹ�ù��췽����Ҳ����ֱ�Ӹı�ʵ�����ĳ�Ա
	
	//�ͻ����ã����͵�½��Ϣʱʹ��
	public Transmit(Admin admin) {
		this.admin = admin;
		this.option = "login";
	}
	
	//��������,�յ���ȷ�ĵ�¼��Ϣ
	public Transmit(Admin admin, MyFriend[] friend_list) {
		this.admin = admin;
		this.friend_list = friend_list;
		this.option = "login";
	}
	
	//��������,�յ�����ĵ�¼��Ϣ
	public Transmit(String error) {
		this.option = "loginError";
	}
	
	//�ͻ��˷��������ã����ͺͽ�����Ϣʱʹ��
	public Transmit(Message message) {
		this.message = message;
		this.option = "sendMessage";
	}
	
	//�ͻ����ã���ӻ�ɾ������ʱʹ��,0ɾ����1���
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
	
	//�������ã���ӻ�ɾ������ʱʹ��,�ÿͻ����յ��µĺ����б�
	public Transmit(MyFriend[] friend_list, String option) {
		this.friend_list = friend_list;
			this.option = option;
			//option = addFriend��deleteFriend��addError
	}
		
	//�����ļ�ʱʹ�ã����ܴ���1M���ݶ�
	public Transmit(File file) {
		this.file = file;
		this.option = "sendFile";
	}
	
	//���͵�������
	public void send()	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			send.writeObject(this);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//ͨ��DatagramSocket���󴴽�udp����
			//ȷ�������ݺ󣬲������ݷ�װ�����ݰ�
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.1.140"),6950);//������ָ��IP��ָ���˿�
			ds.send(dp);//ͨ��send���������ݰ����ͳ�ȥ
			ds.close();//�ر���Դ
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	//���͵�ָ��ip�Ŀͻ���
	public void send_to_client(String IP)	{
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream send = new ObjectOutputStream(baos);
			send.writeObject(this);
			byte[] buf = baos.toByteArray();
			
			DatagramSocket ds = new DatagramSocket();//ͨ��DatagramSocket���󴴽�udp����
			//ȷ�������ݺ󣬲������ݷ�װ�����ݰ�
			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName(IP),6667);//������ָ��IP���˿�6667
			ds.send(dp);//ͨ��send���������ݰ����ͳ�ȥ
			ds.close();//�ر���Դ
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
}
