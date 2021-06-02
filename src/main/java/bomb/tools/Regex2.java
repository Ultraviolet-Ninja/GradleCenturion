package bomb.tools;

import bomb.tools.lib.com.google.re2j.Matcher;
import bomb.tools.lib.com.google.re2j.Pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Regex2 implements Iterable<String>{
    private static final int MAX_FLAG_SIZE = 31;

    private final Pattern regPattern;
    private final Matcher textMatcher;

    public Regex2(String regex){
        regPattern = Pattern.compile(regex);
        textMatcher = regPattern.matcher("");
    }

    public Regex2(String regex, int flags){
        maxFlagCheck(flags);
        regPattern = Pattern.compile(regex, flags);
        textMatcher = regPattern.matcher("");
    }

    @SuppressWarnings("MagicConstant")
    public Regex2(String regex, int ... flags){
        int value = orMask(flags);
        maxFlagCheck(value);
        regPattern = Pattern.compile(regex, value);
        textMatcher = regPattern.matcher("");
    }

    public Regex2(String regex, String matchText){
        this(regex);
        loadText(matchText);
    }

    public Regex2(String regex, String matchText, int flags){
        this(regex, flags);
        loadText(matchText);
    }

    public Regex2(String regex, String matchText, int ... flags){
        this(regex, flags);
        loadText(matchText);
    }

    public void loadText(String text){
        textMatcher.reset(text);
    }

    public void loadCollection(Collection<String> textCollections){
        StringBuilder sb = new StringBuilder();
        textCollections.forEach(text -> sb.append(text).append(" "));
        loadText(sb.toString());
    }

    public boolean hasMatch(){
        nullChecker();
        return textMatcher.matches();
    }

    public List<String> findAllMatches(){
        nullChecker();
        reset();
        ArrayList<String> output = new ArrayList<>();
        while(textMatcher.find()){
            output.add(textMatcher.group());
        }
        return output;
    }

    public String toNewString(){
        nullChecker();
        reset();
        StringBuilder result = new StringBuilder();
        for (String sample : findAllMatches()){
            result.append(sample);
        }
        return result.toString();
    }

    public int flags(){
        return regPattern.flags();
    }

    public void reset(){
        textMatcher.reset();
    }

    @Override
    public Iterator<String> iterator() {
        return findAllMatches().iterator();
    }

    private void nullChecker(){
        if (textMatcher == null)
            throw new IllegalArgumentException("No text was inserted");
    }

    private static void maxFlagCheck(int flags){
        if (flags > MAX_FLAG_SIZE || flags < 0) throw new IllegalArgumentException("Invalid flag number");
    }

    private static int orMask(int[] flags){
        int result = 0;
        for (int flag : flags)
            result |= flag;
        return result;
    }

    @SuppressWarnings("MagicConstant")
    public static String quickCompile(String regex, String text, int ... flags){
        int value = orMask(flags);
        maxFlagCheck(value);

        Pattern pattern = Pattern.compile(regex, value);
        Matcher match = pattern.matcher(text);
        StringBuilder sb = new StringBuilder();

        while (match.find()){
            sb.append(match.group());
        }
        return sb.toString();
    }
}
