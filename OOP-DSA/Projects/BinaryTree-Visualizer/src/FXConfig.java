// BinaryTreeVisualizer.java
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXConfig extends Application {

    private BinaryTree tree = new BinaryTree();
    private TreePane treePane = new TreePane();
    private StackPane centerPane = new StackPane();

    private Label inorderTitleLabel = new Label("Inorder: ");
    private Label inorderExprLabel = new Label();
    private Label preorderTitleLabel = new Label("Preorder: ");
    private Label preorderExprLabel = new Label();
    private Label postorderTitleLabel = new Label("Postorder: ");
    private Label postorderExprLabel = new Label();

    private Label reverseInorderTitleLabel = new Label("Reverse Inorder: ");
    private Label reverseInorderExprLabel = new Label();
    private Label reversePreorderTitleLabel = new Label("Reverse Preorder: ");
    private Label reversePreorderExprLabel = new Label();
    private Label reversePostorderTitleLabel = new Label("Reverse Postorder: ");
    private Label reversePostorderExprLabel = new Label();

    private Label levelOrderTitleLabel = new Label("Level Order: ");
    private Label levelOrderExprLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        centerPane.getChildren().add(treePane);
        treePane.prefWidthProperty().bind(centerPane.widthProperty());
        treePane.prefHeightProperty().bind(centerPane.heightProperty());
        mainPane.setCenter(centerPane);

        String titleStyle = "-fx-font-size: 18px;";
        String exprStyle = "-fx-font-size: 18px; -fx-font-weight: bold;";
        inorderTitleLabel.setStyle(titleStyle);
        inorderExprLabel.setStyle(exprStyle);
        preorderTitleLabel.setStyle(titleStyle);
        preorderExprLabel.setStyle(exprStyle);
        postorderTitleLabel.setStyle(titleStyle);
        postorderExprLabel.setStyle(exprStyle);
        reverseInorderTitleLabel.setStyle(titleStyle);
        reverseInorderExprLabel.setStyle(exprStyle);
        reversePreorderTitleLabel.setStyle(titleStyle);
        reversePreorderExprLabel.setStyle(exprStyle);
        reversePostorderTitleLabel.setStyle(titleStyle);
        reversePostorderExprLabel.setStyle(exprStyle);
        levelOrderTitleLabel.setStyle(titleStyle);
        levelOrderExprLabel.setStyle(exprStyle);

        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(
                new HBox(10, inorderTitleLabel, inorderExprLabel),
                new HBox(10, preorderTitleLabel, preorderExprLabel),
                new HBox(10, postorderTitleLabel, postorderExprLabel)
        );
        leftBox.setAlignment(Pos.CENTER_LEFT);

        VBox rightBox = new VBox(10);
        rightBox.getChildren().addAll(
                new HBox(10, reverseInorderTitleLabel, reverseInorderExprLabel),
                new HBox(10, reversePreorderTitleLabel, reversePreorderExprLabel),
                new HBox(10, reversePostorderTitleLabel, reversePostorderExprLabel)
        );
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setPadding(new Insets(0, 20, 0, 0));

        HBox traversalsBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        traversalsBox.getChildren().addAll(leftBox, spacer, rightBox);
        traversalsBox.setAlignment(Pos.CENTER);

        HBox levelOrderRow = new HBox(10, levelOrderTitleLabel, levelOrderExprLabel);
        levelOrderRow.setAlignment(Pos.CENTER_LEFT);
        VBox bottomBox = new VBox(20, traversalsBox, levelOrderRow);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30));
        mainPane.setBottom(bottomBox);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("Binary Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> updateDisplay());
        centerPane.heightProperty().addListener((obs, oldVal, newVal) -> updateDisplay());
        updateDisplay();
    }

    private void updateDisplay() {
        treePane.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 50;
        treePane.drawTree(tree.getRoot(), centerX, centerY, TreePane.INITIAL_HGAP);

        inorderExprLabel.setText(tree.getInorder());
        preorderExprLabel.setText(tree.getPreorder());
        postorderExprLabel.setText(tree.getPostorder());
        reverseInorderExprLabel.setText(tree.getReverseInorder());
        reversePreorderExprLabel.setText(tree.getReversePreorder());
        reversePostorderExprLabel.setText(tree.getReversePostorder());
        levelOrderExprLabel.setText(tree.getLevelOrder());
    }

    public static void main(String[] args) {
        launch(args);
    }
}




