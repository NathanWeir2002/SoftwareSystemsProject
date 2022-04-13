package project.groupproject3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Server implements Runnable {
    private int portNumber;
    private ServerSocket socket;
    private ArrayList<Socket> clients;
    private ArrayList<ClientThread> clientThreads;
    public ObservableList<String> serverLog;
    public ObservableList<String> clientNames;
    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverLog = FXCollections.observableArrayList();
        clientNames = FXCollections.observableArrayList();
        clients = new ArrayList<>();
        clientThreads = new ArrayList<>();
        socket = new ServerSocket(portNumber);

    }

    public void run() {
        try {
            while (true) {
                final Socket clientSocket = socket.accept();
                clients.add(clientSocket);
                ClientThread clientThreadHolderClass = new ClientThread(clientSocket, this);
                Thread clientThread = new Thread(clientThreadHolderClass);
                clientThreads.add(clientThreadHolderClass);
                clientThread.setDaemon(true);
                clientThread.start();
                ServerMainApplication.threads.add(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToAllSockets(String input) {
        for (ClientThread clientThread : clientThreads) {
            clientThread.writeToServer(input);
        }
    }

}
