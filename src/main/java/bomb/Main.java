package bomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("manual.fxml"));
        primaryStage.setTitle("Centurion Manual");
        primaryStage.setScene(new Scene(root, 1160, 770));
        primaryStage.getIcons().add(new Image("bomb\\KTANE logo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}