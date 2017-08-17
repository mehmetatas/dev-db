package net.mehmetatas.devdb.filter.expressions;

import net.mehmetatas.devdb.filter.parser.FilterBaseVisitor;
import net.mehmetatas.devdb.filter.parser.FilterParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilterExpressionVisitor extends FilterBaseVisitor<Expression> {
    @Override
    public Expression visitNumberExpression(FilterParser.NumberExpressionContext ctx) {
        return ValueExpression.number(ctx.getText());
    }

    @Override
    public Expression visitStringExpression(FilterParser.StringExpressionContext ctx) {
        return ValueExpression.string(ctx.getText());
    }

    @Override
    public Expression visitBoolExpression(FilterParser.BoolExpressionContext ctx) {
        return ValueExpression.bool(ctx.getText());
    }

    @Override
    public Expression visitDateExpression(FilterParser.DateExpressionContext ctx) {
        return ValueExpression.date(ctx.getText());
    }

    @Override
    public Expression visitDateTimeExpression(FilterParser.DateTimeExpressionContext ctx) {
        return ValueExpression.dateTime(ctx.getText());
    }

    @Override
    public Expression visitNullExpression(FilterParser.NullExpressionContext ctx) {
        return ValueExpression.nil();
    }

    @Override
    public Expression visitIdentifierExpression(FilterParser.IdentifierExpressionContext ctx) {
        return new IdentifierExpression(ctx);
    }

    @Override
    public Expression visitEqExpression(FilterParser.EqExpressionContext ctx) {
        return comparison(ctx, ComparisonExpression::eq);
    }

    @Override
    public Expression visitNeqExpression(FilterParser.NeqExpressionContext ctx) {
        return comparison(ctx, ComparisonExpression::neq);
    }

    @Override
    public Expression visitLtExpression(FilterParser.LtExpressionContext ctx) {
        return comparison(ctx, ComparisonExpression::lt);
    }

    @Override
    public Expression visitLteExpression(FilterParser.LteExpressionContext ctx) {
        return comparison(ctx, ComparisonExpression::lte);
    }

    @Override
    public Expression visitGtExpression(FilterParser.GtExpressionContext ctx) {
        return comparison(ctx, ComparisonExpression::gt);
    }

    @Override
    public Expression visitGteExpression(FilterParser.GteExpressionContext ctx) {
        return comparison(ctx, ComparisonExpression::gte);
    }

    private ComparisonExpression comparison(FilterParser.ExpressionContext ctx, ComparisonExpressionFactory factory) {
        Expression left = ctx.children.get(0).accept(this);
        Expression right = ctx.children.get(2).accept(this);
        return factory.create(left, right);
    }

    @FunctionalInterface
    private interface ComparisonExpressionFactory {
        ComparisonExpression create(Expression left, Expression right);
    }

    @Override
    public Expression visitAndExpression(FilterParser.AndExpressionContext ctx) {
        Expression exp1 = ctx.children.get(0).accept(this);
        Expression exp2 = ctx.children.get(2).accept(this);
        return new AndExpression(exp1, exp2);
    }

    @Override
    public Expression visitOrExpression(FilterParser.OrExpressionContext ctx) {
        Expression exp1 = ctx.children.get(0).accept(this);
        Expression exp2 = ctx.children.get(2).accept(this);
        return new OrExpression(exp1, exp2);
    }

    @Override
    public Expression visitParenExpression(FilterParser.ParenExpressionContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Expression visitArray(FilterParser.ArrayContext ctx) {
        List<ParseTree> children = ctx.children;

        if (children.size() == 2) {
            return ArrayExpression.empty();
        }

        List<Expression> itemsExpressions = IntStream.range(0, children.size())
                .filter(n -> n % 2 == 1)
                .mapToObj(children::get)
                .map(this::visit)
                .collect(Collectors.toList());

        return new ArrayExpression(itemsExpressions);
    }

    @Override
    public Expression visitInExpression(FilterParser.InExpressionContext ctx) {
        IdentifierExpression identifier = (IdentifierExpression)ctx.left.accept(this);
        ArrayExpression array = (ArrayExpression)ctx.right.accept(this);
        return new InExpression(identifier, array);
    }

}
