package bomb.modules.dh;

import bomb.modules.dh.emoji.Emojis;
import bomb.modules.dh.emoji.EmojiMath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldEmojiMathTest {
    private final Emojis[] emojiArray = Emojis.values();

    @Test
    void firstAdditionTest(){
        for (Emojis first : emojiArray)
            for (Emojis second : emojiArray)
                assertEquals((first.getIdx() + second.getIdx()),
                        EmojiMath.calculate(first.getLabel() + "+" + second.getLabel()));
    }

    @Test
    void secondAdditionTest(){
        for (Emojis first : emojiArray)
            for (Emojis second : emojiArray)
                assertEquals((second.getIdx() + first.getIdx()),
                        EmojiMath.calculate(second.getLabel() + "+" + first.getLabel()));
    }

    @Test
    void firstSubtractionTest(){
        for (Emojis first : emojiArray)
            for (Emojis second : emojiArray)
                assertEquals((first.getIdx() - second.getIdx()),
                        EmojiMath.calculate(first.getLabel() + "-" + second.getLabel()));
    }

    @Test
    void secondSubtractionTest(){
        for (Emojis first : emojiArray)
            for (Emojis second : emojiArray)
                assertEquals((second.getIdx() - first.getIdx()),
                        EmojiMath.calculate(second.getLabel() + "-" + first.getLabel()));
    }

    @Test
    void videoTest(){
        assertEquals(-1, EmojiMath.calculate("=)(=-=):|"));
        assertEquals(95, EmojiMath.calculate(":|:|+(="));
        assertEquals(189, EmojiMath.calculate("|:=)+|:)="));
        assertEquals(-10, EmojiMath.calculate(":(:)-)::)"));
    }

    @Test
    void theGreatBerate(){
        assertEquals(-54, EmojiMath.calculate(")=:)-:|:("));
        assertEquals(120, EmojiMath.calculate(":((=+(=)="));
        assertEquals(144, EmojiMath.calculate("(=:(+(=:)"));
    }
}
