package bomb.components.simon.screams;

import bomb.abstractions.Resettable;
import bomb.modules.s.simon.Simon.Screams;
import bomb.tools.event.HoverHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomStar extends Pane implements Resettable {
    private final ArrayList<CustomEdge> clicks = new ArrayList<>();

    @FXML private CustomEdge first, second, third, forth, fifth, sixth;

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
        for (CustomEdge edge : new CustomEdge[]{first, second, third, forth, fifth, sixth}) {
            edge.setOnMouseClicked(handler);
            edge.getListReference(clicks);
        }
    }

    public Screams[] collectOrder(){
        return new Screams[]{first.exportColor(), second.exportColor(), third.exportColor(),
                forth.exportColor(), fifth.exportColor(), sixth.exportColor()};
    }

    public boolean confirmDifferentColors(){
        Set<Screams> check = new HashSet<>(Arrays.asList(collectOrder()));
        return check.size() == 6 && !check.contains(null);
    }

    public void setSelectorMode(boolean nextBool){
        first.setSelectorMode(nextBool);
        second.setSelectorMode(nextBool);
        third.setSelectorMode(nextBool);
        forth.setSelectorMode(nextBool);
        fifth.setSelectorMode(nextBool);
        sixth.setSelectorMode(nextBool);
    }

    @Override
    public void reset(){
        first.reset();
        second.reset();
        third.reset();
        forth.reset();
        fifth.reset();
        sixth.reset();
    }

    public Screams[] collectFlashOrder(){
        Screams[] output = new Screams[clicks.size()];
        for (int i = 0; i < clicks.size(); i++){
            output[i] = clicks.get(i).exportColor();
        }
        return output;
    }

    public void resetClicks(){
        clicks.clear();
    }
}
