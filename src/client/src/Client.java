package client.src;

import Phone.Phone;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        try (Phone phone = new Phone("127.0.0.1", 8000)) {
            System.out.println("CONNECTED TO SERVER");
            String request = "Привет";
            System.out.println("Request: " + request);
            phone.WriteLine(request);
            String response = phone.ReadLine();
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
