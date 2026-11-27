package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragDropDemo extends Application {

    @Override
    public void start(Stage primaryStage) {

        // 1. Drag source (the item to move)
        Label itemToDrag = new Label("Drag Me");
        itemToDrag.setStyle("-fx-background-color: #f39c12; -fx-padding: 10; "
                + "-fx-font-size: 16px; -fx-text-fill: white;");

        // 2. Drop target (the zone)
        Rectangle dropZone = new Rectangle(200, 100);
        dropZone.setFill(Color.LIGHTGRAY);
        dropZone.setStroke(Color.GRAY);
        dropZone.setStrokeWidth(2);

        Label dropLabel = new Label("Drop Here!");

        VBox dropContainer = new VBox(10, dropZone, dropLabel);
        dropContainer.setStyle("-fx-alignment: center;");

        // ---- DRAG DETECTED (Source) ----
        itemToDrag.setOnDragDetected(event -> {
            Dragboard db = itemToDrag.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemToDrag.getText());
            db.setContent(content);
            event.consume();
        });

        // ---- DRAG DONE (Source) ----
        // Runs on the source once the drag finishes, successful or not
        itemToDrag.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                itemToDrag.setDisable(true);
            }
            event.consume();
        });

        // ---- DRAG OVER (Target) ----
        // Tells JavaFX this node accepts the dragged item
        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // ---- DRAG ENTERED (visual feedback) ----
        dropZone.setOnDragEntered(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasString()) {
                dropZone.setFill(Color.LIGHTGREEN);
            }
            event.consume();
        });

        // ---- DRAG EXITED (reset feedback) ----
        dropZone.setOnDragExited(event -> {
            dropZone.setFill(Color.LIGHTGRAY);
            event.consume();
        });

        // ---- DRAG DROPPED (Target) ----
        dropZone.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                dropLabel.setText("Dropped: " + db.getString());
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        // Bonus: reset button so you can try again without restarting
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            itemToDrag.setDisable(false);
            dropLabel.setText("Drop Here!");
        });

        VBox root = new VBox(30, itemToDrag, dropContainer, resetBtn);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Drag and Drop Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}