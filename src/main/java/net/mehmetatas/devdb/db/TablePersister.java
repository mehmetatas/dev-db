package net.mehmetatas.devdb.db;

import java.util.Map;

public interface TablePersister {
    Map read(String tableName, boolean create);

    void write(String tableName, Map table);

    void deleteTable(String tableName);
}

