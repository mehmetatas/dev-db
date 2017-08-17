package net.mehmetatas.devdb.filter.expressions;

public interface Expression {
    Object eval(EvalContext ctx);
}
