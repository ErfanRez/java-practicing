package minheap;

import javafx.geometry.VPos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;


public class TreePane extends Pane {
    private static final double FIXED_EDGE_LENGTH = 80;
    public static final double INITIAL_HGAP = 150;
    private static final double HGAP_SHRINK_FACTOR = 0.52;

    public void clear() {
        this.getChildren().clear();
    }

    public void drawTree(int[] a, int n, int index, double x, double y, double hGap) {
        if (index > n)
            return;
        drawNode(x, y, a[index]);
        int leftIndex = 2 * index;
        int rightIndex = 2 * index + 1;

        if (leftIndex <= n) {
            double childX = x - hGap;
            double childY = y + FIXED_EDGE_LENGTH;
            drawEdge(x, y, childX, childY);
            drawTree(a, n, leftIndex, childX, childY,  hGap * HGAP_SHRINK_FACTOR);
        }
        if (rightIndex <= n) {
            double childX = x + hGap;
            double childY = y + FIXED_EDGE_LENGTH;
            drawEdge(x, y, childX, childY);
            drawTree(a, n, rightIndex, childX, childY, hGap * HGAP_SHRINK_FACTOR);
        }
    }

    private void drawNode(double x, double y, int value) {
        Circle circle = new Circle(x, y, 20);
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
