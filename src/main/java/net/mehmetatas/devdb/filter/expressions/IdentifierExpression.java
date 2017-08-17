package net.mehmetatas.devdb.filter.expressions;

import net.mehmetatas.devdb.filter.parser.FilterParser;
import net.mehmetatas.devdb.json.Json;

public class IdentifierExpression implements Expression {
    private final String identifier;

    public IdentifierExpression(FilterParser.IdentifierExpressionContext ctx) {
        this.identifier = ctx.getText();
    }

    @Override
    public Object eval(EvalContext ctx) {
        return Json.get(ctx.getMap(), identifier);
    }
}
