package project.groupproject3;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * This is a client-server chatting program that supports multiple clients and also includes a graph displaying
 * clients' messaging activity over a period of time.
 * @author Ashar Izhar, Tin Trung Bien, Nathan Weir
 */

/**
 * The server application that will show the clients that have been connected to the server.
 */
public class ServerMainApplication extends Application {
    public static ArrayList<Thread> threadList;

    /**
     * Abstract method from abstract Application class that helps set up JavaFX application.
     * @param stage The main stage.
     * @throws IOException Handles any errors that occur in the process of creating a server.
     */
    @Override
    public void start(Stage stage) throws IOException {
        threadList = new ArrayList<>();     // begin new thread list after iteration
        stage.setTitle("Server");

        Server server = new Server(5555);   // constant port number in this program
        Thread serverThread = (new Thread(server));
        serverThread.setDaemon(true);
        serverThread.start();
        threadList.add(serverThread);

        Label clientLabel = new Label("List of Clients");
        ListView<String> clientView = new ListView<>();
        ObservableList<String> clientList = server.clientNamesList;
        clientView.setItems(clientList);

        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.add(clientLabel, 0, 0);
        rootPane.add(clientView, 0, 1);

        Scene scene = new Scene(rootPane, 300, 300);
        stage.setOnHidden(e -> shutdown()); //activates on normal shutdown
        stage.setScene(scene);
        stage.show();
    }


    public void shutdown(){
        Stage graph = new Stage();
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        CategoryAxis categoryAxis = new CategoryAxis();
        NumberAxis numberAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<String, Number>(categoryAxis, numberAxis);
        categoryAxis.setLabel("Time");
        numberAxis.setLabel("Messages");

        try {

            File file = new File("Logs.csv");
            if (!file.exists()){
                file.createNewFile();
            }

            ArrayList<String> data = new ArrayList<>();


            String[] temp;
            String lineTemp;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            //date, time, message (includes time and author)
            while ((lineTemp = bufferedReader.readLine()) != null) {
                if (!(lineTemp.equals("date,time,message"))){
                    temp = lineTemp.split(",", 2);
                    System.out.println(temp[0]);
                    System.out.println(date.format(dateFormat));
                    System.out.println(date.format(dateFormat).equals(temp[0]));
                    if (date.format(dateFormat).equals(temp[0])) {
                        //temp = Arrays.copyOf(temp, temp.length-1);
                        temp = temp[1].split(":");
                        data.add(temp[0]);
                    }
                }


            }
            bufferedReader.close();

            ArrayList<Integer> activity = new ArrayList<>();
            for (int zero = 0; zero < 24; zero++) {
                activity.add(0);
            }

            if (data != null){
                for (int a = 0; a < data.size(); a++) {
                    int index = Integer.parseInt(data.get(a));
                    System.out.println(index);
                    activity.set(index, activity.get(index) + 1);
                }
            }



            String[] times = {"0:00 - 0:59", "1:00 - 1:59", "2:00 - 2:59", "3:00 - 3:59",
                    "4:00 - 4:59", "5:00 - 5:59", "6:00 - 6:59", "7:00 - 7:59",
                    "8:00 - 8:59", "9:00 - 9:59", "10:00 - 10:59", "11:00 - 11:59",
                    "12:00 - 12:59", "13:00 - 13:59", "14:00 - 14:59", "15:00 - 15:59",
                    "16:00 - 16:59", "17:00 - 17:59", "18:00 - 18:59", "19:00 - 19:59",
                    "20:00 - 20:59", "21:00 - 21:59", "22:00 - 22:59", "23:00 - 23:59",};

            XYChart.Series series = new XYChart.Series();
            for (int x = 0; x < 24; x++) {
                series.getData().add(new XYChart.Data(times[x], activity.get(x)));
            }

            barChart.getData().add(series);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();



        }

        Scene scene = new Scene(barChart);
        graph.setTitle("Activity on " + date.format(dateFormat));
        graph.setScene(scene);
        graph.show();


    }


    /**
     * Main method that helps launch the JavaFX application (the server side).
     * @param args Any command-line arguments.
     */
    public static void main(String[] args){
        launch();
    }
}