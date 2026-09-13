package redblack;

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


    public void drawTree(TreeNode node, double x, double y, double hGap, TreeNode TNULL) {
        if (node == TNULL) return;
        drawTreeNode(x, y, node);
        if (node.left != TNULL) {
            double childX = x - hGap;
            double childY = y + FIXED_EDGE_LENGTH;
            drawEdge(x, y, childX, childY);
            drawTree(node.left, childX, childY, hGap * HGAP_SHRINK_FACTOR, TNULL);
        }
        if (node.right != TNULL) {
            double childX = x + hGap;
            double childY = y + FIXED_EDGE_LENGTH;
            drawEdge(x, y, childX, childY);
            drawTree(node.right, childX, childY, hGap * HGAP_SHRINK_FACTOR, TNULL);
        }
    }


    private void drawTreeNode(double x, double y, TreeNode node) {
        Circle circle = new Circle(x, y, NODE_RADIUS);
        if (node.color == 1) { // red
            circle.setFill(Color.RED);
        } else { // black
            circle.setFill(Color.BLACK);
        }
        circle.setStroke(Color.BLACK);

        Text text = new Text(String.valueOf(node.data));
        text.setTextOrigin(VPos.CENTER);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.applyCss();
        double textWidth = text.getLayoutBounds().getWidth();
        text.setFill(Color.WHITE);
        text.setX(x - textWidth / 2);
        text.setY(y);

        this.getChildren().addAll(circle, text);
    }


    private void drawEdge(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double offsetX = dx * NODE_RADIUS / distance;
        double offsetY = dy * NODE_RADIUS / distance;

        Line line = new Line(x1 + offsetX, y1 + offsetY, x2 - offsetX, y2 - offsetY);
        this.getChildren().add(line);
    }
}

