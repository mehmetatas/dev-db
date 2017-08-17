package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.json.Json;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class MapSorterImpl implements MapSorter {
    public void sort(Map[] items, final String prop, final boolean desc) {
        Arrays.sort(items, (item1, item2) -> {
            Comparable val1 = (Comparable) Json.get(item1, prop);
            Comparable val2 = (Comparable) Json.get(item2, prop);

            if (val1 == null && val2 == null) {
                return 0;
            }

            if (val1 == null) {
                return 1;
            }

            if (val2 == null) {
                return -1;
            }

            if (val1.getClass() != val2.getClass() || val1.getClass() == String.class) {
                val1 = val1.toString().toLowerCase();
                val2 = val2.toString().toLowerCase();
            }

            return desc
                    ? val2.compareTo(val1)
                    : val1.compareTo(val2);
        });
    }
}

