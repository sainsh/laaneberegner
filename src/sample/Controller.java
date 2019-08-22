package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Date;


public class Controller {

    @FXML
    TextArea textArea;

    Server server;

    @FXML
    public void initialize(){
        server = new Server(this);

    }

    public void onClick(ActionEvent actionEvent) {
        textArea.setText("Server Started: " + new Date().toString());
        server.run();
    }
}
