package binary;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.geometry.VPos;
import javafx.scene.text.TextBoundsType;

public class TreePane extends Pane {
    public static final double INITIAL_HGAP = 150;
    private static final double FIXED_EDGE_LENGTH = 80;
    private static final double HGAP_SHRINK_FACTOR = 0.52;
    private static final double NODE_RADIUS = 20;

    public void clear() {
        this.getChildren().clear();
    }

    public void drawTree(TreeNode root, double x, double y, double hGap) {
        if (root != null) {
            drawNode(x, y, root.data);
            if (root.left != null) {
                double childX = x - hGap;
                double childY = y + FIXED_EDGE_LENGTH;
                drawEdge(x, y, childX, childY);
                drawTree(root.left, childX, childY, hGap * HGAP_SHRINK_FACTOR);
            }
            if (root.right != null) {
                double childX = x + hGap;
                double childY = y + FIXED_EDGE_LENGTH;
                drawEdge(x, y, childX, childY);
                drawTree(root.right, childX, childY, hGap * HGAP_SHRINK_FACTOR);
            }
        }
    }

    private void drawNode(double x, double y, int value) {
        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(String.valueOf(value));
        text.setTextOrigin(VPos.CENTER);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.applyCss();
        double textWidth = text.getLayoutBounds().getWidth();
        text.setX(x - textWidth / 2);
        text.setY(y);

        this.getChildren().addAll(circle, text);
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