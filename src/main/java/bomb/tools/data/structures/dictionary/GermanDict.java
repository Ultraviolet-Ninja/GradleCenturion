package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

public class GermanDict extends Dictionary{
    public GermanDict(){
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Gas ablassen? - Ja";
        no = "Zünden? - Nein";
        fancyChars = "ä ö ß ü";
        moduleLabels = new String[]{"Der Knopf", "", "Morsecode", "Passwörter", "Austretende Gase"};
        buttonLabels = new String[]{"Rot", "Blau", "Gelb", "Weiß", "Drücken Sie", "Gedrückt halten", "Zünden", "Abbrechen"};
        passwords = new String[]{
                "angst", "atmen", "beten", "bombe", "danke",
                "draht", "druck", "drück", "farbe", "fehlt",
                "ferse", "kabel", "knall", "knapp", "knopf",
                "leere", "lgeal", "lehre", "mathe", "matte",
                "panik", "pieps", "rauch", "ruhig", "saite",
                "sehne", "seite", "sende", "strom", "super",
                "timer", "übrig", "verse", "warte", "zange"};
    }

    @Override
    public String predictWord(String part, boolean isMorse) {
        if (!isMorse) {
            switch (part.toLowerCase()) {
                default: return part;
            }
        }
        switch (part.toLowerCase()){

            default: return part;
        }
    }

    @Override
    protected void initFreqs() {
        frequencies.put("hölle", 3.505);
        frequencies.put("halle", 3.515);
        frequencies.put("hülle", 3.522);
        frequencies.put("heiß", 3.532);
        frequencies.put("siehe", 3.535);
        frequencies.put("sichel", 3.542);
        frequencies.put("leiche", 3.545);
        frequencies.put("eichel", 3.552);
        frequencies.put("fischer", 3.555);
        frequencies.put("sicher", 3.565);
        frequencies.put("sicht", 3.572);
        frequencies.put("sticht", 3.575);
        frequencies.put("ventil", 3.582);
        frequencies.put("steak", 3.592);
        frequencies.put("brücke", 3.595);
        frequencies.put("rücken", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("OKAY", "NEIN, NICHTS,    , COUP, OBEN, NOCHMAL, Q, KUH, OKAY");
        stepTwoMap.put("JA", "OBEN, NICHTS, NEIN, COUP, FERTIG, Q, DRÜCK, DA STEHT, LEER, OKAY, KUH, , NOCHMAL, JA");
        stepTwoMap.put("FERTIG", "KUH, DA STEHT, LEER, JA, , OKAY, Q, NEIN, DRÜCK, OBEN, NOCHMAL, NICHTS, FERTIG");
        stepTwoMap.put("KUH", "LEER, Q, NICHTS, COUP, KUH");
        stepTwoMap.put("DRÜCK", "DA STEHT, Q, NICHTS, COUP, NEIN, KUH, FERTIG, NOCHMAL, OBEN, , LEER, JA, DRÜCK");
        stepTwoMap.put("NEIN", "NICHTS, Q, DA STEHT, COUP, JA, , NOCHMAL, OKAY, DRÜCK, NEIN");
        stepTwoMap.put("    ", "DA STEHT,    ");
        stepTwoMap.put("DA STEHT", "OKAY, DRÜCK, OBEN, , NICHTS, NEIN, Q, FERTIG, NOCHMAL, KUH, DA STEHT");
        stepTwoMap.put("OBEN", "Q, OBEN");
        stepTwoMap.put("Q", "NEIN, DRÜCK, OKAY, NOCHMAL, FERTIG, LEER,    , Q");
        stepTwoMap.put("COUP", "KUH, OKAY, NICHTS, , DRÜCK, NOCHMAL, FERTIG, LEER, OBEN, COUP");
        stepTwoMap.put("NICHTS", "COUP, FERTIG, JA, NEIN, DA STEHT, DRÜCK, LEER, NICHTS");
        stepTwoMap.put("LEER", "DA STEHT, FERTIG, KUH, NICHTS, NEIN, OBEN, JA, NOCHMAL,    , LEER");
        stepTwoMap.put("NOCHMAL", "Q, COUP, NEIN, OKAY, NOCHMAL");
        stepTwoMap.put("SOHN", "SO'N, ZÄH, ZEHN, CN, MOMENT, SO EIN, CEE, 10, OH GOTT, SOHN");
        stepTwoMap.put("ZÄH", "ZEHN, MOMENT, ZEHEN, SO EIN, OH GOTT, WARTE, C, 10, SOHN, ZEH, CN, SO'N, CEE, ZÄH");
        stepTwoMap.put("ZEHN", "C, ZÄH, SO EIN, ZEHN");
        stepTwoMap.put("CN", "SOHN, CN");
        stepTwoMap.put("CEE", "WARTE, ZEH, CEE");
        stepTwoMap.put("ZEH", "SO EIN, SO'N, MOMENT, OH GOTT, CN, CEE, C, WARTE, ZEH");
        stepTwoMap.put("SO EIN", "SO EIN");
        stepTwoMap.put("C", "CEE, ZEH, ZÄH, CN, MOMENT, C");
        stepTwoMap.put("OH GOTT", "SOHN, 10, CN, ZEHN, ZEH, WARTE, C, ZEHEN, ZÄH, SO EIN, CEE, MOMENT, OH GOTT");
        stepTwoMap.put("WARTE", "SO'N, SO EIN, MOMENT, OH GOTT, ZEHN, CEE, CN, 10, ZEHEN, SOHN, ZEH, ZÄH, C, WARTE");
        stepTwoMap.put("MOMENT", "OH GOTT, SO EIN, C, ZEHN, 10, SO'N, MOMENT");
        stepTwoMap.put("10", "ZÄH, ZEH, WARTE, C, SOHN, CEE, SO'N, OH GOTT, CN, MOMENT, 10");
        stepTwoMap.put("SO'N", "ZÄH, WARTE, ZEHEN, CN, SOHN, 10, SO EIN, CEE, SO'N");
        stepTwoMap.put("ZEHEN", "CN, MOMENT, ZEH, CEE, 10, WARTE, C, OH GOTT, SO EIN, SOHN, ZEHEN");
    }
}
