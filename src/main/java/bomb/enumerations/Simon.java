package bomb.enumerations;

import bomb.interfaces.Index;

public enum Simon {
    ;
    public enum States implements Index {
        RED(0), YELLOW(1), GREEN(2), BLUE(3);

        private final int idx;

        @Override
        public int getIdx() {
            return idx;
        }

        States(int idx){
            this.idx = idx;
        }
    }

    public enum Screams implements Index{
        RED(0), ORANGE(1), YELLOW(2),
        GREEN(3), BLUE(4), MAGENTA(5);

        private final int idx;
        private Screams left, right;

        Screams(int idx){
            this.idx = idx;
        }

        public Screams getLeft() {
            return left;
        }

        public Screams getRight() {
            return right;
        }

        public void setLeft(Screams left){
            this.left = left;
        }

        public void setRight(Screams right) {
            this.right = right;
        }

        @Override
        public int getIdx() {
            return idx;
        }
    }
}
