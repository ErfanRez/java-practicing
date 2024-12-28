module ds.expressiontree {
    requires javafx.controls;
    requires javafx.fxml;


    opens ds.expressiontree to javafx.fxml;
    exports ds.expressiontree;
}