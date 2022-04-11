module group.groupproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens group.groupproject to javafx.fxml;
    exports group.groupproject;
}