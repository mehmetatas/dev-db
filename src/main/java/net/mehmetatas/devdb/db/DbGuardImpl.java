package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.exceptions.Forbidden;
import net.mehmetatas.devdb.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class DbGuardImpl implements DbGuard {
    private final TableCache cache;

    @Value("${devdb.datadir}")
    private String datadir;

    @Value("${devdb.local}")
    private boolean local;

    @Value("${devdb.whitelist}")
    private String whitelist;

    @Value("${devdb.admin-token}")
    private String adminToken;

    public DbGuardImpl(TableCache cache) {
        this.cache = cache;
    }

    public void ensureCanWrite(String accountId, String tableName, Map table) {
        if (isWhiteListed(accountId)) {
            return;
        }

        if (table.size() >= MaxItemsPerTable) {
            throw new Forbidden("A table can have max " + MaxItemsPerTable + " items!");
        }

        if (cache.getSize(accountId, tableName) >= MaxTableSizeBytes) {
            throw new Forbidden("A table can be max " + MaxTableSizeBytes / (1024 * 1024) + " Mb!");
        }
    }

    public void ensureAccountTableCount(String accountId) {
        if (isWhiteListed(accountId)) {
            return;
        }

        File file = new File(datadir);

        File[] accountFiles = file.listFiles((dir, name) -> name.startsWith(accountId));

        int tableCount = accountFiles == null ? 0 : accountFiles.length;

        if (tableCount >= MaxTablesPerAccount) {
            throw new Forbidden("An account have max " + MaxTablesPerAccount + " tables!");
        }
    }

    public void ensureAccountIdRegistered(String accountId) {
        if (isWhiteListed(accountId)) {
            return;
        }

        // todo: for now we do not check whether the account ids are registered.
        boolean accountExists = true;

        if (!accountExists) {
            throw new UnauthorizedException("Invalid account id!");
        }
    }

    public boolean isWhiteListed(String accountId) {
        if (accountId == null) {
            return false;
        }

        return local || accountId.equals(adminToken) || whitelist.contains(accountId);
    }
}
