package net.mehmetatas.devdb.db;

import java.util.Map;

public interface TableCache {
    int CacheDuration = 1000 * 60 * 60; // 1 hour in millis
    int MaxItemsInCache = 50; // Max 50 tables are cached

    Map get(String accountId, String tableName);

    int getSize(String accountId, String tableName);

    void put(String accountId, String tableName, Map table, int size);

    void remove(String accountId, String tableName);
}
