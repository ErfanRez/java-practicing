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

    public BinaryTree(BinaryTree original) { // copy constructor (deep copy)
        root = copy(original.root);
    }

    private TreeNode copy(TreeNode p) {
        if (p == null)
            return null;
        TreeNode temp = new TreeNode();
        temp.data = p.data;
        temp.left = copy(p.left);
        temp.right = copy(p.right);
        return temp;
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
        FXConfig.main(args);
    }


    //Mr. Nikian's Codes
    ///////////////////////////////////////////////////////////////////
    public boolean isEmpty() {
        return root == null;
    }

    private static void visit(TreeNode p) {
        System.out.print(p.data + " ");
    }


    public void nonRecursiveInorder() { // iterative
        java.util.Stack < TreeNode > s = new java.util.Stack < > ();
        TreeNode p = root;
        while (true) {
            while (p != null) {
                s.push(p);
                p = p.left;
            }
            if (s.isEmpty())
                return;
            p = s.pop();
            System.out.print(p.data + " "); // visit(p);
            p = p.right;
        }
    }

    public void nonRecursivePreorder() { // iterative
        java.util.Stack < TreeNode > s = new java.util.Stack < > ();
        TreeNode p = root;
        while (true) {
            while (p != null) {
                System.out.print(p.data + " "); // visit(p);
                s.push(p);
                p = p.left;
            }
            if (s.isEmpty())
                return;
            p = s.pop();
            p = p.right;
        }
    }

    public void incrementAllNodes() {
        incrementAllNodes(root);
    }

    private void incrementAllNodes(TreeNode p) {
        if (p != null) {
            p.data++;
            incrementAllNodes(p.left);
            incrementAllNodes(p.right);
        }
    }

    public int numberOfNodes() { // size of a binary tree
        return numberOfNodes(root);
    }

    private int numberOfNodes(TreeNode p) {
        if (p == null)
            return 0;
        return 1 + numberOfNodes(p.left) + numberOfNodes(p.right);
    }

    public int height() { // depth of a binary tree
        return height(root);
    }

    private int height(TreeNode p) {
        if (p == null)
            return -1;
        int m = height(p.left);
        int n = height(p.right);
        if (m > n)
            return 1 + m;
        return 1 + n;
    }

    // private int height(TreeNode p){
    // if (p==null)
    // return -1;
    // return 1 + Math.max(height(p.left),height(p.right));
    // }

    public boolean equals(BinaryTree other) {
        return equals(root, other.root);
    }

    private static boolean equals(TreeNode first, TreeNode second) {
        if (first == null && second == null)
            return true;
        if (first != null && second != null && first.data == second.data &&
                equals(first.left, second.left) &&
                equals(first.right, second.right))
            return true;
        return false;
    }


    public boolean isFull() {
        return isFull(root);
    }

    private boolean isFull(TreeNode p) {
        if (p == null)
            return true;
        if (height(p.left) != height(p.right))
            return false;
        return isFull(p.left) && isFull(p.right);
    }

    public boolean nonRecursiveIsFull() {
        if (root == null)
            return true;
        int n = numberOfNodes();
        int h = height();
        if (n == (int) Math.pow(2, h + 1) - 1) // n=2^(h+1)-1
            return true;
        return false;
    }

    // A binary tree is strict if every node has either 0 or 2 children.
    public boolean isStrict() {
        return isStrict(root);
    }

    private boolean isStrict(TreeNode p) {
        if (p == null)
            return true;
        if (p.left == null && p.right != null)
            return false;
        if (p.left != null && p.right == null)
            return false;
        return isStrict(p.left) && isStrict(p.right);
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(TreeNode p) {
        if (p == null)
            return true;
        return (Math.abs(height(p.left) - height(p.right)) <= 1) &&
                isBalanced(p.left) && isBalanced(p.right);
    }

    public boolean isComplete() {
        return isComplete(root);
    }

    private boolean isComplete(TreeNode p) {
        int leftHeight, rightHeight;
        boolean leftIsFull, rightIsFull, leftIsComplete, rightIsComplete;
        if (p == null)
            return true;
        leftHeight = height(p.left);
        rightHeight = height(p.right);
        leftIsFull = isFull(p.left);
        rightIsFull = isFull(p.right);
        leftIsComplete = isComplete(p.left);
        rightIsComplete = isComplete(p.right);
        if (leftIsFull && rightIsComplete && (leftHeight == rightHeight))
            return true;
        if (leftIsComplete && rightIsFull && (leftHeight == (rightHeight + 1)))
            return true;
        return false;
    }

    public void printTree() {
        printTree(root);
    }

    private void printTree(TreeNode p) {
        if (p != null) {
            if (p.left != null)
                System.out.print("(");
            printTree(p.left);
            if (p.left != null)
                System.out.print(")");
            System.out.print(" " + p.data + " ");
            if (p.right != null)
                System.out.print("(");
            printTree(p.right);
            if (p.right != null)
                System.out.print(")");
        }
    }

    public void printSideways() {
        printSideways(root, " ");
    }

    private void printSideways(TreeNode p, String indent) {
        if (p != null) {
            printSideways(p.right, indent + " ");
            System.out.println(indent + p.data);
            printSideways(p.left, indent + " ");
        }
    }

    // public void test(){
    // test(root);
    // }
    //
    // private void test(TreeNode p){
    // if (p!=null){
    // System.out.print(p.data + " ");
    // test(p.right);
    // System.out.print(p.data + " ");
    // test(p.left);
    // }
    // }
}