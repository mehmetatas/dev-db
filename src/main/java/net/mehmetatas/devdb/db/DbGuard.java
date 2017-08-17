package net.mehmetatas.devdb.db;

import java.util.Map;

public interface DbGuard {
    int MaxTableSizeBytes = 1024 * 1024; // a table can be max 1 Mb
    int MaxItemsPerTable = 1000; // a table can have max 1000 items
    int MaxTablesPerAccount = 50; // an account can have max 50 tables

    void ensureAccountTableCount(String accountId);

    void ensureCanWrite(String accountId, String tableName, Map table);

    void ensureAccountIdRegistered(String accountId);

    boolean isWhiteListed(String accountId);
}
