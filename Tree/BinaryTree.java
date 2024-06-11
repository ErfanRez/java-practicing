import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// Define the Node class
class Node<T> {
    T data;
    Node<T> left;
    Node<T> right;

    public Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

// Define the Tree class
class Tree<T> {
    Node<T> root;

    public Tree() {
        this.root = null;
    }

    // Method to search for a value using Breadth-First Search (BFS)
    public boolean bfsSearch(T value) {
        if (root == null) {
            return false;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            if (current.data.equals(value)) {
                return true;
            }

            if (current.left != null) {
                queue.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
            }
        }

        return false;
    }

    // Method to search for a value using Depth-First Search (DFS)
    public boolean dfsSearch(T value) {
        if (root == null) {
            return false;
        }

        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            if (current.data.equals(value)) {
                return true;
            }

            // Push right child first so that left child is processed first
            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return false;
    }
}

// Main class to test the BFS and DFS search methods
public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.root = new Node<>(1);
        tree.root.left = new Node<>(2);
        tree.root.right = new Node<>(3);
        tree.root.left.left = new Node<>(4);
        tree.root.left.right = new Node<>(5);
        tree.root.right.left = new Node<>(6);
        tree.root.right.right = new Node<>(7);

        int valueToSearch = 2;

        boolean isFoundBFS = tree.bfsSearch(valueToSearch);
        System.out.println("BFS: Value " + valueToSearch + (isFoundBFS ? " found" : " not found") + " in the tree.");

        boolean isFoundDFS = tree.dfsSearch(valueToSearch);
        System.out.println("DFS: Value " + valueToSearch + (isFoundDFS ? " found" : " not found") + " in the tree.");
    }
}
