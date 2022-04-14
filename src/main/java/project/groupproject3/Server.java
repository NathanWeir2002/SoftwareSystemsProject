package project.groupproject3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Server implements Runnable {
    public ObservableList<String> clientNamesList;
    private final ArrayList<ClientThread> clientThreads;    // list containing threads that clients are on
    private final ServerSocket serverSocket;
    public Server(int portNumber) throws IOException {
        clientNamesList = FXCollections.observableArrayList();
        clientThreads = new ArrayList<>();
        serverSocket = new ServerSocket(portNumber);
    }

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

    public void outputToSockets(String message) {
        for (ClientThread clientThread : clientThreads) {
            // write messages to server so that messages box can be updated for all clients
            clientThread.writeToServer(message);
        }
    }
}
