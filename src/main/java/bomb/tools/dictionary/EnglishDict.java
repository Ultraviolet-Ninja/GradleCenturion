package bomb.tools.dictionary;

import java.util.HashMap;

public class EnglishDict extends Dictionary{
    public EnglishDict(){
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Vent Gas? - Yes";
        no = "Detonate - No";
        fancyChars = "";
        buttonLabels = new String[]{"Red", "Blue", "Yellow", "White", "Hold", "Press", "Detonate", "Abort"};
        moduleLabels = new String[]{"", "", "", "", ""};
        passwords = new String[]{
                "about", "after", "again", "below", "could",
                "every", "first", "found", "great", "house",
                "large", "learn", "never", "other", "place",
                "plant", "point", "right", "small", "sound",
                "spell", "still", "study", "their", "there",
                "these","thing", "think", "three", "water",
                "where", "which", "world", "would", "write"};
    }

    @Override
    public String predictWord(String part, boolean isMorse) {
        if (!isMorse) {
            switch (part.toLowerCase()) {
                case "n": return"no";
                case "w": case "wa": case "wai": case "wt": case "wat": case "wit": return"wait";
                case "redy": case "rdy": case "ry": return"ready";
                case "b": case "blk": case "blnk": case "bnk": case "bl": case "bla": case "baln": case "balnk": case "blan": return"blank";
                case "p": case "pr": case "pre": case "ss": case "pres": case "prss": case "ress": return"press";
                case "rig": case "righ": case "ryt": case "rye": return"right";
                case "mid": case "mi": case "midd": case "middl": return"middle";
                case "nex": case "ne": case "xt": return"next";
                case "s": case "su": case "ure": return"sure";
                case "e": case "ty": case "em": case "emp": case "ep": case "ept": return"empty";
                case "l": return"led";
                case "le": case "lee": return"leed";
                case "la": case "lea": return"lead";
                case "li": case "ike": case "lik": case "lke": return"like";
                case "r": case "rd": return"red";
                case "ree": case "re": return"reed";
                case "rad": case "rea": case "ra": return"read";
                case "ho": case "hol": return"hold";
                case "holdn": case "holdon": case "hod": case "hoon": case "holon": case "holn": return"hold on";
                case "d": case "di": case "dis": case "disp": case "displ": case "displa": return"display";
                case "do": case "don": case "dne": case "one": return"done";
                case "uh": case "uh u": case "uhu": case "uhuh": return"uh uh";
                case "yre": case "y're": case "y'r": case "yr'": case "yu're": case "youre": case "yur'": case "yor'": case "yor'e": case "yur'e": return"you're";
                case "uh h": case "uh ": case "uhh": case "uhhuh": return"uh huh";
                case "yur": case "yure": case "youare": case "you ": case "you a": case "you ar": case "youa": case "youar": case "yare": case "ya": case "yar": case "yoare": case
                        "yuare": case "yoa": case "yoar": case "yuar": case "yua": return"you are";
                case "q": case "?": case "wq": case "whatq": return"what?";
                case "tyre": case "th're": case "ty're": case "t're": case "thyr": case "thyre": case "theyr": case "theyre": case "eyre": return"they're";
                case "tyare": case "eyare": case "they ar": case "they re": case "they r": case "theya": case "theyar": case "theyare": case
                        "thya": case "thy": return"they are";
                case "wht": case "wh": case "wnq": return"what";
                case "ye": case "es": return"yes";
                case "yor": case "our": case "yr": return"your";
                case "y": case "yo": case "yu": return"you";
                case "ce": return"cee";
                case "ok": case "oka": case "oak": case "oaky": return"okay";
                case "sa": case "say": case "sys": return"says";
                case "f": case "fir": case "firs": case "fi": case "1st": return"first";
                case "not": case "noth": return"nothing";
                case "ere": return"there";
                case "eir": case "ir": return"their";
                case "lef": case "lft": return"left";
                default: return part;
            }
        }
        switch (part) {
            case "sh": case "she": case "shel": return "shell";
            case "h" : case"ha": case "hal": case "hall": return"halls";
            case "sl": case "sli": case "slic": return"slick";
            case "t": case "tr": case "tri": case "tric": return"trick";
            case "box": case "boxe": return"boxes";
            case "l": case "le": case "lea": case "leak": return"leaks";
            case "str": case "stro": case "strob": case "strb": return"strobe";
            case "f": case "fl": case "fli": case "flic": return"flick";
            case "bom": case "bomb": return"bombs";
            case "bre": case "brea": case "break": case "reak": return"breaks";
            case "bri": case "bric": case "brick": case "icks": return"bricks";
            case "ste": case "stea": return"steak";
            case "st": case "sti": case "stin": return"sting";
            case "v": case "ve": case "vec": case "vect": case "vecto": return"vector";
            case "be": case "bea": case "beat": case "eats": case "ats": return "beats";
            default: return part;
        }
    }

