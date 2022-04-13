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
    private ArrayList<Thread> threads;

    @Override
    public void start(Stage primaryStage) {
        threads = new ArrayList<>();
        primaryStage.setTitle("Choose Your Name!");

        GridPane rootPane = new GridPane();
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();

        Label nameLabel = new Label("Name:");
        Label errorLabel = new Label();
        Button submitClientInfoButton = new Button("Submit");
        submitClientInfoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                Client client;
                try {
                    client = new Client(nameField.getText());
                    Thread clientThread = new Thread(client);
                    clientThread.setDaemon(true);
                    clientThread.start();
                    threads.add(clientThread);

                    primaryStage.close();
                    primaryStage.setTitle(nameField.getText() + " - Messenger");
                    primaryStage.setScene(makeChatUI(client));
                    primaryStage.show();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        rootPane.add(nameLabel, 0, 0);
        rootPane.add(nameField, 1, 0);
        rootPane.add(submitClientInfoButton, 0, 3, 2, 1);
        rootPane.add(errorLabel, 0, 4);

        Scene scene = new Scene(rootPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        for (Thread thread: threads){
            thread.interrupt();
        }
    }

    public Scene makeChatUI(Client client) {
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setHgap(10);
        rootPane.setVgap(10);

        ListView<String> chatListView = new ListView<>();
        chatListView.setItems(client.chatLog);

        TextField chatTextField = new TextField();
        chatTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.writeToServer(chatTextField.getText());
                chatTextField.clear();
            }
        });

        rootPane.add(chatListView, 0, 0);
        rootPane.add(chatTextField, 0, 1);

        return new Scene(rootPane, 300, 300);

    }

    public static void main(String[] args){
        launch();
    }
}