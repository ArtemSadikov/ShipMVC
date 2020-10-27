package server;

import Phone.Phone;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");

            while (true) {
                try (Phone phone = new Phone(server)) {
                    String request = phone.ReadLine();
                    System.out.println(request);
                    String response = Math.floor(Math.random() * 1000) + "";
                    phone.WriteLine(response);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
