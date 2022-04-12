package group.serverside;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ProjectServer {

    public static void main(String[] args) {
         String msg;
        System.out.println("Server Starting");
        try{

            ServerSocket serverSocket = new ServerSocket(1221);

            Socket socket = serverSocket.accept();

            System.out.println("A client has joined. " + socket.getRemoteSocketAddress());

            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(input);

            //reads client msg
            msg = bufferedReader.readLine();
            System.out.println("Client: " + msg );

            //writes to client
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.println("It works");
            output.flush();


            //closes input and output streams
            input.close();
            output.close();

            System.out.println("Closing Server");
            serverSocket.close();
            socket.close();

        } catch (IOException e){

            System.out.println("Server failed to run.");
            System.out.println(Arrays.toString(e.getStackTrace()));


        }


    }

    public void serverClose(){


    }



}
