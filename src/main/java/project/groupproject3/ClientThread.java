package project.groupproject3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import javafx.application.Platform;

public class ClientThread implements Runnable {

    private final Socket clientSocket;
    private final Server mainServer;
    private BufferedReader inMessageReader;
    private PrintWriter outMessageWriter;
    private String clientName;

    public ClientThread(Socket clientSocket, Server mainServer) {
        this.clientSocket = clientSocket;
        this.mainServer = mainServer;
        try {
            inMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outMessageWriter = new PrintWriter(clientSocket.getOutputStream(), true); // boolean true for auto flush
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            this.clientName = inMessageReader.readLine();
            // ensure that contents below in run() method are added at some later point 
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // add client name and client socket address to server 
                    mainServer.clientNamesList.add(clientName + ": " + clientSocket.getRemoteSocketAddress());
                }
            });
            while (true) {
                String messageToServer = inMessageReader.readLine();
                mainServer.outputToSockets(messageToServer);
            }
        } catch (SocketException e) {
            /*
            if a client leaves the server and the server's still running afterwards, the server will display the below
            message when another client sends a message.
             */
            mainServer.clientNamesList.add(clientName + " has left server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer(String input) {
        outMessageWriter.println(input);
    }
}
