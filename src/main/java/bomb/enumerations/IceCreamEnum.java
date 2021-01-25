package bomb.enumerations;

import bomb.interfaces.Index;
import bomb.interfaces.Labeled;

public enum IceCreamEnum {
    ;
    public enum Person implements Index {
        ;

        private final int idx;

        Person(int idx){
            this.idx = idx;
        }

        @Override
        public int getIdx() {
            return idx;
        }
    }

    public enum Flavor implements Labeled {
        ;

        private final String label;

        Flavor(String label){
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }
}
