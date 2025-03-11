package avl;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AVLTreeVisualizer extends Application {

    private Node root;
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
        bottomBox.setPadding(new Insets(0, 0, 30, 30)); // top, right, bottom, left
        mainPane.setBottom(bottomBox);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("AVL Tree Visualizer");
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

        clearButton.setOnAction(e -> {
            root = null;
            visualizeTree();
        });
    }

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T = x.right;

        x.right = y;
        y.left = T;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T = x.left;

        x.left = y;
        y.right = T;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private int getBalanceFactor(Node n) {
        return n == null ? 0 : height(n.right) - height(n.left);
    }

    private Node insert(Node node, int key) {
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor < -1 && key < node.left.key)
            return rightRotate(node);

        if (balanceFactor > 1 && key > node.right.key)
            return leftRotate(node);

        if (balanceFactor < -1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balanceFactor > 1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(int key) {
        root = insert(root, key);
        visualizeTree();
    }

//  Deleting a node (Minimum of right subtree)
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

//  Deleting a node (Maximum of left subtree)
    private Node maxValueNode(Node node) {
        Node current = node;
        while (current.right != null)
            current = current.right;
        return current;
    }

    private Node delete(Node node, int key) {
        if (node == null)
            return node;

        if (key < node.key)
            node.left = delete(node.left, key);
        else if (key > node.key)
            node.right = delete(node.right, key);
        else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            }
//            deleting using minimum of right subtree
//            else {
//                Node temp = minValueNode(node.right);
//                node.key = temp.key;
//                node.right = delete(node.right, temp.key);
//            }

//          deleting using maximum of left subtree
            else {
                Node temp = maxValueNode(node.left);
                node.key = temp.key;
                node.left = delete(node.left, temp.key);
            }
        }

        if (node == null)
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor < -1 && getBalanceFactor(node.left) <= 0)
            return rightRotate(node);

        if (balanceFactor > 1 && getBalanceFactor(node.right) >= 0)
            return leftRotate(node);

        if (balanceFactor < -1 && getBalanceFactor(node.left) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balanceFactor > 1 && getBalanceFactor(node.right) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }


    public void delete(int key) {
        root = delete(root, key);
        visualizeTree();
    }

    private void visualizeTree() {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 80;
        visualizer.drawTree(root, centerX, centerY, 150);


        inorderExprLabel.setText(getInorder(root).trim());
        reverseInorderExprLabel.setText(getReverseInorder(root).trim());
    }


    private String getInorder(Node node) {
        if (node == null)
            return "";
        return getInorder(node.left) + node.key + " " + getInorder(node.right);
    }

    private String getReverseInorder(Node node) {
        if (node == null)
            return "";
        return getReverseInorder(node.right) + node.key + " " + getReverseInorder(node.left);
    }
}
