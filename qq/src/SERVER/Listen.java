package SERVER;
import java.io.*;
import java.net.*;

public class Listen {
	
	private BufferedReader reader;
	private ServerSocket server;
	private Socket mySocket;
	
	void getserver() {
		try {
			server = new ServerSocket(6666);
			while(true) {
				System.out.println("等待客户端链接");
				mySocket = server.accept();
				reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
				getClientMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getClientMessage() {
		try {
			while(true) {
				System.out.println("收到"+reader.readLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(reader!=null) {
				reader.close();
			}
			if(mySocket!=null) mySocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Listen listen = new Listen();
		listen.getserver();
	}
}
