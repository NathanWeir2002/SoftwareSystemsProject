package project.groupproject3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import javafx.application.Platform;

/**
 * Class that sets up capability of multiple clients being present in the server.
 */
public class ClientThread implements Runnable {

    private final Server mainServer;
    private BufferedReader inMessageReader;
    private PrintWriter outMessageWriter;
    private String clientName;

    /**
     * Constructor for this class.
     * @param clientSocket The client socket.
     * @param mainServer The current server.
     * @throws SocketException Handles any errors that occur in the process of creating or accessing a socket.
     */
    public ClientThread(Socket clientSocket, Server mainServer) throws SocketException {
        clientSocket.setTcpNoDelay(true);   // allow for faster updates to server when a client is added
        this.mainServer = mainServer;
        try {
            inMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outMessageWriter = new PrintWriter(clientSocket.getOutputStream(), true); // boolean true for auto flush
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gathers the client's username and socket information which the server keeps track of. The client's
     * message that they want to send is also handled here.
     */
    public void run() {
        try {
            this.clientName = inMessageReader.readLine();
            // ensure that contents below in run() method are added at some later point 
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // add client name and client socket address to server
                    mainServer.clientNamesList.add(clientName + " has joined the server.");
                }
            });
            while (true) {
                String messageToServer = inMessageReader.readLine();
                mainServer.outputMessageToSockets(messageToServer);
            }
        } catch (SocketException e) {
            // if a client leaves the server but the server is still running, the server will display the below message.
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mainServer.clientNamesList.add(clientName + " has left the server.");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that handles sending messages that have been stored in sockets to the server.
     * @param message The message to be sent to the server.
     */
    public void writeToServer(String message) {
        outMessageWriter.println(message);
    }
}
