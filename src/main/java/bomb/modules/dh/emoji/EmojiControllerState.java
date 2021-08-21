package bomb.modules.dh.emoji;

import bomb.abstractions.State;

public enum EmojiControllerState implements State<EmojiControllerState> {
    START {
        @Override
        public EmojiControllerState nextState() {
            return FIRST_EMOJI_PRESS;
        }
    },

    FIRST_EMOJI_PRESS {
        @Override
        public EmojiControllerState nextState() {
            return null;
        }
    };
}
