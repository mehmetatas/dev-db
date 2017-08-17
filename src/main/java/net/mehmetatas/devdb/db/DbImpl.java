package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.filter.expressions.Expression;
import net.mehmetatas.devdb.server.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class DbImpl implements Db {
    private final TablePersister persister;
    private final MapSorter sorter;
    private final DbGuard guard;
    private final String accountId;

    public DbImpl(TablePersister persister, MapSorter sorter, DbGuard guard, RequestContext ctx) {
        this.persister = persister;
        this.sorter = sorter;
        this.guard = guard;
        this.accountId = ctx.getAccountId();
    }

    @Override
    public String insert(String tableName, Map map) {
        return getTable(tableName).insert(map);
    }

    @Override
    public void update(String tableName, String id, Map map) {
        getTable(tableName).update(id, map);
    }

    @Override
    public void patch(String tableName, String id, Map map) {
        getTable(tableName).patch(id, map);
    }

    @Override
    public void delete(String tableName, String id) {
        getTable(tableName).delete(id);
    }

    @Override
    public void delete(String tableName) {
        getTable(tableName).delete();
    }

    @Override
    public Map get(String tableName, String id) {
        return getTable(tableName).get(id);
    }

    @Override
    public Page search(String tableName, Expression filter, int pageIndex, int pageSize, String sortExpression) {
        return getTable(tableName).search(filter, pageIndex, pageSize, sortExpression);
    }

    private Table getTable(String tableName) {
        return new TableImpl(accountId, tableName, persister, sorter, guard);
    }
}
