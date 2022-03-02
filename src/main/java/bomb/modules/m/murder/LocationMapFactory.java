package bomb.modules.m.murder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("ConstantConditions")
public class LocationMapFactory {
    private static final String FILENAME = "location_list.csv";

    static Pair<EnumMap<Suspect, List<Location>>, EnumMap<Weapon, List<Location>>> createMaps()
            throws IllegalStateException {
        EnumMap<Suspect, List<Location>> suspectMap = new EnumMap<>(Suspect.class);
        EnumMap<Weapon, List<Location>> weaponMap = new EnumMap<>(Weapon.class);

        Suspect[] suspects = Suspect.values();
        Weapon[] weapons = Weapon.values();
        List<List<Location>> locationOrderLists = createLocationLists();
        List<Location> list;

        int size = weapons.length;
        for (int i = 0; i < size; i++) {
            list = locationOrderLists.get(i);
            suspectMap.put(suspects[i], list);
            weaponMap.put(weapons[i], list);
        }

        return new Pair<>(suspectMap, weaponMap);
    }

    private static List<List<Location>> createLocationLists() throws IllegalStateException {
        InputStream in = Location.class.getResourceAsStream(FILENAME);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(in, UTF_8))) {
            return csvReader.readAll()
                    .stream()
                    .map(Arrays::stream)
                    .map(stream -> stream
                            .map(Location::valueOf)
                            .toList()
                    )
                    .toList();
        } catch (IOException | CsvException e) {
            throw new IllegalStateException(e);
        }
    }
}
