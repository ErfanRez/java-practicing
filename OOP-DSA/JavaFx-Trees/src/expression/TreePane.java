package expression;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TreePane extends Pane {

    private static final double MIN_GAP = 50;

    public void clear() {
        this.getChildren().clear();
    }

    public void drawTree(TreeNode root, double x, double y, double hGap, int level, int maxDepth) {
        if (root != null) {
            drawNode(x, y, root.data);

            double adjustedGap = Math.max(MIN_GAP, hGap / Math.pow(1.5, level));

            if (level < maxDepth) {
                if (root.left != null) {
                    drawEdge(x, y, x - adjustedGap, y + 50);
                    drawTree(root.left, x - adjustedGap, y + 50, adjustedGap, level + 1, maxDepth);
                }
                if (root.right != null) {
                    drawEdge(x, y, x + adjustedGap, y + 50);
                    drawTree(root.right, x + adjustedGap, y + 50, adjustedGap, level + 1, maxDepth);
                }
            }
        }
    }

    private void drawNode(double x, double y, String value) {
        Circle circle = new Circle(20);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(value);

        StackPane stack = new StackPane();
        stack.setLayoutX(x - 20);
        stack.setLayoutY(y - 20);
        stack.getChildren().addAll(circle, text);

        this.getChildren().add(stack);
    }


    private void drawEdge(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double offsetX = dx * 20 / distance;
        double offsetY = dy * 20 / distance;

        Line line = new Line(x1 + offsetX, y1 + offsetY, x2 - offsetX, y2 - offsetY);
        this.getChildren().add(line);
    }
}