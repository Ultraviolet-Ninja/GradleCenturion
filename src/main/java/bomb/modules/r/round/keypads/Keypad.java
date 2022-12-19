package bomb.modules.r.round.keypads;

import bomb.abstractions.Flaggable;
import javafx.scene.image.Image;

public enum Keypad implements Flaggable {
    RACKET1, A_T,
    STROKED_LAMBDA1, HARRY_POTTER,
    RUSSIAN_CAT1, CURVY_H1,
    BACK_C1,

    REVERSE_EURO1, RACKET2,
    BACK_C2, DISNEY_Q1,
    HOLLOW_STAR1, CURVY_H2,
    SPANISH_QUESTION1,

    COPYRIGHT, THE_SAC,
    DISNEY_Q2, RUSSIAN_X1,
    NOT_THREE, STROKED_LAMBDA2,
    HOLLOW_STAR2,

    RUSSIAN_SIX1, PARAGRAPH1,
    TB_1, RUSSIAN_CAT2,
    RUSSIAN_X2, SPANISH_QUESTION2,
    SMILEY1,

    PSI1, SMILEY2, TB_2,
    C_DOT, PARAGRAPH2,
    ALIEN_THREE, STAR,

    RUSSIAN_SIX2, REVERSE_EURO2,
    PUZZLE, AE, PSI2,
    RUSSIAN_NH, OMEGA;

    static final Keypad[] KEYPAD_ARRAY;

    static {
        KEYPAD_ARRAY = values();
    }

    private boolean flag;
    private Image memory = null;

    public Image getMemory() {
        return memory;
    }

    public void setMemoryIfNull(Image memory) {
        if (this.memory == null)
            this.memory = memory;
    }

    Keypad() {
        this.flag = false;
    }

    @Override
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean getFlag() {
        return flag;
    }
}
