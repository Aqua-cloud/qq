package SERVER;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client extends Thread {

    //����һ��Socket����
    Socket socket = null;

    public Client(String host, int port) {
        try {
            //��Ҫ��������IP��ַ�Ͷ˿ںţ����ܻ����ȷ��Socket����
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //�ͻ���һ���ӾͿ���д���ݸ���������
        new sendMessThread().start();
        super.run();
        try {
            // ��Sock���������
            InputStream s = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = s.read(buf)) != -1) {
                String receive = new String(buf, 0, len,"GBK");
                System.out.println(receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //��Socket����д���ݣ���Ҫ�¿�һ���߳�
    class sendMessThread extends Thread{
        @Override
        public void run() {
            super.run();
            //д����
            Scanner scanner=null;
            OutputStream os= null;
            try {
                scanner=new Scanner(System.in);
                os= socket.getOutputStream();
                String in="";
                do {
                    in=scanner.next();
                    os.write((""+in).getBytes());
                    os.flush();
                } while (!in.equals("bye"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getdate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = format.format(date);
        return result;
    }
    
    //�������
    public static void main(String[] args) {
        //��Ҫ����������ȷ��IP��ַ�Ͷ˿ں�
        Client clientTest=new Client("192.168.1.140", 6666);
        clientTest.start();
        
    }
}