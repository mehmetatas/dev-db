package net.mehmetatas.devdb.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

class JsonImpl implements Json {
    private final Gson gson;

    JsonImpl() {
        gson = new GsonBuilder()
                .setDateFormat(dateTimeFormat)
                .registerTypeAdapter(Map.class, new CustomJsonDeserializer())
                .create();
    }

    @Override
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public Map deserialize(String json) {
        return gson.fromJson(json, Map.class);
    }
}
