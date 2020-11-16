package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8888);
            int counter = 0;
            System.out.println("Server Started ....");
            while (true) {
                counter++;
                Socket serverClient = server.accept();  // сервер принимает запрос на подключение клиента
                System.out.println(" >> " + "Client No:" + counter + " started!");
                Thread sct = new Thread(new ServerClientThread(serverClient, counter)); // отправляем запрос в отдельный поток
                sct.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerClientThread implements Runnable {
        Socket serverClient;
        int clientNo;
        int squre;

        ServerClientThread(Socket inSocket, int counter) {
            serverClient = inSocket;
            clientNo = counter;
        }

        @Override
        public void run() {
            try {
                DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
                DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
                String clientMessage, serverMessage;
                while (!serverClient.isClosed()) {
                    clientMessage = inStream.readUTF();
                    System.out.println("From Client-" + clientNo + ": Number is :" + clientMessage);
                    squre = Integer.parseInt(clientMessage) * Integer.parseInt(clientMessage);
                    serverMessage = "From Server to Client-" + clientNo + " Square of " + clientMessage + " is " + squre;
                    outStream.writeUTF(serverMessage);
                    outStream.flush();
                }
                inStream.close();
                outStream.close();
                serverClient.close();
            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                System.out.println("Client -" + clientNo + " exit!! ");
            }
        }
    }
}
