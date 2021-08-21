package bomb.modules.dh.emoji;

import bomb.abstractions.State;

public enum EmojiControllerState implements State<EmojiControllerState> {
    FIRST_EMOJI_PRESS {
        @Override
        public EmojiControllerState nextState() {
            return SECOND_EMOJI_PRESS;
        }
    },
    SECOND_EMOJI_PRESS {
        @Override
        public EmojiControllerState nextState() {
            return MATH_SYMBOL_PRESS;
        }
    },
    MATH_SYMBOL_PRESS {
        @Override
        public EmojiControllerState nextState() {
            return THIRD_EMOJI_PRESS;
        }
    },
    THIRD_EMOJI_PRESS {
        @Override
        public EmojiControllerState nextState() {
            return FOURTH_EMOJI_PRESS;
        }
    },
    FOURTH_EMOJI_PRESS {
        @Override
        public EmojiControllerState nextState() {
            return END;
        }
    },
    END {
        @Override
        public EmojiControllerState nextState() {
            return RESET;
        }
    },
    RESET {
        @Override
        public EmojiControllerState nextState() {
            return FIRST_EMOJI_PRESS;
        }
    }
}
