package project.groupproject3;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The client application that will ask clients for their username and then allow them to start messaging.
 */
public class ClientMainApplication extends Application {
    private ArrayList<Thread> threadList;
    private LocalTime time;
    public DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
    /**
     * Abstract method from abstract Application class that helps set up JavaFX application.
     * @param stage The main stage.
     */
    @Override
    public void start(Stage stage) {
        threadList = new ArrayList<>();

        TextField usernameField = new TextField();

        Label usernameLabel = new Label();
        usernameLabel.setText("Name:");
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(new EventHandler<>() {
            /**
             * Method that controls what happens once the client has entered their username and clicked the 'Submit'
             * button.
             *
             * @param Event Event of client clicking the button.
             */
            @Override
            public void handle(ActionEvent Event) {
                Client client;
                try {
                    client = new Client(usernameField.getText());
                    Thread clientThread = new Thread(client);
                    clientThread.setDaemon(true);
                    clientThread.start();
                    threadList.add(clientThread);

                    stage.close();  // start new stage afterwards with new scene

                    stage.setTitle(usernameField.getText() + " - Messenger");
                    stage.setScene(messagingUIContent(client));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        GridPane gridpane = new GridPane();
        gridpane.setVgap(15);
        gridpane.add(usernameLabel, 0, 0);
        gridpane.add(usernameField, 1, 0);
        gridpane.add(submitButton, 0, 2);
        gridpane.setAlignment(Pos.CENTER);

        stage.setTitle("Choose Your Username");
        Scene scene = new Scene(gridpane, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method builds the contents of the messaging system for each client.
     * @param client The current client.
     * @return The completed messaging system scene.
     */
    public Scene messagingUIContent(Client client) {
        ListView<String> messagingLogList = new ListView<>();
        messagingLogList.setItems(client.messages);

        TextField messageField = new TextField();
        messageField.setPromptText("Enter message here...");
        messageField.setOnAction(new EventHandler<>() {
            /**
             * Method that controls what happens when the client hits enter on their keyboard to send the message.
             * @param event Event of client hitting enter on their keyboard.
             */
            @Override
            public void handle(ActionEvent event) {
                // enter message into messages box when client hits enter on keyboard
                client.writeMessageToServer("(" + time.now().format(formatTime) + ") " + messageField.getText());
                messageField.clear();
            }
        });
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(15));
        gridpane.setHgap(10);
        gridpane.setVgap(15);
        gridpane.add(messagingLogList, 0, 0);
        gridpane.add(messageField, 0, 1);
        gridpane.setAlignment(Pos.CENTER);

        return new Scene(gridpane, 300, 300);
    }

    /**
     * Method that controls what happens when a client exits the server.
     * @throws Exception Handles any errors that occur in the process of interrupting the appropriate thread.
     */
    @Override
    public void stop() throws Exception {
        super.stop();   // stop() function from Application abstract class
        for (Thread thread: threadList){
            thread.interrupt();
        }
    }

    /**
     * Main method that helps launch the JavaFX application (the client side).
     * @param args Any command-line arguments.
     */
    public static void main(String[] args){
        launch();
    }
}