package bt;

public class BinaryTree {
    private TreeNode root;

    //         9
    //       /   \
    //      5     8
    //     / \     \
    //    3   1     6

    //    public BinaryTree() {
    //        root = new TreeNode(9);
    //        root.left = new TreeNode(5);
    //        root.right = new TreeNode(8);
    //        root.left.left = new TreeNode(3);
    //        root.left.right = new TreeNode(1);
    //        root.right.right = new TreeNode(6);
    //    }

    public BinaryTree() {
        root = new TreeNode(14);
        root.left = new TreeNode(2);
        root.right = new TreeNode(11);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(10);
        root.right.right = new TreeNode(30);
        root.right.left.left = new TreeNode(7);
        root.right.right.left = new TreeNode(40);
    }

    public TreeNode getRoot() {
        return root;
    }


    public String getLevelOrder() {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        java.util.Queue < TreeNode > queue = new java.util.LinkedList < > ();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            sb.append(curr.data).append(" ");
            if (curr.left != null)
                queue.offer(curr.left);
            if (curr.right != null)
                queue.offer(curr.right);
        }
        return sb.toString().trim();
    }

    public String getInorder() {
        return inorder(root).trim();
    }

    private String inorder(TreeNode node) {
        if (node == null)
            return "";
        return inorder(node.left) + node.data + " " + inorder(node.right);
    }

    public String getPreorder() {
        return preorder(root).trim();
    }

    private String preorder(TreeNode node) {
        if (node == null)
            return "";
        return node.data + " " + preorder(node.left) + preorder(node.right);
    }

    public String getPostorder() {
        return postorder(root).trim();
    }

    private String postorder(TreeNode node) {
        if (node == null)
            return "";
        return postorder(node.left) + postorder(node.right) + node.data + " ";
    }

    public String getReverseInorder() {
        return reverseInorder(root).trim();
    }

    private String reverseInorder(TreeNode node) {
        if (node == null)
            return "";
        return reverseInorder(node.right) + node.data + " " + reverseInorder(node.left);
    }

    public String getReversePreorder() {
        return reversePreorder(root).trim();
    }

    private String reversePreorder(TreeNode node) {
        if (node == null)
            return "";
        return node.data + " " + reversePreorder(node.right) + reversePreorder(node.left);
    }

    public String getReversePostorder() {
        return reversePostorder(root).trim();
    }

    private String reversePostorder(TreeNode node) {
        if (node == null)
            return "";
        return reversePostorder(node.right) + reversePostorder(node.left) + node.data + " ";
    }

    public static void main(String[] args) {
        BinaryTreeVisualizer.main(args);
    }
}