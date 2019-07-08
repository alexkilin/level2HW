package l6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class С {

    public static void main(String[] args) throws InterruptedException {
    Socket socket = null;
        try {socket = new Socket("localhost", 8189);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            Thread thread1 = new Thread (new Runnable(){
                @Override
                public void run() {
                    try {
                        while (true) {
                            String in = dis.readUTF();
                            System.out.println("Сервер: "+in);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread1.setDaemon(true);
            thread1.start();



            while(true){
                    String client_str= br.readLine();
                 //   System.out.println("отправляем на сервер: "+client_str);
                    out.writeUTF(client_str);
                    out.flush();
                    if (client_str.equals("/end")){
                        System.out.println("Клиент отключился");
                        break;
                    }

                }


        }  catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
