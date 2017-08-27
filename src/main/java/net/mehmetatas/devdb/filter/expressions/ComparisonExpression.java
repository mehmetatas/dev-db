package net.mehmetatas.devdb.filter.expressions;

public class ComparisonExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final Comparer comparer;

    private ComparisonExpression(Expression left, Expression right, Comparer comparer) {
        this.left = left;
        this.right = right;
        this.comparer = comparer;
    }

    @Override
    public Object eval(EvalContext ctx) {
        Object val1 = left.eval(ctx);
        Object val2 = right.eval(ctx);

        return comparer.compare(ctx, val1, val2);
    }

    @FunctionalInterface
    private interface Comparer {
        Boolean compare(EvalContext ctx, Object obj1, Object obj2);
    }

    private static boolean neq(EvalContext ctx, Object o1, Object o2) {
        return !eq(ctx, o1, o2);
    }

    static boolean eq(EvalContext ctx, Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }

        if (o1 == null || o2 == null) {
            return false;
        }

        if (o1 instanceof Number && o2 instanceof Number) {
            o1 = ((Number) o1).doubleValue();
            o2 = ((Number) o2).doubleValue();
        }

        if (!o1.getClass().equals(o2.getClass())) {
            return false;
        }

        if (!(o1 instanceof String && o2 instanceof String)) {
            return compare((Comparable) o1, (Comparable) o2, 0);
        }

        String val = ((String) o1).toLowerCase(ctx.getLocale());
        String str = ((String) o2).toLowerCase(ctx.getLocale());

        boolean sw = str.startsWith("*");
        boolean ew = str.endsWith("*");

        if (sw && ew) {
            str = str.substring(1, str.length() - 1);
            return val.contains(str);
        } else if (sw) {
            str = str.substring(1);
            return val.endsWith(str);
        } else if (ew) {
            str = str.substring(0, str.length() - 1);
            return val.startsWith(str);
        }

        return val.equals(str);
    }

    private static boolean lt(EvalContext ctx, Object o1, Object o2) {
        return canCompare(o1, o2) && compare((Comparable) o1, (Comparable) o2, -1);
    }

    private static boolean gt(EvalContext ctx, Object o1, Object o2) {
        return canCompare(o1, o2) && compare((Comparable) o1, (Comparable) o2, 1);
    }

    private static boolean lte(EvalContext ctx, Object o1, Object o2) {
        return canCompare(o1, o2) && !gt(ctx, o1, o2);
    }

    private static boolean gte(EvalContext ctx, Object o1, Object o2) {
        return canCompare(o1, o2) && !lt(ctx, o1, o2);
    }

    private static boolean canCompare(Object o1, Object o2) {
        if (!(o1 instanceof Comparable && o2 instanceof Comparable)) {
            return false;
        }

        if (o1.getClass().equals(o2.getClass())) {
            return true;
        }

        return o1 instanceof Number && o2 instanceof Number;
    }

    private static boolean compare(Comparable c1, Comparable c2, int expected) {
        if (c1 instanceof Number && c2 instanceof Number) {
            c1 = ((Number) c1).doubleValue();
            c2 = ((Number) c2).doubleValue();
        }

        int result = c1.compareTo(c2);

        return result == expected ||
                expected < 0 && result < 0 ||
                expected > 0 && result > 0;
    }

    public static ComparisonExpression eq(Expression left, Expression right) {
        return new ComparisonExpression(left, right, ComparisonExpression::eq);
    }

    public static ComparisonExpression neq(Expression left, Expression right) {
        return new ComparisonExpression(left, right, ComparisonExpression::neq);
    }

    public static ComparisonExpression lt(Expression left, Expression right) {
        return new ComparisonExpression(left, right, ComparisonExpression::lt);
    }

    public static ComparisonExpression lte(Expression left, Expression right) {
        return new ComparisonExpression(left, right, ComparisonExpression::lte);
    }

    public static ComparisonExpression gt(Expression left, Expression right) {
        return new ComparisonExpression(left, right, ComparisonExpression::gt);
    }

    public static ComparisonExpression gte(Expression left, Expression right) {
        return new ComparisonExpression(left, right, ComparisonExpression::gte);
    }
}
