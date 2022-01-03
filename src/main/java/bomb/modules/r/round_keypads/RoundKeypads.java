package bomb.modules.r.round_keypads;

import bomb.Widget;
import bomb.abstractions.Flaggable;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.function.UnaryOperator;

import static bomb.modules.r.round_keypads.Keypad.KEYPAD_ARRAY;
import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class RoundKeypads extends Widget {
    private static final byte IMAGES_PER_COLUMN = 7;
    private static final UnaryOperator<Color> HIGHLIGHT_COMMAND = previousColor ->
            new Color(0, previousColor.getGreen(), previousColor.getBlue(), 1);

    public static int determineBadColumn() {
        Map<Integer, Long> frequencyCounter = stream(KEYPAD_ARRAY)
                .filter(Flaggable::getFlag)
                .mapToInt(Enum::ordinal)
                .mapToObj(num -> Math.floorDiv(num, IMAGES_PER_COLUMN))
                .collect(groupingBy(identity(), counting()));

        return frequencyCounter.entrySet().stream()
                .max((entryOne, entryTwo) -> {
                    int comparison = entryOne.getValue().compareTo(entryTwo.getValue());
                    if (comparison == 0)
                        return entryOne.getKey().compareTo(entryTwo.getKey());
                    return comparison;
                })
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public static Image toggleImageColor(Keypad toEdit, Image original) {
        if (toEdit.getFlag()) {
            toEdit.setFlag(false);
            return toEdit.getMemory();
        }
        toEdit.setFlag(true);
        toEdit.setMemoryIfNull(original);
        return highLightImage(original);
    }

    private static Image highLightImage(Image original) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage nextImage = new WritableImage(width, height);
        Color transform;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                transform = HIGHLIGHT_COMMAND.apply(original.getPixelReader().getColor(x, y));
                nextImage.getPixelWriter().setColor(x, y, transform);
            }
        }
        return nextImage;
    }

    public static void reset() {
        for (Keypad currentKeypad : KEYPAD_ARRAY)
            currentKeypad.setFlag(false);
    }
}
