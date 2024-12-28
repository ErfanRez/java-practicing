package ds.avl;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class TreeVisualizer extends Pane {

    public void clear() {
        this.getChildren().clear();
    }

    public void drawTree(Node root, double x, double y, double hGap) {
        if (root != null) {
            drawNode(x, y, root.key);
            if (root.left != null) {
                drawEdge(x, y, x - hGap, y + 50);
                drawTree(root.left, x - hGap, y + 50, hGap / 2);
            }
            if (root.right != null) {
                drawEdge(x, y, x + hGap, y + 50);
                drawTree(root.right, x + hGap, y + 50, hGap / 2);
            }
        }
    }

    private void drawNode(double x, double y, int value) {
        Circle circle = new Circle(x, y, 20);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 6, y + 5, String.valueOf(value));

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
