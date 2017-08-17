package net.mehmetatas.devdb.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageTests {
    private int getTotalPageCount(int totalRecords, int pageSize) {
        Page page = new PageImpl(null, pageSize, 0, totalRecords);
        return page.getTotalPageCount();
    }

    @Test
    public void should_calculate_correct_total_page_count() {
        assertEquals(0, getTotalPageCount(0, 10));
        assertEquals(1, getTotalPageCount(1, 10));
        assertEquals(1, getTotalPageCount(9, 10));
        assertEquals(1, getTotalPageCount(10, 10));
        assertEquals(2, getTotalPageCount(11, 10));
    }
}
