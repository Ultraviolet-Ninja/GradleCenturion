package bomb.tools.dictionary;

import java.util.HashMap;

public class FinnishDict extends Dictionary {
    public FinnishDict() {
        frequencies = new HashMap<>();
        stepTwoMap = new HashMap<>();
        initFreqs();
        initStepTwo();
        yes = "";
        no = "";
        fancyChars = "ä ö";
        buttonLabels = new String[]{"Punainen", "Keltainen", "Sininen", "Valkoinen", "", "", "", ""};
        moduleLabels = new String[]{"Painike", "Nakkivene", "Morsekoodi", "Salasanat", "Vaativat Moduulit"};
        passwords = new String[]{
                "alava", "etelä", "hikeä", "huone", "korva",
                "kuksa", "kuusi", "käyrä", "luomi", "maata",
                "massa", "metsä", "museo", "neljä", "nuori",
                "odota", "onnea", "orava", "paras", "pommi",
                "puoli", "pätkä", "ravut", "risti", "suoli",
                "suora", "tammi", "tukki", "tuppi", "tyslä",
                "ulkoa", "uudet", "viisi", "viiva", "väljä"};
    }

    @Override
    public String predictWord(String part, boolean isMorse) {
        if (!isMorse) {
            switch (part.toLowerCase()) {
                default: return part;
            }
        }
        switch (part.toLowerCase()) {

            default: return part;
        }
    }

    @Override
    protected void initFreqs() {
        frequencies.put("kenttä", 3.505);
        frequencies.put("potku", 3.515);
        frequencies.put("kettu", 3.522);
        frequencies.put("hissi", 3.532);
        frequencies.put("katko", 3.535);
        frequencies.put("takoi", 3.542);
        frequencies.put("rikos", 3.545);
        frequencies.put("pirinä", 3.552);
        frequencies.put("pentti", 3.555);
        frequencies.put("heheh", 3.565);
        frequencies.put("pinhdit", 3.572);
        frequencies.put("koita", 3.575);
        frequencies.put("tulos", 3.582);
        frequencies.put("pommi", 3.592);
        frequencies.put("passi", 3.595);
        frequencies.put("lahti", 3.6);
    }

    @Override
    protected void initStepTwo() {
        stepTwoMap.put("T", "VALMIS, Ö, OIKEIN, ÖÖH, ODOTA, MITÄ?, HETKI, SITTEN, T");
        stepTwoMap.put("TEE", "ODOTA, Ö, VALMIS, ÖÖH, TEHTY, HETKI, PAINA, VÄÄRIN, MITÄ, T, SITTEN, OIKEIN, MITÄ?, TEE");
        stepTwoMap.put("TEHTY", "SITTEN, VÄÄRIN, MITÄ, TEE, OIKEIN, T, HETKI, VALMIS, PAINA, ODOTA, MITÄ?, Ö, TEHTY");
        stepTwoMap.put("SITTEN", "MITÄ, HETKI, Ö, ÖÖH, SITTEN");
        stepTwoMap.put("PAINA", "VÄÄRIN, HETKI, Ö, ÖÖH, VALMIS, SITTEN, TEHTY, MITÄ?, ODOTA, OIKEIN, MITÄ, TEE, PAINA");
        stepTwoMap.put("VALMIS", "Ö, HETKI, VÄÄRIN, ÖÖH, TEE, OIKEIN, MITÄ?, T, PAINA, VALMIS");
        stepTwoMap.put("OIKEIN", "VÄÄRIN, OIKEIN");
        stepTwoMap.put("VÄÄRIN", "T, PAINA, ODOTA, OIKEIN, Ö, VALMIS, HETKI, TEHTY, MITÄ?, SITTEN, VÄÄRIN");
        stepTwoMap.put("ODOTA", "HETKI, ODOTA");
        stepTwoMap.put("HETKI", "VALMIS, PAINA, T, MITÄ?, TEHTY, MITÄ, OIKEIN, HETKI");
        stepTwoMap.put("ÖÖH", "SITTEN, T, Ö, OIKEIN, PAINA, MITÄ?, TEHTY, MITÄ, ODOTA, ÖÖH");
        stepTwoMap.put("Ö", "ÖÖH, TEHTY, TEE, VALMIS, VÄÄRIN, PAINA, MITÄ, Ö");
        stepTwoMap.put("MITÄ", "VÄÄRIN, TEHTY, SITTEN, Ö, VALMIS, ODOTA, TEE, MITÄ?, OIKEIN, MITÄ");
        stepTwoMap.put("MITÄ?", "HETKI, ÖÖH, VALMIS, T, MITÄ?");
        stepTwoMap.put("MIKÄ", "RUOAN, MIKÄ?, JUU, JEP, PIANO, LÖYTYY, KYLLÄ, PIENO, EI OLE, MIKÄ");
        stepTwoMap.put("MIKÄ?", "JUU, PIANO, RUUAN, LÖYTYY, EI OLE, EI OO, EI, PIENO, MIKÄ, ON, JEP, RUOAN, KYLLÄ, MIKÄ?");
        stepTwoMap.put("JUU", "EI, MIKÄ?, LÖYTYY, JUU");
        stepTwoMap.put("JEP", "MIKÄ, JEP");
        stepTwoMap.put("KYLLÄ", "EI OO, ON, KYLLÄ");
        stepTwoMap.put("ON", "LÖYTYY, RUOAN, PIANO, EI OLE, JEP, KYLLÄ, EI, EI OO, ON");
        stepTwoMap.put("LÖYTYY", "LÖYTYY");
        stepTwoMap.put("EI", "KYLLÄ, ON, MIKÄ?, JEP, PIANO, EI");
        stepTwoMap.put("EI OLE", "MIKÄ, PIENO, JEP, JUU, ON, EI OO, EI, RUUAN, MIKÄ?, LÖYTYY, KYLLÄ, PIANO, EI OLE");
        stepTwoMap.put("EI OO", "RUOAN, LÖYTYY, PIANO, EI OLE, JUU, KYLLÄ, JEP, PIENO, RUUAN, MIKÄ, ON, MIKÄ?, EI, EI OO");
        stepTwoMap.put("PIANO", "EI OLE, LÖYTYY, EI, JUU, PIENO, RUOAN, PIANO");
        stepTwoMap.put("PIENO", "MIKÄ?, ON, EI OO, EI, MIKÄ, KYLLÄ, RUOAN, EI OLE, JEP, PIANO, PIENO");
        stepTwoMap.put("RUOAN", "MIKÄ?, EI OO, RUUAN, JEP, MIKÄ, PIENO, LÖYTYY, KYLLÄ, RUOAN");
        stepTwoMap.put("RUUAN", "JEP, PIANO, ON, KYLLÄ, PIENO, EI OO, EI, EI OLE, LÖYTYY, MIKÄ, RUUAN");
    }
}