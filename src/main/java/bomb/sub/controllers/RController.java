package bomb.sub.controllers;

import bomb.modules.r.RoundKeypads;
import bomb.enumerations.Keypads;
import bomb.interfaces.Reset;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class RController implements Reset {

    @FXML
    private ImageView
            emptyRacket1, aT, lambda1, harryPotter, russianCat1, curvyH1, backC1,
            backEuro1, emptyRacket2, backC2, disneyQ1, hollowStar1, curvyH2, spanish1,
            copyright, leSac, disneyQ2, russianX1, not3, lambda2, hollowStar2,
            russianSix1, paragraph1, tB1, russianCat2, russianX2, spanish2, smile1,
            psi1, smile2,tB2, cDot, paragraph2, shrek3, star,
            russianSix2, backEuro2, puzzle, aeyyLmao, psi2, russianHN, ohms;

    @FXML
    private VBox keypad1, keypad2, keypad3, keypad4, keypad5, keypad6;

    //Round Keypad methods
    //TODO - Be able to flag keypads to not press
    @FXML
    private void keyPressed(){
        if (emptyRacket1.isHover()) {
            emptyRacket1.setImage(RoundKeypads.change(Keypads.RACKET1,
                    emptyRacket1.getImage()));
            emptyRacket2.setImage(RoundKeypads.change(Keypads.RACKET2,
                    emptyRacket2.getImage()));
        } else if (aT.isHover()) {
            aT.setImage(RoundKeypads.change(Keypads.A_T, aT.getImage()));
        } else if (lambda1.isHover()) {
            lambda1.setImage(RoundKeypads.change(Keypads.STROKED_LAMBDA1,
                    lambda1.getImage()));
            lambda2.setImage(RoundKeypads.change(Keypads.STROKED_LAMBDA2,
                    lambda2.getImage()));
        } else if (harryPotter.isHover()) {
            harryPotter.setImage(RoundKeypads.change(Keypads.HARRY_POTTER,
                    harryPotter.getImage()));
        } else if (russianCat1.isHover()) {
            russianCat1.setImage(RoundKeypads.change(Keypads.RUSSIAN_CAT1,
                    russianCat1.getImage()));
            russianCat2.setImage(RoundKeypads.change(Keypads.RUSSIAN_CAT2,
                    russianCat2.getImage()));
        } else if (curvyH1.isHover()) {
            curvyH1.setImage(RoundKeypads.change(Keypads.CURVY_H1,
                    curvyH1.getImage()));
            curvyH2.setImage(RoundKeypads.change(Keypads.CURVY_H2,
                    curvyH2.getImage()));
        } else if (backC1.isHover()) {
            backC1.setImage(RoundKeypads.change(Keypads.BACK_C1,
                    backC1.getImage()));
            backC2.setImage(RoundKeypads.change(Keypads.BACK_C2,
                    backC2.getImage()));
        } else if (backEuro1.isHover()) {
            backEuro1.setImage(RoundKeypads.change(Keypads.REVERSE_EURO1,
                    backEuro1.getImage()));
            backEuro2.setImage(RoundKeypads.change(Keypads.REVERSE_EURO2,
                    backEuro2.getImage()));
        } else if (emptyRacket2.isHover()) {
            emptyRacket1.setImage(RoundKeypads.change(Keypads.RACKET1,
                    emptyRacket1.getImage()));
            emptyRacket2.setImage(RoundKeypads.change(Keypads.RACKET2,
                    emptyRacket2.getImage()));
        } else if (backC2.isHover()) {
            backC1.setImage(RoundKeypads.change(Keypads.BACK_C1,
                    backC1.getImage()));
            backC2.setImage(RoundKeypads.change(Keypads.BACK_C2,
                    backC2.getImage()));
        } else if (disneyQ1.isHover()) {
            disneyQ1.setImage(RoundKeypads.change(Keypads.DISNEY_Q1,
                    disneyQ1.getImage()));
            disneyQ2.setImage(RoundKeypads.change(Keypads.DISNEY_Q2,
                    disneyQ2.getImage()));
        } else if (hollowStar1.isHover()) {
            hollowStar1.setImage(RoundKeypads.change(Keypads.HOLLOW_STAR1,
                    hollowStar1.getImage()));
            hollowStar2.setImage(RoundKeypads.change(Keypads.HOLLOW_STAR2,
                    hollowStar2.getImage()));
        } else if (curvyH2.isHover()) {
            curvyH1.setImage(RoundKeypads.change(Keypads.CURVY_H1,
                    curvyH1.getImage()));
            curvyH2.setImage(RoundKeypads.change(Keypads.CURVY_H2,
                    curvyH2.getImage()));
        } else if (spanish1.isHover()) {
            spanish1.setImage(RoundKeypads.change(Keypads.SPANISH_QUESTION1,
                    spanish1.getImage()));
            spanish2.setImage(RoundKeypads.change(Keypads.SPANISH_QUESTION2,
                    spanish2.getImage()));
        } else if (copyright.isHover()) {
            copyright.setImage(RoundKeypads.change(Keypads.COPYRIGHT,
                    copyright.getImage()));
        } else if (leSac.isHover()) {
            leSac.setImage(RoundKeypads.change(Keypads.THE_SAC,
                    leSac.getImage()));
        } else if (disneyQ2.isHover()) {
            disneyQ1.setImage(RoundKeypads.change(Keypads.DISNEY_Q1,
                    disneyQ1.getImage()));
            disneyQ2.setImage(RoundKeypads.change(Keypads.DISNEY_Q2,
                    disneyQ2.getImage()));
        } else if (russianX1.isHover()) {
            russianX1.setImage(RoundKeypads.change(Keypads.RUSSIAN_X1,
                    russianX1.getImage()));
            russianX2.setImage(RoundKeypads.change(Keypads.RUSSIAN_X2,
                    russianX2.getImage()));
        } else if (not3.isHover()) {
            not3.setImage(RoundKeypads.change(Keypads.NOT_THREE,
                    not3.getImage()));
        } else if (lambda2.isHover()) {
            lambda1.setImage(RoundKeypads.change(Keypads.STROKED_LAMBDA1,
                    lambda1.getImage()));
            lambda2.setImage(RoundKeypads.change(Keypads.STROKED_LAMBDA2,
                    lambda2.getImage()));
        } else if (hollowStar2.isHover()) {
            hollowStar1.setImage(RoundKeypads.change(Keypads.HOLLOW_STAR1,
                    hollowStar1.getImage()));
            hollowStar2.setImage(RoundKeypads.change(Keypads.HOLLOW_STAR2,
                    hollowStar2.getImage()));
        } else if (russianSix1.isHover()) {
            russianSix1.setImage(RoundKeypads.change(Keypads.RUSSIAN_SIX1,
                    russianSix1.getImage()));
            russianSix2.setImage(RoundKeypads.change(Keypads.RUSSIAN_SIX2,
                    russianSix2.getImage()));
        } else if (paragraph1.isHover()) {
            paragraph1.setImage(RoundKeypads.change(Keypads.PARAGRAPH1,
                    paragraph1.getImage()));
            paragraph2.setImage(RoundKeypads.change(Keypads.PARAGRAPH2,
                    paragraph2.getImage()));
        } else if (tB1.isHover()) {
            tB1.setImage(RoundKeypads.change(Keypads.TB_1,
                    tB1.getImage()));
            tB2.setImage(RoundKeypads.change(Keypads.TB_2,
                    tB2.getImage()));
        } else if (russianCat2.isHover()) {
            russianCat1.setImage(RoundKeypads.change(Keypads.RUSSIAN_CAT1,
                    russianCat1.getImage()));
            russianCat2.setImage(RoundKeypads.change(Keypads.RUSSIAN_CAT2,
                    russianCat2.getImage()));
        } else if (russianX2.isHover()) {
            russianX1.setImage(RoundKeypads.change(Keypads.RUSSIAN_X1,
                    russianX1.getImage()));
            russianX2.setImage(RoundKeypads.change(Keypads.RUSSIAN_X2,
                    russianX2.getImage()));
        } else if (spanish2.isHover()) {
            spanish1.setImage(RoundKeypads.change(Keypads.SPANISH_QUESTION1,
                    spanish1.getImage()));
            spanish2.setImage(RoundKeypads.change(Keypads.SPANISH_QUESTION2,
                    spanish2.getImage()));
        } else if (smile1.isHover()) {
            smile1.setImage(RoundKeypads.change(Keypads.SMILEY1,
                    smile1.getImage()));
            smile2.setImage(RoundKeypads.change(Keypads.SMILEY2,
                    smile2.getImage()));
        } else if (psi1.isHover()) {
            psi1.setImage(RoundKeypads.change(Keypads.PSI1,
                    psi1.getImage()));
            psi2.setImage(RoundKeypads.change(Keypads.PSI2,
                    psi2.getImage()));
        } else if (smile2.isHover()) {
            smile1.setImage(RoundKeypads.change(Keypads.SMILEY1,
                    smile1.getImage()));
            smile2.setImage(RoundKeypads.change(Keypads.SMILEY2,
                    smile2.getImage()));
        } else if (tB2.isHover()) {
            tB1.setImage(RoundKeypads.change(Keypads.TB_1,
                    tB1.getImage()));
            tB2.setImage(RoundKeypads.change(Keypads.TB_2,
                    tB2.getImage()));
        } else if (cDot.isHover()) {
            cDot.setImage(RoundKeypads.change(Keypads.C_DOT,
                    cDot.getImage()));
        } else if (paragraph2.isHover()) {
            paragraph1.setImage(RoundKeypads.change(Keypads.PARAGRAPH1,
                    paragraph1.getImage()));
            paragraph2.setImage(RoundKeypads.change(Keypads.PARAGRAPH2,
                    paragraph2.getImage()));
        } else if (shrek3.isHover()) {
            shrek3.setImage(RoundKeypads.change(Keypads.ALIEN_THREE,
                    shrek3.getImage()));
        } else if (star.isHover()) {
            star.setImage(RoundKeypads.change(Keypads.STAR,
                    star.getImage()));
        } else if (russianSix2.isHover()) {
            russianSix1.setImage(RoundKeypads.change(Keypads.RUSSIAN_SIX1,
                    russianSix1.getImage()));
            russianSix2.setImage(RoundKeypads.change(Keypads.RUSSIAN_SIX2,
                    russianSix2.getImage()));
        } else if (backEuro2.isHover()) {
            backEuro1.setImage(RoundKeypads.change(Keypads.REVERSE_EURO1,
                    backEuro1.getImage()));
            backEuro2.setImage(RoundKeypads.change(Keypads.REVERSE_EURO2,
                    backEuro2.getImage()));
        } else if (puzzle.isHover()) {
            puzzle.setImage(RoundKeypads.change(Keypads.PUZZLE,
                    puzzle.getImage()));
        } else if (aeyyLmao.isHover()) {
            aeyyLmao.setImage(RoundKeypads.change(Keypads.AE,
                    aeyyLmao.getImage()));
        } else if (psi2.isHover()) {
            psi1.setImage(RoundKeypads.change(Keypads.PSI1,
                    psi1.getImage()));
            psi2.setImage(RoundKeypads.change(Keypads.PSI2,
                    psi2.getImage()));
        } else if (russianHN.isHover()) {
            russianHN.setImage(RoundKeypads.change(Keypads.RUSSIAN_NH,
                    russianHN.getImage()));
        } else if (ohms.isHover()) {
            ohms.setImage(RoundKeypads.change(Keypads.OMEGA,
                    ohms.getImage()));
        }
        highlightBox(RoundKeypads.autoDetect(Keypads.values()));
    }

    private void highlightBox(int which){
        String toHighlight = "-fx-background-color: crimson",
                fromHighlight = "-fx-background-color: white";

        switch (which) {
            case -1: {
                keypad1.setStyle(fromHighlight);
                keypad2.setStyle(fromHighlight);
                keypad3.setStyle(fromHighlight);
                keypad4.setStyle(fromHighlight);
                keypad5.setStyle(fromHighlight);
                keypad6.setStyle(fromHighlight);
            } break;
            case 0: {
                keypad1.setStyle(toHighlight);
                keypad2.setStyle(fromHighlight);
                keypad3.setStyle(fromHighlight);
                keypad4.setStyle(fromHighlight);
                keypad5.setStyle(fromHighlight);
                keypad6.setStyle(fromHighlight);
            } break;
            case 1: {
                keypad1.setStyle(fromHighlight);
                keypad2.setStyle(toHighlight);
                keypad3.setStyle(fromHighlight);
                keypad4.setStyle(fromHighlight);
                keypad5.setStyle(fromHighlight);
                keypad6.setStyle(fromHighlight);
            } break;
            case 2: {
                keypad1.setStyle(fromHighlight);
                keypad2.setStyle(fromHighlight);
                keypad3.setStyle(toHighlight);
                keypad4.setStyle(fromHighlight);
                keypad5.setStyle(fromHighlight);
                keypad6.setStyle(fromHighlight);
            } break;
            case 3: {
                keypad1.setStyle(fromHighlight);
                keypad2.setStyle(fromHighlight);
                keypad3.setStyle(fromHighlight);
                keypad4.setStyle(toHighlight);
                keypad5.setStyle(fromHighlight);
                keypad6.setStyle(fromHighlight);
            } break;
            case 4: {
                keypad1.setStyle(fromHighlight);
                keypad2.setStyle(fromHighlight);
                keypad3.setStyle(fromHighlight);
                keypad4.setStyle(fromHighlight);
                keypad5.setStyle(toHighlight);
                keypad6.setStyle(fromHighlight);
            } break;
            default: {
                keypad1.setStyle(fromHighlight);
                keypad2.setStyle(fromHighlight);
                keypad3.setStyle(fromHighlight);
                keypad4.setStyle(fromHighlight);
                keypad5.setStyle(fromHighlight);
                keypad6.setStyle(toHighlight);
            }
        }
    }

    @Override
    public void reset() {
        highlightBox(-1);
    }
}
