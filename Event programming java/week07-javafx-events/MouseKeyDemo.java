package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MouseKeyDemo extends Application {

    private static final double WIDTH = 500;
    private static final double HEIGHT = 400;
    private static final double RADIUS = 20;
    private static final double STEP = 15; // How far to move per key press

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f4f4f4;");

        // The player character
        Circle player = new Circle(WIDTH / 2, HEIGHT / 2, RADIUS, Color.BLUE);

        // Text labels
        Text mouseCoords = new Text(10, 20, "Mouse Coordinates: X=0, Y=0");
        Text hint = new Text(10, 40, "Arrow keys move the circle. Click to teleport it.");

        root.getChildren().addAll(player, mouseCoords, hint);

        // ---- MOUSE EVENTS ----
        // Track mouse movement over the whole pane
        root.setOnMouseMoved(e -> mouseCoords.setText(
                String.format("Mouse Coordinates: X=%.0f, Y=%.0f", e.getX(), e.getY())));

        // Bonus: click to move the circle to the mouse position
        root.setOnMouseClicked(e -> {
            player.setCenterX(clamp(e.getX(), RADIUS, root.getWidth() - RADIUS));
            player.setCenterY(clamp(e.getY(), RADIUS, root.getHeight() - RADIUS));
        });

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // ---- KEY EVENTS ----
        // Listening on the Scene means focus is never a problem.
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            double x = player.getCenterX();
            double y = player.getCenterY();

            if (key == KeyCode.UP)    y -= STEP;
            if (key == KeyCode.DOWN)  y += STEP;
            if (key == KeyCode.LEFT)  x -= STEP;
            if (key == KeyCode.RIGHT) x += STEP;

            // Keep the circle inside the window (works even if you resize it)
            x = clamp(x, RADIUS, root.getWidth() - RADIUS);
            y = clamp(y, RADIUS, root.getHeight() - RADIUS);

            player.setCenterX(x);
            player.setCenterY(y);
            e.consume();
        });

        primaryStage.setTitle("Mouse and Key Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public static void main(String[] args) {
        launch(args);
    }
}