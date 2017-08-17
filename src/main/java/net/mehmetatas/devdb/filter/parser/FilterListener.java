// Generated from D:/Code/github/mehmetatas/dev-db/src/main/antlr\Filter.g4 by ANTLR 4.7
package net.mehmetatas.devdb.filter.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FilterParser}.
 */
public interface FilterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLtExpression(FilterParser.LtExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLtExpression(FilterParser.LtExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(FilterParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(FilterParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code neqExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNeqExpression(FilterParser.NeqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code neqExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNeqExpression(FilterParser.NeqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpression(FilterParser.ValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpression(FilterParser.ValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpression(FilterParser.EqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpression(FilterParser.EqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gtExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGtExpression(FilterParser.GtExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gtExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGtExpression(FilterParser.GtExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(FilterParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(FilterParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInExpression(FilterParser.InExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInExpression(FilterParser.InExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(FilterParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(FilterParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lteExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLteExpression(FilterParser.LteExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lteExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLteExpression(FilterParser.LteExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(FilterParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(FilterParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gteExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGteExpression(FilterParser.GteExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gteExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGteExpression(FilterParser.GteExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FilterParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(FilterParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(FilterParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpression(FilterParser.NumberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpression(FilterParser.NumberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(FilterParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(FilterParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpression(FilterParser.BoolExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpression(FilterParser.BoolExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNullExpression(FilterParser.NullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNullExpression(FilterParser.NullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dateExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterDateExpression(FilterParser.DateExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dateExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitDateExpression(FilterParser.DateExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dateTimeExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterDateTimeExpression(FilterParser.DateTimeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dateTimeExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitDateTimeExpression(FilterParser.DateTimeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpression(FilterParser.ArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpression}
	 * labeled alternative in {@link FilterParser#value}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpression(FilterParser.ArrayExpressionContext ctx);
}