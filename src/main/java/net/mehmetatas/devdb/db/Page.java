package net.mehmetatas.devdb.db;

import java.util.Map;

public interface Page {
    Map[] getItems();

    int getPageSize();
    int getCurrentPageIndex();
    int getTotalItemCount();
    int getTotalPageCount();
}
