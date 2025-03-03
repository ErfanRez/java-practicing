package exptree;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Stack;

public class ExpressionTreeVisualizer extends Application {

    private Node root;
    private TreePane visualizer = new TreePane();
    private TextField expressionField = new TextField();
    private Button drawButton = new Button("Draw Tree");


    private StackPane centerPane = new StackPane();


    private Label preorderTitleLabel = new Label("Preorder (Prefix): ");
    private Label preorderExprLabel = new Label();
    private Label postorderTitleLabel = new Label("Postorder (Postfix): ");
    private Label postorderExprLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootLayout = new BorderPane();

        centerPane.getChildren().add(visualizer);
        rootLayout.setCenter(centerPane);

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


        HBox preorderBox = new HBox();
        preorderBox.setAlignment(Pos.CENTER_LEFT);
        preorderBox.setSpacing(10);
        preorderTitleLabel.setStyle("-fx-font-size: 18px;");
        preorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        preorderBox.getChildren().addAll(preorderTitleLabel, preorderExprLabel);

        HBox postorderBox = new HBox();
        postorderBox.setAlignment(Pos.CENTER_LEFT);
        postorderBox.setSpacing(10);
        postorderTitleLabel.setStyle("-fx-font-size: 18px;");
        postorderExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        postorderBox.getChildren().addAll(postorderTitleLabel, postorderExprLabel);

        //Vertical Spacing
        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30)); // top, right, bottom, left
        bottomBox.getChildren().addAll(preorderBox, postorderBox);
        rootLayout.setBottom(bottomBox);

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setTitle("Expression Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (root != null) {
                int maxDepth = getMaxDepth(root);
                drawTreeCentered(maxDepth);
            }
        });
    }

    private void drawTreeFromInput() {
        String expression = expressionField.getText();
        if (!expression.isEmpty()) {
            root = constructTree(expression);
            int maxDepth = getMaxDepth(root);
            drawTreeCentered(maxDepth);


            preorderExprLabel.setText(getPreorder(root).trim());
            postorderExprLabel.setText(getPostorder(root).trim());
        }
    }

    private void drawTreeCentered(int maxDepth) {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 80;
        visualizer.drawTree(root, centerX, centerY, 150, 0, maxDepth);
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
            default:  return 0;
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private static String infixToPostfix(String expression) {
        expression = expression.replace(" ", "");
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        StringBuilder number = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (!isOperator(c) && c != '(' && c != ')') {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    postfix.append(number).append(" ");
                    number.setLength(0);
                }
                if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix.append(operators.pop()).append(" ");
                    }
                    operators.pop();
                } else {
                    while (!operators.isEmpty() && precedence(operators.peek()) > precedence(c)) {
                        postfix.append(operators.pop()).append(" ");
                    }
                    if (!(c == '^' && !operators.isEmpty() && operators.peek() == '^')) {
                        while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                            postfix.append(operators.pop()).append(" ");
                        }
                    }
                    operators.push(c);
                }
            }
        }
        if (number.length() > 0) {
            postfix.append(number).append(" ");
        }
        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(" ");
        }
        return postfix.toString().trim();
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
        if (node == null)
            return 0;
        else {
            int leftDepth = getMaxDepth(node.left);
            int rightDepth = getMaxDepth(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }


    private String getPreorder(Node node) {
        if (node == null) return "";
        return node.data + " " + getPreorder(node.left) + getPreorder(node.right);
    }

    private String getPostorder(Node node) {
        if (node == null) return "";
        return getPostorder(node.left) + getPostorder(node.right) + node.data + " ";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
