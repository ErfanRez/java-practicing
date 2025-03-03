//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

import java.util.*;


class Node {
    int key, height;
    Node left, right;

    public Node(){}

    public Node(int d) {
        key = d;
        height = 1;
    }
}

// Console App
public class AVLTree {

    private Node root;

    public AVLTree(){}

    private int height(Node n) {
        if (n == null)
            return 0;
        return n.height;
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

    // Get balance factor of a node
    private int getBalanceFactor(Node n) {
        if (n == null)
            return 0;
        return height(n.right) - height(n.left);
    }

    public void insert(int item){
        root = this.insert(root, item);
    }

    // Insert a node
    private Node insert(Node node, int item) {
        if (node == null)
            return (new Node(item));
        if (item < node.key)
            node.left = insert(node.left, item);
        else if (item > node.key)
            node.right = insert(node.right, item);
        else
            return node;

        // Update the height of the ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this node
        int balanceFactor = getBalanceFactor(node);


        if (balanceFactor < -1) { // Left-heavy subtree
            if (item < node.left.key) {
                return rightRotate(node); // Left-Left Case
            } else if (item > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node); // Left-Right Case
            }
        }
        if (balanceFactor > 1) { // Right-heavy subtree
            if (item > node.right.key) {
                return leftRotate(node); // Right-Right Case
            } else if (item < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node); // Right-Left Case
            }
        }

        return node;
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

    public void delete(int item){
        root = this.delete(root, item);
    }

    // Delete a node
    private Node delete(Node node, int item) {
        if (node == null)
            return node;
        if (item < node.key)
            node.left = delete(node.left, item);
        else if (item > node.key)
            node.right = delete(node.right, item);
        else {
            if ((node.left == null) || (node.right == null)) {
                Node temp = node.left != null ? node.left : node.right;

                if (temp == null) {
                    temp = node;
                    node = null;
                } else
                    node = temp;
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

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);


        if (balanceFactor < -1) { // Left-heavy subtree
            if (getBalanceFactor(node.left) <= 0) {
                return rightRotate(node); // Left-Left Case
            } else {
                node.left = leftRotate(node.left);
                return rightRotate(node); // Left-Right Case
            }
        }
        if (balanceFactor > 1) { // Right-heavy subtree
            if (getBalanceFactor(node.right) >= 0) {
                return leftRotate(node); // Right-Right Case
            } else {
                node.right = rightRotate(node.right);
                return leftRotate(node); // Right-Left Case
            }
        }

        return node;
    }

    private void inOrder(){
        this.inOrder(root);
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    private void reverseInOrder(){
        this.reverseInOrder(root);
    }

    private void reverseInOrder(Node node){
        if (node != null) {
            reverseInOrder(node.right);
            System.out.print(node.key + " ");
            reverseInOrder(node.left);
        }
    }

    private void preOrder(){
        this.preOrder(root);
    }

    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    private void postOrder(){
        this.postOrder(root);
    }

    public void postOrder(Node node) {
        if (node != null) {
            preOrder(node.left);
            preOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    // Print the tree
    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(Node currPtr, String indent, boolean last) {
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(currPtr.key);
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        System.out.println("Inserting 40:");
        tree.insert(40);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Inserting 60:");
        tree.insert(60);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Inserting 50:");
         tree.insert(50);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Inserting 33:");
        tree.insert(33);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Inserting 55:");
        tree.insert(55);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Inserting 8:");
        tree.insert(8);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Inserting 11:");
        tree.insert(11);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Deleting 40:");
        tree.delete(40);
        tree.printTree();
        System.out.print("\n\n");

        System.out.println("Deleting 33: ");
        tree.delete(33);
        tree.printTree();

        System.out.print("\n\n");

        System.out.println("Inorder traversal: ");
        tree.inOrder();

        System.out.print("\n\n");

        System.out.println("Reverse inorder traversal: ");
        tree.reverseInOrder();

        // System.out.println("Preorder traversal: ");
        // tree.preOrder(tree.root);

        // System.out.println();

        // System.out.println("Postorder traversal: ");
        // tree.postOrder(tree.root);
    }
}
