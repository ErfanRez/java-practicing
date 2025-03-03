package exptree;

public class Node {
    String data;
    Node left, right;

    public Node(String value) {
        this.data = value;
        left = right = null;
    }
}
