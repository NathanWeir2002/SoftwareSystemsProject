package group.groupproject;

import java.io.*;
import java.net.*;

public class ProjectServer {

    public static void main(String[] args) {

        try{

            ServerSocket serverSocket = new ServerSocket(1221);
            System.out.println("Server Starting");
            Socket socket = serverSocket.accept();

            System.out.println("Server Started");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            String message = input.readUTF();
            System.out.println(message);
            System.out.println("Closing Server");
            serverSocket.close();
            socket.close();

        } catch (Exception e){

            System.out.println("Server failed to run.");


        }


    }



}
