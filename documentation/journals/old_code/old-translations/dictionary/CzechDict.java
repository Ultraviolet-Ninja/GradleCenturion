package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

public class CzechDict extends Dictionary {
    public CzechDict() {
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "Odvzdušňovací plyn? - Ano";
        no = "Vybuchnout? - Ne";
        fancyChars = "á č ď é ě í ň ó ř š ť ú ů ý ž";
        moduleLabels = new String[]{"Tlačítko", "Kdo je na první", "Morseovka", "Hesla", "Upouštění páry"};
        buttonLabels = new String[]{"Červené", "Modrý", "Žlutý", "Bílý", "Držet", "Lis", "Odpálit", "Zrušit"};
        passwords = new String[]{
                "agent", "barva", "bomba", "budík", "copak",
                "domov", "dráha", "hokej", "hvord", "kabel",
                "kočka", "kolem", "kosti", "lustr", "místo",
                "okolo", "pokoj", "pomoc", "pozdě", "praha",
                "práce", "právo", "písek", "rehek", "skála",
                "strom", "strop", "škola", "šperk", "tráva",
                "vazba", "volat", "volba", "zájem", "zásah"};
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
        frequencies.put("strach", 3.505);
        frequencies.put("plomba", 3.515);
        frequencies.put("bronz", 3.522);
        frequencies.put("sklep", 3.532);
        frequencies.put("houby", 3.535);
        frequencies.put("klouby", 3.542);
        frequencies.put("chleba", 3.545);
        frequencies.put("prach", 3.552);
        frequencies.put("shluk", 3.555);
        frequencies.put("strop", 3.565);
        frequencies.put("bomba", 3.572);
        frequencies.put("klenba", 3.575);
        frequencies.put("brach", 3.582);
        frequencies.put("strup", 3.592);
        frequencies.put("papouch", 3.595);
        frequencies.put("klacek", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("MŮŽU", "CO, OK, TEDA, DOLE, VPRAVO, ZMÁČKNI, NAHOŘE, NIC, MŮŽU");
        stepTwoMap.put("NE", "VPRAVO, OK, CO, DOLE, PRÁZDNÉ, NAHOŘE, ANO, VLEVO, POČKEJ, MŮŽU, NIC, TEDA, ZMÁČKNI, NE");
        stepTwoMap.put("PRÁZDNÉ", "NIC, VLEVO, POČKEJ, NE, TEDA, MŮŽU, NAHOŘE, CO, ANO, VPRAVO, ZMÁČKNI, OK, PRÁZDNÉ");
        stepTwoMap.put("NIC", "POČKEJ, NAHOŘE, OK, DOLE, NIC");
        stepTwoMap.put("ANO", "VLEVO, NAHOŘE, OK, DOLE, CO, NIC, PRÁZDNÉ, ZMÁČKNI, VPRAVO, TEDA, POČKEJ, NE, ANO");
        stepTwoMap.put("CO", "OK, NAHOŘE, VLEVO, DOLE, NE, TEDA, ZMÁČKNI, MŮŽU, ANO, CO");
        stepTwoMap.put("TEDA", "VLEVO, TEDA");
        stepTwoMap.put("VLEVO", "MŮŽU, ANO, VPRAVO, TEDA, OK, CO, NAHOŘE, PRÁZDNÉ, ZMÁČKNI, NIC, VLEVO");
        stepTwoMap.put("VPRAVO", "NAHOŘE, VPRAVO");
        stepTwoMap.put("NAHOŘE", "CO, ANO, MŮŽU, ZMÁČKNI, PRÁZDNÉ, POČKEJ, TEDA, NAHOŘE");
        stepTwoMap.put("DOLE", "NIC, MŮŽU, OK, TEDA, ANO, ZMÁČKNI, PRÁZDNÉ, POČKEJ, VPRAVO, DOLE");
        stepTwoMap.put("OK", "DOLE, PRÁZDNÉ, NE, CO, VLEVO, ANO, POČKEJ, OK");
        stepTwoMap.put("POČKEJ", "VLEVO, PRÁZDNÉ, NIC, OK, CO, VPRAVO, NE, ZMÁČKNI, TEDA, POČKEJ");
        stepTwoMap.put("ZMÁČKNI", "NAHOŘE, DOLE, CO, MŮŽU, ZMÁČKNI");
        stepTwoMap.put("BYLI", "JASNĚ, BYLY, BÍLÍ, BÍLÝ, DÁL, NO?, O NĚM, DRŽ, CO?, BYLI");
        stepTwoMap.put("BYLY", "BÍLÍ, DÁL, JAKO, NO?, CO?, HOTOVO, NO, DRŽ, BYLI, ONĚM, BÍLÝ, JASNĚ, O NĚM, BYLY");
        stepTwoMap.put("BÍLÍ", "NO, BYLY, NO?, BÍLÍ");
        stepTwoMap.put("BÍLÝ", "BYLI, BÍLÝ");
        stepTwoMap.put("O NĚM", "HOTOVO, ONĚM, O NĚM");
        stepTwoMap.put("ONĚM", "NO?, JASNĚ, DÁL, CO?, BÍLÝ, O NĚM, NO, HOTOVO, ONĚM");
        stepTwoMap.put("NO?", "NO?");
        stepTwoMap.put("NO", "O NĚM, ONĚM, BYLY, BÍLÝ, DÁL, NO");
        stepTwoMap.put("CO?", "BYLI, DRŽ, BÍLÝ, BÍLÍ, ONĚM, HOTOVO, NO, JAKO, BYLY, NO?, O NĚM, DÁL, CO?");
        stepTwoMap.put("HOTOVO", " JASNĚ, NO?, DÁL, CO?, BÍLÍ, O NĚM, BÍLÝ, DRŽ, JAKO, BYLI, ONĚM, BYLY, NO, HOTOVO");
        stepTwoMap.put("DÁL", "CO?, NO?, NO, BÍLÍ, DRŽ, JASNĚ, DÁL");
        stepTwoMap.put("DRŽ", "BYLY, ONĚM, HOTOVO, NO, BYLI, O NĚM, JASNĚ, CO?, BÍLÝ, DÁL, DRŽ");
        stepTwoMap.put("JASNĚ", "BYLY, HOTOVO, JAKO, BÍLÝ, BYLI, DRŽ, NO?, O NĚM, JASNĚ");
        stepTwoMap.put("JAKO", "BÍLÝ, DÁL, ONĚM, O NĚM, DRŽ, HOTOVO, NO, CO?, NO?, BYLI, JAKO");
    }
}
