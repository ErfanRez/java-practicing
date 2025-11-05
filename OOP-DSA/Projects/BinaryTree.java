//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

class TreeNode {

    int data;
    TreeNode left;
    TreeNode right;

    public TreeNode() {}

    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTree {
    private TreeNode root;

     public BinaryTree(){
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

    // Pathological (For testing isPathological method, should return true)
//    public BinaryTree(){
//        root = new TreeNode(14);
//        root.right = new TreeNode(11);
//        root.right.left = new TreeNode(10);
//        root.right.left.right = new TreeNode(7);
//
//    }

    public boolean isEmpty() {
        return root == null;
    }

    public void incrementAllLeaves(){
         incrementAllLeaves(root);
    }

    private static void incrementAllLeaves(TreeNode p){
         if(p == null) return;

        if(p.left == null && p.right == null){
            p.data = p.data + 1;
        }

        incrementAllLeaves(p.left);
        incrementAllLeaves(p.right);
    }

    public int numberOfEvenNodes(){

         return numberOfEvenNodes(root);
    }

    private static int numberOfEvenNodes(TreeNode p){
        if (p == null)
            return 0;

        int count = 0;

        if(p.data % 2 == 0) count++;

        count += numberOfEvenNodes(p.left);
        count += numberOfEvenNodes(p.right);

        return count;
    }

    public int numberOfTargetNodes(int target){

         return numberOfTargetNodes(root, target);
    }

    private static int numberOfTargetNodes(TreeNode p, int target){
        if (p == null)
            return 0;

        int count = 0;

        if(p.data == target && (p.left != null && p.right != null)) count++;

        count += numberOfTargetNodes(p.left, target);
        count += numberOfTargetNodes(p.right, target);

        return count;
    }

    public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;

        t1.data += t2.data;

        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);

        return t1;
    }

    public boolean isPathological(){

         return isPathological(root);
    }

    private static boolean isPathological(TreeNode p){
         if(p == null) return true;

         if(p.left != null && p.right != null) return false;


         return p.left != null ? isPathological(p.left) : isPathological(p.right);
    }

    public void mirror(){
         root = mirror(root);
    }

    private TreeNode mirror(TreeNode p) {
        if (p == null)
            return null;

        TreeNode temp = new TreeNode();
        temp.data = p.data;
        temp.left = mirror(p.right);
        temp.right = mirror(p.left);
        return temp;
    }

    public boolean contains(int item){
         return contains(item, root);
    }

    private boolean contains(int item, TreeNode p){
         if(p == null) return false;

         if(p.data == item) return true;

         return contains(item, p.left) || contains(item, p.right);
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(TreeNode currPtr, String indent, boolean last) {
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(currPtr.data);
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }

    public static void main(String[] args) {
        BinaryTree t = new BinaryTree();

        System.out.println("Original binary tree: ");
        t.printTree();
        System.out.print("\n");
        System.out.print("Number of even nodes: " + t.numberOfEvenNodes()); // 5

        System.out.print("\n\n");

        System.out.println("After incrementing all leaves: ");
        t.incrementAllLeaves();
        t.printTree();
        System.out.print("\n");
        System.out.println("Number of even nodes: " + t.numberOfEvenNodes()); // 7

        System.out.print("\n");

        System.out.println("Is tree pathological? " + (t.isPathological() ? "Yes" : "No")); // No

        System.out.print("\n");

        System.out.println("Does tree contain 8? " + (t.contains(8) ? "Yes" : "No")); // Yes
        System.out.println("Does tree contain 24? " + (t.contains(24) ? "Yes" : "No")); // No

        System.out.print("\n");

        System.out.println("Tree after mirroring: ");
        t.mirror();
        t.printTree();

        System.out.print("\n\n");

        //Tests for merge and target nodes with degree of 2

        BinaryTree tree1 = new BinaryTree();
        BinaryTree tree2 = new BinaryTree();


        tree1.root = new TreeNode(1);
        tree1.root.left = new TreeNode(3);
        tree1.root.right = new TreeNode(2);
        tree1.root.left.left = new TreeNode(5);


        tree2.root = new TreeNode(2);
        tree2.root.left = new TreeNode(1);
        tree2.root.right = new TreeNode(3);
        tree2.root.left.right = new TreeNode(4);
        tree2.root.right.right = new TreeNode(7);

        System.out.println("Tree 1:");
        tree1.printTree();
        System.out.println("\nTree 2:");
        tree2.printTree();


        TreeNode mergedTreeRoot = BinaryTree.mergeTrees(tree1.root, tree2.root);
        System.out.println("\nMerged Tree:");
        tree1.root = mergedTreeRoot;
        tree1.printTree();


        int target = 4;
        int count = tree1.numberOfTargetNodes(target);
        System.out.println("\nNumber of nodes with value " + target + " having two children: " + count);
    }
}