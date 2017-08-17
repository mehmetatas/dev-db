package net.mehmetatas.devdb.db;

import java.util.Map;

public class PageImpl implements Page {
    private final Map[] items;
    private final int pageSize;
    private final int currentPageIndex;
    private final int totalItemCount;
    private final int totalPageCount;

    public PageImpl(Map[] items, int pageSize, int currentPageIndex, int totalItemCount) {
        this.items = items;
        this.pageSize = pageSize;
        this.currentPageIndex = currentPageIndex;
        this.totalItemCount = totalItemCount;
        this.totalPageCount = (int) Math.floor((totalItemCount - 1) / (double) pageSize) + 1;
    }

    @Override
    public Map[] getItems() {
        return items;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    @Override
    public int getTotalItemCount() {
        return totalItemCount;
    }

    @Override
    public int getTotalPageCount() {
        return totalPageCount;
    }
}
