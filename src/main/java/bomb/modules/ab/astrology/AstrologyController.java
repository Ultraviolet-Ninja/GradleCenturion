package bomb.modules.ab.astrology;

import bomb.interfaces.Resettable;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AstrologyController implements Resettable {
    private AstroSymbols[] set = new AstroSymbols[3];
    @FXML
    private ImageView fire, water, earth, air,
            mercury, venus, mars, jupiter, saturn, uranus, neptune, pluto, sun, moon,
            aquarius, aries, cancer, capricorn, gemini, leo, libra, pisces, sag, scorpio, taurus, virgo;

    @FXML
    private TextField omen;

    @FXML
    private Button astroReset;

    @FXML
    private void getElement(){
        if (fire.isHover()){
            set[0] = AstroSymbols.FIRE;
        } else if (earth.isHover()){
            set[0] = AstroSymbols.EARTH;
        } else if (air.isHover()){
            set[0] = AstroSymbols.AIR;
        } else if (water.isHover()){
            set[0] = AstroSymbols.WATER;
        }

        elementDisable(true);
        getOmen();
    }

    @FXML
    private void getZodiac(){
        if (aquarius.isHover()){
            set[2] = AstroSymbols.AQUARIUS;
        } else if (aries.isHover()){
            set[2] = AstroSymbols.ARIES;
        } else if (cancer.isHover()){
            set[2] = AstroSymbols.CANCER;
        } else if (capricorn.isHover()){
            set[2] = AstroSymbols.CAPRICORN;
        } else if (gemini.isHover()){
            set[2] = AstroSymbols.GEMINI;
        } else if (leo.isHover()){
            set[2] = AstroSymbols.LEO;
        } else if (libra.isHover()){
            set[2] = AstroSymbols.LIBRA;
        } else if (pisces.isHover()){
            set[2] = AstroSymbols.PISCES;
        } else if (sag.isHover()){
            set[2] = AstroSymbols.SAGITTARIUS;
        } else if (scorpio.isHover()){
            set[2] = AstroSymbols.SCORPIO;
        } else if (taurus.isHover()){
            set[2] = AstroSymbols.TAURUS;
        } else if (virgo.isHover()){
            set[2] = AstroSymbols.VIRGO;
        }
        zodiacDisable(true);
        getOmen();
    }

    @FXML
    private void getCeleste(){
        if (mercury.isHover()){
            set[1] = AstroSymbols.MERCURY;
        } else if (venus.isHover()){
            set[1] = AstroSymbols.VENUS;
        } else if (mars.isHover()){
            set[1] = AstroSymbols.MARS;
        } else if (jupiter.isHover()){
            set[1] = AstroSymbols.JUPITER;
        } else if (saturn.isHover()){
            set[1] = AstroSymbols.SATURN;
        } else if (uranus.isHover()){
            set[1] = AstroSymbols.URANUS;
        } else if (neptune.isHover()){
            set[1] = AstroSymbols.NEPTUNE;
        } else if (pluto.isHover()){
            set[1] = AstroSymbols.PLUTO;
        } else if (sun.isHover()){
            set[1] = AstroSymbols.SUN;
        } else if (moon.isHover()){
            set[1] = AstroSymbols.MOON;
        }
        celesteDisable(true);
        getOmen();
    }

    @FXML
    private void astroReset(){
        elementDisable(false);
        celesteDisable(false);
        zodiacDisable(false);
        set = new AstroSymbols[3];
        astroReset.setDisable(true);
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

    }
}
