//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

import java.util.*;

public class Eval {

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



    private static int evaluatePostfix(String postfix) {
        Stack<Integer> values = new Stack<>();


        for (String token : postfix.split(" ")) {

            if (token.length() == 1 && isOperator(token.charAt(0))) {
                int b = values.pop();
                int a = values.pop();
                char operator = token.charAt(0);
                switch (operator) {
                    case '+':
                        values.push(a + b);
                        break;
                    case '-':
                        values.push(a - b);
                        break;
                    case '*':
                        values.push(a * b);
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero.");
                        }
                        values.push(a / b);
                        break;
                    case '^':
                        values.push((int) Math.pow(a, b));
                        break;
                }
            }

            else {
                values.push(Integer.parseInt(token));
            }
        }

        return values.pop();
    }


    public static int eval(String expression) {
        String postfix = infixToPostfix(expression);
        System.out.println("Postfix: " + postfix);
        return evaluatePostfix(postfix);
    }


    public static void main(String[] args) {

        String expression1 = "( 3 + 4 ) * 2 ^ 2 + (5 + 6) ^ 2"; // 149
        System.out.println("Infix Expression: " + expression1);
        System.out.println("Result: " + eval(expression1) + "\n");

        String expression2 = "10 + 2 * 6"; // 22
        System.out.println("Infix Expression: " + expression2);
        System.out.println("Result: " + eval(expression2) + "\n");

        String expression3 = "100 * 2 + 12"; // 212
        System.out.println("Infix Expression: " + expression3);
        System.out.println("Result: " + eval(expression3) + "\n");

        String expression4 = "100 * ( 2 + 12 )"; // 1400
        System.out.println("Infix Expression: " + expression4);
        System.out.println("Result: " + eval(expression4) + "\n");

        String expression5 = "100 * ( 2 + 12 ) / 14"; // 100
        System.out.println("Infix Expression: " + expression5);
        System.out.println("Result: " + eval(expression5) + "\n");

        String expression6 = "5 ^ 2"; // 25
        System.out.println("Infix Expression: " + expression6);
        System.out.println("Result: " + eval(expression6) + "\n");

        String expression7 = "3 + 4 * 2 / ( 1 - 5 ) ^ 2"; // 3
        System.out.println("Infix Expression: " + expression7);
        System.out.println("Result: " + eval(expression7) + "\n");

        String expression8 = "7 + (6 * 5 ^ (2 - 2))"; // 13
        System.out.println("Infix Expression: " + expression8);
        System.out.println("Result: " + eval(expression8) + "\n");

        String expression9 = "(1 + 2) * (3 + 4)"; // 21
        System.out.println("Infix Expression: " + expression9);
        System.out.println("Result: " + eval(expression9) + "\n");

        String expression10 = "2 * (3 + 5) / (4 - 2)"; // 8
        System.out.println("Infix Expression: " + expression10);
        System.out.println("Result: " + eval(expression10) + "\n");
    }

}
