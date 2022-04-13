package project.groupproject3;

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


public class ServerMainApplication extends Application {
    public static ArrayList<Thread> threads;

    @Override
    public void start(Stage primaryStage) throws Exception {
        threads = new ArrayList<>();
        primaryStage.setTitle("Server");

        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);

        Server server = new Server(5555);
        Thread serverThread = (new Thread(server));
        serverThread.setName("Thread");
        serverThread.setDaemon(true);
        serverThread.start();
        threads.add(serverThread);

        Label clientLabel = new Label("Clients Connected");
        ListView<String> clientView = new ListView<String>();
        ObservableList<String> clientList = server.clientNames;
        clientView.setItems(clientList);

        rootPane.add(clientLabel, 0, 0);
        rootPane.add(clientView, 0, 1);

        Scene scene = new Scene(rootPane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }
}