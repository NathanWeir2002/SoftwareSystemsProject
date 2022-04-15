package project.groupproject3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.LocalDate;
import java.io.*;

/**
 * Class that sets up the server side of the application.
 */
public class Server implements Runnable {
    public ObservableList<String> clientNamesList;
    private final ArrayList<ClientThread> clientThreads;    // list containing threads that clients are on
    private final ServerSocket serverSocket;

    private File file = new File("Logs.csv");
    private LocalTime time;
    private LocalDate date;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); //for the graph
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private FileWriter fileWriter;

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
    public void outputMessageToSockets(String message) throws IOException {
        //check if file is empty. If empty or nonexistent, returns 0;
        long fileLength = file.length(); //can only check when file isn't opened else returns 0


        if (fileLength>0){
            //writes to file by appending text to the end of the file
            fileWriter = new FileWriter(file, true);
            log(fileWriter, message, fileLength);
            fileWriter.flush();
        } else {
            //writes the header first before writing actual message
            //could use improvement
            FileWriter fW = new FileWriter(file, false); //writes at the top
            fileWriter = new FileWriter(file, true); //appends
            String header = "date,time,message"; //can be increased for things like number of characters, etc.

            fW.write(header);
            fW.flush();

            log(fileWriter, message, fileLength);
            fW.close();
            fileWriter.flush();

        }

        fileWriter.close();


        for (ClientThread clientThread : clientThreads) {
            // write messages to server so that messages box can be updated for all clients
            clientThread.writeToServer(message);
        }



    }
    //logs messages in csv format
    public void log(FileWriter fWriter, String message, long length) throws IOException {

        try{

            String logMSG = "\n" +date.now().format(dateFormatter) + ","
                    + time.now().format(timeFormatter) + ","
                    + message;
            System.out.println(logMSG);
            fWriter.write(logMSG);

        } catch (IOException e){
            System.out.println("Error occurred at logging");
            e.printStackTrace();
            fWriter.close(); //closing just in case
        }


    }
}
