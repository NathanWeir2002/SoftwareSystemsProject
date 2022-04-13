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
    private final BufferedReader reader;  // server to client
    private final PrintWriter writer;     // client to server
    private final String name;
    public ObservableList<String> messages;

    public Client(String name) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 5555);
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        messages = FXCollections.observableArrayList();
        this.name = name;
        writer.println(name);
    }

    public void writeMessageToServer(String message) {
        writer.println(name + ": " + message);
    }

    public void run() {
        while (true) {
            try {
                final String messageFromServer = reader.readLine();
                Platform.runLater(new Runnable() {
                    public void run() {
                        messages.add(messageFromServer);
                    }
                });
            } catch (SocketException e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        messages.add("Encountered problem with the server.\n Please close all current client\n " +
                                "messengers and then restart the server.");
                    }
                });
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
