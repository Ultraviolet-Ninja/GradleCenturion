package bomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SuppressWarnings("ConstantConditions")
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("manual.fxml"));
        primaryStage.setTitle("Centurion Bomb Manual");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("KTANE logo.png"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
