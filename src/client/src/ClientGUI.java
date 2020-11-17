package client.src;

import client.src.ClientThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
//        Button connect = new Button("connectBtn");
//        connect.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                connect.setText("COnnect !");
//
//            }
//        });
//        ClientThread clientThread = new ClientThread();
//        Thread thread = new Thread(clientThread);
//        thread.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
