package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

public class EstonianDict extends Dictionary{
 public EstonianDict(){
  frequencies = new HashMap<>();
  stepTwoMap = new HashMap<>();
  initFreqs();
  initStepTwo();
  yes = "Kas õhutada õhku? - Jah";
  no = "Detoneerima? - Ei";
  fancyChars = "ä ö õ š ü ž";
  buttonLabels = new String[]{"Punane", "Sinine", "Kollane", "Valge", "Hoia", "Vajutage", "Õhka", "Katkesta"};
  moduleLabels = new String[]{"Nupp", "Kes on esimesel", "Morse kood", "Paroolid", "Gaasi tuulutamine"};
  passwords = new String[]{
          "aitan", "aknal", "aksel", "diiva", "dinod",
          "edasi", "eesti", "ehtne", "haile", "happe",
          "herne", "ihkan", "jumal", "juura", "kaart",
          "kanad", "kevad", "magus", "morss", "nupud",
          "piima", "ratas", "ringi", "rumal", "särin",
          "sügav", "sügis", "tabav", "taibu", "taust",
          "tugev", "vaata", "öhtus", "öigem", "ütlen"};
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
  frequencies.put("parras", 3.505);
  frequencies.put("varras", 3.515);
  frequencies.put("varas", 3.522);
  frequencies.put("emake", 3.532);
  frequencies.put("kenake", 3.535);
  frequencies.put("nupud", 3.542);
  frequencies.put("taimer", 3.545);
  frequencies.put("vaikne", 3.552);
  frequencies.put("paigal", 3.555);
  frequencies.put("pusker", 3.565);
  frequencies.put("ääres", 3.572);
  frequencies.put("ämblik", 3.575);
  frequencies.put("kallis", 3.582);
  frequencies.put("juurde", 3.592);
  frequencies.put("kuular", 3.595);
  frequencies.put("öösel", 3.6);
 }

 @Override
 protected void initStepTwo() {
  stepTwoMap.put("VALMIS", "JAH, OKEI, MIDA, KESKEL, VASAK, VAJUTA, PAREM, TÜHI, VALMIS");
  stepTwoMap.put("ESIMENE", "VASAK, OKEI, JAH, KESKEL, EI, PAREM, MIDAGI, AHHH, OOTA, VALMIS, TÜHI, MIDA, VAJUTA, ESIMENE");
  stepTwoMap.put("EI", "TÜHI, AHHH, OOTA, ESIMENE, MIDA, VALMIS, PAREM, JAH, MIDAGI, VASAK, VAJUTA, OKEI, EI");
  stepTwoMap.put("TÜHI", "OOTA, PAREM, OKEI, KESKEL, TÜHI");
  stepTwoMap.put("MIDAGI", "AHHH, PAREM, OKEI, KESKEL, JAH, TÜHI, EI, VAJUTA, VASAK, MIDA, OOTA, ESIMENE, MIDAGI");
  stepTwoMap.put("JAH", "OKEI, PAREM, AHHH, KESKEL, ESIMENE, MIDA, VAJUTA, VALMIS, MIDAGI, JAH");
  stepTwoMap.put("MIDA", "AHHH, MIDA");
  stepTwoMap.put("AHHH", "VALMIS, MIDAGI, VASAK, MIDA, OKEI, JAH, PAREM, EI, VAJUTA, TÜHI, AHHH");
  stepTwoMap.put("VASAK", "PAREM, VASAK");
  stepTwoMap.put("PAREM", "JAH, MIDAGI, VALMIS, VAJUTA, EI, OOTA, MIDA, PAREM");
  stepTwoMap.put("KESKEL", "TÜHI, VALMIS, OKEI, MIDA, MIDAGI, VAJUTA, EI, OOTA, VASAK, KESKEL");
  stepTwoMap.put("OKEI", "KESKEL, EI, ESIMENE, JAH, AHHH, MIDAGI, OOTA, OKEI");
  stepTwoMap.put("OOTA", "AHHH, EI, TÜHI, OKEI, JAH, VASAK, ESIMENE, VAJUTA, MIDA, OOTA");
  stepTwoMap.put("VAJUTA", "PAREM, KESKEL, JAH, VALMIS, VAJUTA");
  stepTwoMap.put("SA", "OK, SINA, SINU, SU, EDASI, AH HAH, SUU, OOT, MIDA?, SA");
  stepTwoMap.put("SINA", "SINU, EDASI, NAGU, AH HAH, MIDA?, OLEMAS, AH AH, OOT, SA, U, SU, OK, SUU, SINA");
  stepTwoMap.put("SINU", "AH AH, SINA, AH HAH, SINU");
  stepTwoMap.put("SU", "SA, SU");
  stepTwoMap.put("SUU", "OLEMAS, U, SUU");
  stepTwoMap.put("U", "AH HAH, OK, EDASI, MIDA?, SU, SUU, AH AH, OLEMAS, U");
  stepTwoMap.put("AH HAH", "AH HAH");
  stepTwoMap.put("AH AH", "SUU, U, SINA, SU, EDASI, AH AH");
  stepTwoMap.put("MIDA?", "SA, OOT, SU, SINU, U, OLEMAS, AH AH, NAGU, SINA, AH HAH, SUU, EDASI, MIDA?");
  stepTwoMap.put("OLEMAS", "OK, AH HAH, EDASI, MIDA?, SINU, SUU, SU, OOT, NAGU, SA, U, SINA, AH AH, OLEMAS");
  stepTwoMap.put("EDASI", "MIDA?, AH HAH, AH AH, SINU, OOT, OK, EDASI");
  stepTwoMap.put("OOT", "SINA, U, OLEMAS, AH AH, SA, SUU, OK, MIDA?, SU, EDASI, OOT");
  stepTwoMap.put("OK", "SINA, OLEMAS, NAGU, SU, SA, OOT, AH HAH, SUU, OK");
  stepTwoMap.put("NAGU", "SU, EDASI, U, SUU, OOT, OLEMAS, AH AH, MIDA?, AH HAH, SA, NAGU");
 }
}