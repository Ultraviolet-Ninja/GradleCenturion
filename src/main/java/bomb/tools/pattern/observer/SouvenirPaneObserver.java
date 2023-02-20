package bomb.tools.pattern.observer;

import bomb.modules.s.souvenir.SouvenirController;

public final class SouvenirPaneObserver implements Observer {
    private final SouvenirController controller;

    public SouvenirPaneObserver(SouvenirController controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        controller.liveUpdate();
    }
}
