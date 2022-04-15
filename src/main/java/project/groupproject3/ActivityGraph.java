package project.groupproject3;

import java.io.*;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;



import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

/**
 * Prepares graph that is presented once the server has shutdown.
 */
public class ActivityGraph {

    private final GridPane root;
    private LocalDate date = LocalDate.now();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public ActivityGraph() {
        root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        CategoryAxis categoryAxis = new CategoryAxis();
        NumberAxis numberAxis = new NumberAxis();
        BarChart<String, Number> barChart =  new BarChart<String, Number>(categoryAxis, numberAxis);
        categoryAxis.setLabel("Time");
        numberAxis.setLabel("Messages");

        try{
            File file = new File("./src/main/resources/Logs.csv");
            ArrayList<String> data = null;
            String[] temp;
            String lineTemp;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            //date, time, message (includes time and author)
            while((lineTemp = bufferedReader.readLine()) != null){
                temp = lineTemp.split(",");

                if (date.format(dateFormat) == temp[0]){
                    //temp = Arrays.copyOf(temp, temp.length-1);
                    temp = temp[1].split(":");
                    data.add(temp[0]);
                }

            }

            ArrayList<Integer> activity = new ArrayList<>();
            for (int zero = 0; zero < 24; zero++){
                activity.add(0);
            }

            for (int a =0; a < data.size(); a++){
                activity.set(Integer.parseInt(data.get(a)), activity.get(Integer.parseInt(data.get(a)))+1);
            }

            String[] times = {"0:00 - 0:59", "1:00 - 1:59", "2:00 - 2:59", "3:00 - 3:59",
                    "4:00 - 4:59", "5:00 - 5:59", "6:00 - 6:59", "7:00 - 7:59",
                    "8:00 - 8:59", "9:00 - 9:59", "10:00 - 10:59", "11:00 - 11:59",
                    "12:00 - 12:59", "13:00 - 13:59", "14:00 - 14:59", "15:00 - 15:59",
                    "16:00 - 16:59", "17:00 - 17:59", "18:00 - 18:59", "19:00 - 19:59",
                    "20:00 - 20:59", "21:00 - 21:59", "22:00 - 22:59", "23:00 - 23:59", };

            XYChart.Series series = new XYChart.Series();
            for (int x = 0; x < 24; x++){
                series.getData().add(new XYChart.Data(times[x], activity.get(x)));
            }

            Scene scene = new Scene(barChart, 800, 800);
            barChart.getData().add(series);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GridPane getRoot(){
        return root;
    }

}
