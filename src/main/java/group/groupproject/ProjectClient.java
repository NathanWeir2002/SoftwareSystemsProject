package group.groupproject;

import java.io.*;
import java.net.*;

public class ProjectClient {

    public static void main(String[] args) {
        try{
            //173.34.5.118
            Socket socket = new Socket("localhost", 1221);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF("Hellllo");
            output.flush();
            output.close();

            socket.close(); // closes socket

        } catch (Exception e){
            System.out.println("Client failed");
        }
    }

}
