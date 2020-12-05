package SERVER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/***
 * �������Եķ����
 */
public class Server_Test extends Thread {
    ServerSocket server = null;
    Socket socket = null;

    public Server_Test(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        super.run();
        while(true) {try {
            System.out.println(getdate() + "  �ȴ��ͻ�������...");
            socket = server.accept();
            new sendMessThread().start();// ���Ӳ�����socket�������÷�����Ϣ�߳�
            System.out.println(getdate() + "  �ͻ��� ��" + socket.getInetAddress().getHostAddress() + "�� ���ӳɹ�...");
            InputStream in = socket.getInputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                System.out.println(getdate() + "  �ͻ���: ��"
                        + socket.getInetAddress().getHostAddress() + "��˵��"
                        + new String(buf, 0, len, "GBK"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }}
    }

    public static String getdate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = format.format(date);
        return result;
    }

    class sendMessThread extends Thread {
        @Override
        public void run() {
            super.run();
            Scanner scanner = null;
            OutputStream out = null;
            try {
                if (socket != null) {
                    scanner = new Scanner(System.in);
                    out = socket.getOutputStream();
                    String in = "";
                    do {
                        in = scanner.next();
                        out.write(("" + in).getBytes("GBK"));
                        out.flush();// ��ջ�����������
                    } while (!in.equals("q"));
                    scanner.close();
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // �������
    public static void main(String[] args) {
        Server_Test server1 = new Server_Test(6666);
        Server_Test server2 = new Server_Test(6667);
        server1.start();
        server2.start();
    }
}