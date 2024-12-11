package ds.avl.avltree;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class Node {
    int key, height;
    Node left, right;

    public Node(int d) {
        key = d;
        height = 1;
    }
}

public class AVLTreeVisualizer extends Application {

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


        insert(25);
        insert(20);
        insert(5);
        insert(34);
        insert(50);
        insert(30);
        insert(10);

//        insert(20);
//        insert(10);
//        insert(30);
//        insert(5);
//        insert(15);
//        insert(40);
//        insert(12);
//        insert(17);


        // delete(40);
        // delete(33);
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

class TreeVisualizer extends Pane {

    public void clear() {
        this.getChildren().clear();
    }

    public void drawTree(Node root, double x, double y, double hGap) {
        if (root != null) {
            drawNode(x, y, root.key);
            if (root.left != null) {
                drawEdge(x, y, x - hGap, y + 50);
                drawTree(root.left, x - hGap, y + 50, hGap / 2);
            }
            if (root.right != null) {
                drawEdge(x, y, x + hGap, y + 50);
                drawTree(root.right, x + hGap, y + 50, hGap / 2);
            }
        }
    }

    private void drawNode(double x, double y, int value) {
        Circle circle = new Circle(x, y, 20);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 6, y + 5, String.valueOf(value));

        this.getChildren().addAll(circle, text);
    }

    private void drawEdge(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double offsetX = dx * 20 / distance;
        double offsetY = dy * 20 / distance;

        Line line = new Line(x1 + offsetX, y1 + offsetY, x2 - offsetX, y2 - offsetY);
        this.getChildren().add(line);
    }
}
