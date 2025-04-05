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

public class FXConfig extends Application {

    private ExpressionTree tree;
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
            if (tree.getRoot() != null) {
                int maxDepth = tree.getMaxDepth();
                drawTreeCentered(maxDepth);
            }
        });
    }

    private void drawTreeFromInput() {
        String expression = expressionField.getText();
        if (!expression.isEmpty()) {
            tree = new ExpressionTree(expression);
            int maxDepth = tree.getMaxDepth();
            drawTreeCentered(maxDepth);


            preorderExprLabel.setText(tree.getPreorder().trim());
            postorderExprLabel.setText(tree.getPostorder().trim());
        }
    }

    private void drawTreeCentered(int maxDepth) {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 80;
        visualizer.drawTree(tree.getRoot(), centerX, centerY, 150, 0, maxDepth);
    }

    public static void main(String[] args) {
        launch(args);
    }
}