package bomb.tools.pattern.facade;

import io.github.palexdev.materialfx.controls.MFXSlider;

public class MaterialFacade {
    public static void resetSliderValues(MFXSlider... sliders){
        for (MFXSlider slider : sliders)
            slider.setValue(0.0);
    }
}
