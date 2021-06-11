package bomb.components.simon.screams;

import bomb.modules.s.simon.Simon.Screams;
import bomb.tools.data.structures.ring.ReadOnlyRing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.paint.Color.WHITE;

public class CustomEdge extends Pane{
    private boolean selectorMode;
    private ArrayList<CustomEdge> internalReference;

    private final ReadOnlyRing<Screams> colors;

    @FXML
    private Polygon base;

    public CustomEdge(){
        super();
        selectorMode = false;
        colors = new ReadOnlyRing<>(6);
        fill();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edge.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fill(){
        for (Screams scream : Screams.values())
            colors.add(scream);
        colors.rotateHeadCounter();
    }

    public void setSelectorMode(boolean nextBool){
        selectorMode = nextBool;
    }

    public void clickAction(){
        if (selectorMode){
            colors.rotateHeadClockwise();
            base.setFill(Color.web(colors.getHeadData().getLabel(), 1.0));
        } else {
            if (base.getFill() == WHITE) return;
            internalReference.add(this);
        }
    }

    public Screams exportColor(){
        if (base.getFill() == WHITE)
            return null;
        return colors.getHeadData();
    }

    public void getListReference(ArrayList<CustomEdge> list){
        internalReference = list;
    }

    public void resetEdge(){
        base.setFill(WHITE);
        while (colors.getHeadData() != Screams.PURPLE) colors.rotateHeadClockwise();
        selectorMode = false;
    }
}
