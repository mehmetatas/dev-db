package net.mehmetatas.devdb.filter.expressions;

import java.util.*;

public class ArrayExpression implements Expression {
    private final static ArrayExpression EmptyArrayExpression = new ArrayExpression();

    private final List<Expression> itemsExpressions;
    private Object[] array;

    private ArrayExpression() {
        itemsExpressions = new LinkedList<>();
        array = new Object[0];
    }

    public ArrayExpression(List<Expression> itemsExpressions) {
        this.itemsExpressions = new LinkedList<>(itemsExpressions);
    }

    @Override
    public Object[] eval(EvalContext ctx) {
        if (array == null) {
            array = itemsExpressions.stream()
                    .map(expression -> expression.eval(ctx))
                    .toArray();
        }
        return array;
    }

    public static ArrayExpression empty() {
        return EmptyArrayExpression;
    }
}
