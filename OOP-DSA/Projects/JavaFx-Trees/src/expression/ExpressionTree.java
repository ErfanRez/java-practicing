package expression;

//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

import java.util.*;


//Console App
public class ExpressionTree {
    private TreeNode root;

    ExpressionTree(String expression){
        constructTree(expression);
    }

    public TreeNode getRoot(){
        return root;
    }

    private static int precedence(char op) {
        switch (op) {
            case '+': case '-':
                return 1;
            case '*': case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private static String infixToPostfix(String expression) {
        expression = expression.replace(" ","");
        String postfix = "";
        Stack<Character> operators = new Stack<>();
        String number = "";

        for (char c : expression.toCharArray()) {

            if (!isOperator(c) && c != '(' && c != ')') {
                number += c;
            } else {

                if (!number.isEmpty()) {
                    postfix += number + " ";
                    number = "";
                }


                if (c == '(') {
                    operators.push(c);
                }

                else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix += operators.pop() + " ";
                    }
                    operators.pop(); // Remove '('
                }

                else if (isOperator(c)) {
                    while (!operators.isEmpty() && precedence(operators.peek()) > precedence(c)) {
                        postfix += operators.pop() + " ";
                    }

                    if (!(c == '^' && !operators.isEmpty() && operators.peek() == '^')) {
                        while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                            postfix += operators.pop() + " ";
                        }
                    }

                    operators.push(c);
                }

            }
        }

        if (!number.isEmpty()) {
            postfix += number + " ";
        }


        while (!operators.isEmpty()) {
            postfix += operators.pop() + " ";
        }

        return postfix.trim();
    }

    public void constructTree(String expression) {
        String postfix = infixToPostfix(expression);
        Stack<TreeNode> stack = new Stack<>();

        for (String token : postfix.split(" ")) {
            if (token.length() == 1 && isOperator(token.charAt(0))) { // Operator
                TreeNode operatorNode = new TreeNode(token);
                operatorNode.right = stack.pop(); // Right operand
                operatorNode.left = stack.pop(); // Left operand
                stack.push(operatorNode);
            } else { // Operand
                stack.push(new TreeNode(token));
            }
        }

        root = stack.pop();
    }

    // Inorder traversal to verify the tree
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.data + " ");
            inorderTraversal(root.right);
        }
    }

    public String getPreorder(){
        return this.preorder(root);
    }

    private String preorder(TreeNode node) {
        if (node == null) return "";
        return node.data + " " + preorder(node.left) + preorder(node.right);
    }

    public String getPostorder(){
        return this.postorder(root);
    }

    private String postorder(TreeNode node) {
        if (node == null) return "";
        return postorder(node.left) + postorder(node.right) + node.data + " ";
    }

    public int getMaxDepth(){
        return maxDepth(root);
    }

    private int maxDepth(TreeNode node) {
        if (node == null)
            return 0;
        else {
            int leftDepth = maxDepth(node.left);
            int rightDepth = maxDepth(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(TreeNode currPtr, String indent, boolean last) {
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R....");
                indent += "   ";
            } else {
                System.out.print("L....");
                indent += "|  ";
            }
            System.out.println(currPtr.data);
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }

    public static void main(String[] args) {
        String expression1 = "A + B - C * ( D / E - F ) + G"; //Infix expression
        ExpressionTree et1 = new ExpressionTree(expression1);

        et1.printTree();
        System.out.println();
        System.out.print("Inorder Traversal: ");
        et1.inorderTraversal(); // A + B - C * D / E - F + G

        System.out.print("\n\n");

        String expression2 = "3 + 4 * 2 / ( 1 - 5 ) ^ 2";
        ExpressionTree et2 = new ExpressionTree(expression2);

        et2. printTree();
        System.out.println();
        System.out.print("Inorder Traversal: ");
        et2.inorderTraversal(); // 3 + 4 * 2 / 1 - 5 ^ 2
    }
}
