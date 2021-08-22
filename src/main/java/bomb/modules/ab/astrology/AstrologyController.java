package bomb.modules.ab.astrology;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.Arrays;

public class AstrologyController implements Resettable {
    private final AstroSymbol[] set;

    @FXML
    private ImageView fire, water, earth, air,
            mercury, venus, mars, jupiter, saturn, uranus, neptune, pluto, sun, moon,
            aquarius, aries, cancer, capricorn, gemini, leo, libra, pisces, sag, scorpio, taurus, virgo;

    @FXML
    private TextField omen;

    @FXML
    private Button astroReset;

    public AstrologyController() {
        set = new AstroSymbol[3];
    }

    @FXML
    private void getElement(){
        if (fire.isHover()){
            set[0] = AstroSymbol.FIRE;
        } else if (earth.isHover()){
            set[0] = AstroSymbol.EARTH;
        } else if (air.isHover()){
            set[0] = AstroSymbol.AIR;
        } else if (water.isHover()){
            set[0] = AstroSymbol.WATER;
        }

        elementDisable(true);
        getOmen();
    }

    @FXML
    private void getZodiac(){
        if (aquarius.isHover()){
            set[2] = AstroSymbol.AQUARIUS;
        } else if (aries.isHover()){
            set[2] = AstroSymbol.ARIES;
        } else if (cancer.isHover()){
            set[2] = AstroSymbol.CANCER;
        } else if (capricorn.isHover()){
            set[2] = AstroSymbol.CAPRICORN;
        } else if (gemini.isHover()){
            set[2] = AstroSymbol.GEMINI;
        } else if (leo.isHover()){
            set[2] = AstroSymbol.LEO;
        } else if (libra.isHover()){
            set[2] = AstroSymbol.LIBRA;
        } else if (pisces.isHover()){
            set[2] = AstroSymbol.PISCES;
        } else if (sag.isHover()){
            set[2] = AstroSymbol.SAGITTARIUS;
        } else if (scorpio.isHover()){
            set[2] = AstroSymbol.SCORPIO;
        } else if (taurus.isHover()){
            set[2] = AstroSymbol.TAURUS;
        } else if (virgo.isHover()){
            set[2] = AstroSymbol.VIRGO;
        }
        zodiacDisable(true);
        getOmen();
    }

    @FXML
    private void getCeleste(){
        if (mercury.isHover()){
            set[1] = AstroSymbol.MERCURY;
        } else if (venus.isHover()){
            set[1] = AstroSymbol.VENUS;
        } else if (mars.isHover()){
            set[1] = AstroSymbol.MARS;
        } else if (jupiter.isHover()){
            set[1] = AstroSymbol.JUPITER;
        } else if (saturn.isHover()){
            set[1] = AstroSymbol.SATURN;
        } else if (uranus.isHover()){
            set[1] = AstroSymbol.URANUS;
        } else if (neptune.isHover()){
            set[1] = AstroSymbol.NEPTUNE;
        } else if (pluto.isHover()){
            set[1] = AstroSymbol.PLUTO;
        } else if (sun.isHover()){
            set[1] = AstroSymbol.SUN;
        } else if (moon.isHover()){
            set[1] = AstroSymbol.MOON;
        }
        celesteDisable(true);
        getOmen();
    }

    @FXML
    private void astroReset(){
        elementDisable(false);
        celesteDisable(false);
        zodiacDisable(false);
        astroReset.setDisable(true);
        Arrays.fill(set, null);
    }

    private void elementDisable(boolean set){
        toggleImageViewArray(set, fire, earth, air, water);
    }

    private void celesteDisable(boolean set){
        toggleImageViewArray(set, sun, moon, mercury, venus, mars, jupiter, saturn, uranus, neptune, pluto);
    }

    private void zodiacDisable(boolean set){
        toggleImageViewArray(set, aquarius, aries, cancer, capricorn, gemini, leo, libra, pisces, sag, scorpio,
                taurus, virgo);
    }

    private void toggleImageViewArray(boolean set, ImageView ... views){
        if (set) FacadeFX.disableMultiple(views);
        else FacadeFX.enableMultiple(views);
    }

    private void getOmen(){
        try {
            if (set[0] != null && set[1] != null && set[2] != null) {
                omen.setText(Astrology.calculate(set[0], set[1], set[2]));
                astroReset();
                astroReset.setDisable(true);
            } else
                astroReset.setDisable(false);
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.ERROR, "The Serial Code wasn't set");
        }
    }

    @Override
    public void reset() {
        astroReset();
    }
}
