package net.mehmetatas.devdb.filter.expressions;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValueExpression implements Expression {
    private static final ValueExpression NullExpression = new ValueExpression(null);

    private static final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DateTimeWithMillisFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final Object value;

    private ValueExpression(Object value) {
        this.value = value;
    }

    @Override
    public Object eval(EvalContext ctx) {
        return this.value;
    }

    public static ValueExpression number(String strNumber) {
        return new ValueExpression(Double.parseDouble(strNumber));
    }

    public static ValueExpression string(String str) {
        str = str.substring(1, str.length() - 1);
        return new ValueExpression(str);
    }

    public static ValueExpression bool(String strBool) {
        return new ValueExpression(Boolean.parseBoolean(strBool));
    }

    public static ValueExpression date(String strDate) {
        try {
            return new ValueExpression(DateFormat.parse(strDate.substring(1)));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid Date format!", e);
        }
    }

    public static ValueExpression dateTime(String strDateTime) {
        strDateTime = strDateTime.substring(1);
        SimpleDateFormat sdf = strDateTime.length() == DateTimeFormat.toPattern().length()
                ? DateTimeFormat
                : DateTimeWithMillisFormat;

        try {
            return new ValueExpression(sdf.parse(strDateTime));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid DateTime format!", e);
        }
    }

    public static ValueExpression nil() {
        return NullExpression;
    }
}
