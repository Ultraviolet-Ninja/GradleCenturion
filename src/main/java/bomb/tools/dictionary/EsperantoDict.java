package bomb.tools.dictionary;

import java.util.HashMap;

public class EsperantoDict extends Dictionary{
    public EsperantoDict(){
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Vent gaso? - Jes";
        no = "Eksplodi - Ne";
        fancyChars = "ĉ ĝ ĥ ĵ ŝ ŭ";
        buttonLabels = new String[]{"Ruĝa", "Blua", "Flava", "Blanka", "Puŝu", "Tenu", "Eksplodigu", "Haltu"};
        moduleLabels = new String[]{"Butono", "Kiel vi Nomiĝas", "Morse-Kodo", "Pasvortoj", ""};
        passwords = new String[]{
                "afero", "aliel", "aliom", "ambaŭ", "antaŭ",
                "astro", "bombo", "daŭre", "domoj", "dumil",
                "iliaj", "inter", "karoo", "lampo", "larĝa",
                "lavas", "legas", "lerni", "malek", "neniu",
                "okdek", "pagas", "paŭzo", "pensi", "pinto",
                "plaĉe", "povas", "revas", "studi", "super",
                "ŝerco", "trans", "trefo", "turni", "unuan"};
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
        frequencies.put("kontraŭ", 3.505);
        frequencies.put("raŭka", 3.515);
        frequencies.put("ŝtono", 3.522);
        frequencies.put("ĵetono", 3.532);
        frequencies.put("risko", 3.535);
        frequencies.put("barako", 3.542);
        frequencies.put("rompi", 3.545);
        frequencies.put("piroj", 3.552);
        frequencies.put("butono", 3.555);
        frequencies.put("bombo", 3.565);
        frequencies.put("batalo", 3.572);
        frequencies.put("betono", 3.575);
        frequencies.put("radaro", 3.582);
        frequencies.put("regulo", 3.592);
        frequencies.put("brilas", 3.595);
        frequencies.put("pulsi", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("PRETA", "JES, ATENDU, KIO, EN ORDO, DEKSTRA, NENIU, MEZA, NENIO, PRETA");
        stepTwoMap.put("UNUA", " DEKSTRA, ATENDU, JES, EN ORDO, NE, MEZA, NENION, EEE, " +
                "PUŜU, PRETA, NENIO, KIO, NENIU, UNUA");
        stepTwoMap.put("NE", "NENIO, EEE, PUŜU, UNUA, KIO, PRETA, MEZA, JES, NENION, DEKSTRA, NENIU, ATENDU, NE");
        stepTwoMap.put("NENIO", "PUŜU, MEZA, ATENDU, EN ORDO, NENIO");
        stepTwoMap.put("NENION", "EEE, MEZA, ATENDU, EN ORDO, JES, NENIO, NE, NENIU, DEKSTRA, KIO, PUŜU, UNUA, NENION");
        stepTwoMap.put("JES", "ATENDU, MEZA, EEE, EN ORDO, UNUA, KIO, NENIU, PRETA, NENION, JES");
        stepTwoMap.put("KIO", "EEE, KIO");
        stepTwoMap.put("EEE", "PRETA, NENION, DEKSTRA, KIO, ATENDU, JES, MEZA, NE, NENIU, NENIO, EEE");
        stepTwoMap.put("DEKSTRA", "MEZA, DEKSTRA");
        stepTwoMap.put("MEZA", "JES, NENION, PRETA, NENIU, NE, PUŜU, KIO, MEZA");
        stepTwoMap.put("EN ORDO", "NENIO, PRETA, ATENDU, KIO, NENION, NENIU, NE, PUŜU, DEKSTRA, EN ORDO");
        stepTwoMap.put("ATENDU", "EN ORDO, NE, UNUA, JES, EEE, NENION, PUŜU, ATENDU");
        stepTwoMap.put("PUŜU", "EEE, NE, NENIO, ATENDU, JES, DEKSTRA, UNUA, NENIU, KIO, PUŜU");
        stepTwoMap.put("NENIU", "MEZA, EN ORDO, JES, PRETA, NENIU");
        stepTwoMap.put("NENIAN", "SEKVA, NENIAM, ĜUSTE, D, KIO?, DO DO, DD, FINITA, DUFOJE, NENIAN");
        stepTwoMap.put("NENIAM", "ĜUSTE, KIO?, ATENTU, DO DO, DUFOJE, PRAVE, DU, FINITA, NENIAN, DO, D, SEKVA, DD, NENIAM");
        stepTwoMap.put("ĜUSTE", "DU, NENIAM, DO DO, ĜUSTE");
        stepTwoMap.put("D", "NENIAN, D");
        stepTwoMap.put("DD", "PRAVE, DO, DD");
        stepTwoMap.put("DO", "DO DO, SEKVA, KIO?, DUFOJE, D, DD, DU, PRAVE, DO");
        stepTwoMap.put("DO DO", "DO DO");
        stepTwoMap.put("DU", "DD, DO, NENIAM, D, KIO?, DU");
        stepTwoMap.put("DUFOJE", "NENIAN, FINITA, D, ĜUSTE, DO, PRAVE, DU, ATENTU, NENIAM, DO DO, DD, KIO?, DUFOJE");
        stepTwoMap.put("PRAVE", "SEKVA, DO DO, KIO?, DUFOJE, ĜUSTE, DD, D, FINITA, ATENTU, NENIAN, DO, NENIAM, DU, PRAVE\n");
        stepTwoMap.put("KIO?", "DUFOJE, DO DO, DU, ĜUSTE, FINITA, SEKVA, KIO?");
        stepTwoMap.put("FINITA", "NENIAM, DO, PRAVE, DU, NENIAN, DD, SEKVA, DUFOJE, D, KIO?, FINITA");
        stepTwoMap.put("SEKVA", "NENIAM, PRAVE, ATENTU, D, NENIAN, FINITA, DO DO, DD, SEKVA");
        stepTwoMap.put("ATENTU", "D, KIO?, DO, DD, FINITA, PRAVE, DU, DUFOJE, DO DO, NENIAN, ATENTU");
    }
}
