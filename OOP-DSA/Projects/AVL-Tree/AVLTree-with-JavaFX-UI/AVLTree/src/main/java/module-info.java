module ds.avl.avltree {
    requires javafx.controls;
    requires javafx.fxml;


    opens ds.avl.avltree to javafx.fxml;
    exports ds.avl.avltree;
}