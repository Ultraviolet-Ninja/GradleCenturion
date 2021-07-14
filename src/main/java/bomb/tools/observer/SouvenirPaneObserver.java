package bomb.tools.observer;

import bomb.modules.s.souvenir.NewSouvenirController;

public class SouvenirPaneObserver implements Observer{
    private final NewSouvenirController controller;

    public SouvenirPaneObserver(NewSouvenirController controller){
        this.controller = controller;
    }

    @Override
    public void update() {
        controller.liveUpdate();
    }
}
