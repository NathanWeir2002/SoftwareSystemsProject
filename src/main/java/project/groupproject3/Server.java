package project.groupproject3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that sets up the server side of the application.
 */
public class Server implements Runnable {
    public ObservableList<String> clientNamesList;
    private final ArrayList<ClientThread> clientThreads;    // list containing threads that clients are on
    private final ServerSocket serverSocket;

    /**
     * Constructor for this class.
     * @param portNumber The server's port number.
     * @throws IOException Handles any errors that occur in the process of creating a server.
     */
    public Server(int portNumber) throws IOException {
        clientNamesList = FXCollections.observableArrayList();
        clientThreads = new ArrayList<>();
        serverSocket = new ServerSocket(portNumber);
    }

    /**
     * Method that helps form connection between server and each client for messaging process to occur.
     */
    public void run() {
        try {
            while (true) {      // adding new client to server
                final Socket clientSocket = serverSocket.accept();
                ClientThread clientThreadHolderClass = new ClientThread(clientSocket, this);
                Thread clientThread = new Thread(clientThreadHolderClass);
                clientThreads.add(clientThreadHolderClass);
                clientThread.setDaemon(true);
                clientThread.start();
                ServerMainApplication.threadList.add(clientThread);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that handles the process of sending messages that a client has created to the sockets to keep track of
     * that data. That information is then sent to the server.
     * @param message The message that the client wants to send.
     */
    public void outputToSockets(String message) {
        for (ClientThread clientThread : clientThreads) {
            // write messages to server so that messages box can be updated for all clients
            clientThread.writeToServer(message);
        }
    }
}
