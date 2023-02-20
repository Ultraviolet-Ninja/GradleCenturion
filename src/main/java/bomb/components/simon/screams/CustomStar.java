package bomb.components.simon.screams;

import bomb.abstractions.Resettable;
import bomb.modules.s.simon.SimonColors.ScreamColor;
import bomb.tools.event.HoverHandler;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CustomStar extends Pane implements Resettable {
    private final List<CustomEdge> clicks = new ArrayList<>();

    @FXML private CustomEdge first, second, third, forth, fifth, sixth;

    public CustomStar(){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("star.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);
        initializeEdges();
    }

    private void initializeEdges(){
        HoverHandler<MouseEvent> handler = new HoverHandler<>(event -> ((CustomEdge) event.getSource()).clickAction());
        for (CustomEdge edge : new CustomEdge[]{first, second, third, forth, fifth, sixth}) {
            edge.setOnMouseClicked(handler);
            edge.getListReference(clicks);
        }
    }

    public ScreamColor[] collectOrder(){
        return new ScreamColor[]{first.exportColor(), second.exportColor(), third.exportColor(),
                forth.exportColor(), fifth.exportColor(), sixth.exportColor()};
    }

    public boolean confirmDifferentColors(){
        Set<ScreamColor> check = new HashSet<>(Arrays.asList(collectOrder()));
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

    public ScreamColor[] collectFlashOrder(){
        ScreamColor[] output = new ScreamColor[clicks.size()];
        for (int i = 0; i < clicks.size(); i++){
            output[i] = clicks.get(i).exportColor();
        }
        return output;
    }

    public void resetClicks(){
        clicks.clear();
    }
}
