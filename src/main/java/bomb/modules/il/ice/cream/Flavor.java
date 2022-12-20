package bomb.modules.il.ice.cream;

import java.util.EnumSet;

import static bomb.modules.il.ice.cream.Allergen.CHERRY;
import static bomb.modules.il.ice.cream.Allergen.CHOCOLATE;
import static bomb.modules.il.ice.cream.Allergen.COOKIES;
import static bomb.modules.il.ice.cream.Allergen.FRUIT;
import static bomb.modules.il.ice.cream.Allergen.MARSHMALLOW;
import static bomb.modules.il.ice.cream.Allergen.MINT;
import static bomb.modules.il.ice.cream.Allergen.NUTS;
import static bomb.modules.il.ice.cream.Allergen.RASPBERRY;
import static bomb.modules.il.ice.cream.Allergen.STRAWBERRY;

public enum Flavor {
    TUTTI_FRUTTI(FRUIT),
    ROCKY_ROAD(CHOCOLATE, NUTS, MARSHMALLOW),
    RASPBERRY_RIPPLE(RASPBERRY),
    DOUBLE_CHOCOLATE(CHOCOLATE),
    DOUBLE_STRAWBERRY(STRAWBERRY),
    COOKIES_N_CREAM(COOKIES),
    NEAPOLITAN(STRAWBERRY, CHOCOLATE),
    MINT_CHIP(MINT, CHOCOLATE),
    THE_CLASSIC(CHOCOLATE, CHERRY),
    VANILLA;

    private final EnumSet<Allergen> allergens;

    Flavor() {
        allergens = EnumSet.noneOf(Allergen.class);
    }

    Flavor(Allergen a1) {
        allergens = EnumSet.of(a1);
    }

    Flavor(Allergen a1, Allergen a2) {
        allergens = EnumSet.of(a1, a2);
    }

    Flavor(Allergen a1, Allergen a2, Allergen a3) {
        allergens = EnumSet.of(a1, a2, a3);
    }

    public EnumSet<Allergen> getAllergens() {
        return allergens;
    }
}
