package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

public class DutchDict extends Dictionary {
    public DutchDict() {
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Gas ontluchten? - Ja(Yes)";
        no = "Ontploffen? - Nee(No)";
        fancyChars = "á é í ó ú à è ë ï ö ü";
        buttonLabels = new String[]{"Rood", "Blauw", "Geel", "Wit", "Vasthouden", "Ingedrukt", "Ontsteken", "Afbreken"};
        moduleLabels = new String[]{"De Knop", "", "Morse-codering", "Wachtwoorden", "Over afreageren"};
        passwords = new String[]{
                "ander", "boven", "draad", "eerst", "groot",
                "ieder", "klank", "klant", "klein", "kling",
                "klink", "klopt", "knalt", "kruid", "kruit",
                "links", "naast", "negen", "niets", "nogal",
                "onder", "plant", "ploft", "speld", "spelt",
                "stuur", "tekst", "wacht", "water", "welke",
                "zendt", "zenuw", "zeven", "zweed", "zweet"};
    }

    @Override
    public String predictWord(String part, boolean isMorse) {
        if (!isMorse) {
            switch (part.toLowerCase()) {
                default:
                    return part;
            }
        }
        switch (part.toLowerCase()) {

            default:
                return part;
        }
    }

    @Override
    protected void initFreqs() {
        frequencies.put("stuur", 3.505);
        frequencies.put("buurt", 3.515);
        frequencies.put("truus", 3.522);
        frequencies.put("uurtje", 3.532);
        frequencies.put("trucs", 3.535);
        frequencies.put("broek", 3.542);
        frequencies.put("bruin", 3.545);
        frequencies.put("bruis", 3.552);
        frequencies.put("bruist", 3.555);
        frequencies.put("ruist", 3.565);
        frequencies.put("ruiste", 3.572);
        frequencies.put("suist", 3.575);
        frequencies.put("suiste", 3.582);
        frequencies.put("rijst", 3.592);
        frequencies.put("prijs", 3.595);
        frequencies.put("prijst", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("BOVEN", "HAUWT, IK ZEG, HOU, HOUWT, HOUSE, KLAAR, HOUT, EERSTE, BOVEN");
        stepTwoMap.put("DRUK", "HOUSE, IK ZEG, HAUWT, HOUWT, ECHT, HOUT, HAUSSE, HOUD, JA, BOVEN, EERSTE, HOU, KLAAR, DRUK");
        stepTwoMap.put("ECHT", "EERSTE, HOUD, JA, DRUK, HOU, BOVEN, HOUT, HAUWT, HAUSSE, HOUSE, KLAAR, IK ZEG, ECHT");
        stepTwoMap.put("EERSTE", "JA, HOUT, IK ZEG, HOUWT, EERSTE");
        stepTwoMap.put("HAUSSE", "HOUD, HOUT, IK ZEG, HOUWT, HAUWT, EERSTE, ECHT, KLAAR, HOUSE, HOU, JA, DRUK, HAUSSE");
        stepTwoMap.put("HAUWT", "IK ZEG, HOUT, HOUD, HOUWT, DRUK, HOU, KLAAR, BOVEN, HAUSSE, HAUWT");
        stepTwoMap.put("HOU", "HOUD, HOU");
        stepTwoMap.put("HOUD", "BOVEN, HAUSSE, HOUSE, HOU, IK ZEG, HAUWT, HOUT, ECHT, KLAAR, EERSTE, HOUD");
        stepTwoMap.put("HOUSE", "HOUT, HOUSE");
        stepTwoMap.put("HOUT", "HAUWT, HAUSSE, BOVEN, KLAAR, ECHT, JA, HOU, HOUT");
        stepTwoMap.put("HOUWT", "EERSTE, BOVEN, IK ZEG, HOU, HAUSSE, KLAAR, ECHT, JA, HOUSE, HOUWT");
        stepTwoMap.put("IK ZEG", "HOUWT, ECHT, DRUK, HAUWT, HOUD, HAUSSE, JA, IK ZEG");
        stepTwoMap.put("JA", "HOUD, ECHT, EERSTE, IK ZEG, HAUWT, HOUSE, DRUK, KLAAR, HOU, JA");
        stepTwoMap.put("KLAAR", "HOUT, HOUWT, HAUWT, BOVEN, KLAAR");
        stepTwoMap.put("LEEG", "WAT, LINKS, MIDDEN, NAAST, EH, NU, NEE, WACHT, ONDER, LEEg");
        stepTwoMap.put("LINKS", "MIDDEN, EH, WAT?, NU, ONDER, RECHTS, OK, WACHT, LEEG, NIETS, NAAST, WAT, NEE, LINKS");
        stepTwoMap.put("MIDDEN", "OK, LINKS, NU, MIDDEN");
        stepTwoMap.put("NAAST", "LEEG, NAAST");
        stepTwoMap.put("NEE", "RECHTS, NIETS, NEE");
        stepTwoMap.put("NIETS", "NU, WAT, EH, ONDER, NAAST, NEE, OK, RECHTS, NIETS");
        stepTwoMap.put("NU", "NU");
        stepTwoMap.put("OK", "NEE, NIETS, LINKS, NAAST, EH, OK");
        stepTwoMap.put("ONDER", "LEEG, WACHT, NAAST, MIDDEN, NIETS, RECHTS, OK, WAT?, LINKS, NU, NEE, EH, ONDER");
        stepTwoMap.put("RECHTS", "WAT, NU, EH, ONDER, MIDDEN, NEE, NAAST, WACHT, WAT?, LEEG, NIETS, LINKS, OK, RECHTS");
        stepTwoMap.put("EH", "ONDER, NU, OK, MIDDEN, WACHT, WAT, E");
        stepTwoMap.put("WACHT", "LINKS, NIETS, RECHTS, OK, LEEG, NEE, WAT, ONDER, NAAST, EH, WACHT");
        stepTwoMap.put("WAT", "LINKS, RECHTS, WAT?, NAAST, LEEG, WACHT, NU, NEE, WAT");
        stepTwoMap.put("WAT?", "NAAST, EH, NIETS, NEE, WACHT, RECHTS, OK, ONDER, NU, LEEG, WAT?");
    }
}
