package bomb.components.simon.screams;

import bomb.abstractions.Resettable;
import bomb.modules.s.simon.SimonColors.ScreamColor;
import bomb.tools.data.structures.ring.ArrayRing;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.List;

import static javafx.scene.paint.Color.WHITE;

public class CustomEdge extends Polygon implements Resettable {
    private boolean selectorMode;
    private List<CustomEdge> internalReference;

    private final ArrayRing<ScreamColor> colors;

    public CustomEdge() {
        super();
        selectorMode = false;
        colors = new ArrayRing<>(6);
        fill();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edge.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);
    }

    private void fill() {
        for (ScreamColor scream : ScreamColor.values())
            colors.add(scream);
        colors.rotateCounterClockwise();
    }

    public void setSelectorMode(boolean nextBool) {
        selectorMode = nextBool;
    }

    public void clickAction() {
        if (selectorMode) {
            colors.rotateClockwise();
            setFill(Color.web(colors.getHeadData().getLabel(), 1.0));
        } else {
            if (getFill() == WHITE) return;
            internalReference.add(this);
            indicateButtonPress();
        }
    }

    private void indicateButtonPress() {
        FacadeFX.parallelTransition(
                this,
                setupFade(),
                setupPressAnimation()
        );
    }

    public ScreamColor exportColor() {
        if (getFill() == WHITE)
            return null;
        return colors.getHeadData();
    }

    public void getListReference(List<CustomEdge> list) {
        internalReference = list;
    }

    @Override
    public void reset() {
        setFill(WHITE);
        while (colors.getHeadData() != ScreamColor.PURPLE) colors.rotateClockwise();
        selectorMode = false;
    }

    private static FadeTransition setupFade() {
        FadeTransition fade = new FadeTransition(Duration.millis(200));
        fade.setFromValue(0.5);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.setAutoReverse(true);
        return fade;
    }

    private static ScaleTransition setupPressAnimation() {
        ScaleTransition scale = new ScaleTransition(Duration.millis(50));
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(0.85);
        scale.setToY(0.85);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        return scale;
    }
}
