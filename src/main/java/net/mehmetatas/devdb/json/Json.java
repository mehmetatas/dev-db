package net.mehmetatas.devdb.json;

import java.util.Map;

public interface Json {
    String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    Json instance = new JsonImpl();

    String serialize(Object obj);

    Map deserialize(String json);

    static Object get(Map map, String path) {
        String[] props = path.split("\\.");

        Map child = map;

        int i = 0;
        for (; i < props.length - 1; i++) {
            child = (Map) child.get(props[i]);
        }

        return child.get(props[i]);
    }
}
