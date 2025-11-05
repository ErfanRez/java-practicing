package bst;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BSTHardCoded extends Application {

    private BST tree = new BST();
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();


    private Label inorderTitleLabel = new Label("Inorder: ");
    private Label inorderExprLabel = new Label();
    private Label reverseInorderTitleLabel = new Label("Reverse Inorder: ");
    private Label reverseInorderExprLabel = new Label();


    // Hard coded insertion and deletion from here
    private void testData(){

        tree.insert(40);
        tree.insert(60);
        tree.insert(50);
        tree.insert(33);
        tree.insert(55);
        tree.insert(11);


//         tree.delete(10);

    }

    @Override
    public void start(Stage primaryStage) {
        centerPane.getChildren().add(visualizer);
        visualizer.prefWidthProperty().bind(centerPane.widthProperty());
        visualizer.prefHeightProperty().bind(centerPane.heightProperty());


        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(centerPane);


        HBox inorderBox = new HBox(10);
        inorderBox.setAlignment(Pos.CENTER_LEFT);
        inorderTitleLabel.setStyle("-fx-font-size: 18px;");
        inorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        inorderBox.getChildren().addAll(inorderTitleLabel, inorderExprLabel);

        HBox reverseInorderBox = new HBox(10);
        reverseInorderBox.setAlignment(Pos.CENTER_LEFT);
        reverseInorderTitleLabel.setStyle("-fx-font-size: 18px;");
        reverseInorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        reverseInorderBox.getChildren().addAll(reverseInorderTitleLabel, reverseInorderExprLabel);

        VBox bottomBox = new VBox(10, inorderBox, reverseInorderBox);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30)); // top, right, bottom, left
        mainPane.setBottom(bottomBox);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("BST Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (tree.getRoot() != null) {
                visualizeTree();
            }
        });

        testData();

        visualizeTree();
    }

    private void visualizeTree() {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 50;
        visualizer.drawTree(tree.getRoot(), centerX, centerY, TreePane.INITIAL_HGAP);


        inorderExprLabel.setText(tree.getInorder().trim());
        reverseInorderExprLabel.setText(tree.getReverseInorder().trim());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