    @Override
    protected void initFreqs() {
        frequencies.put("shell", 3.505);
        frequencies.put("halls", 3.515);
        frequencies.put("slick", 3.522);
        frequencies.put("trick", 3.532);
        frequencies.put("boxes", 3.535);
        frequencies.put("leaks", 3.542);
        frequencies.put("strobe", 3.545);
        frequencies.put("bistro", 3.552);
        frequencies.put("flick", 3.555);
        frequencies.put("bombs", 3.565);
        frequencies.put("break", 3.572);
        frequencies.put("brick", 3.575);
        frequencies.put("steak", 3.582);
        frequencies.put("sting", 3.592);
        frequencies.put("vector", 3.595);
        frequencies.put("beats", 3.6);
}

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("ready", "YES, OKAY, WHAT, MIDDLE, LEFT, PRESS, RIGHT, BLANK, READY");
        stepTwoMap.put("first", "LEFT, OKAY, YES, MIDDLE, NO, RIGHT, NOTHING, UHHH, WAIT, READY, BLANK, WHAT, PRESS, FIRST");
        stepTwoMap.put("no", "BLANK, UHHH, WAIT, FIRST, WHAT, READY, RIGHT, YES, NOTHING, LEFT, PRESS, OKAY, NO");
        stepTwoMap.put("blank", "WAIT, RIGHT, OKAY, MIDDLE, BLANK");
        stepTwoMap.put("nothing", "UHHH, RIGHT, OKAY, MIDDLE, YES, BLANK, NO, PRESS, LEFT, WHAT, WAIT, FIRST, NOTHING");
        stepTwoMap.put("yes", "OKAY, RIGHT, UHHH, MIDDLE, FIRST, WHAT, PRESS, READY, NOTHING, YES");
        stepTwoMap.put("what", "UHHH, WHAT");
        stepTwoMap.put("uhhh", "READY, NOTHING, LEFT, WHAT, OKAY, YES, RIGHT, NO, PRESS, BLANK, UHHH");
        stepTwoMap.put("left", "RIGHT, LEFT");
        stepTwoMap.put("right", "YES, NOTHING, READY, PRESS, NO, WAIT, WHAT, RIGHT");
        stepTwoMap.put("middle", "BLANK, READY, OKAY, WHAT, NOTHING, PRESS, NO, WAIT, LEFT, MIDDLE");
        stepTwoMap.put("okay", "MIDDLE, NO, FIRST, YES, UHHH, NOTHING, WAIT, OKAY");
        stepTwoMap.put("wait", "UHHH, NO, BLANK, OKAY, YES, LEFT, FIRST, PRESS, WHAT, WAIT");
        stepTwoMap.put("press", "RIGHT, MIDDLE, YES, READY, PRESS");
        stepTwoMap.put("you", "SURE, YOU ARE, YOUR, YOU'RE, NEXT, UH HUH, UR, HOLD, WHAT?, YOU");
        stepTwoMap.put("you are", "YOUR, NEXT, LIKE, UH HUH, WHAT?, DONE, UH UH, HOLD, YOU, U, YOU'RE, SURE, UR, YOU ARE");
        stepTwoMap.put("your", "UH UH, YOU ARE, UH HUH, YOUR");
        stepTwoMap.put("you're", "YOU, YOU'RE");
        stepTwoMap.put("ur", "DONE, U, UR");
        stepTwoMap.put("u", "UH HUH, SURE, NEXT, WHAT?, YOU'RE, UR, UH UH, DONE, U");
        stepTwoMap.put("uh huh", "UH HUH");
        stepTwoMap.put("uh uh", "UR, U, YOU ARE, YOU'RE, NEXT, UH UH");
        stepTwoMap.put("what?", "YOU, HOLD, YOU'RE, YOUR, U, DONE, UH UH, LIKE, YOU ARE, UH HUH, UR, NEXT, WHAT?");
        stepTwoMap.put("done", "SURE, UH HUH, NEXT, WHAT?, YOUR, UR, YOU'RE, HOLD, LIKE, YOU, U, YOU ARE, UH UH, DONE");
        stepTwoMap.put("next", "WHAT?, UH HUH, UH UH, YOUR, HOLD, SURE, NEXT");
        stepTwoMap.put("hold", "YOU ARE, U, DONE, UH UH, YOU, UR, SURE, WHAT?, YOU'RE, NEXT, HOLD");
        stepTwoMap.put("sure", "YOU ARE, DONE, LIKE, YOU'RE, YOU, HOLD, UH HUH, UR, SURE");
        stepTwoMap.put("like", "YOU'RE, NEXT, U, UR, HOLD, DONE, UH UH, WHAT?, UH HUH, YOU, LIKE");
    }
}
