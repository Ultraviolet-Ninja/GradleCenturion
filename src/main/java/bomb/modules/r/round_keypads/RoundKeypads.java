package bomb.modules.r.round_keypads;

import bomb.Widget;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 */
public class RoundKeypads extends Widget {
    //TODO - Finished Javadocs

    /**
     * @param values
     * @return
     */
    public static int autoDetect(Keypads[] values) {
        int[] counters = new int[6];
        int max = 0, intOut = 0;
        for (int i = 0; i < values.length; i++) {
            if (i < 7 && !values[i].getFlag()) counters[0]++;
            else if (i < 14 && !values[i].getFlag()) counters[1]++;
            else if (i < 21 && !values[i].getFlag()) counters[2]++;
            else if (i < 28 && !values[i].getFlag()) counters[3]++;
            else if (i < 35 && !values[i].getFlag()) counters[4]++;
            else if (!values[i].getFlag()) counters[5]++;
        }

        for (int j = counters.length - 1; j >= 0; j--) {
            if (counters[j] > max) {
                max = counters[j];
                intOut = j;
            }
        }
        if (counters[intOut] == 0) intOut = -1;

        return intOut;
    }

    /**
     * Highlights or de-highlights the Keypad that was clicked by the user.
     *
     * @param toEdit - the Keypad to highlight or return to normal
     * @param og     - the Image of the original keypad appearance
     * @return - the highlighted or normal image of the keypad
     */
    public static Image change(Keypads toEdit, Image og) {
        //TODO - Break down
        if (toEdit.getFlag()) {
            WritableImage nextImage = new WritableImage((int) og.getWidth(), (int) og.getHeight());
            for (int x = 0; x < (int) og.getWidth(); x++) {
                for (int y = 0; y < (int) og.getHeight(); y++) {
                    nextImage.getPixelWriter().setColor(x, y,
                            highLight(og.getPixelReader().getColor(x, y)));
                }
            }
            toEdit.setFlag(true);
            toEdit.setMemoryIfNull(og);
            return nextImage;
        } else {
            toEdit.setFlag(false);
            return toEdit.getMemory();
        }
    }

    /**
     * @param toHigh
     * @return
     */
    private static Color highLight(Color toHigh) {
        return new Color(0, toHigh.getGreen(), toHigh.getBlue(), 1);
    }
}
