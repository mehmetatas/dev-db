package net.mehmetatas.devdb.filter.expressions;

public class AndExpression implements Expression {
    private final Expression exp1;
    private final Expression exp2;

    public AndExpression(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Object eval(EvalContext ctx) {
        return (Boolean)exp1.eval(ctx) && (Boolean)exp2.eval(ctx);
    }
}
