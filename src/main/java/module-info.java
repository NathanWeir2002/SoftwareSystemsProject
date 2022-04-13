module project.groupproject3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.groupproject3 to javafx.fxml;
    exports project.groupproject3;
}