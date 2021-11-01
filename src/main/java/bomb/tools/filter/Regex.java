package bomb.tools.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("MagicConstant")
public class Regex implements Iterable<String> {
    private static final int MAX_FLAG_SIZE = 0x1ff;

    private final Pattern regPattern;
    private final Matcher textMatcher;

    public Regex(String regex) {
        regPattern = Pattern.compile(regex);
        textMatcher = regPattern.matcher("");
    }

    public Regex(String regex, int flag) {
        maxFlagCheck(flag);
        regPattern = Pattern.compile(regex, flag);
        textMatcher = regPattern.matcher("");
    }

    public Regex(String regex, int... flags) {
        int value = orMask(flags);
        maxFlagCheck(value);
        regPattern = Pattern.compile(regex, value);
        textMatcher = regPattern.matcher("");
    }

    public Regex(String regex, String matchText) {
        regPattern = Pattern.compile(regex);
        textMatcher = regPattern.matcher(matchText);
    }

    public Regex(String regex, String matchText, int flag) {
        maxFlagCheck(flag);
        regPattern = Pattern.compile(regex, flag);
        textMatcher = regPattern.matcher(matchText);
    }

    public Regex(String regex, String matchText, int... flags) {
        int value = orMask(flags);
        maxFlagCheck(value);
        regPattern = Pattern.compile(regex, value);
        textMatcher = regPattern.matcher(matchText);
    }

    public void loadText(String text) {
        textMatcher.reset(text);
    }

    public void loadCollection(Collection<String> textCollections) {
        StringBuilder sb = new StringBuilder();
        textCollections.forEach(text -> sb.append(text).append(" "));
        loadText(sb.toString());
    }

    public boolean hasMatch() {
        return textMatcher.find();
    }

    public boolean matchesRegex() {
        return textMatcher.matches();
    }

    public String captureGroup(int group) {
        return textMatcher.group(group);
    }

    public String captureGroup(String customGroup) {
        return textMatcher.group(customGroup);
    }

    public int groupCount() {
        return textMatcher.groupCount();
    }

    public List<String> findAllMatches() {
        reset();
        List<String> output = new ArrayList<>();
        while (textMatcher.find()) {
            output.add(textMatcher.group());
        }
        return output;
    }

    public String getOriginalPattern() {
        return regPattern.pattern();
    }

    public String createFilteredString() {
        reset();
        StringBuilder result = new StringBuilder();
        for (String sample : findAllMatches()) {
            result.append(sample);
        }
        return result.toString();
    }

    public int flags() {
        return regPattern.flags();
    }

    public void reset() {
        textMatcher.reset();
    }

    @Override
    public String toString() {
        return "Regex: " + regPattern.pattern();
    }

    @Override
    public Iterator<String> iterator() {
        return findAllMatches().iterator();
    }

    private static void maxFlagCheck(int flags) {
        if (flags > MAX_FLAG_SIZE || flags < 0)
            throw new IllegalArgumentException("Invalid flag number");
    }

    private static int orMask(int[] flags) {
        int result = 0;
        for (int flag : flags)
            result |= flag;
        return result;
    }
}
