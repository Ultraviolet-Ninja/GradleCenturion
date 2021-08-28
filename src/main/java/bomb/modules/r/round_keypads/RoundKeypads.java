package bomb.modules.r.round_keypads;

import bomb.Widget;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.function.UnaryOperator;

/**
 *
 */
public class RoundKeypads extends Widget {
    //TODO - Finished Javadocs

    private static final byte NUMBER_OF_COLUMNS = 6, IMAGES_PER_COLUMN = 7;
    private static final UnaryOperator<Color> HIGHLIGHT_COMMAND = previousColor ->
            new Color(0, previousColor.getGreen(), previousColor.getBlue(), 1);

    /**
     * @param values
     * @return
     */
    public static int autoDetect(Keypad[] values) {
        int[] columnCounters = new int[NUMBER_OF_COLUMNS];
        int maxHighlightedImages = 0, columnToHighlight = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i].getFlag()) columnCounters[Math.floorDiv(i, IMAGES_PER_COLUMN)]++;
        }

        for (int j = columnCounters.length - 1; j >= 0; j--) {
            if (columnCounters[j] > maxHighlightedImages) {
                maxHighlightedImages = columnCounters[j];
                columnToHighlight = j;
            }
        }
        if (columnCounters[columnToHighlight] == 0) columnToHighlight = -1;

        return columnToHighlight;
    }

    /**
     * Highlights or de-highlights the Keypad that was clicked by the user.
     *
     * @param toEdit   - the Keypad to highlight or return to normal
     * @param original - the Image of the original keypad appearance
     * @return - the highlighted or normal image of the keypad
     */
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
        WritableImage nextImage = new WritableImage((int) original.getWidth(), (int) original.getHeight());
        for (int x = 0; x < (int) original.getWidth(); x++) {
            for (int y = 0; y < (int) original.getHeight(); y++) {
                nextImage.getPixelWriter().setColor(x, y,
                        HIGHLIGHT_COMMAND.apply(original.getPixelReader().getColor(x, y)));
            }
        }
        return nextImage;
    }
}
