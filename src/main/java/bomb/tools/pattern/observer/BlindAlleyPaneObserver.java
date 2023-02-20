package bomb.tools.pattern.observer;

import bomb.modules.ab.blind.alley.BlindAlleyController;

public final class BlindAlleyPaneObserver implements Observer {
    private final BlindAlleyController controller;

    public BlindAlleyPaneObserver(BlindAlleyController controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        controller.liveUpdate();
    }
}
