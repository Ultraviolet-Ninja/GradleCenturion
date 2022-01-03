package bomb.tools.filter;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("MagicConstant")
public class Regex implements Iterable<String> {
    public static final Function<String, Regex> CREATE_INSENSITIVE_SET =
            input -> new Regex("[" + input + "]", CASE_INSENSITIVE),
            CREATE_NEGATED_SET = input -> new Regex("[^" + input + "]");

    private static final Function<Matcher, Stream<String>> RESULT_STREAM = matcher ->
            matcher.reset().results().map(MatchResult::group);

    private static final int MAX_FLAG_SIZE = 0x1ff;

    private final Pattern regPattern;
    private final Matcher textMatcher;

    public Regex(@Language("regexp") @NotNull String regex) {
        regPattern = Pattern.compile(regex);
        textMatcher = regPattern.matcher("");
    }

    public Regex(@Language("regexp") @NotNull String regex, int flag) throws IllegalArgumentException {
        maxFlagCheck(flag);
        regPattern = Pattern.compile(regex, flag);
        textMatcher = regPattern.matcher("");
    }

    public Regex(@Language("regexp") @NotNull String regex, int... flags) throws IllegalArgumentException {
        int value = orMask(flags);
        maxFlagCheck(value);
        regPattern = Pattern.compile(regex, value);
        textMatcher = regPattern.matcher("");
    }

    public Regex(@Language("regexp") @NotNull String regex, @NotNull String matchText) {
        regPattern = Pattern.compile(regex);
        textMatcher = regPattern.matcher(matchText);
    }

    public Regex(@Language("regexp") @NotNull String regex, @NotNull String matchText, int flag)
            throws IllegalArgumentException {
        maxFlagCheck(flag);
        regPattern = Pattern.compile(regex, flag);
        textMatcher = regPattern.matcher(matchText);
    }

    public Regex(@Language("regexp") @NotNull String regex, @NotNull String matchText, int... flags)
            throws IllegalArgumentException {
        int value = orMask(flags);
        maxFlagCheck(value);
        regPattern = Pattern.compile(regex, value);
        textMatcher = regPattern.matcher(matchText);
    }

    public void loadText(@NotNull String text) {
        textMatcher.reset(text);
    }

    public List<String> filterCollection(@NotNull Collection<String> textCollections) {
        return textCollections.stream()
                .map(textMatcher::reset)
                .map(RESULT_STREAM)
                .map(stream -> stream.collect(joining()))
                .collect(toList());
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

    public String captureGroup(@NotNull String customGroup) {
        return textMatcher.group(customGroup);
    }

    public int groupCount() {
        return textMatcher.groupCount();
    }

    public List<String> findAllMatches() {
        return RESULT_STREAM.apply(textMatcher)
                .collect(toList());
    }

    public String getOriginalPattern() {
        return regPattern.pattern();
    }

    public String createFilteredString() {
        return RESULT_STREAM.apply(textMatcher)
                .collect(joining());
    }

    public boolean doesCollectionMatch(@NotNull Collection<String> c) {
        String pattern = regPattern.pattern();

        return c.stream()
                .map(phrase -> phrase.matches(pattern))
                .reduce((b1, b2) -> b1 || b2)
                .orElse(false);
    }

    public void reset() {
        textMatcher.reset();
    }

    @Override
    public String toString() {
        return "Regex: " + regPattern.pattern();
    }

    public Stream<String> stream() {
        return RESULT_STREAM.apply(textMatcher);
    }

    @Override
    public Iterator<String> iterator() {
        return RESULT_STREAM.apply(textMatcher).iterator();
    }

    public Regex appendToPattern(@Language("regexp") @NotNull String pattern) {
        return new Regex(this.regPattern.pattern() + pattern, regPattern.flags());
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
