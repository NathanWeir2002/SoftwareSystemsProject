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

/**
 * Class that sets up the client side of the application.
 */
public class Client implements Runnable {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final String username;
    public ObservableList<String> messages;

    /**
     * Constructor for this class.
     * @param username Client's username.
     * @throws IOException Handles any errors that occur in the process of getting either the input stream or the output
     * stream for the client socket.
     */
    public Client(String username) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 5555);    // constant host address and port number in program
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        messages = FXCollections.observableArrayList();
        this.username = username;
        writer.println(username);
    }

    /**
     * Method that handles writing messages to server.
     * @param message The client's message.
     */
    public void writeMessageToServer(String message) {
        writer.println(username + ": " + message);  // how messages from clients will appear on messages box
    }

    /**
     * Method that allows messages from each client to appear collectively for each client on messages box via
     * connection between server and each client.
     */
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
                    // message to appear when server has been closed but clients are still active
                    public void run() {
                        messages.add("Encountered problem with the server.\nPlease close all current client\n" +
                                "messengers and then restart the server.");
                    }
                });
                // ensure messages can no longer be sent by any client because of the server being closed
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
