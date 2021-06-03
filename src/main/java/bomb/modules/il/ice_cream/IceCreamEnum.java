package bomb.modules.il.ice_cream;

import bomb.interfaces.Labeled;

public enum IceCreamEnum {
    ;
    public enum Person {
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
