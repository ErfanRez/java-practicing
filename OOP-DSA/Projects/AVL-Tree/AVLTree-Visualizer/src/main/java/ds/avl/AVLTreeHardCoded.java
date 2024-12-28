package ds.avl;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AVLTreeHardCoded extends Application {

    private Node root;
    private TreeVisualizer visualizer = new TreeVisualizer();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.getChildren().add(visualizer);
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("AVL Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();


//        insert(25);
//        insert(20);
//        insert(5);
//        insert(34);
//        insert(50);
//        insert(30);
//        insert(10);

        insert(20);
        insert(10);
        insert(30);
        insert(5);
        insert(15);
        insert(40);
        insert(12);
        insert(17);


//        delete(20);
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