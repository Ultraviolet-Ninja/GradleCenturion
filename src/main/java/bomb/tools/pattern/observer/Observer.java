package bomb.tools.pattern.observer;

public sealed interface Observer permits BlindAlleyPaneObserver, ForgetMeNotToggleObserver, ResetObserver, SouvenirPaneObserver, SouvenirToggleObserver, TurnTheKeysStrictModeObserver {
    void update();
}
