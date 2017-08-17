package net.mehmetatas.devdb.db;

import net.mehmetatas.devdb.exceptions.BadRequest;
import net.mehmetatas.devdb.exceptions.InternalServerError;
import net.mehmetatas.devdb.exceptions.NotFoundException;
import net.mehmetatas.devdb.filter.expressions.EvalContext;
import net.mehmetatas.devdb.filter.expressions.Expression;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TableImpl implements Table {
    private final String accountId;
    private final String name;
    private final TablePersister persister;
    private final MapSorter sorter;
    private final DbGuard guard;

    public TableImpl(String accountId, String name, TablePersister persister, MapSorter sorter, DbGuard guard) {
        this.name = name;
        this.persister = persister;
        this.sorter = sorter;
        this.guard = guard;
        this.accountId = accountId;
    }

    @Override
    public String insert(Map item) {
        String id = UUID.randomUUID().toString();

        item.put("id", id);

        Map table = persister.read(name, true);

        if (table == null) {
            throw new InternalServerError("Could not create table to insert!");
        }

        guard.ensureCanWrite(accountId, name, table);

        table.put(id, item);
        persister.write(name, table);

        return id;
    }

    @Override
    public void update(String id, Map item) {
        Map table = persister.read(name, false);

        if (table == null || !table.containsKey(id)) {
            throw new NotFoundException("Could not find any item to update!");
        }

        guard.ensureCanWrite(accountId, name, table);

        item.put("id", id);
        table.put(id, item);

        persister.write(name, table);
    }

    @Override
    public void patch(String id, Map item) {
        Map table = persister.read(name, false);

        if (table == null || !table.containsKey(id)) {
            throw new NotFoundException("Could not find any item to patch!");
        }

        guard.ensureCanWrite(accountId, name, table);

        Map existing = (Map) table.get(id);

        patch(item, existing);

        persister.write(name, table);
    }

    private void patch(Map source, Map target) {
        for (Object key : source.keySet()) {
            Object childSource = source.get(key);

            if (childSource instanceof Map) {
                Object existing = target.get(key);

                if (existing instanceof Map) {
                    patch((Map) childSource, (Map) existing);
                    continue;
                }
            }

            target.put(key, childSource);
        }
    }

    @Override
    public void delete(String id) {
        Map table = persister.read(name, false);

        if (table == null || !table.containsKey(id)) {
            throw new NotFoundException("Could not find item to delete!");
        }

        table.remove(id);

        persister.write(name, table);
    }

    @Override
    public void delete() {
        persister.deleteTable(name);
    }

    @Override
    public Map get(String id) {
        Map table = persister.read(name, false);

        if (table == null || !table.containsKey(id)) {
            throw new NotFoundException("Could not find any item with the given id!");
        }

        return (Map) table.get(id);
    }

    @Override
    public Page search(Expression filter, int pageIndex, int pageSize, String sortExpression) {
        Map[] items = search(filter, sortExpression);

        int firstItemIndex = (pageIndex - 1) * pageSize;
        int lastItemIndex = firstItemIndex + pageSize - 1;

        List<Map> result = new LinkedList<>();

        for (int i = firstItemIndex; i <= lastItemIndex && i < items.length; i++) {
            result.add(items[i]);
        }

        Map[] arr = result.toArray(new Map[0]);

        return new PageImpl(arr, pageSize, pageIndex, items.length);
    }

    private Map[] search(Expression filter, String sortExpression) {
        Map table = persister.read(name, false);

        if (table == null) {
            throw new NotFoundException("Could not find table!");
        }

        List<Map> result = new LinkedList<>();

        EvalContext ctx = new EvalContext();

        for (Object item : table.values()) {
            Map map = (Map) item;
            try {
                boolean match = filter == null || (boolean) filter.eval(ctx.setMap(map));
                if (match) {
                    result.add(map);
                }
            } catch (Exception ex) {
                throw new BadRequest("Invalid filter expression!");
            }
        }

        Map[] arr = result.toArray(new Map[0]);

        sort(arr, sortExpression);

        return arr;
    }

    private void sort(Map[] arr, String sortExpression) {
        if (sortExpression == null) {
            return;
        }

        String[] sortProps = sortExpression.split(",");

        for (int i = sortProps.length - 1; i >= 0; i--) {
            String sortProp = sortProps[i].trim();

            char sortChar = sortProp.charAt(sortProp.length() - 1);

            if (sortChar != '+' && sortChar != '-') {
                throw new BadRequest("Invalid sort expression!");
            }

            String prop = sortProp.substring(0, sortProp.length() - 1);

            sorter.sort(arr, prop, sortChar == '-');
        }
    }
}
