package expression;

import java.util.*;

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
            case '~': case '@':
                return 4;
            default:
                return 0;
        }
    }

    private static boolean isBinaryOpChar(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '~' || c == '@';
    }

    private static boolean isRightAssociative(char op) {
        return op == '^' || op == '~' || op == '@';
    }

    private static String infixToPostfix(String expression) {
        expression = expression.replace(" ", "");
        String postfix = "";
        Stack<Character> operators = new Stack<>();
        String number = "";

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (!isBinaryOpChar(c) && c != '(' && c != ')') {
                number += c;
            } else {
                if (!number.isEmpty()) {
                    postfix += number + " ";
                    number = "";
                }

                if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix += operators.pop() + " ";
                    }
                    if (!operators.isEmpty()) operators.pop();
                } else if (isBinaryOpChar(c)) {
                    boolean unary = (c == '+' || c == '-') &&
                            (i == 0 ||
                                    expression.charAt(i - 1) == '(' ||
                                    isBinaryOpChar(expression.charAt(i - 1)));

                    if (unary) {
                        char unarySym = (c == '-') ? '~' : '@';
                        while (!operators.isEmpty() &&
                                (precedence(operators.peek()) > precedence(unarySym))) {
                            postfix += operators.pop() + " ";
                        }
                        operators.push(unarySym);
                    } else {
                        while (!operators.isEmpty()
                                && ((precedence(operators.peek()) > precedence(c))
                                || (precedence(operators.peek()) == precedence(c) && !isRightAssociative(c)))
                                && operators.peek() != '(') {
                            postfix += operators.pop() + " ";
                        }
                        operators.push(c);
                    }
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

        for (String token : postfix.split("\\s+")) {
            if (token.isEmpty()) continue;

            if (token.length() == 1 && token.charAt(0) == '@') {
                continue;
            }

            if (token.length() == 1 && token.charAt(0) == '~') {
                TreeNode opNode = new TreeNode(token);
                if (!stack.isEmpty()) {
                    opNode.right = stack.pop();
                }
                opNode.left = null;
                stack.push(opNode);
            } else if (token.length() == 1 && isOperator(token.charAt(0))) {
                TreeNode operatorNode = new TreeNode(token);
                TreeNode right = stack.isEmpty() ? null : stack.pop();
                TreeNode left = stack.isEmpty() ? null : stack.pop();
                operatorNode.right = right;
                operatorNode.left = left;
                stack.push(operatorNode);
            } else {
                stack.push(new TreeNode(token));
            }
        }

        root = stack.isEmpty() ? null : stack.pop();
    }

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
        return this.preorder(root).trim();
    }

    private String preorder(TreeNode node) {
        if (node == null) return "";
        return node.data + " " + preorder(node.left) + preorder(node.right);
    }

    public String getPostorder(){
        return this.postorder(root).trim();
    }

    private String postorder(TreeNode node) {
        if (node == null) return "";
        return (postorder(node.left) + postorder(node.right) + node.data + " ");
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
        String[] tests = {
                "A + B - C * ( D / E - F ) + G",
                "3 + 4 * 2 / ( 1 - 5 ) ^ 2",
        };

        for (String expr : tests) {
            System.out.println("Expression: " + expr);
            ExpressionTree et = new ExpressionTree(expr);
            et.printTree();
            System.out.println();
            System.out.print("Inorder: ");
            et.inorderTraversal();
            System.out.println();
            System.out.println("-----------\n");
        }
    }
}
