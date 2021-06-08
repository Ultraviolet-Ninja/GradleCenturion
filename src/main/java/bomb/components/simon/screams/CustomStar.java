package bomb.components.simon.screams;

import bomb.modules.s.simon.Simon.Screams;
import bomb.tools.HoverHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomStar extends Pane {
    //Might need
    private ArrayList<CustomEdge> clicks = new ArrayList<>();

    @FXML
    private CustomEdge first, second, third, forth, fifth, sixth;

    public CustomStar(){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("star.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeEdges();
    }

    private void initializeEdges(){
        HoverHandler<MouseEvent> handler = new HoverHandler<>(event -> ((CustomEdge) event.getSource()).clickAction());
        first.setOnMouseClicked(handler);
        second.setOnMouseClicked(handler);
        third.setOnMouseClicked(handler);
        forth.setOnMouseClicked(handler);
        fifth.setOnMouseClicked(handler);
        sixth.setOnMouseClicked(handler);
    }

    public Screams[] collect(){
        return new Screams[]{first.exportColor(), second.exportColor(), third.exportColor(),
                forth.exportColor(), fifth.exportColor(), sixth.exportColor()};
    }

    public boolean confirmDifferentColors(){
        Set<Screams> check = new HashSet<>(Arrays.asList(collect()));
        return check.size() == 6;
    }

    public void setSelectorMode(boolean nextBool){
        first.setSelectorMode(nextBool);
        second.setSelectorMode(nextBool);
        third.setSelectorMode(nextBool);
        forth.setSelectorMode(nextBool);
        fifth.setSelectorMode(nextBool);
        sixth.setSelectorMode(nextBool);
    }

    public void resetStar(){
        first.resetEdge();
        second.resetEdge();
        third.resetEdge();
        forth.resetEdge();
        fifth.resetEdge();
        sixth.resetEdge();
    }

    public void resetClicks(){
        first.resetClick();
        second.resetClick();
        third.resetClick();
        forth.resetClick();
        fifth.resetClick();
        sixth.resetClick();
    }
}
