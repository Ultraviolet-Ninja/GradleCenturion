package bomb.tools.pattern.facade;

import io.github.palexdev.materialfx.controls.MFXLabel;
import io.github.palexdev.materialfx.controls.MFXSlider;

public class MaterialFacade {
    public static void resetSliderValues(MFXSlider... sliders) {
        for (MFXSlider slider : sliders)
            slider.setValue(0.0);
    }

    public static void clearMultipleLabels(MFXLabel... labels) {
        for (MFXLabel label : labels)
            clearMFXLabel(label);
    }

    public static void clearMFXLabel(MFXLabel label){
        label.setText("");
    }
}
