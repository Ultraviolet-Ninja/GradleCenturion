package bomb.modules.r.round.keypads;

import bomb.abstractions.Resettable;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

import static bomb.modules.r.round.keypads.Keypad.AE;
import static bomb.modules.r.round.keypads.Keypad.ALIEN_THREE;
import static bomb.modules.r.round.keypads.Keypad.A_T;
import static bomb.modules.r.round.keypads.Keypad.BACK_C1;
import static bomb.modules.r.round.keypads.Keypad.BACK_C2;
import static bomb.modules.r.round.keypads.Keypad.COPYRIGHT;
import static bomb.modules.r.round.keypads.Keypad.CURVY_H1;
import static bomb.modules.r.round.keypads.Keypad.CURVY_H2;
import static bomb.modules.r.round.keypads.Keypad.C_DOT;
import static bomb.modules.r.round.keypads.Keypad.DISNEY_Q1;
import static bomb.modules.r.round.keypads.Keypad.DISNEY_Q2;
import static bomb.modules.r.round.keypads.Keypad.HARRY_POTTER;
import static bomb.modules.r.round.keypads.Keypad.HOLLOW_STAR1;
import static bomb.modules.r.round.keypads.Keypad.HOLLOW_STAR2;
import static bomb.modules.r.round.keypads.Keypad.KEYPAD_ARRAY;
import static bomb.modules.r.round.keypads.Keypad.NOT_THREE;
import static bomb.modules.r.round.keypads.Keypad.OMEGA;
import static bomb.modules.r.round.keypads.Keypad.PARAGRAPH1;
import static bomb.modules.r.round.keypads.Keypad.PARAGRAPH2;
import static bomb.modules.r.round.keypads.Keypad.PSI1;
import static bomb.modules.r.round.keypads.Keypad.PSI2;
import static bomb.modules.r.round.keypads.Keypad.PUZZLE;
import static bomb.modules.r.round.keypads.Keypad.RACKET1;
import static bomb.modules.r.round.keypads.Keypad.RACKET2;
import static bomb.modules.r.round.keypads.Keypad.REVERSE_EURO1;
import static bomb.modules.r.round.keypads.Keypad.REVERSE_EURO2;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_CAT1;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_CAT2;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_NH;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_SIX1;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_SIX2;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_X1;
import static bomb.modules.r.round.keypads.Keypad.RUSSIAN_X2;
import static bomb.modules.r.round.keypads.Keypad.SMILEY1;
import static bomb.modules.r.round.keypads.Keypad.SMILEY2;
import static bomb.modules.r.round.keypads.Keypad.SPANISH_QUESTION1;
import static bomb.modules.r.round.keypads.Keypad.SPANISH_QUESTION2;
import static bomb.modules.r.round.keypads.Keypad.STAR;
import static bomb.modules.r.round.keypads.Keypad.STROKED_LAMBDA1;
import static bomb.modules.r.round.keypads.Keypad.STROKED_LAMBDA2;
import static bomb.modules.r.round.keypads.Keypad.TB_1;
import static bomb.modules.r.round.keypads.Keypad.TB_2;
import static bomb.modules.r.round.keypads.Keypad.THE_SAC;
import static java.util.Arrays.asList;

public class RoundKeypadsController implements Resettable {
    private static final String BAD_COLUMN_BACKGROUND = "-fx-background-color: crimson",
            NORMAL_BACKGROUND = "-fx-background-color: transparent";

    @FXML
    private ImageView emptyRacket1, aT, lambda1, harryPotter, russianCat1, curvyH1, backC1,
            backEuro1, emptyRacket2, backC2, disneyQ1, hollowStar1, curvyH2, spanishQuestion1,
            copyright, leSac, disneyQ2, russianX1, notThree, lambda2, hollowStar2,
            russianSix1, paragraph1, tB1, russianCat2, russianX2, spanishQuestion2, smile1,
            psi1, smile2, tB2, cDot, paragraph2, shrekThree, star,
            russianSix2, backEuro2, puzzlePiece, aeyyLmao, psi2, russianHN, omega;

    @FXML
    private VBox firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn;

    public void initialize() {
        initializeSingularImages();
        initializePairedImages();
    }

    private void initializeSingularImages() {
        ImageView[] singleViewArray = {aT, harryPotter, copyright, leSac, notThree, cDot, shrekThree, star,
                puzzlePiece, aeyyLmao, omega, russianHN};
        Keypad[] matchingKeypads = {A_T, HARRY_POTTER, COPYRIGHT, THE_SAC, NOT_THREE, C_DOT, ALIEN_THREE,
                STAR, PUZZLE, AE, OMEGA, RUSSIAN_NH};

        for (int i = 0; i < singleViewArray.length; i++) {
            Keypad matchingKey = matchingKeypads[i];
            singleViewArray[i].setOnMouseClicked(event -> {
                ImageView source = ((ImageView) event.getSource());
                source.setImage(
                        RoundKeypads.toggleImageColor(matchingKey, source.getImage())
                );
                highlightBox(RoundKeypads.determineBadColumn());
            });
        }
    }

