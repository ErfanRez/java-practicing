package bst;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BSTVisualizer extends Application {

    private TreeNode root;
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();


    private Label inorderTitleLabel = new Label("Inorder: ");
    private Label inorderExprLabel = new Label();
    private Label preorderTitleLabel = new Label("Preorder: ");
    private Label preorderExprLabel = new Label();
    private Label postorderTitleLabel = new Label("Postorder: ");
    private Label postorderExprLabel = new Label();

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

        HBox controlPanel = new HBox(10, insertField, insertButton, deleteField, deleteButton);
        controlPanel.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
        controlPanel.setAlignment(Pos.CENTER);

        Separator separator = new Separator();
        VBox topSection = new VBox(10, controlPanel, separator);
        topSection.setAlignment(Pos.CENTER);
        mainPane.setTop(topSection);

        HBox inorderBox = new HBox(10);
        inorderBox.setAlignment(Pos.CENTER_LEFT);
        inorderTitleLabel.setStyle("-fx-font-size: 18px;");
        inorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        inorderBox.getChildren().addAll(inorderTitleLabel, inorderExprLabel);

        HBox preorderBox = new HBox(10);
        preorderBox.setAlignment(Pos.CENTER_LEFT);
        preorderTitleLabel.setStyle("-fx-font-size: 18px;");
        preorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        preorderBox.getChildren().addAll(preorderTitleLabel, preorderExprLabel);

        HBox postorderBox = new HBox(10);
        postorderBox.setAlignment(Pos.CENTER_LEFT);
        postorderTitleLabel.setStyle("-fx-font-size: 18px;");
        postorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        postorderBox.getChildren().addAll(postorderTitleLabel, postorderExprLabel);

        VBox bottomBox = new VBox(10, inorderBox, preorderBox, postorderBox);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30));
        mainPane.setBottom(bottomBox);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("BST Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (root != null) {
                visualizeTree();
            }
        });

        insertButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(insertField.getText().trim());
                insert(key);
                insertField.clear();
            } catch (NumberFormatException ex) {
                insertField.clear();
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(deleteField.getText().trim());
                delete(key);
                deleteField.clear();
            } catch (NumberFormatException ex) {
                deleteField.clear();
            }
        });
    }

    public boolean insertKey(int key) {
        TreeNode p = root;
        TreeNode q = null;
        while (p != null) {
            q = p;
            if (key == p.data)
                return false;
            else if (key < p.data)
                p = p.left;
            else
                p = p.right;
        }
        TreeNode temp = new TreeNode(key);
        if (root == null)
            root = temp;
        else if (key < q.data)
            q.left = temp;
        else
            q.right = temp;
        return true;
    }

    public void insert(int key) {
        insertKey(key);
        visualizeTree();
    }


    public boolean deleteKey(int key) {
        TreeNode p = root;
        TreeNode q = null;
        while (p != null && key != p.data) {
            q = p;
            if (key < p.data)
                p = p.left;
            else
                p = p.right;
        }
        if (p == null)
            return false; // key not found


        if (p.left != null && p.right != null) {
            TreeNode s = p.left;
            TreeNode ps = p; // parent of s
            while (s.right != null) {
                ps = s;
                s = s.right;
            }
            p.data = s.data;
            p = s;
            q = ps;
        }


        TreeNode c;
        if (p.left == null)
            c = p.right;
        else
            c = p.left;

        if (p == root)
            root = c;
        else {
            if (p == q.left)
                q.left = c;
            else
                q.right = c;
        }
        return true;
    }

    public boolean delete(int key) {
        boolean result = deleteKey(key);
        visualizeTree();
        return result;
    }

    private void visualizeTree() {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 80;
        visualizer.drawTree(root, centerX, centerY, 150);

        inorderExprLabel.setText(getInorder(root).trim());
        preorderExprLabel.setText(getPreorder(root).trim());
        postorderExprLabel.setText(getPostorder(root).trim());
    }


    private String getInorder(TreeNode node) {
        if (node == null)
            return "";
        return getInorder(node.left) + node.data + " " + getInorder(node.right);
    }


    private String getPreorder(TreeNode node) {
        if (node == null)
            return "";
        return node.data + " " + getPreorder(node.left) + getPreorder(node.right);
    }


    private String getPostorder(TreeNode node) {
        if (node == null)
            return "";
        return getPostorder(node.left) + getPostorder(node.right) + node.data + " ";
    }
}
