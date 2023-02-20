package bomb.modules.dh.emoji.math;

import bomb.abstractions.State;

public enum EmojiControllerState implements State<EmojiControllerState> {
    FIRST_EMOJI_PRESS {
        @Override
        public EmojiControllerState toNextState() {
            return SECOND_EMOJI_PRESS;
        }
    },
    SECOND_EMOJI_PRESS {
        @Override
        public EmojiControllerState toNextState() {
            return MATH_SYMBOL_PRESS;
        }
    },
    MATH_SYMBOL_PRESS {
        @Override
        public EmojiControllerState toNextState() {
            return THIRD_EMOJI_PRESS;
        }
    },
    THIRD_EMOJI_PRESS {
        @Override
        public EmojiControllerState toNextState() {
            return FOURTH_EMOJI_PRESS;
        }
    },
    FOURTH_EMOJI_PRESS {
        @Override
        public EmojiControllerState toNextState() {
            return END;
        }
    },
    END {
        @Override
        public EmojiControllerState toNextState() {
            return RESET;
        }
    },
    RESET {
        @Override
        public EmojiControllerState toNextState() {
            return FIRST_EMOJI_PRESS;
        }
    }
}
