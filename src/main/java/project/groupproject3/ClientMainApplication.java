package project.groupproject3;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientMainApplication extends Application {
    private ArrayList<Thread> threadList;

    @Override
    public void start(Stage stage) {
        threadList = new ArrayList<>();
        stage.setTitle("Choose Your Username!");

        TextField usernameField = new TextField();

        Label usernameLabel = new Label();
        usernameLabel.setText("Name:");
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                Client client;
                try {
                    client = new Client(usernameField.getText());
                    Thread clientThread = new Thread(client);
                    clientThread.setDaemon(true);
                    clientThread.start();
                    threadList.add(clientThread);

                    stage.close();
                    
                    stage.setTitle(usernameField.getText() + " - Messenger");
                    stage.setScene(messagingUIContent(client));
                    stage.show();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        GridPane rootPane = new GridPane();
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.add(usernameLabel, 0, 0);
        rootPane.add(usernameField, 1, 0);
        rootPane.add(submitButton, 0, 3, 2, 1);

        Scene scene = new Scene(rootPane, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        for (Thread thread: threadList){
            thread.interrupt();
        }
    }

    public Scene messagingUIContent(Client client) {
        ListView<String> messagingLogList = new ListView<>();
        messagingLogList.setItems(client.messages);

        TextField messageField = new TextField();
        messageField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.writeMessageToServer(messageField.getText());
                messageField.clear();
            }
        });
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setHgap(10);
        rootPane.setVgap(10);
        rootPane.add(messagingLogList, 0, 0);
        rootPane.add(messageField, 0, 1);

        return new Scene(rootPane, 300, 300);
    }

    public static void main(String[] args){
        launch();
    }
}