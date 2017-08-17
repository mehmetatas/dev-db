package net.mehmetatas.devdb.filter.parser;

import net.mehmetatas.devdb.filter.expressions.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static org.junit.Assert.*;

public class ParserTests {
    private final EvalContext ctx;

    public ParserTests() {
        ctx = new EvalContext()
                .setLocale("tr-TR");
    }

    private Expression parse(String expression) {
        try {
            CharStream input = new ANTLRInputStream(new StringReader(expression));

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

    @Test
    public void parseEq() {
        Expression ex = parse("age = 18");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 17);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 18);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 19);
        }})).equals(false));
    }

    @Test
    public void parseNeq() {
        Expression ex = parse("age != 18");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 17);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 18);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 19);
        }})).equals(true));
    }

    @Test
    public void parseLt() {
        Expression ex = parse("age < 18");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 17);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 18);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 19);
        }})).equals(false));
    }

    @Test
    public void parseLte() {
        Expression ex = parse("age <= 18");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 17);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 18);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 19);
        }})).equals(false));
    }

    @Test
    public void parseGt() {
        Expression ex = parse("age > 18");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 17);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 18);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 19);
        }})).equals(true));
    }

    @Test
    public void parseGte() {
        Expression ex = parse("age >= 18");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 17);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 18);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 19);
        }})).equals(true));
    }

    @Test
    public void parseEqString() {
        Expression ex = parse("name = \"ali\"");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "veli");
        }})).equals(false));
    }

    @Test
    public void parseEqBoolean() {
        Expression ex = parse("status = false");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("status", false);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("status", true);
        }})).equals(false));
    }

    @Test
    public void parseEqStartsWith() {
        Expression ex = parse("name = \"ali*\"");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "alican");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ALİ");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "al");
        }})).equals(false));
    }

    @Test
    public void endsWithTest() {
        String i1 = "i";
        String i2 = "İ".toLowerCase(Locale.forLanguageTag("en-US"));
        String i3 = "İ".toLowerCase(Locale.forLanguageTag("tr-TR"));

        assertTrue(i2.startsWith(i1));
        assertTrue(!i2.endsWith(i1));
        assertTrue(!i1.endsWith(i2));
        assertTrue(!i1.startsWith(i2));

        assertTrue(i3.startsWith(i1));
        assertTrue(i3.endsWith(i1));
        assertTrue(i1.endsWith(i3));
        assertTrue(i1.startsWith(i3));
    }

    @Test
    public void parseEqEndsWith() {
        Expression ex = parse("name = \"*li\"");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "li");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "Lİ");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "alican");
        }})).equals(false));
    }

    @Test
    public void parseEqContains() {
        Expression ex = parse("name = \"*li*\"");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "li");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "lila");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "LİLA");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "osman");
        }})).equals(false));
    }

    @Test
    public void parseEqLikeSpecialChar() {
        Expression ex = parse("name = \"****\"");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali**");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "**ali");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "**");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "*ali*");
        }})).equals(false));
    }

    @Test
    public void parseEqTypesNotMatch() {
        Expression ex = parse("age = 18");
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "18");
        }})).equals(false));
    }

    @Test
    public void parseLtTypesNotMatch() {
        Expression ex = parse("age < 18");
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "12");
        }})).equals(false));
    }

    @Test
    public void parseAnd() {
        Expression ex = parse("age >= 18 & name=\"ali\"");

        assertTrue(ex instanceof AndExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
            put("age", 18);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
            put("age", "18");
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "veli");
            put("age", 18);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "veli");
            put("age", "18");
        }})).equals(false));
    }

    @Test
    public void parseOr() {
        Expression ex = parse("age >= 18 | name=\"ali\"");

        assertTrue(ex instanceof OrExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
            put("age", 18);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
            put("age", "18");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "veli");
            put("age", 18);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "veli");
            put("age", "18");
        }})).equals(false));
    }

    @Test
    public void parseNull() {
        Expression ex = parse("name = null");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", null);
        }})).equals(true));
    }

    @Test
    public void parseEqTwoFields() {
        Expression ex = parse("name = surname");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
            put("surname", "ali");
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
            put("surname", "veli");
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", null);
            put("surname", null);
        }})).equals(true));
    }

    @Test
    public void parseEqTwoValues() {
        Expression ex = parse("1 = 2");

        assertTrue(ex instanceof ComparisonExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap())).equals(false));
    }

    @Test
    public void parseValueTrue() {
        Expression ex = parse("true");

        assertTrue(ex instanceof ValueExpression);
        assertTrue(ex.eval(ctx).equals(true));
    }

    @Test
    public void parseValueFalse() {
        Expression ex = parse("false");

        assertTrue(ex instanceof ValueExpression);
        assertTrue(ex.eval(ctx).equals(false));
    }

    @Test
    public void parseValueNull() {
        Expression ex = parse("null");

        assertTrue(ex instanceof ValueExpression);
        assertTrue(ex.eval(ctx) == null);
    }

    @Test
    public void parseValueArray() {
        Expression ex = parse("[1, \"ali\", true, @2012-12-12, [1, 3, 5]]");

        assertTrue(ex instanceof ArrayExpression);

        Object[] array = (Object[]) ex.eval(ctx);

        assertEquals(1.0D, array[0]);
        assertEquals("ali", array[1]);
        assertEquals(true, array[2]);
        assertEquals(new Date(2012 - 1900, Calendar.DECEMBER, 12), array[3]);
        assertArrayEquals(new Object[]{1.0, 3.0, 5.0}, (Object[]) array[4]);
    }

    @Test
    public void parseInExpression() {
        Expression ex = parse("age in [18, 42, 65]");

        assertTrue(ex instanceof InExpression);

        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 42);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", 30);
        }})).equals(false));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("age", "18");
        }})).equals(false));
    }

    @Test
    public void parseIdentifier() {
        Expression ex = parse("name");

        assertTrue(ex instanceof IdentifierExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("name", "ali");
        }})).equals("ali"));
    }

    @Test
    public void parseIdentifierPath() {
        Expression ex = parse("address.street");

        assertTrue(ex instanceof IdentifierExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("address", new HashMap<String, Object>() {{
                put("street", "mirallos");
            }});
        }})).equals("mirallos"));
    }

    @Test
    public void parseDate() {
        Expression ex = parse("@2012-10-10");

        assertTrue(ex instanceof ValueExpression);
        assertTrue(ex.eval(ctx).equals(new Date(2012 - 1900, Calendar.OCTOBER, 10)));
    }

    @Test
    public void parseDateTime() {
        Expression ex = parse("@2012-10-10 12:34:56");

        assertTrue(ex instanceof ValueExpression);
        assertTrue(ex.eval(ctx).equals(new Date(2012 - 1900, Calendar.OCTOBER, 10, 12, 34, 56)));
    }

    @Test
    public void parseDateTimeWithMillis() {
        Expression ex = parse("@2012-10-10 12:34:56.123");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(2012 - 1900, Calendar.OCTOBER, 10, 12, 34, 56));
        cal.set(Calendar.MILLISECOND, 123);

        assertTrue(ex instanceof ValueExpression);
        assertTrue(ex.eval(ctx).equals(cal.getTime()));
    }

    @Test
    public void parseBinaryPriority() {
        Expression ex = parse("a1 = 1 & a2 = 2 | a3 = 3");

        assertTrue(ex instanceof OrExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 11);
            put("a2", 22);
            put("a3", 3);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 1);
            put("a2", 2);
            put("a3", 33);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 11);
            put("a2", 22);
            put("a3", 33);
        }})).equals(false));
    }

    @Test
    public void parseParanPriority() {
        Expression ex = parse("a1 = 1 & ((a2 = 2 | a3 = 3))");

        assertTrue(ex instanceof AndExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 1);
            put("a2", 2);
            put("a3", 33);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 1);
            put("a2", 22);
            put("a3", 3);
        }})).equals(true));
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 11);
            put("a2", 2);
            put("a3", 3);
        }})).equals(false));
    }

    @Test
    public void parseBinaryOrTrue() {
        Expression ex = parse("(a1 = 1 & ((a2 = 2 | a3 = 3))) | true");

        assertTrue(ex instanceof OrExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 11);
            put("a2", 22);
            put("a3", 33);
        }})).equals(true));
    }

    @Test
    public void parseBinaryAndFalse() {
        Expression ex = parse("(a1 = 1 & ((a2 = 2 | a3 = 3))) & false");

        assertTrue(ex instanceof AndExpression);
        assertTrue(ex.eval(ctx.setMap(new HashMap<String, Object>() {{
            put("a1", 1);
            put("a2", 2);
            put("a3", 3);
        }})).equals(false));
    }
}
