package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.filter.expressions.Expression;

import java.util.Map;

public interface Table {
    String insert(Map map);

    void update(String id, Map map);

    void patch(String id, Map map);

    void delete(String id);

    void delete();

    Map get(String id);

    Page search(Expression filter, int pageIndex, int pageSize, String sortExpression);
}
