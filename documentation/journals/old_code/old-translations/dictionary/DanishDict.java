package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

public class DanishDict extends Dictionary {
    public DanishDict() {
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Udluftningsgas? - Yes";
        no = "Detonere? - No";
        fancyChars = "å æ ø";
        moduleLabels = new String[]{"Knappen", "Hvem er på første", "Morsekode", "Kodeord", "Udluftning af gas"};
        buttonLabels = new String[]{"Rød", "blå", "Gul", "Hvid", "Trykke", "Hold", "Spræng", "Afbryd"};
        passwords = new String[]{
                "andre", "deres", "dreng", "efter", "endnu",
                "finde", "først", "grine", "hende", "hjælp",
                "hvert", "ingen", "koste", "kunne", "kunst",
                "kæmpe", "ligne", "lille", "lærer", "minut",
                "nogle", "onkel", "point", "siden", "sidst",
                "skole", "snyde", "stave", "store", "tekst",
                "tænke", "under", "viden", "ville", "vinde"};
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
        frequencies.put("afvis", 3.505);
        frequencies.put("blandt", 3.515);
        frequencies.put("bomber", 3.522);
        frequencies.put("brugt", 3.532);
        frequencies.put("firma", 3.535);
        frequencies.put("kalder", 3.542);
        frequencies.put("klart", 3.545);
        frequencies.put("klasse", 3.552);
        frequencies.put("klippe", 3.555);
        frequencies.put("rekord", 3.565);
        frequencies.put("sidst", 3.572);
        frequencies.put("skarp", 3.575);
        frequencies.put("skifte", 3.582);
        frequencies.put("skole", 3.592);
        frequencies.put("slippe", 3.595);
        frequencies.put("vinder", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("VENSTRE", "HVAD, NEJ, HVAD?, JA, VENT, C, OKAY, TRYK, VENSTRE");
        stepTwoMap.put("HØJRE", "VENT, NEJ, HVAD, JA, MIDTEN, OKAY, FÆRDIG, NÆSTE, ØJEBLIK, VENSTRE, TRYK, HVAD?, C, HØJRE");
        stepTwoMap.put("MIDTEN", "TRYK, NÆSTE, ØJEBLIK, HØJRE, HVAD?, VENSTRE, OKAY, HVAD, FÆRDIG, VENT, C, NEJ, MIDTEN");
        stepTwoMap.put("TRYK", "ØJEBLIK, OKAY, NEJ, JA, TRYK");
        stepTwoMap.put("FÆRDIG", "NÆSTE, OKAY, NEJ, JA, HVAD, TRYK, MIDTEN, C, VENT, HVAD?, ØJEBLIK, HØJRE, FÆRDIG");
        stepTwoMap.put("HVAD", "NEJ, OKAY, NÆSTE, JA, HØJRE, HVAD?, C, VENSTRE, FÆRDIG, HVAD");
        stepTwoMap.put("HVAD?", "NÆSTE, HVAD?");
        stepTwoMap.put("NÆSTE", "VENSTRE, FÆRDIG, VENT, HVAD?, NEJ, HVAD, OKAY, MIDTEN, C, TRYK, NÆSTE");
        stepTwoMap.put("VENT", "OKAY, VENT");
        stepTwoMap.put("OKAY", "HVAD, FÆRDIG, VENSTRE, C, MIDTEN, ØJEBLIK, HVAD?, OKAY");
        stepTwoMap.put("JA", "TRYK, VENSTRE, NEJ, HVAD?, FÆRDIG, C, MIDTEN, ØJEBLIK, VENT, JA");
        stepTwoMap.put("NEJ", "JA, MIDTEN, HØJRE, HVAD, NÆSTE, FÆRDIG, ØJEBLIK, NEJ");
        stepTwoMap.put("ØJEBLIK", "NÆSTE, MIDTEN, TRYK, NEJ, HVAD, VENT, HØJRE, C, HVAD?, ØJEBLIK");
        stepTwoMap.put("C", "OKAY, JA, HVAD, VENSTRE, C");
        stepTwoMap.put("SE", "NÅRH, TRYG, VÆR, VÆRD, NÅR, ØH, VEJR, NÅH, IGEN, SE");
        stepTwoMap.put("TRYG", "VÆR, NÅR, NÅ, ØH, IGEN, GENTAG, ØHHH, NÅH, SE, HVER, VÆRD, NÅRH, VEJR, TRYG");
        stepTwoMap.put("VÆR", "ØHHH, TRYG, ØH, VÆR");
        stepTwoMap.put("VÆRD", "SE, VÆRD");
        stepTwoMap.put("VEJR", "GENTAG, HVER, VEJR");
        stepTwoMap.put("HVER", "ØH, NÅRH, NÅR, IGEN, VÆRD, VEJR, ØHHH, GENTAG, HVER");
        stepTwoMap.put("ØH", "ØH");
        stepTwoMap.put("ØHHH", "VEJR, HVER, TRYG, VÆRD, NÅR, ØHHH");
        stepTwoMap.put("IGEN", "SE, NÅH, VÆRD, VÆR, HVER, GENTAG, ØHHH, NÅ, TRYG, ØH, VEJR, NÅR, IGEN");
        stepTwoMap.put("GENTAG", "NÅRH, ØH, NÅR, IGEN, VÆR, VEJR, VÆRD, NÅH, NÅ, SE, HVER, TRYG, ØHHH, GENTAG\n");
        stepTwoMap.put("NÅR", "IGEN, ØH, ØHHH, VÆR, NÅH, NÅRH, NÅR");
        stepTwoMap.put("NÅH", "TRYG, HVER, GENTAG, ØHHH, SE, VEJR, NÅRH, IGEN, VÆRD, NÅR, NÅH");
        stepTwoMap.put("NÅRH", "TRYG, GENTAG, NÅ, VÆRD, SE, NÅH, ØH, VEJR, NÅRH");
        stepTwoMap.put("NÅ", "VÆRD, NÅR, HVER, VEJR, NÅH, GENTAG, ØHHH, IGEN, ØH, SE, NÅ");
    }
}
