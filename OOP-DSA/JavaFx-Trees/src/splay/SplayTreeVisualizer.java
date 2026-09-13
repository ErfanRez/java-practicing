package splay;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SplayTreeVisualizer extends Application {

    private SplayTree tree = new SplayTree();
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();

    private Label inorderTitleLabel = new Label("Inorder: ");
    private Label inorderExprLabel = new Label();

    private Label preorderTitleLabel = new Label("Preorder: ");
    private Label preorderExprLabel = new Label();

    private Label postorderTitleLabel = new Label("Postorder: ");
    private Label postorderExprLabel = new Label();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {

        BorderPane mainPane = new BorderPane();

        centerPane.getChildren().add(visualizer);

        visualizer.prefWidthProperty().bind(centerPane.widthProperty());
        visualizer.prefHeightProperty().bind(centerPane.heightProperty());

        mainPane.setCenter(centerPane);


        TextField inputField = new TextField();
        inputField.setPromptText("Enter key");

        Button insertButton = new Button("Insert");

        Button deleteButton = new Button("Delete");

        Button searchButton = new Button("Search");

        Button clearButton = new Button("Clear");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        HBox controlPanel = new HBox(10, inputField, insertButton, deleteButton, searchButton, spacer, clearButton);

        controlPanel.setAlignment(Pos.CENTER_LEFT);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #f0f0f0;");


        Separator separator = new Separator();

        VBox topSection = new VBox(10, controlPanel, separator);

        topSection.setAlignment(Pos.CENTER);

        mainPane.setTop(topSection);

        configureLabel(inorderTitleLabel, inorderExprLabel);
        configureLabel(preorderTitleLabel, preorderExprLabel);
        configureLabel(postorderTitleLabel, postorderExprLabel);


        HBox inorderBox = new HBox(10, inorderTitleLabel, inorderExprLabel);
        inorderBox.setAlignment(Pos.CENTER_LEFT);


        HBox preorderBox = new HBox(10, preorderTitleLabel, preorderExprLabel);
        preorderBox.setAlignment(Pos.CENTER_LEFT);


        HBox postorderBox = new HBox(10, postorderTitleLabel, postorderExprLabel);
        postorderBox.setAlignment(Pos.CENTER_LEFT);


        VBox bottomBox = new VBox(10, inorderBox, preorderBox, postorderBox);

        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30));

        mainPane.setBottom(bottomBox);

        Scene scene = new Scene(mainPane, 1000, 700);

        primaryStage.setTitle("Splay Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> { if (tree.getRoot() != null) { visualizeTree(); } });

        insertButton.setOnAction(e -> {

            try {

                int key = Integer.parseInt(inputField.getText().trim());

                tree.insert(key);

                inputField.clear();

                visualizeTree();

            } catch (NumberFormatException ex) {

                inputField.clear();

                showInvalidInput();
            }
        });


        deleteButton.setOnAction(e -> {

            try {

                int key = Integer.parseInt(inputField.getText().trim());

                tree.remove(key);

                inputField.clear();

                visualizeTree();

            } catch (NumberFormatException ex) {

                inputField.clear();

                showInvalidInput();
            }
        });


        // =========================================================
        // Search
        // =========================================================

        searchButton.setOnAction(e -> {

            try {

                int key = Integer.parseInt(inputField.getText().trim());

                boolean found = tree.search(key);

                inputField.clear();

                // Important:
                // search() can change the tree because the
                // searched node is splayed to the root.
                visualizeTree();

                if (!found) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Search");
                    alert.setHeaderText(null);

                    alert.setContentText("Key " + key + " was not found.");

                    alert.showAndWait();
                }

            } catch (NumberFormatException ex) {

                inputField.clear();

                showInvalidInput();
            }
        });


        // =========================================================
        // Clear
        // =========================================================

        clearButton.setOnAction(e -> {

            tree.clear();

            visualizeTree();
        });
    }


    // =============================================================
    // Visualization
    // =============================================================

    private void visualizeTree() {

        visualizer.clear();

        TreeNode root = tree.getRoot();

        if (root != null) {

            double centerX = centerPane.getWidth() / 2;

            double centerY = 50;

            visualizer.drawTree(root, centerX, centerY, TreePane.INITIAL_HGAP);
        }

        updateTraversals();
    }


    // =============================================================
    // Traversals
    // =============================================================

    private void updateTraversals() {

        inorderExprLabel.setText(getInorder());

        preorderExprLabel.setText(getPreorder());

        postorderExprLabel.setText(getPostorder());
    }


    private String getInorder() {

        StringBuilder result = new StringBuilder();

        inorder(tree.getRoot(), result);

        return result.toString();
    }


    private void inorder(TreeNode node, StringBuilder result) {

        if (node == null)
            return;

        inorder(node.left, result);

        result.append(node.element).append(" ");

        inorder(node.right, result);
    }


    private String getPreorder() {

        StringBuilder result = new StringBuilder();

        preorder(tree.getRoot(), result);

        return result.toString();
    }


    private void preorder(TreeNode node, StringBuilder result) {

        if (node == null)
            return;

        result.append(node.element).append(" ");

        preorder(node.left, result);

        preorder(node.right, result);
    }


    private String getPostorder() {

        StringBuilder result = new StringBuilder();

        postorder(tree.getRoot(), result);

        return result.toString();
    }


    private void postorder(TreeNode node, StringBuilder result) {

        if (node == null)
            return;

        postorder(node.left, result);

        postorder(node.right, result);

        result.append(node.element).append(" ");
    }


    // =============================================================
    // UI helpers
    // =============================================================

    private void configureLabel(Label title, Label expression) {

        title.setStyle("-fx-font-size: 18px;");

        expression.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;");
    }


    private void showInvalidInput() {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);

        alert.setContentText("Please enter a valid integer.");

        alert.showAndWait();
    }
}
