package bomb.tools.data.structures.dictionary;

import java.util.HashMap;

//28 Panels, 28 Keywords

public abstract class Dictionary {
    protected HashMap<String, Double> frequencies;
    protected HashMap<String, String> stepTwoMap;
    protected String yes, no, fancyChars;
    protected String[] passwords,
            buttonLabels, //Order: red, blue, yellow, white, hold, press, detonate, abort
            moduleLabels; //Order: Button, Who's, Morse Code, Passwords, Vent Gas

    public abstract String predictWord(String part, boolean isMorse);

    protected abstract void initFreqs();

    protected abstract void initStepTwo();

    public String getFancyChars(){
        return fancyChars;
    }

    public String[] exportPasswords(){
        return passwords;
    }

    public String[] getButtons(){
        return buttonLabels;
    }

    public String[] getMods(){
        return moduleLabels;
    }

    public String[] getPhrases(){
        return new String[] {yes, no};
    }

    public HashMap<String, Double> getFrqMap(){
        return frequencies;
    }

    public HashMap<String, String> getStepTwo(){
        return stepTwoMap;
    }
}