    private void initializePairedImages() {
        List<ImageView> doubleViewArray = List.of(emptyRacket1, emptyRacket2, lambda1, lambda2, russianCat1,
                russianCat2, curvyH1, curvyH2, backC1, backC2, disneyQ1, disneyQ2, hollowStar1, hollowStar2,
                spanishQuestion1, spanishQuestion2, russianX1, russianX2, paragraph1, paragraph2, tB1,
                tB2, smile1, smile2, psi1, psi2, backEuro1, backEuro2, russianSix1, russianSix2);

        List<Keypad> matchingKeypads = List.of(RACKET1, RACKET2, STROKED_LAMBDA1, STROKED_LAMBDA2,
                RUSSIAN_CAT1, RUSSIAN_CAT2, CURVY_H1, CURVY_H2, BACK_C1, BACK_C2, DISNEY_Q1, DISNEY_Q2,
                HOLLOW_STAR1, HOLLOW_STAR2, SPANISH_QUESTION1, SPANISH_QUESTION2, RUSSIAN_X1, RUSSIAN_X2,
                PARAGRAPH1, PARAGRAPH2, TB_1, TB_2, SMILEY1, SMILEY2, PSI1, PSI2, REVERSE_EURO1,
                REVERSE_EURO2, RUSSIAN_SIX1, RUSSIAN_SIX2);
        int size = doubleViewArray.size();

        for (int i = 0; i < size; i += 2) {
            setDoubleAction(doubleViewArray.get(i), matchingKeypads.get(i),
                    doubleViewArray.get(i+1), matchingKeypads.get(i+1));
        }
    }

    private void setDoubleAction(ImageView firstImage, Keypad firstMatchingKey,
                                 ImageView secondImage, Keypad secondMatchingKey) {
        firstImage.setOnMouseClicked(event -> {
            ImageView source = ((ImageView) event.getSource());
            source.setImage(
                    RoundKeypads.toggleImageColor(firstMatchingKey, source.getImage())
            );
            secondImage.setImage(
                    RoundKeypads.toggleImageColor(secondMatchingKey, secondImage.getImage())
            );
            highlightBox(RoundKeypads.determineBadColumn());
        });

        secondImage.setOnMouseClicked(event -> {
            ImageView source = ((ImageView) event.getSource());
            source.setImage(
                    RoundKeypads.toggleImageColor(secondMatchingKey, source.getImage())
            );
            firstImage.setImage(
                    RoundKeypads.toggleImageColor(firstMatchingKey, firstImage.getImage())
            );
            highlightBox(RoundKeypads.determineBadColumn());
        });
    }

    private void highlightBox(int which) {
        setBoxStyle(firstColumn, which, 0);
        setBoxStyle(secondColumn, which, 1);
        setBoxStyle(thirdColumn, which, 2);
        setBoxStyle(fourthColumn, which, 3);
        setBoxStyle(fifthColumn, which, 4);
        setBoxStyle(sixthColumn, which, 5);
    }

    private void setBoxStyle(VBox column, int which, int required) {
        column.setStyle(which == required ? BAD_COLUMN_BACKGROUND : NORMAL_BACKGROUND);
    }

    @Override
    public void reset() {
        resetColumns();
        resetImageViews();
        RoundKeypads.reset();
    }

    private void resetColumns() {
        firstColumn.setStyle(NORMAL_BACKGROUND);
        secondColumn.setStyle(NORMAL_BACKGROUND);
        thirdColumn.setStyle(NORMAL_BACKGROUND);
        fourthColumn.setStyle(NORMAL_BACKGROUND);
        fifthColumn.setStyle(NORMAL_BACKGROUND);
        sixthColumn.setStyle(NORMAL_BACKGROUND);
    }

    private void resetImageViews() {
        List<ImageView> allViews = List.of(emptyRacket1, aT, lambda1, harryPotter, russianCat1, curvyH1, backC1,
                backEuro1, emptyRacket2, backC2, disneyQ1, hollowStar1, curvyH2, spanishQuestion1,
                copyright, leSac, disneyQ2, russianX1, notThree, lambda2, hollowStar2,
                russianSix1, paragraph1, tB1, russianCat2, russianX2, spanishQuestion2, smile1,
                psi1, smile2, tB2, cDot, paragraph2, shrekThree, star,
                russianSix2, backEuro2, puzzlePiece, aeyyLmao, psi2, russianHN, omega);
        List<Keypad> keypads = asList(KEYPAD_ARRAY);
        int size = keypads.size();

        for (int i = 0; i < size; i++) {
            if (keypads.get(i).getFlag())
                allViews.get(i).setImage(keypads.get(i).getMemory());
            i++;
        }
    }
}
