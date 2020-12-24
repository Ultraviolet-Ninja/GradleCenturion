package bomb.sub.controllers;

import bomb.modules.il.Laundry;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static bomb.tools.Mechanics.numberRegex;
import static bomb.tools.Mechanics.ultimateFilter;

public class ILController {

    @FXML
    private ImageView wash, dry;

    @FXML
    private Label washText, dryText, ironText, specialText, article, bob;

    @FXML
    private TextField modNum, needies;

    //Laundry Methods
    @FXML
    private void coinInsert(){
        try{
            String modBuffer = ultimateFilter(modNum.getText(), numberRegex),
                    needyBuffer = ultimateFilter(needies.getText(), numberRegex);
            String[] outputs = Laundry.clean(modBuffer, needyBuffer);
            wash.setImage(new Image(outputs[0]));
            dry.setImage(new Image(outputs[1]));
            washText.setText(separateText(outputs[0]));
            dryText.setText(separateText(outputs[1]));

            ironText.setText(outputs[2]);
            specialText.setText(outputs[3]);
            article.setText(restructure(outputs[4]));
            if (outputs.length == 6) bob.setText(outputs[5]);
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(illegal.getMessage());
            alert.showAndWait();
        }
    }

    private String separateText(String filename){
        if (filename.contains("Wash"))
            return filename.substring(37).replaceAll(".png", "");
        return filename.substring(36).replaceAll(".png", "");
    }

    private String restructure(String in){
        String[] buffer = in.split(" - ");
        StringBuilder builder = new StringBuilder();

        for (String s : buffer) {
            builder.append(s, 0, 1).append(s.substring(1).toLowerCase()).append(" - ");
        }

        return builder.substring(0, builder.length()-3);
    }
}
