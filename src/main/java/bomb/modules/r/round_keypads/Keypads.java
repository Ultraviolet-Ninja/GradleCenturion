package bomb.modules.r.round_keypads;

import bomb.interfaces.Flaggable;
import bomb.interfaces.Labeled;
import javafx.scene.image.Image;

public enum Keypads implements Labeled, Flaggable {
    RACKET1("Empty Racket", false), A_T("A_T", false),
    STROKED_LAMBDA1("Lambda", false), HARRY_POTTER("Harry Potter", false),
    RUSSIAN_CAT1("Russian Cat", false), CURVY_H1("Curvy H", false),
    BACK_C1("BackwardsC", false),

    REVERSE_EURO1("Reverse Euro", false), RACKET2("Empty Racket", false),
    BACK_C2("BackwardsC", false), DISNEY_Q1("Disney Q", false),
    HOLLOW_STAR1("Hollow Star", false), CURVY_H2("Curvy H", false),
    SPANISH_QUESTION1("Spanish Question", false),

    COPYRIGHT("Copyright", false), THE_SAC("Sac", false),
    DISNEY_Q2("Disney Q", false), RUSSIAN_X1("Russian X", false),
    NOT_THREE("Not3", false), STROKED_LAMBDA2("Lambda", false),
    HOLLOW_STAR2("Hollow Star", false),

    RUSSIAN_SIX1("Russian 6", false),PARAGRAPH1("Paragraph", false),
    TB_1("Tb", false), RUSSIAN_CAT2("Russian Cat", false),
    RUSSIAN_X2("Russian X", false), SPANISH_QUESTION2("Spanish Question", false),
    SMILEY1("Smily", false),

    PSI1("Psi", false),SMILEY2("Smily", false), TB_2("Tb", false),
    C_DOT("C", false), PARAGRAPH2("Paragraph", false),
    ALIEN_THREE("Alien 3", false), STAR("Star", false),

    RUSSIAN_SIX2("Russian 6", false), REVERSE_EURO2("Reverse Euro", false),
    PUZZLE("Puzzle", false), AE("ae", false), PSI2("Psi", false),
    RUSSIAN_NH("Russian NH", false), OMEGA("Omega", false);

    private final String imageLocation;
    private boolean flag;
    private Image memory = null;

    public Image getMemory() {
        return memory;
    }

    public void setMemoryIfNull(Image memory) {
        if (this.memory == null)
            this.memory = memory;
    }

    Keypads(String label, boolean flag){
        this.flag = flag;
        imageLocation = label;
    }

    @Override
    public String getLabel() {
        return  imageLocation + ".PNG";
    }

    @Override
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean getFlag() {
        return !flag;
    }
}
