package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static final int PORT = 1889;
    static final String IP_ADDRESS = "localhost";
    static Socket socket;
    static DataInputStream inText;
    static DataOutputStream outText;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket(IP_ADDRESS,PORT);

            outText = new DataOutputStream(socket.getOutputStream());

            inText = new DataInputStream(socket.getInputStream());

            Thread inMsgThread = new Thread(()->{
                while(true){
                    String inMsg = null;
                    try {
                        inMsg = inText.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Client: " + inMsg);
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


//                String inMsg = inText.readUTF();
//
//                System.out.println("Server: " + inMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
