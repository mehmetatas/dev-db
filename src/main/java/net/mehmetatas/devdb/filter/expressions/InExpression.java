package net.mehmetatas.devdb.filter.expressions;

public class InExpression implements Expression {
    private final IdentifierExpression identifier;
    private final ArrayExpression array;

    public InExpression(IdentifierExpression identifier, ArrayExpression array) {
        this.identifier = identifier;
        this.array = array;
    }

    @Override
    public Object eval(EvalContext ctx) {
        Object val = identifier.eval(ctx);
        Object[] arr = array.eval(ctx);

        for (Object item : arr) {
            if (ComparisonExpression.eq(ctx, val, item)) {
                return true;
            }
        }

        return false;
    }
}
