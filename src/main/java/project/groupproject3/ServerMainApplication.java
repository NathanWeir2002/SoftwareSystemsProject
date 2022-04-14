package project.groupproject3;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This is a client-server chatting program that supports multiple clients and also includes a graph displaying
 * clients' messaging activity over a period of time.
 * @author Ashar Izhar, Tin Trung Bien, Nathan Weir
 */

/**
 * The server application that will show the clients that have been connected to the server.
 */
public class ServerMainApplication extends Application {
    public static ArrayList<Thread> threadList;

    /**
     * Abstract method from abstract Application class that helps set up JavaFX application.
     * @param stage The main stage.
     * @throws IOException Handles any errors that occur in the process of creating a server.
     */
    @Override
    public void start(Stage stage) throws IOException {
        threadList = new ArrayList<>();     // begin new thread list after iteration
        stage.setTitle("Server");

        Server server = new Server(5555);   // constant port number in this program
        Thread serverThread = (new Thread(server));
        serverThread.setDaemon(true);
        serverThread.start();
        threadList.add(serverThread);

        Label clientLabel = new Label("List of Clients");
        ListView<String> clientView = new ListView<>();
        ObservableList<String> clientList = server.clientNamesList;
        clientView.setItems(clientList);

        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.add(clientLabel, 0, 0);
        rootPane.add(clientView, 0, 1);

        Scene scene = new Scene(rootPane, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method that helps launch the JavaFX application (the server side).
     * @param args Any command-line arguments.
     */
    public static void main(String[] args){
        launch();
    }
}