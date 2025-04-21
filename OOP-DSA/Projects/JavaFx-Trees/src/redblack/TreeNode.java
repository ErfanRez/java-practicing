package redblack;

public class TreeNode {
    int data;
    TreeNode parent;
    TreeNode left;
    TreeNode right;
    int color;

    public TreeNode(int data) {
        this.data = data;
        this.color = 1; // red by default
        this.parent = null;
        this.left = null;
        this.right = null;
    }


    public TreeNode() { }
}