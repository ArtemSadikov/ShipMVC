package client.src;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;

public class Controller {

    public Text connectStatus;
    @FXML
    private Button connectBtn;

    @FXML
    void initialize() {

    }

    public void connectPress(javafx.event.ActionEvent actionEvent) {
        connectBtn.setText(".!.");
        ClientThread clientThread = new ClientThread();
        Thread thread = new Thread(clientThread);
        thread.start();
        if(!thread.isAlive()){
            connectStatus.setText("failed to connect");
            return;
        }
        connectStatus.setText("You're connected!");
    }

}
