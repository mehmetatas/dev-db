package net.mehmetatas.devdb.db;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SorterTests {
    private final MapSorter sorter = new MapSorterImpl();

    private Map createUser(String name) {
        return new HashMap() {{
            put("name", name);
        }};
    }

    @Test
    public void should_sort_asc() {
        Map[] users = {
                createUser("ali"),
                createUser("veli"),
                createUser("ahmet"),
                createUser("mehmet")
        };

        sorter.sort(users, "name", false);

        assertEquals("ahmet", users[0].get("name"));
        assertEquals("ali", users[1].get("name"));
        assertEquals("mehmet", users[2].get("name"));
        assertEquals("veli", users[3].get("name"));
    }

    @Test
    public void should_sort_desc() {
        Map[] users = {
                createUser("ali"),
                createUser("veli"),
                createUser("ahmet"),
                createUser("mehmet")
        };

        sorter.sort(users, "name", true);

        assertEquals("veli", users[0].get("name"));
        assertEquals("mehmet", users[1].get("name"));
        assertEquals("ali", users[2].get("name"));
        assertEquals("ahmet", users[3].get("name"));
    }

    @Test
    public void null_should_come_last_when_sorted_asc() {
        Map[] users = {
                createUser("ali"),
                createUser("veli"),
                createUser(null),
                createUser("mehmet")
        };

        sorter.sort(users, "name", false);

        assertEquals("ali", users[0].get("name"));
        assertEquals("mehmet", users[1].get("name"));
        assertEquals("veli", users[2].get("name"));
        assertEquals(null, users[3].get("name"));
    }

    @Test
    public void null_should_come_last_when_sorted_desc() {
        Map[] users = {
                createUser("ali"),
                createUser("veli"),
                createUser(null),
                createUser("mehmet")
        };

        sorter.sort(users, "name", true);

        assertEquals("veli", users[0].get("name"));
        assertEquals("mehmet", users[1].get("name"));
        assertEquals("ali", users[2].get("name"));
        assertEquals(null, users[3].get("name"));
    }

    @Test
    public void should_sort_case_insensitive() {
        Map[] users = {
                createUser("MEHMET"),
                createUser("ahmet"),
                createUser("ALİ")
        };

        sorter.sort(users, "name", false);

        assertEquals("ahmet", users[0].get("name"));
        assertEquals("ALİ", users[1].get("name"));
        assertEquals("MEHMET", users[2].get("name"));
    }
}
