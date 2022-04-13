package project.groupproject3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client implements Runnable {
    private Socket clientSocket;
    private BufferedReader serverToClientReader;
    private PrintWriter clientToServerWriter;
    private String name;
    public ObservableList<String> chatLog;

    public Client(String name) throws IOException {
        clientSocket = new Socket("127.0.0.1", 5555);
        serverToClientReader = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        clientToServerWriter = new PrintWriter(
                clientSocket.getOutputStream(), true);
        chatLog = FXCollections.observableArrayList();
        this.name = name;
        clientToServerWriter.println(name);
    }

    public void writeToServer(String input) {
        clientToServerWriter.println(name + " : " + input);
    }

    public void run() {
        while (true) {
            try {
                final String inputFromServer = serverToClientReader.readLine();
                Platform.runLater(new Runnable() {
                    public void run() {
                        chatLog.add(inputFromServer);
                    }
                });
            } catch (SocketException e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        chatLog.add("Error in server");
                    }
                });
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
