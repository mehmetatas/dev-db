package net.mehmetatas.devdb.json;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GsonTests {
    private final static Json json = Json.instance;

    private final Date date;

    public GsonTests() {
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.DECEMBER, 12, 12, 34, 56);
        cal.set(Calendar.MILLISECOND, 789);
        date = cal.getTime();
    }

    @Test
    public void deserializeToMapOneLevel() {
        String str = "{" +
                "\"name\":\"mehmet\"," +
                "\"age\":18," +
                "\"active\":true," +
                "\"book\":null," +
                "\"items1\":[1,2,3]," +
                "\"items2\":[1.5,true,\"ali\"]" +
                "}";

        Map map = json.deserialize(str);

        assertEquals("mehmet", map.get("name"));
        assertEquals(18.0, (Double) map.get("age"), 0.0000001);
        assertEquals(true, map.get("active"));
        assertEquals(null, map.get("book"));
        assertEquals(null, map.get("something"));
        assertArrayEquals(new Object[]{1.0, 2.0, 3.0}, (Object[]) map.get("items1"));
        assertArrayEquals(new Object[]{1.5, true, "ali"}, (Object[]) map.get("items2"));
    }

    @Test
    public void serializeToMapOneLevel() {
        Map map = new HashMap<>();
        map.put("name", "mehmet");
        map.put("age", 18);
        map.put("active", true);
        map.put("book", null);
        map.put("items1", new Object[]{1, 2, 3});
        map.put("items2", new Object[]{1.5, true, "ali"});

        String str = json.serialize(map);

        map = json.deserialize(str);

        assertEquals("mehmet", map.get("name"));
        assertEquals(18.0, (Double) map.get("age"), 0.0000001);
        assertEquals(true, map.get("active"));
        assertEquals(null, map.get("book"));
        assertEquals(null, map.get("something"));
        assertArrayEquals(new Object[]{1.0, 2.0, 3.0}, (Object[]) map.get("items1"));
        assertArrayEquals(new Object[]{1.5, true, "ali"}, (Object[]) map.get("items2"));
    }

    @Test
    public void deserializeToMapMultiLevel() {
        String str = "{" +
                "\"address\":{\"no\":6}," +
                "\"info\": {\"date\":\"2012-12-12T12:34:56.789Z\"}" +
                "}";

        Map map = json.deserialize(str);

        assertTrue(map.get("address") instanceof Map);
        assertTrue(map.get("info") instanceof Map);

        assertEquals(6.0, (double) Json.get(map, "address.no"), 0.00001);
        assertEquals(date, Json.get(map, "info.date"));
    }

    @Test
    public void serializeDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);

        String str = json.serialize(map);

        assertEquals("{\"date\":\"2012-12-12T12:34:56.789Z\"}", str);
    }

    @Test
    public void deserializeDate() {
        String str = "{\"date\":\"2012-12-12T12:34:56.789Z\"}";

        Map map = json.deserialize(str);

        assertTrue(map.get("date") instanceof Date);
        assertEquals(date, map.get("date"));
    }
}
