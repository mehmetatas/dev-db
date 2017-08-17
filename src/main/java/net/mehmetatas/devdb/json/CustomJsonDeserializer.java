package net.mehmetatas.devdb.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class CustomJsonDeserializer implements JsonDeserializer<Object> {
    private static final SimpleDateFormat dateTimeParser = new SimpleDateFormat(Json.dateTimeFormat);
    private static final String dateTimePattern = "^\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\dZ$";

    public Object deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        if (json.isJsonNull()) {
            return null;
        }

        if (json.isJsonPrimitive()) {
            return handlePrimitive(json.getAsJsonPrimitive());
        }

        if (json.isJsonArray()) {
            return handleArray(json.getAsJsonArray(), context);
        }

        return handleObject(json.getAsJsonObject(), context);
    }

    private Object handlePrimitive(JsonPrimitive json) {
        if (json.isBoolean()) {
            return json.getAsBoolean();
        }

        if (json.isNumber()) {
            return json.getAsDouble();
        }

        String str = json.getAsString();

        if (str.matches(dateTimePattern)) {
            try {
                return dateTimeParser.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return str;
    }

    private Object handleArray(JsonArray json, JsonDeserializationContext context) {
        return IntStream.range(0, json.size())
                .mapToObj(i -> context.deserialize(json.get(i), Object.class))
                .toArray();
    }

    private Object handleObject(JsonObject json, JsonDeserializationContext context) {
        Map<String, Object> map = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            map.put(entry.getKey(), deserialize(entry.getValue(), Object.class, context));
        }

        return map;
    }
}