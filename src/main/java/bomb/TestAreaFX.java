package bomb;

import com.jfoenix.controls.JFXButton;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.LinearSkin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class TestAreaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Gauge gauge = new Gauge();
        gauge.setSkin(new LinearSkin(gauge));
        Pane display = new Pane();
//        display.getChildren().add(gauge);
        display.getChildren().add(new JFXButton("Test"));
        Scene scene = new Scene(display, 300, 250);
        primaryStage.setTitle("Gauge Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
