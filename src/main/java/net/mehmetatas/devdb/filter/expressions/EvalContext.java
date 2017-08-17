package net.mehmetatas.devdb.filter.expressions;

import java.util.Locale;
import java.util.Map;

public class EvalContext {
    private Locale locale = Locale.forLanguageTag("en-GB");
    private Map map;

    public Locale getLocale() {
        return locale;
    }

    public Map getMap() {
        return map;
    }

    public EvalContext setLocale(String languageTag) {
        this.locale = Locale.forLanguageTag(languageTag);
        return this;
    }

    public EvalContext setMap(Map map) {
        this.map = map;
        return this;
    }
}
