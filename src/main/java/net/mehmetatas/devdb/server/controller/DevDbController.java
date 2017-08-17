package net.mehmetatas.devdb.server.controller;

import net.mehmetatas.devdb.db.Page;
import net.mehmetatas.devdb.db.Db;
import net.mehmetatas.devdb.server.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@RestController
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class DevDbController {
    private final Db db;
    private final RequestContext ctx;

    @Autowired
    public DevDbController(Db db, RequestContext ctx) {
        this.db = db;
        this.ctx = ctx;
    }

    @RequestMapping(value = "/{table:^[a-z][a-z|\\-|\\d]*$}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@PathVariable String table) {
        String id = db.insert(table, ctx.getBody());
        ctx.setResponseHeader("X-DevDb-Id", id);
    }

    @RequestMapping(value = "/{table:^[a-z][a-z|\\-|\\d]*$}/{id:^[0-9a-f]{8}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{12}$}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String table, @PathVariable String id) {
        db.update(table, id, ctx.getBody());
    }

    @RequestMapping(value = "/{table:^[a-z][a-z|\\-|\\d]*$}/{id:^[0-9a-f]{8}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{12}$}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void patch(@PathVariable String table, @PathVariable String id) {
        db.patch(table, id, ctx.getBody());
    }

    @RequestMapping(value = "/{table:^[a-z][a-z\\-\\d]*$}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTable(@PathVariable String table) {
        db.delete(table);
    }

    @RequestMapping(value = "/{table:^[a-z][a-z|\\-|\\d]*$}/{id:^[0-9a-f]{8}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{12}$}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String table, @PathVariable String id) {
        db.delete(table, id);
    }

    @RequestMapping(value ={ "/{table:^[a-z][a-z|\\-|\\d]*$}"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page search(@PathVariable String table) {
        return db.search(table, ctx.getFilterExpression(), ctx.getPageIndex(), ctx.getPageSize(), ctx.getSortExpression());
    }

    @RequestMapping(value = "/{table:^[a-z][a-z|\\-|\\d]*$}/{id:^[0-9a-f]{8}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{4}[-][0-9a-f]{12}$}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map get(@PathVariable String table, @PathVariable String id) {
        return db.get(table, id);
    }
}
