import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UnidirectionalDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField nameField = new TextField();
        nameField.setPromptText("Type your name here...");

        Label greetingLabel = new Label();

        VBox root = new VBox(20, nameField, greetingLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // ---- UNIDIRECTIONAL BINDING ----
        // As the TextField changes, the Label updates automatically.
        // Bindings.when(...) shows "Hello, Guest" while the field is empty.
        greetingLabel.textProperty().bind(
                Bindings.when(nameField.textProperty().isEmpty())
                        .then("Hello, Guest")
                        .otherwise(nameField.textProperty().concat(", welcome!"))
        );

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Unidirectional Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}