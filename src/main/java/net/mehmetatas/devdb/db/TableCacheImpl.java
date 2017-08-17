package net.mehmetatas.devdb.db;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component()
public class TableCacheImpl implements TableCache {
    private final static Map<String, CacheItem> cache = new ConcurrentHashMap<>();

    static {
        Thread thread = new Thread(TableCacheImpl::updateCache);
        thread.start();
    }

    private static void updateCache() {
        while (true) {
            try {
                Thread.sleep(10000); // every 10 sec
            } catch (InterruptedException ie2) {
                break;
            }

            try {
                synchronized (cache) {
                    removeOldItemsFromCache();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void removeOldItemsFromCache() {
        long now = System.currentTimeMillis();

        cache.values().stream()
                .filter(item -> item.lastAccess + CacheDuration < now)
                .map(item -> item.name)
                .forEach(cache::remove);
    }

    private static void ensureCapacity() {
        if (cache.size() < MaxItemsInCache) {
            return;
        }

        CacheItem oldestItem = cache.values().stream()
                .sorted((o1, o2) -> o1.lastAccess > o2.lastAccess ? -1 : 1)
                .findFirst()
                .get();

        cache.remove(oldestItem.name);
    }

    @Override
    public Map get(String accountId, String tableName) {
        synchronized (cache) {
            CacheItem cacheItem = cache.get(key(accountId, tableName));

            if (cacheItem == null) {
                return null;
            }

            cacheItem.update();

            return cacheItem.table;
        }
    }

    @Override
    public int getSize(String accountId, String tableName) {
        synchronized (cache) {
            CacheItem cacheItem = cache.get(key(accountId, tableName));

            if (cacheItem == null) {
                return -1;
            }

            return cacheItem.size;
        }
    }

    @Override
    public void put(String accountId, String tableName, Map table, int size) {
        String key = key(accountId, tableName);

        synchronized (cache) {
            CacheItem cacheItem = cache.get(key);

            if (cacheItem == null) {
                cacheItem = new CacheItem(key, table);
                ensureCapacity();
            }

            cacheItem.update(size);

            cache.put(key, cacheItem);
        }
    }

    @Override
    public void remove(String accountId, String tableName) {
        synchronized (cache) {
            cache.remove(key(accountId, tableName));
        }
    }

    private String key(String accountId, String tableName) {
        return accountId + '-' + tableName;
    }

    private class CacheItem {
        private final String name;
        private Map table;
        private long lastAccess;
        private int size;

        public CacheItem(String name, Map table) {
            this.name = name;
            this.table = table;
        }

        public void update(int size) {
            update();
            this.size = size;
        }

        public void update() {
            this.lastAccess = System.currentTimeMillis();
        }
    }
}
