package bomb.modules.m.murder;

import bomb.Widget;
import com.opencsv.exceptions.CsvException;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.TRN;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.SERIAL;
import static bomb.modules.m.murder.Location.BILLIARD_ROOM;
import static bomb.modules.m.murder.Location.CONSERVATORY;
import static bomb.modules.m.murder.Location.DINING_ROOM;
import static bomb.modules.m.murder.Location.HALL;
import static bomb.modules.m.murder.Location.LOUNGE;
import static bomb.modules.m.murder.Location.STUDY;
import static bomb.tools.string.StringFormat.TO_TITLE_CASE;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class Murder extends Widget {
    public static String solve(Location bodyFoundRoom, @NotNull EnumSet<Weapon> possibleWeapons,
                               @NotNull EnumSet<Suspect> possibleSuspects) throws IllegalArgumentException {
        validateInput(possibleWeapons, possibleSuspects, bodyFoundRoom);

        Pair<EnumMap<Suspect, List<Location>>, EnumMap<Weapon, List<Location>>> mapPair;

        try {
            mapPair = LocationMapFactory.createMaps();
        } catch (IOException | CsvException e) {
            throw new IllegalArgumentException(e);
        }

        EnumMap<Location, Suspect> locationsToSuspect = getLocationsToType(mapPair.getValue0(),
                possibleSuspects, getSuspectRow(bodyFoundRoom) - 1);
        EnumMap<Location, Weapon> locationsToWeapon = getLocationsToType(mapPair.getValue1(),
                possibleWeapons, getWeaponRow(bodyFoundRoom) - 1);

        Triplet<Suspect, Weapon, Location> tuple = findIntersection(locationsToSuspect, locationsToWeapon);
        return Stream.of(tuple.getValue0(), tuple.getValue1(), tuple.getValue2())
                .map(Enum::name)
                .map(TO_TITLE_CASE)
                .collect(joining(" - "));
    }

    private static Triplet<Suspect, Weapon, Location> findIntersection(EnumMap<Location, Suspect> locationsToSuspect,
                                                                       EnumMap<Location, Weapon> locationsToWeapon) {
        return locationsToSuspect.entrySet()
                .stream()
                .filter(entry -> locationsToWeapon.containsKey(entry.getKey()))
                .map(entry -> {
                    Weapon murderWeapon = locationsToWeapon.get(entry.getKey());
                    return new Triplet<>(entry.getValue(), murderWeapon, entry.getKey());
                })
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static <T extends Enum<T>> EnumMap<Location, T> getLocationsToType(EnumMap<T, List<Location>> map,
                                                                               EnumSet<T> possibilities, int row) {
        return map.entrySet()
                .stream().filter(entry -> possibilities.contains(entry.getKey()))
                .collect(toMap(
                        entry -> entry.getValue().get(row),
                        Map.Entry::getKey,
                        (l, r) -> {
                            throw new IllegalArgumentException("Duplicate keys " + l + "and " + r + ".");
                        },
                        () -> new EnumMap<>(Location.class)
                ));
    }

    private static int getSuspectRow(Location bodyFoundRoom) {
        if (hasLitIndicator(TRN)) return 5;
        if (bodyFoundRoom == DINING_ROOM) return 7;
        if (hasMorePortsThanSpecified(RCA, 1)) return 8;
        if (numDBatteries == 0) return 2;
        if (bodyFoundRoom == STUDY) return 4;
        if (getAllBatteries() >= 5) return 9;
        if (hasUnlitIndicator(FRQ)) return 1;
        if (bodyFoundRoom == CONSERVATORY) return 3;
        return 6;
    }

    private static int getWeaponRow(Location bodyFoundRoom) {
        if (bodyFoundRoom == LOUNGE) return 3;
        if (getAllBatteries() >= 5) return 1;
        if (doesPortExists(SERIAL)) return 9;
        if (bodyFoundRoom == BILLIARD_ROOM) return 4;
        if (getAllBatteries() == 0) return 6;
        if (countIndicators(LIT) == 0) return 5;
        if (bodyFoundRoom == HALL) return 7;
        if (hasMorePortsThanSpecified(RCA, 1)) return 2;
        return 8;
    }

    private static void validateInput(EnumSet<Weapon> possibleWeapons, EnumSet<Suspect> possibleSuspects,
                                      Location bodyFoundRoom) throws IllegalArgumentException {
        if (possibleSuspects.size() != 4 || possibleWeapons.size() != 4)
            throw new IllegalArgumentException("Insufficient amount of possible weapons and/or suspects");
        if (bodyFoundRoom == null)
            throw new IllegalArgumentException("The room where the body was found is needed");
    }
}
