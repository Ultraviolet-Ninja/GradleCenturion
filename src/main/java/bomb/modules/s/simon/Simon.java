package bomb.modules.s.simon;

public enum Simon {
    ;
    public enum States {
        RED, YELLOW, GREEN, BLUE
    }

    public enum Screams{
        RED, ORANGE, YELLOW, GREEN, BLUE, MAGENTA;

        private Screams left, right;

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
    }
}
