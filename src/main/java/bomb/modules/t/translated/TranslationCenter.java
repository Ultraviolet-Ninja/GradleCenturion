package bomb.modules.t.translated;

import bomb.tools.dictionary.*;
import bomb.Widget;

import java.util.HashMap;

public class TranslationCenter extends Widget {
    public static String[] moduleNames, buttonNames;

    protected static String[] passwords;
    protected static String yes, no, fancy;
    protected static HashMap<String, Double> frequencies;
    protected static HashMap<String, String> stepTwo;

    private static Dictionary current;

    public static void setLanguage(String dict){
        switch (dict){
            case "Brazil": current = new BrazilianDict(); break;
            case "Czech": current = new CzechDict(); break;
            case "Denmark": current = new DanishDict(); break;
            case "Netherlands": current = new DutchDict(); break;
            case "Esperanto": current = new EsperantoDict(); break;
            case "Finnish": current = new FinnishDict(); break;
            case "US": current = new EnglishDict(); break;
            case "Estonia": current = new EstonianDict(); break;
            default: current = new GermanDict();
        }
        setWords();
    }

    private static void setWords(){
        passwords = current.exportPasswords();
        String[] buffer = current.getPhrases();
        yes = buffer[0];
        no = buffer[1];
        frequencies = current.getFrqMap();
        stepTwo = current.getStepTwo();
        fancy = current.getFancyChars();
        moduleNames = current.getMods();
        buttonNames = current.getButtons();
    }
}
