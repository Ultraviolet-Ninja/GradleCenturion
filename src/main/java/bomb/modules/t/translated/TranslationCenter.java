package bomb.modules.t.translated;

import bomb.Widget;
import bomb.tools.data.structures.dictionary.BrazilianDict;
import bomb.tools.data.structures.dictionary.CzechDict;
import bomb.tools.data.structures.dictionary.DanishDict;
import bomb.tools.data.structures.dictionary.Dictionary;
import bomb.tools.data.structures.dictionary.DutchDict;
import bomb.tools.data.structures.dictionary.EnglishDict;
import bomb.tools.data.structures.dictionary.EsperantoDict;
import bomb.tools.data.structures.dictionary.EstonianDict;
import bomb.tools.data.structures.dictionary.FinnishDict;
import bomb.tools.data.structures.dictionary.GermanDict;

import java.util.HashMap;

public sealed class TranslationCenter extends Widget permits Button, Morse, OnFirst, Passwords, VentGas {
    public static String[] moduleNames, buttonNames;

    protected static String[] passwords;
    protected static String yes, no, fancy;
    protected static HashMap<String, Double> frequencies;
    protected static HashMap<String, String> stepTwo;

    private static Dictionary current;

    public static void setLanguage(String dict) {
        switch (dict) {
            case "Brazil":
                current = new BrazilianDict();
                break;
            case "Czech":
                current = new CzechDict();
                break;
            case "Denmark":
                current = new DanishDict();
                break;
            case "Netherlands":
                current = new DutchDict();
                break;
            case "Esperanto":
                current = new EsperantoDict();
                break;
            case "Finnish":
                current = new FinnishDict();
                break;
            case "US":
                current = new EnglishDict();
                break;
            case "Estonia":
                current = new EstonianDict();
                break;
            default:
                current = new GermanDict();
        }
        setWords();
    }

    private static void setWords() {
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
