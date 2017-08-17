package net.mehmetatas.devdb.server;

import net.mehmetatas.devdb.db.DbGuard;
import net.mehmetatas.devdb.exceptions.BadRequest;
import net.mehmetatas.devdb.filter.expressions.Expression;
import net.mehmetatas.devdb.filter.expressions.FilterExpressionVisitor;
import net.mehmetatas.devdb.filter.parser.FilterLexer;
import net.mehmetatas.devdb.filter.parser.FilterParser;
import net.mehmetatas.devdb.json.Json;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class RequestContext {
    public final HttpServletRequest request;
    private final HttpServletResponse response;
    private final DbGuard guard;

    private final static int maxRequestSize = 1024 * 1024; // 1Mb

    @Value("${devdb.local}")
    private boolean local;

    @Autowired
    public RequestContext(@SuppressWarnings("SpringJavaAutowiringInspection") HttpServletRequest request, HttpServletResponse response, DbGuard guard) {
        this.request = request;
        this.response = response;
        this.guard = guard;
    }

    public String getAccountId() {
        return request.getHeader("X-DevDb-AccountId");
    }

    public Map getBody() {
        if (request.getMethod().equals("GET") || request.getMethod().equals("DELETE")) {
            return null;
        }

        try {
            UUID.fromString(getAccountId());

            if (!guard.isWhiteListed(getAccountId()) && request.getContentLength() > maxRequestSize) {
                throw new BadRequest(String.format("Request body cannot be more than %s Kb!", maxRequestSize / 1024));
            }

            String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            return Json.instance.deserialize(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequest("Unable to process request: " + e.getMessage());
        }
    }

    public Expression getFilterExpression() {
        String filter = getStringParam("filter");

        if (filter == null) {
            return null;
        }

        try {
            CharStream input = new ANTLRInputStream(new StringReader(filter));

            FilterLexer lexer = new FilterLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FilterParser parser = new FilterParser(tokens);
            ParseTree tree = parser.expression();

            FilterExpressionVisitor visitor = new FilterExpressionVisitor();
            return visitor.visit(tree);
        } catch (IOException ex) {
            throw new RuntimeException("Could not parse expression: " + ex.getMessage(), ex);
        }
    }

    public int getPageIndex() {
        return getIntParam("pageIndex", 1);
    }

    public int getPageSize() {
        return getIntParam("pageSize", DbGuard.MaxItemsPerTable);
    }

    public String getSortExpression() {
        String orderby = getStringParam("orderby");

        // When called from javascript and orderby is encoded with encodeURICOmpoenent
        // getParameter already decodes %2B into plus ('+')
        // decoding it one more time using URLDecoder causes '+' to turn into ' '
        // When called from java (FunctionalTests) this does not happen.
        // And I do not really care about the root cause.
        if (orderby != null && orderby.endsWith(" ")) {
            orderby = orderby.substring(0, orderby.length() - 1) + "+";
        }

        return orderby;
    }

    private String getStringParam(String paramName) {
        String paramValue = request.getParameter(paramName);

        if (paramValue == null || paramValue.trim().length() == 0) {
            return null;
        }

        try {
            return URLDecoder.decode(paramValue, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Could not decode query param " + paramValue + ": " + e.getMessage(), e);
        }
    }

    private int getIntParam(String paramName, int defaultValue) {
        int res;

        try {
            res = Integer.parseInt(request.getParameter(paramName));

            if (res <= 0) {
                res = defaultValue;
            }
        } catch (Exception ex) {
            res = defaultValue;
        }

        return res;
    }

    public void setResponseHeader(String key, String value) {
        response.setHeader(key, value);
    }
}
