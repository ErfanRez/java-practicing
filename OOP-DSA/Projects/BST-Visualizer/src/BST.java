
class TreeNode{ // BSTNode
    // package visible data fields
    int data; // key
    TreeNode left; // left child or left subtree
    TreeNode right; // right child or right subtree
    public TreeNode(){
    }
    public TreeNode(int data){
        this.data=data;
        this.left=null;
        this.right=null;
    }
}

public class BST{ // IntBinarySearchTree
    private TreeNode root; // reference to the overall root

    public BST(){
        root=null;
    }
    /////////////////////////////////////////////////////////////////
    public BST(BST original){ // copy constructor (deep copy)
        root=copy(original.root);
    }
    private TreeNode copy(TreeNode p){
        if (p==null)
            return null;
        TreeNode temp=new TreeNode();
        temp.data=p.data;
        temp.left=copy(p.left);
        temp.right=copy(p.right);
        return temp;
    }
    /////////////////////////////////////////////////////////////////
    public boolean equals(BST other){
        return equals(root,other.root);
    }
    private static boolean equals(TreeNode first,TreeNode second){
        if (first==null && second==null)
            return true;
        if (first!=null && second!=null && first.data==second.data
                && equals(first.left,second.left)
                && equals(first.right,second.right))
            return true;
        return false;
    }
    public TreeNode search(int key){
        return search(root,key);
    }
    private TreeNode search(TreeNode p,int key){
        if (p==null)
            return null;
        else if (key==p.data)
            return p;
        else if (key<p.data)
            return search(p.left,key);
        else
            return search(p.right,key);
    }
    /////////////////////////////////////////////////////////////////
    public TreeNode nonRecursiveSearch(int key){ // iterative search
        TreeNode p=root;
        while (p!=null)
        {
            if (key==p.data)
                return p;
            else if (key<p.data)
                p=p.left;
            else
                p=p.right;
        }
        return null;
    }

    /////////////////////////////////////////////////////////////////
    public boolean insert(int key){
        // search for key, q is parent of p
        TreeNode p=root;
        TreeNode q=null; // q is parent of p
        while (p!=null)
        {
            q=p;
            if (key==p.data)
                return false; // key is already in BST
            else if (key<p.data)
                p=p.left;
            else
                p=p.right;
        }
        // perform insertion
        TreeNode temp=new TreeNode(key);
        if (root==null)
            root=temp;
        else if (key<q.data)
            q.left=temp;
        else
            q.right=temp;
        return true;
    }
    /////////////////////////////////////////////////////////////////
    public static BST readBST(){
        BST t=new BST();
        java.util.Scanner input=new java.util.Scanner(System.in);
        System.out.print("Enter an integer number, negative or zero to quit: ");
        int key=input.nextInt();
        while (key>0)
        {
            t.insert(key);
            System.out.print("Enter an integer number, negative or zero to quit: ");
            key=input.nextInt();
        }
        return t;
    }
    public static BST createBST(int... args){
        BST t=new BST();
        for (int i = 0; i < args.length; i++)
            t.insert(args[i]);
        return t;
    }
    /////////////////////////////////////////////////////////////////

    public boolean delete(int key){
        // set p to point to node with key, q is parent of p
        TreeNode p=root;
        TreeNode q=null;
        while (p!=null && key!=p.data)
        {
            // move to a child of p
            q=p;
            if (key<p.data)
                p=p.left;
            else
                p=p.right;
        }
        if (p==null)  // no element with key
            return false;
        // restructure tree
        // handle case when p has two children
        if (p.left!=null && p.right!=null)
        {
            // two children
            // convert to zero or one child case
            // find element with largest key in left subtree of p
            TreeNode s=p.left;
            TreeNode ps=p;// parent of s
            while (s.right!=null)
            {
                // move to larger element
                ps=s;
                s=s.right;
            }
            // move largest element from s to p
            p.data=s.data;
            p=s;
            q=ps;
        }
        // p has at most one child, save this child in c
        TreeNode c;
        if (p.left==null)
            c=p.right;
        else
            c=p.left;
        // remove node p
        if (p==root)
            root=c;
        else
        {
            // is p left or right child of q?
            if (p==q.left)
                q.left=c;
            else
                q.right=c;
        }
        return true;
    }
    public boolean isBST(){ // efficient algorithm (efficient version)
        return isBST(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }
    private boolean isBST(TreeNode p,int min,int max){
        if (p==null)
            return true;
        if (p.data<min || p.data>max)
            return false;
        return isBST(p.left,min,p.data-1) &&
                isBST(p.right,p.data+1,max);
    }
    /////////////////////////////////////////////////////////////////
    //  public boolean isBST(){
    //    return isBST(root);
    //  }
    //
    //  private boolean isBST(TreeNode p){
    //    if (p==null)
    //      return true;
    //    if (p.left==null && p.right==null)
    //      return true;
    //    if (p.left!=null && p.left.data<p.data
    //                     && p.right!=null && p.right.data>p.data
    //                     && isBST(p.left) && isBST(p.right))
    //      return true;
    //    if (p.left!=null && p.left.data<p.data
    //                     && p.right==null && isBST(p.left))
    //      return true;
    //    if (p.left==null && p.right!=null
    //                     && p.right.data>p.data && isBST(p.right))
    //      return true;
    //    return false;
    //  }
}