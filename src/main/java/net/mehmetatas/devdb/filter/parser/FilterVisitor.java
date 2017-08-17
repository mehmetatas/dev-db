// Generated from D:/Code/github/mehmetatas/dev-db/src/main/antlr\Filter.g4 by ANTLR 4.7
package net.mehmetatas.devdb.filter.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FilterParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FilterVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtExpression(FilterParser.LtExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(FilterParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code neqExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeqExpression(FilterParser.NeqExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExpression(FilterParser.ValueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExpression(FilterParser.EqExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gtExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtExpression(FilterParser.GtExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(FilterParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInExpression(FilterParser.InExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpression(FilterParser.IdentifierExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lteExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLteExpression(FilterParser.LteExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpression(FilterParser.ParenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gteExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGteExpression(FilterParser.GteExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FilterParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(FilterParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpression(FilterParser.NumberExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpression(FilterParser.StringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpression(FilterParser.BoolExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullExpression(FilterParser.NullExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dateExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateExpression(FilterParser.DateExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dateTimeExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateTimeExpression(FilterParser.DateTimeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpression(FilterParser.ArrayExpressionContext ctx);
}