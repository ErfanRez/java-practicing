//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

import java.util.Stack;

class Node {
    String data;
    Node left, right;

    Node(String value) {
        this.data = value;
        left = right = null;
    }
}

public class ExpressionTree {
    private Node root;

    ExpressionTree(String expression){
        constructTree(expression);
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
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                        postfix += operators.pop() + " ";
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
        Stack<Node> stack = new Stack<>();

        for (String token : postfix.split(" ")) {
            if (token.length() == 1 && isOperator(token.charAt(0))) { // Operator
                Node operatorNode = new Node(token);
                operatorNode.right = stack.pop(); // Right operand
                operatorNode.left = stack.pop(); // Left operand
                stack.push(operatorNode);
            } else { // Operand
                stack.push(new Node(token));
            }
        }

        root = stack.pop();
    }

    // Inorder traversal to verify the tree
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.data + " ");
            inorderTraversal(root.right);
        }
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(Node currPtr, String indent, boolean last) {
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

