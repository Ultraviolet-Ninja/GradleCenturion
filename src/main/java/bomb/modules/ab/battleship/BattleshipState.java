package bomb.modules.ab.battleship;

import bomb.abstractions.State;

public enum BattleshipState implements State<BattleshipState> {
    RADAR_MODE{
        @Override
        public BattleshipState nextState() {
            return DEFUSER_CONFIRMATION;
        }
    },
    DEFUSER_CONFIRMATION{
        @Override
        public BattleshipState nextState() {
            return SOLVE_MODE;
        }
    },
    SOLVE_MODE{
        @Override
        public BattleshipState nextState() {
            return RADAR_MODE;
        }
    }
}
