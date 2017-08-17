package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.filter.expressions.Expression;

import java.util.Map;

public interface Db {
    String insert(String tableName, Map map);

    void update(String tableName, String id, Map map);

    void patch(String tableName, String id, Map map);

    void delete(String tableName, String id);

    void delete(String tableName);

    Map get(String tableName, String id);

    Page search(String tableName, Expression filter, int pageIndex, int pageSize, String sortExpression);
}
