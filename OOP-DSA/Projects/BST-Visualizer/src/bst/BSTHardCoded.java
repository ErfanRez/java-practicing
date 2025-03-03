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
        bottomBox.setPadding(new Insets(0, 0, 30, 30)); // top, right, bottom, left
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

//        Hard coded insertion and deletion from here

        insert(40);
        insert(60);
        insert(50);
        insert(33);
        insert(55);
        insert(11);


//         delete(10);
    }

    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
        } else {
            TreeNode p = root;
            TreeNode q = null;
            while (p != null) {
                q = p;
                if (key == p.data) {
                    // key already exists, do nothing
                    return;
                } else if (key < p.data) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }
            TreeNode temp = new TreeNode(key);
            if (key < q.data)
                q.left = temp;
            else
                q.right = temp;
        }
        visualizeTree();
    }

    public void delete(int key) {
        root = delete(root, key);
        visualizeTree();
    }

    private TreeNode delete(TreeNode node, int key) {
        if (node == null)
            return null;
        TreeNode p = node;
        TreeNode q = null;
        while (p != null && p.data != key) {
            q = p;
            if (key < p.data)
                p = p.left;
            else
                p = p.right;
        }
        if (p == null)
            return node;

        if (p.left != null && p.right != null) {
            TreeNode s = p.left;
            TreeNode ps = p;
            while (s.right != null) {
                ps = s;
                s = s.right;
            }
            p.data = s.data;

            p = s;
            q = ps;
        }

        TreeNode c = (p.left == null) ? p.right : p.left;
        if (p == node) {
            node = c;
        } else {
            if (p == q.left)
                q.left = c;
            else
                q.right = c;
        }
        return node;
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
