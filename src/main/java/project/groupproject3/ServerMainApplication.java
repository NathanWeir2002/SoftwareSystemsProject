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
    public static ArrayList<Thread> threadList;

    @Override
    public void start(Stage stage) throws Exception {
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

    public static void main(String[] args){
        launch();
    }
}