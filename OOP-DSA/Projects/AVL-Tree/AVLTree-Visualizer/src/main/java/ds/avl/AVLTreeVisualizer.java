package ds.avl;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class AVLTreeVisualizer extends Application {

    private Node root;
    private TreeVisualizer visualizer = new TreeVisualizer();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        Pane treePane = new Pane();
        visualizer.prefWidthProperty().bind(treePane.widthProperty());
        visualizer.prefHeightProperty().bind(treePane.heightProperty());

        // Input controls for adding and deleting nodes
        TextField insertField = new TextField();
        insertField.setPromptText("Insert key");
        Button insertButton = new Button("Insert");

        TextField deleteField = new TextField();
        deleteField.setPromptText("Delete key");
        Button deleteButton = new Button("Delete");

        HBox controlPanel = new HBox(10, insertField, insertButton, deleteField, deleteButton);
        controlPanel.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        Line separatorLine = new Line(0, 0, 800, 0);
        separatorLine.setStroke(Color.GRAY);
        separatorLine.setStrokeWidth(1);
        separatorLine.setTranslateY(5);

        Pane separatorPane = new Pane(separatorLine);

        BorderPane topPane = new BorderPane();
        topPane.setTop(controlPanel);
        topPane.setCenter(separatorPane);

        mainPane.setTop(topPane);
        mainPane.setCenter(treePane);

        treePane.getChildren().add(visualizer);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("AVL Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button actions
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

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
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
            } else {
                Node temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = delete(node.right, temp.key);
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
        visualizer.drawTree(root, 400, 50, 150);
    }
}

