package redblack;

public class RedBlackTree {
    private TreeNode root;
    private TreeNode TNULL;

    public RedBlackTree() {
        TNULL = new TreeNode();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public void leftRotate(TreeNode x) {
        TreeNode y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(TreeNode x) {
        TreeNode y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }


    private void rbTransplant(TreeNode u, TreeNode v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    // Delete using minimum of right
    public TreeNode minimum(TreeNode node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // Delete using maximum of left
    public TreeNode maximum(TreeNode node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    private void deleteTreeNode(TreeNode node, int key) {
        TreeNode z = TNULL;
        TreeNode x, y;
        while (node != TNULL) {
            if (node.data == key) {
                z = node;
            }

            if (node.data <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = maximum(z.left); // or minimum(z.right)
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0) {
            fixDelete(x);
        }
    }

    // Balance the tree after deletion of a node
    private void fixDelete(TreeNode x) {
        TreeNode s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0 && s.right.color == 0) {
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    public void insert(int key) {
        TreeNode node = new TreeNode();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1;

        TreeNode y = null;
        TreeNode x = this.root;

        while (x != TNULL) {
            y = x;
            if (key == x.data) {
                return;  // key already in the tree → do nothing
            } else if (key < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (key < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = 0;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    // Balance the node after insertion
    private void fixInsert(TreeNode k) {
        TreeNode u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public TreeNode getTNULL() {
        return TNULL;
    }

    // Preorder
    private void preOrder(TreeNode node) {
        if (node != TNULL) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // Inorder
    private void inOrder(TreeNode node) {
        if (node != TNULL) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    // Post order
    private void postOrder(TreeNode node) {
        if (node != TNULL) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }

    public void preorder() {
        preOrder(this.root);
    }

    public void inorder() {
        inOrder(this.root);
    }

    public void postorder() {
        postOrder(this.root);
    }

    private String inOrderString(TreeNode node) {
        if (node == TNULL) return "";
        return inOrderString(node.left) + node.data + " " + inOrderString(node.right);
    }

    public String getInorder() {
        return inOrderString(root);
    }

    private String reverseInOrderString(TreeNode node) {
        if (node == TNULL) return "";
        return reverseInOrderString(node.right) + node.data + " " + reverseInOrderString(node.left);
    }

    public String getReverseInorder() {
        return reverseInOrderString(root);
    }

    public void delete(int data) {
        deleteTreeNode(this.root, data);
    }

    private void printTree(TreeNode root, String indent, boolean last) {
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printTree(root.left, indent, false);
            printTree(root.right, indent, true);
        }
    }

    public void printTree() {
        printTree(this.root, "", true);
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(40);
        tree.insert(60);
        tree.insert(50);
        tree.insert(33);
        tree.insert(55);
        tree.insert(11);
        tree.printTree();

        System.out.println("\nAfter deleting node 50:");
        tree.delete(50);
        tree.printTree();
    }
}