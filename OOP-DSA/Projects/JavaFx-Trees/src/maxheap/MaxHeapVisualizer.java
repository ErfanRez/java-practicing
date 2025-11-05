package maxheap;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class MaxHeapVisualizer extends Application {


    private MaxHeap heap = new MaxHeap();
    private TreePane visualizer = new TreePane();
    private StackPane centerPane = new StackPane();


    private Label arrayViewTitleLabel = new Label("Array View: ");
    private Label arrayViewExprLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane mainPane = new BorderPane();

        centerPane.getChildren().add(visualizer);
        visualizer.prefWidthProperty().bind(centerPane.widthProperty());
        visualizer.prefHeightProperty().bind(centerPane.heightProperty());
        mainPane.setCenter(centerPane);

        TextField insertField = new TextField();
        insertField.setPromptText("Insert key");
        Button insertButton = new Button("Insert");


        Button deleteButton = new Button("Remove Max");
        Button clearButton = new Button("Clear");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox controlPanel = new HBox(10, insertField, insertButton, deleteButton, spacer, clearButton);
        controlPanel.setAlignment(Pos.CENTER_LEFT);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #f0f0f0;");

        Separator separator = new Separator();

        VBox topSection = new VBox(10, controlPanel, separator);
        topSection.setAlignment(Pos.CENTER);
        mainPane.setTop(topSection);


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
        primaryStage.setTitle("Max Heap Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();


        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            visualizeTree();
        });


        insertButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(insertField.getText().trim());
                heap.add(key);
                insertField.clear();
                visualizeTree();
            } catch (NumberFormatException ex) {
                insertField.clear();
            }
        });


        deleteButton.setOnAction(e -> {
            try {
                heap.removeMax();
                visualizeTree();
            } catch (EmptyMaxHeapException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Heap Empty");
                alert.setHeaderText(null);
                alert.setContentText("The max-heap is empty.");
                alert.showAndWait();
            }
        });


        clearButton.setOnAction(e -> {
            heap = new MaxHeap();
            visualizeTree();
        });
    }


    private void visualizeTree() {
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



