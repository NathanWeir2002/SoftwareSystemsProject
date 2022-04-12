package group.clientside;

import java.io.*;
import java.net.*;

public class ProjectClient {

    public static void main(String[] args) {
        try{
            Socket socket = new Socket("127.0.0.1", 1221);


            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(input);

            //writes msg
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.println("Client works");
            output.flush();

            //reads msg
            String msg = bufferedReader.readLine();
            System.out.println("Server: " + msg );


            input.close();
            output.close();

            socket.close(); // closes socket


        } catch (Exception e){
            System.out.println("Client failed");
        }
    }

}
