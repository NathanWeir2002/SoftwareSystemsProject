module group.groupproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens group.groupproject to javafx.fxml;
    exports group.groupproject;
    exports group.serverside;
    opens group.serverside to javafx.fxml;
    exports group.clientside;
    opens group.clientside to javafx.fxml;
}