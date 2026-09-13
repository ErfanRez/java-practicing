package minheap;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MinHeapHardCoded extends Application {

    private MinHeap heap = new MinHeap();
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();

    private Label arrayViewTitleLabel = new Label("Array View: ");
    private Label arrayViewExprLabel = new Label();


    // Hard coded insertion and deletion from here
    private void testData() throws EmptyMinHeapException {

        heap.add(44);
        heap.add(30);
        heap.add(50);
        heap.add(22);
        heap.add(60);
        heap.add(55);
        heap.add(77);
        heap.add(55);
        heap.add(11);

//        heap.removeMin();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws EmptyMinHeapException {
        BorderPane mainPane = new BorderPane();
        centerPane.getChildren().add(visualizer);
        visualizer.prefWidthProperty().bind(centerPane.widthProperty());
        visualizer.prefHeightProperty().bind(centerPane.heightProperty());
        mainPane.setCenter(centerPane);


        HBox arrayViewBox = new HBox(10);
        arrayViewBox.setAlignment(Pos.CENTER_LEFT);
        arrayViewTitleLabel.setStyle("-fx-font-size: 18px;");
        arrayViewExprLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        arrayViewBox.getChildren().addAll(arrayViewTitleLabel, arrayViewExprLabel);

        VBox bottomBox = new VBox(10, arrayViewBox);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 30, 30)); // top, right, bottom, left
        mainPane.setBottom(bottomBox);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("Min Heap Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();


        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (heap.size() > 0) {
                visualizeHeap();
            }
        });

        testData();

        visualizeHeap();
    }


    private void visualizeHeap() {
        visualizer.clear();
        double centerX = centerPane.getWidth() / 2;
        double centerY = 80;
        int[] heapArray = heap.getHeapArray();
        int n = heap.size();
        if (n > 0 && heapArray != null) {
            visualizer.drawTree(heapArray, n, 1, centerX, centerY, TreePane.INITIAL_HGAP);
        }
        arrayViewExprLabel.setText(getArrayView(heapArray, n));
    }


    private String getArrayView(int[] a, int n) {
        if (a == null || n == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 1; i <= n; i++) {
            sb.append(a[i]);
            if (i < n) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

