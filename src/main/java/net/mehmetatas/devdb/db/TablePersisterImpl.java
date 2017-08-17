package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.json.Json;
import net.mehmetatas.devdb.server.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class TablePersisterImpl implements TablePersister {
    private final TableCache cache;
    private final DbGuard guard;
    private final String accountId;

    @Value("${devdb.datadir}")
    private String datadir;

    @Autowired
    public TablePersisterImpl(TableCache cache, DbGuard guard, RequestContext ctx) {
        this.cache = cache;
        this.guard = guard;
        this.accountId = ctx.getAccountId();
    }

    @Override
    public Map read(String tableName, boolean create) {
        Map table = cache.get(accountId, tableName);

        if (table != null) {
            return table;
        }

        String json = loadFromFile(tableName, create);

        if (json == null) {
            return null;
        }

        table = Json.instance.deserialize(json);

        if (table == null) {
            return null;
        }

        cache.put(accountId, tableName, table, json.length());

        return table;
    }

    @Override
    public void write(String tableName, Map table) {
        String json = Json.instance.serialize(table);

        try {
            byte[] bytes = json.getBytes("utf-8");

            cache.put(accountId, tableName, table, bytes.length);

            Files.write(Paths.get(getFilePath(tableName)), bytes);
        } catch (IOException e) {
            System.err.println("Could not save file : " + tableName + " [IOException] " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTable(String tableName) {
        cache.remove(accountId, tableName);

        try {
            Files.delete(Paths.get(getFilePath(tableName)));
        } catch (IOException e) {
            System.err.println("Could not delete file : " + tableName + " [IOException] " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String loadFromFile(String tableName, boolean create) {
        File file = new File(getFilePath(tableName));

        if (!file.exists()) {
            if (!create) {
                return null;
            }

            guard.ensureAccountTableCount(accountId);

            try {
                if (!file.createNewFile()) {
                    return null;
                }
                return "{}";
            } catch (IOException e) {
                System.err.println("Could not create file : " + tableName + " [IOException] " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
            return new String(encoded, "utf-8");
        } catch (IOException e) {
            System.err.println("Could not read file : " + tableName + " [IOException] " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private String getFilePath(String tableName) {
        return datadir + accountId + "_" + tableName + ".json";
    }
}
