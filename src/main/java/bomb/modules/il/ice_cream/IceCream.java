package bomb.modules.il.ice_cream;

import bomb.Widget;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.Widget.IndicatorFilter.UNLIT;
import static bomb.modules.il.ice_cream.Flavor.COOKIES_N_CREAM;
import static bomb.modules.il.ice_cream.Flavor.DOUBLE_CHOCOLATE;
import static bomb.modules.il.ice_cream.Flavor.DOUBLE_STRAWBERRY;
import static bomb.modules.il.ice_cream.Flavor.MINT_CHIP;
import static bomb.modules.il.ice_cream.Flavor.NEAPOLITAN;
import static bomb.modules.il.ice_cream.Flavor.RASPBERRY_RIPPLE;
import static bomb.modules.il.ice_cream.Flavor.ROCKY_ROAD;
import static bomb.modules.il.ice_cream.Flavor.THE_CLASSIC;
import static bomb.modules.il.ice_cream.Flavor.TUTTI_FRUTTI;
import static bomb.modules.il.ice_cream.Flavor.VANILLA;

public class IceCream extends Widget {
    public static Flavor findFlavor(Person person, EnumSet<Flavor> possibleFlavors, boolean hasEmptyPortPlate)
            throws IllegalArgumentException, IllegalStateException {
        validateInput(possibleFlavors);

        int index = createIndexFromSerialCode();
        List<Flavor> popularityRanking = createPopularFlavorList(hasEmptyPortPlate);
        EnumMap<Person, EnumSet<Allergen>> personMap = Person.getPersonAllergens(index);
        EnumSet<Allergen> personAllergens = personMap.get(person);
        possibleFlavors.removeIf(flavor ->
                doesFlavorHaveAllergens(flavor.getAllergens(), personAllergens));

        if (possibleFlavors.isEmpty()) return VANILLA;
        if (possibleFlavors.size() == 1) return possibleFlavors.iterator().next();

        int choice = possibleFlavors.stream()
                .mapToInt(popularityRanking::indexOf)
                .min()
                .orElseThrow(IllegalStateException::new);

        return popularityRanking.get(choice);
    }

    private static int createIndexFromSerialCode() {
        return (serialCode.charAt(5) - '0') / 2;
    }

    private static boolean doesFlavorHaveAllergens(EnumSet<Allergen> flavorAllergens,
                                                   EnumSet<Allergen> personAllergens) {
        for (Allergen personAllergen : personAllergens) {
            if (personAllergen.test(flavorAllergens))
                return true;
        }
        return false;
    }

    private static List<Flavor> createPopularFlavorList(boolean hasEmptyPortPlate) {
        if (countIndicators(LIT) > countIndicators(UNLIT)) {
            return List.of(
                    COOKIES_N_CREAM, NEAPOLITAN, TUTTI_FRUTTI, THE_CLASSIC, ROCKY_ROAD,
                    DOUBLE_CHOCOLATE, MINT_CHIP, DOUBLE_STRAWBERRY, RASPBERRY_RIPPLE
            );
        } else if (hasEmptyPortPlate) {
            return List.of(
                    DOUBLE_CHOCOLATE, MINT_CHIP, NEAPOLITAN, ROCKY_ROAD, TUTTI_FRUTTI,
                    DOUBLE_STRAWBERRY, COOKIES_N_CREAM, RASPBERRY_RIPPLE, THE_CLASSIC
            );
        } else if (getAllBatteries() > 3) {
            return List.of(
                    NEAPOLITAN, TUTTI_FRUTTI, COOKIES_N_CREAM, RASPBERRY_RIPPLE,
                    DOUBLE_STRAWBERRY, MINT_CHIP, DOUBLE_CHOCOLATE, THE_CLASSIC, ROCKY_ROAD
            );
        } else {
            return List.of(
                    DOUBLE_STRAWBERRY, COOKIES_N_CREAM, ROCKY_ROAD, NEAPOLITAN,
                    DOUBLE_CHOCOLATE, TUTTI_FRUTTI, RASPBERRY_RIPPLE, MINT_CHIP
            );
        }
    }

    private static void validateInput(EnumSet<Flavor> possibleFlavors) throws IllegalArgumentException {
        checkSerialCode();
        if (possibleFlavors.contains(VANILLA))
            throw new IllegalArgumentException("Cannot have Vanilla in the set");
        if (possibleFlavors.size() != 4)
            throw new IllegalArgumentException("Need 4 flavors to work");
    }
}
