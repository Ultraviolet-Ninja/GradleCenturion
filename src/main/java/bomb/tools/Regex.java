package bomb.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex implements Iterable<String>{
    private static final int MAX_FLAG_SIZE = 511;

    private final Pattern regPattern;
    private Matcher textMatcher;

    public Regex(String regex){
        regPattern = Pattern.compile(regex);
        textMatcher = null;
    }

    public Regex(String regex, int flags){
        maxFlagCheck(flags);
        regPattern = Pattern.compile(regex, flags);
        textMatcher = null;
    }

    @SuppressWarnings("MagicConstant")
    public Regex(String regex, int ... flags){
        int value = orMask(flags);
        maxFlagCheck(value);
        regPattern = Pattern.compile(regex, value);
        textMatcher = null;
    }

    public Regex(String regex, String matchText){
        this(regex);
        loadText(matchText);
    }

    public Regex(String regex, String matchText, int flags){
        this(regex, flags);
        loadText(matchText);
    }

    public Regex(String regex, String matchText, int ... flags){
        this(regex, flags);
        loadText(matchText);
    }

    public void loadText(String text){
        textMatcher = regPattern.matcher(text);
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
