package bomb.modules.m.murder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.javatuples.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class LocationMapFactory {
    private static final String FILENAME = "location_list.csv";

    static Pair<EnumMap<Suspect, List<Location>>, EnumMap<Weapon, List<Location>>> createMaps()
            throws IOException, CsvException {
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

    private static List<List<Location>> createLocationLists() throws IOException, CsvException {
        InputStream in = Location.class.getResourceAsStream(FILENAME);
        CSVReader reader = new CSVReader(new InputStreamReader(in));

        return reader.readAll()
                .stream()
                .map(Arrays::stream)
                .map(stream -> stream
                        .map(Location::valueOf)
                        .toList()
                )
                .toList();
    }
}
