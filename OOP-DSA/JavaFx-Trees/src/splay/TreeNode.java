package splay;

/** Class Node **/
public class TreeNode
{
    TreeNode left, right, parent;
    int element;

    /** Constructor **/
    public TreeNode()
    {
        this(0, null, null, null);
    }
    /** Constructor **/
    public TreeNode(int ele)
    {
        this(ele, null, null, null);
    }
    /** Constructor **/
    public TreeNode(int ele, TreeNode left, TreeNode right, TreeNode parent)
    {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = ele;
    }
}
