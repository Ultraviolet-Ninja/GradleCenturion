package bomb;

import com.jfoenix.controls.JFXTextField;
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
        JFXTextField field = new JFXTextField();
        field.setLabelFloat(true);
        field.setPromptText("Floating prompt");
        display.getChildren().add(field);
        Scene scene = new Scene(display, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
