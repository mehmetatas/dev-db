package net.mehmetatas.devdb.db;

import java.util.Map;

public interface MapSorter {
    void sort(Map[] items, final String prop, final boolean desc);
}
