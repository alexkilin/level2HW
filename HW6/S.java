package l6;
import  java.io.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class S {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");
            socket = server.accept();
            System.out.println("Клиент подключился");

            DataOutputStream out = new DataOutputStream ((socket.getOutputStream()));
            DataInputStream in = new DataInputStream (socket.getInputStream());
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            System.out.println("Клиент: " + str);
                            if (str.equals("qwit")) {
                                System.out.println("Клиент отключился");
                                break;
                            }
                        }
                    } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                    thread1.setDaemon(true);
                    thread1.start();

            while(true){
                    String client_str= br.readLine();
                  //  System.out.println("отправляем клиенту"+client_str);
                    out.writeUTF(client_str);
                    out.flush();
                if (client_str.equals("/end")){
                    System.out.println("Клиент отключился");
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


