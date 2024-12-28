module ds.avl {
    requires javafx.controls;
    requires javafx.fxml;


    opens ds.avl to javafx.fxml;
    exports ds.avl;
}