package bomb.modules.il.ice_cream;

import java.util.function.Predicate;

public enum Allergen implements Predicate<Allergen> {
    CHOCOLATE, STRAWBERRY, RASPBERRY, NUTS, COOKIES, MINT,

    FRUIT {
        @Override
        public boolean test(Allergen allergen) {
            return this == allergen || allergen == STRAWBERRY || allergen == RASPBERRY || allergen == CHERRY;
        }
    },

    CHERRY, MARSHMALLOW;

    @Override
    public boolean test(Allergen allergen) {
        return this == allergen;
    }

    public static Allergen getByIndex(int index) {
        return index < 0 || index > 9 ?
                null :
                values()[index];
    }
}
