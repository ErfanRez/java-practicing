import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class BSTVisualizer extends Application {

    private BST tree = new BST();
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();

    private Label inorderTitleLabel = new Label("Inorder: ");
    private Label inorderExprLabel = new Label();
    private Label reverseInorderTitleLabel = new Label("Reverse Inorder: ");
    private Label reverseInorderExprLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();

        centerPane.getChildren().add(visualizer);
        visualizer.prefWidthProperty().bind(centerPane.widthProperty());
        visualizer.prefHeightProperty().bind(centerPane.heightProperty());
        mainPane.setCenter(centerPane);

        TextField insertField = new TextField();
        insertField.setPromptText("Insert key");
        Button insertButton = new Button("Insert");

        TextField deleteField = new TextField();
        deleteField.setPromptText("Delete key");
        Button deleteButton = new Button("Delete");

        Button clearButton = new Button("Clear");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox controlPanel = new HBox(10, insertField, insertButton, deleteField, deleteButton, spacer, clearButton);
        controlPanel.setAlignment(Pos.CENTER_LEFT);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #f0f0f0;");

        Separator separator = new Separator();
        VBox topSection = new VBox(10, controlPanel, separator);
        topSection.setAlignment(Pos.CENTER);
        mainPane.setTop(topSection);

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
        bottomBox.setPadding(new Insets(0, 0, 30, 30));
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

        insertButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(insertField.getText().trim());
                tree.insert(key);
                insertField.clear();
                visualizeTree();
            } catch (NumberFormatException ex) {
                insertField.clear();
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(deleteField.getText().trim());
                tree.delete(key);
                deleteField.clear();
                visualizeTree();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tree Empty");
                alert.setHeaderText(null);
                alert.setContentText("The Tree is empty.");
                alert.showAndWait();
            }
        });

        clearButton.setOnAction(e -> {
            tree = new BST();
            visualizeTree();
        });
    }

    private void visualizeTree() {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 50;
        visualizer.drawTree(tree.getRoot(), centerX, centerY, TreePane.INITIAL_HGAP);

        inorderExprLabel.setText(tree.getInorder().trim());
        reverseInorderExprLabel.setText(tree.getReverseInorder().trim());
    }
}