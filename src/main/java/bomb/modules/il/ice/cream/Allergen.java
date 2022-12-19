package bomb.modules.il.ice.cream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public enum Allergen implements Predicate<EnumSet<Allergen>> {
    CHOCOLATE, STRAWBERRY, RASPBERRY, NUTS, COOKIES, MINT,

    FRUIT {
        @Override
        public boolean test(@NotNull EnumSet<Allergen> allergens) {
            return allergens.stream().anyMatch(allergen ->
                    allergen == this || allergen == RASPBERRY || allergen == CHERRY
                            || allergen == STRAWBERRY
            );
        }
    },

    CHERRY, MARSHMALLOW;

    private static final Allergen[] ALLERGENS = values();

    @Override
    public boolean test(@NotNull EnumSet<Allergen> allergens) {
        return allergens.contains(this);
    }

    @Contract(pure = true)
    public static @Nullable Allergen getByIndex(int index) {
        return index < 0 || index > 9 ?
                null :
                ALLERGENS[index];
    }
}
