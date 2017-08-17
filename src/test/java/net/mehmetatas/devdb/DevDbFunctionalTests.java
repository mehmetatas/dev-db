package net.mehmetatas.devdb;

import net.mehmetatas.devdb.client.DevDbClient;
import net.mehmetatas.devdb.db.DbGuard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevDbFunctionalTests {
    private final static String testTableName = "test-users";

    @Autowired
    private DevDbClient client;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void before() {
        client.setPort(randomServerPort);
    }

    @After
    public void after() {
        client.deleteTable(testTableName);
    }

    private void assertNotExists(String id) {
        try {
            client.get(testTableName, id);
            fail("get should have failed!");
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getResponseHeaders().get("X-DevDb-ErrorMessage").get(0).equals("Could not find any item with the given id!"));
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }
    }

    @Test
    public void insert_should_return_uuid() {
        String id = client.insert(testTableName, new HashMap() {{
            put("name", "mehmet ali");
        }});

        UUID result = UUID.fromString(id);

        assertNotNull(result);
    }

    @Test
    public void get_should_return_item() {
        String id = client.insert(testTableName, new HashMap() {{
            put("name", "mehmet ali");
        }});

        Map user = client.get(testTableName, id);

        assertEquals("mehmet ali", user.get("name"));
    }

    @Test
    public void update_should_override_properties() {
        Map user = new HashMap() {{
            put("name", "mehmet ali");
            put("surname", "yılmaz");
        }};

        String id = client.insert(testTableName, user);

        user.put("id", id);

        user.remove("name");
        user.put("fullname", "osman");

        client.update(testTableName, user);

        user = client.get(testTableName, id);

        assertEquals("osman", user.get("fullname"));
        assertNull(user.get("name"));
    }

    @Test
    public void patch_should_not_override_properties() {
        Map user = new HashMap() {{
            put("name", "mehmet ali");
            put("surname", "yılmaz");
        }};

        String id = client.insert(testTableName, user);

        user.put("id", id);

        user.remove("name");
        user.put("fullname", "osman");

        client.patch(testTableName, user);

        user = client.get(testTableName, id);

        assertEquals("osman", user.get("fullname"));
        assertEquals("mehmet ali", user.get("name"));
    }

    @Test
    public void delete_should_remove_item() {
        Map user = new HashMap() {{
            put("name", "mehmet ali");
            put("surname", "yılmaz");
        }};

        String id = client.insert(testTableName, user);

        client.delete(testTableName, id);

        assertNotExists(id);
    }

    @Test
    public void get_should_return_404_for_non_existing_item() {
        assertNotExists(UUID.randomUUID().toString());

        // just to prevent @After from failing
        client.insert(testTableName, new HashMap());
    }

    @Test
    public void delete_table_should_remove_everything() {
        Map user1 = new HashMap() {{
            put("name", "mehmet ali");
            put("surname", "yılmaz");
        }};

        Map user2 = new HashMap() {{
            put("name", "osman");
            put("surname", "yıldız");
        }};

        String id1 = client.insert(testTableName, user1);
        client.insert(testTableName, user2);

        client.deleteTable(testTableName);

        assertNotExists(id1);

        // just to prevent @After from failing
        client.insert(testTableName, new HashMap());
    }

    @Test
    public void search_should_match_filter_and_do_paging_and_sort() {
        client.insert(testTableName, new HashMap() {{
            put("name", "mehmet ali");
            put("surname", "yılmaz");
            put("age", 30);
        }});

        client.insert(testTableName, new HashMap() {{
            put("name", "osman");
            put("middle", "ali");
            put("surname", "yıldız");
            put("age", 40);
        }});

        String id3 = client.insert(testTableName, new HashMap() {{
            put("fullname", "halil ibrahim");
            put("surname", "kaya");
            put("age", 20);
        }});

        Map page = client.search(testTableName, "(surname=\"yıl*\" | age < 25) & (fullname != null | name = \"*ali\")", 1, 1, "age+");

        assertEquals(1, page.get("currentPageIndex"));
        assertEquals(1, page.get("pageSize"));
        assertEquals(2, page.get("totalItemCount"));
        assertEquals(2, page.get("totalPageCount"));

        List items = (List) page.get("items");

        assertEquals(1, items.size());

        Map item = (Map) items.get(0);

        assertNotNull(item);

        assertTrue(item.get("id").equals(id3));
    }

    @Test
    public void search_should_support_multiple_sort() {
        String id1 = client.insert(testTableName, new HashMap() {{
            put("name", "mehmet");
            put("surname", "yılmaz");
            put("age", 30);
        }});

        String id2 = client.insert(testTableName, new HashMap() {{
            put("name", "mehmet");
            put("surname", "yılmaz");
            put("age", 40);
        }});

        String id3 = client.insert(testTableName, new HashMap() {{
            put("name", "ahmet");
            put("surname", "yılmaz");
            put("age", 30);
        }});

        Map page = client.search(testTableName, "", 1, 3, "name+,surname-,age+");

        assertEquals(1, page.get("currentPageIndex"));
        assertEquals(3, page.get("pageSize"));
        assertEquals(3, page.get("totalItemCount"));
        assertEquals(1, page.get("totalPageCount"));

        List items = (List) page.get("items");

        assertEquals(3, items.size());

        assertTrue(((Map) items.get(0)).get("id").equals(id3));
        assertTrue(((Map) items.get(1)).get("id").equals(id1));
        assertTrue(((Map) items.get(2)).get("id").equals(id2));
    }

    @Test
    public void request_cannot_be_more_than_1_mb() {
        char[] chars = new char[1024 * 1023];
        Arrays.fill(chars, 'a');
        String shortName = new String(chars);

        client.insert(testTableName, new HashMap() {{
            put("name", shortName);
        }});

        try {
            chars = new char[1024];
            Arrays.fill(chars, 'a');
            String longName = shortName + new String(chars);

            client.insert(testTableName, new HashMap() {{
                put("name", longName);
            }});

            fail("insert should have failed!");
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getResponseHeaders().get("X-DevDb-ErrorMessage").get(0).contains("Request body cannot be more than"));
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    public void table_cannot_have_more_than_1000_items() {
        for (int i = 0; i < 1000; i++) {
            client.insert(testTableName, new HashMap() {{
                put("name", "ali");
            }});
        }

        try {
            client.insert(testTableName, new HashMap() {{
                put("name", "ali");
            }});
            fail("1001st insert should have failed");
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getResponseHeaders().get("X-DevDb-ErrorMessage").get(0).contains("A table can have max"));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }
    }

    @Test
    public void table_cannot_be_more_than_1_mb() {
        char[] chars = new char[1024 * 1023];
        Arrays.fill(chars, 'a');
        String nearly1MbName = new String(chars);

        // fill table with nearly 1mb data
        client.insert(testTableName, new HashMap() {{
            put("name", nearly1MbName);
        }});

        try {
            chars = new char[1024];
            Arrays.fill(chars, 'a');
            String nearly1KbName = new String(chars);

            // add another 1kb. after this operation table actually exceed max
            // but we will be able to detect it only after this data is inserted.
            client.insert(testTableName, new HashMap() {{
                put("name", nearly1KbName);
            }});

            // this should detect data exceeded
            client.insert(testTableName, new HashMap() {{
                put("name", nearly1KbName);
            }});

            fail("3rd insert should have failed");
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getResponseHeaders().get("X-DevDb-ErrorMessage").get(0).contains("A table can be max"));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }
    }

    @Test
    public void account_can_have_max_50_tables() {
        // we are doing this separately becuase after method will try to delete this.
        client.insert(testTableName, new HashMap() {{
            put("name", "ali");
        }});

        for (int i = 1; i < DbGuard.MaxTablesPerAccount; i++) {
            client.insert(testTableName + i, new HashMap() {{
                put("name", "ali");
            }});
        }

        try {
            client.insert(testTableName + DbGuard.MaxTablesPerAccount, new HashMap() {{
                put("name", "ali");
            }});

            fail((DbGuard.MaxTablesPerAccount + 1) + "st insert should have failed");
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getResponseHeaders().get("X-DevDb-ErrorMessage").get(0).contains("An account have max"));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }

        client.deleteTable(testTableName + 1);

        client.insert(testTableName + 1, new HashMap() {{
            put("name", "ali");
        }});

        try {
            client.insert(testTableName + DbGuard.MaxTablesPerAccount, new HashMap() {{
                put("name", "ali");
            }});

            fail("2nd insert after deletion should have failed");
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getResponseHeaders().get("X-DevDb-ErrorMessage").get(0).contains("An account have max"));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }

        for (int i = 1; i < DbGuard.MaxTablesPerAccount; i++) {
            client.deleteTable(testTableName + i);
        }
    }
}
