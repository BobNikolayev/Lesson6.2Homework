package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static final int PORT = 1889;
    static ServerSocket server;
    static Socket socket;
    static DataInputStream inText;
    static DataOutputStream outText;

    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server running");

            Socket socket = server.accept();
            System.out.println("Client connected");

            inText = new DataInputStream(socket.getInputStream());

            outText = new DataOutputStream(socket.getOutputStream());

            Thread inMsgThread = new Thread(()->{
                while(true){
                    String inMsg = null;
                    try {
                        inMsg = inText.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Server: " + inMsg);

                    if(inMsg.equals("/end")){
                        break;
                    }

                }
            });

            inMsgThread.start();

            while (true){


                String outMsg = scanner.nextLine();

                outText.writeUTF(outMsg);

                if(outMsg.equals("/end")){
                    break;
                }
            }




        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

    }
}
