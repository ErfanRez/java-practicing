package redblack;

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

public class RedBlackTreeHardCoded extends Application {

    private RedBlackTree tree = new RedBlackTree();
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();

    private Label inorderTitleLabel = new Label("Inorder: ");
    private Label inorderExprLabel = new Label();
    private Label reverseInorderTitleLabel = new Label("Reverse Inorder: ");
    private Label reverseInorderExprLabel = new Label();

    // Hard coded insertion from here
    private void testData() {
        tree.insert(40);
        tree.insert(60);
        tree.insert(50);
        tree.insert(33);
        tree.insert(55);
        tree.insert(11);

//        tree.delete(50);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        centerPane.getChildren().add(visualizer);
        visualizer.prefWidthProperty().bind(centerPane.widthProperty());
        visualizer.prefHeightProperty().bind(centerPane.heightProperty());
        mainPane.setCenter(centerPane);

        setupUI(mainPane);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("Red-Black Tree Visualizer (Hardcoded)");
        primaryStage.setScene(scene);
        primaryStage.show();

        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (tree.getRoot() != tree.getTNULL()) {
                visualizeTree();
            }
        });


        testData();
        visualizeTree();
    }

    private void setupUI(BorderPane mainPane) {
        HBox inorderBox = new HBox(10, inorderTitleLabel, inorderExprLabel);
        HBox reverseInorderBox = new HBox(10, reverseInorderTitleLabel, reverseInorderExprLabel);

        inorderTitleLabel.setStyle("-fx-font-size: 18px;");
        inorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        reverseInorderTitleLabel.setStyle("-fx-font-size: 18px;");
        reverseInorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox bottomBox = new VBox(10, inorderBox, reverseInorderBox);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30));
        mainPane.setBottom(bottomBox);
    }

    private void visualizeTree() {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 50;
        visualizer.drawTree(tree.getRoot(), centerX, centerY, TreePane.INITIAL_HGAP, tree.getTNULL());

        inorderExprLabel.setText(tree.getInorder());
        reverseInorderExprLabel.setText(tree.getReverseInorder());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
