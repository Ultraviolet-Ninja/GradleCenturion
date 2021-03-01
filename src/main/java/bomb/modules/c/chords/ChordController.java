package bomb.modules.c.chords;

import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChordController {
    private int counter = 0;
    private final String[] notes = new String[4];
    private StringBuilder track = new StringBuilder();

    @FXML
    private Button a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp;

    @FXML
    private TextField inputChord, outputChord;

    @FXML
    private void type(){
        if (a.isHover())
            add(a.getText());
        else if (aSharp.isHover())
            add(aSharp.getText());
        else if (b.isHover())
            add(b.getText());
        else if (c.isHover())
            add(c.getText());
        else if (cSharp.isHover())
            add(cSharp.getText());
        else if (d.isHover())
            add(d.getText());
        else if (dSharp.isHover())
            add(dSharp.getText());
        else if (e.isHover())
            add(e.getText());
        else if (f.isHover())
            add(f.getText());
        else if (fSharp.isHover())
            add(fSharp.getText());
        else if (g.isHover())
            add(g.getText());
        else if (gSharp.isHover())
            add(gSharp.getText());
    }

    private void add(String note){
        notes[counter++] = note;
        track.append(note);
        if (counter == 4) {
            try {
                solve();
            } catch (IllegalArgumentException illegal){
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage(), "", "");
            }
            counter = 0;
            track = new StringBuilder();
        } else
            track.append(", ");
        inputChord.setText(track.toString());
    }

    private void solve(){
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < notes.length; i++){
            temp.append(notes[i]);
            if (i != 3)
                temp.append(" ");
        }
        outputChord.setText(ChordQualities.solve(temp.toString()));
    }
}
