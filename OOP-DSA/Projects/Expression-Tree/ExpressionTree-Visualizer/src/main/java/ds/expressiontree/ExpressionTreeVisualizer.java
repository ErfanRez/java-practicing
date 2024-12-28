package ds.expressiontree;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;

public class ExpressionTreeVisualizer extends Application {

    private Node root;
    private TreeVisualizer visualizer = new TreeVisualizer();
    private TextField expressionField = new TextField();
    private Button drawButton = new Button("Draw Tree");

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootLayout = new BorderPane();

        Pane pane = new Pane();
        pane.getChildren().add(visualizer);
        rootLayout.setCenter(pane);

        expressionField.setPromptText("Enter infix expression");
        expressionField.setMinWidth(300);
        expressionField.setAlignment(Pos.CENTER);

        drawButton.setOnAction(event -> drawTreeFromInput());

        HBox topLayout = new HBox(10);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.getChildren().addAll(expressionField, drawButton);

        Separator separator = new Separator();

        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);
        topSection.getChildren().addAll(topLayout, separator);

        rootLayout.setTop(topSection);

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setTitle("Expression Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void drawTreeFromInput() {
        String expression = expressionField.getText();
        if (!expression.isEmpty()) {
            root = constructTree(expression);
            int maxDepth = getMaxDepth(root);
            visualizeTree(maxDepth);
        }
    }

    private void visualizeTree(int maxDepth) {
        visualizer.clear();
        visualizer.drawTree(root, 400, 50, 150, 0, maxDepth);
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
        expression = expression.replace(" ", "");
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
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix += operators.pop() + " ";
                    }
                    operators.pop();
                } else if (isOperator(c)) {
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

    public Node constructTree(String expression) {
        String postfix = infixToPostfix(expression);
        Stack<Node> stack = new Stack<>();

        for (String token : postfix.split(" ")) {
            if (token.length() == 1 && isOperator(token.charAt(0))) {
                Node operatorNode = new Node(token);
                operatorNode.right = stack.pop();
                operatorNode.left = stack.pop();
                stack.push(operatorNode);
            } else {
                stack.push(new Node(token));
            }
        }

        return stack.pop();
    }

    private int getMaxDepth(Node node) {
        if (node == null) {
            return 0;
        } else {
            int leftDepth = getMaxDepth(node.left);
            int rightDepth = getMaxDepth(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }
}
