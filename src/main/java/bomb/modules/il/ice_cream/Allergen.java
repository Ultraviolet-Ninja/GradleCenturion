package bomb.modules.il.ice_cream;

import java.util.EnumSet;
import java.util.function.Predicate;

public enum Allergen implements Predicate<EnumSet<Allergen>> {
    CHOCOLATE, STRAWBERRY, RASPBERRY, NUTS, COOKIES, MINT,

    FRUIT {
        @Override
        public boolean test(EnumSet<Allergen> allergens) {
            return allergens.stream().anyMatch(allergen ->
                    allergen == this || allergen == RASPBERRY || allergen == CHERRY
                            || allergen == STRAWBERRY
            );
        }
    },

    CHERRY, MARSHMALLOW;

    @Override
    public boolean test(EnumSet<Allergen> allergens) {
        return allergens.contains(this);
    }

    public static Allergen getByIndex(int index) {
        return index < 0 || index > 9 ?
                null :
                values()[index];
    }
}
