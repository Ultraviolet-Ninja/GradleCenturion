package bomb.tools.observer;

import bomb.modules.ab.blind_alley.BlindAlleyController;

public class BlindAlleyObserver implements Observer {
    private final BlindAlleyController controller;

    public BlindAlleyObserver(BlindAlleyController controller){
        this.controller = controller;
    }

    @Override
    public void update() {
        controller.liveUpdate();
    }
}
