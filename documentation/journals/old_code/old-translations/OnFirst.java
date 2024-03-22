/*
 * Author: Ultraviolet-Ninja
 * Project: Bomb Defusal Manual for Keep Talking and Nobody Explodes [Vanilla]
 * Section: Who's On First
 */

package bomb.modules.t.translated;

import bomb.enumerations.WhosOnFirstPictures;
import bomb.enumerations.WhosOnFirstWords;
import javafx.scene.image.Image;

/**
 * OnFirst class refers to the Who's on First module.
 */
@Deprecated
public final class OnFirst extends TranslationCenter {

    /**
     * findPanel() finds the correct panel for Step 1 of the module
     *
     * @param text - the partial word
     * @return - the correct Step 1 Panel
     */
    public static Image findPanel(String text) {
        text = format(text);
        for (WhosOnFirstPictures pic : WhosOnFirstPictures.values()) {
            if (pic.getLabel().replace("file:src\\Step1_Panels\\", "")
                    .replace(".PNG", "").equalsIgnoreCase(text)) {
                return new Image(pic.getLabel());
            }
        }
        return null;
    }

    /**
     * retList() returns the list words associated with given keyword found by the Step 1 Panel
     *
     * @param keyword - the button based on the Step 1 panel
     * @return - the word list associated with the keyword
     */
    public static String[] retList(String keyword) {
        keyword = format(keyword);
        for (WhosOnFirstWords word : WhosOnFirstWords.values()) {
            if (word.getLabel().equalsIgnoreCase(keyword)) {
                return word.getWords();
            }
        }

        return null;
    }

    private static String format(String initial) {
        //TODO - Rid the duplicate code
        switch (initial.toLowerCase()) {
            case "n":
                return "no";
            case "w":
            case "wa":
            case "wai":
            case "wt":
            case "wat":
            case "wit":
                return "wait";
            case "redy":
            case "rdy":
            case "ry":
                return "ready";
            case "b":
            case "blk":
            case "blnk":
            case "bnk":
            case "bl":
            case "bla":
            case "baln":
            case "balnk":
            case "blan":
                return "blank";
            case "p":
            case "pr":
            case "pre":
            case "ss":
            case "pres":
            case "prss":
            case "ress":
                return "press";
            case "rig":
            case "righ":
            case "ryt":
            case "rye":
                return "right";
            case "mid":
            case "mi":
            case "midd":
            case "middl":
                return "middle";
            case "nex":
            case "ne":
            case "xt":
                return "next";
            case "s":
            case "su":
            case "ure":
                return "sure";
            case "e":
            case "ty":
            case "em":
            case "emp":
            case "ep":
            case "ept":
                return "empty";
            case "l":
                return "led";
            case "le":
            case "lee":
                return "leed";
            case "la":
            case "lea":
                return "lead";
            case "li":
            case "ike":
            case "lik":
            case "lke":
                return "like";
            case "r":
            case "rd":
                return "red";
            case "ree":
            case "re":
                return "reed";
            case "rad":
            case "rea":
            case "ra":
                return "read";
            case "ho":
            case "hol":
                return "hold";
            case "holdn":
            case "holdon":
            case "hod":
            case "hoon":
            case "holon":
            case "holn":
                return "hold on";
            case "d":
            case "di":
            case "dis":
            case "disp":
            case "displ":
            case "displa":
                return "display";
            case "do":
            case "don":
            case "dne":
            case "one":
                return "done";
            case "uh":
            case "uh u":
            case "uhu":
            case "uhuh":
                return "uh uh";
            case "yre":
            case "y're":
            case "y'r":
            case "yr'":
            case "yu're":
            case "youre":
            case "yur'":
            case "yor'":
            case "yor'e":
            case "yur'e":
                return "you're";
            case "uh h":
            case "uh ":
            case "uhh":
            case "uhhuh":
                return "uh huh";
            case "yur":
            case "yure":
            case "youare":
            case "you ":
            case "you a":
            case "you ar":
            case "youa":
            case "youar":
            case "yare":
            case "ya":
            case "yar":
            case "yoare":
            case "yuare":
            case "yoa":
            case "yoar":
            case "yuar":
            case "yua":
                return "you are";
            case "q":
            case "?":
            case "wq":
            case "whatq":
                return "what?";
            case "tyre":
            case "th're":
            case "ty're":
            case "t're":
            case "thyr":
            case "thyre":
            case "theyr":
            case "theyre":
            case "eyre":
                return "they're";
            case "tyare":
            case "eyare":
            case "they ar":
            case "they re":
            case "they r":
            case "theya":
            case "theyar":
            case "theyare":
            case "thya":
            case "thy":
                return "they are";
            case "wht":
            case "wh":
            case "wnq":
                return "what";
            case "ye":
            case "es":
                return "yes";
            case "yor":
            case "our":
            case "yr":
                return "your";
            case "y":
            case "yo":
            case "yu":
                return "you";
            case "ce":
                return "cee";
            case "ok":
            case "oka":
            case "oak":
            case "oaky":
                return "okay";
            case "sa":
            case "say":
            case "sys":
                return "says";
            case "f":
            case "fir":
            case "firs":
            case "fi":
            case "1st":
                return "first";
            case "not":
            case "noth":
                return "nothing";
            case "ere":
                return "there";
            case "eir":
            case "ir":
                return "their";
            case "lef":
            case "lft":
                return "left";
            default:
                return initial;
        }
    }
}
