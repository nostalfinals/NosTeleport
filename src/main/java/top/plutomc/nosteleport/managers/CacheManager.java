package top.plutomc.nosteleport.managers;

import top.plutomc.nosteleport.utils.ChunkHelper;
import top.plutomc.nosteleport.utils.Cord;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CacheManager {
    private static List<Cord> cordCache;
    private static boolean stopped = true;

    private CacheManager() {
    }

    public static void init() {
        // Use a thread safe list
        cordCache = new CopyOnWriteArrayList<>();
        stopped = false;
    }

    public static List<Cord> getCordCache() {
        return cordCache;
    }

    public static int size() {
        return cordCache.size();
    }

    public static void add(Cord cord) {
        cordCache.add(cord);
        ChunkHelper.addTicket(cord);
    }

    public static Cord get() {
        if (size() > 0) {
            Cord cord = cordCache.get(size() - 1);
            cordCache.remove(size() - 1);
            return cord;
        }
        return null;
    }

    public static void stop() {
        stopped = true;
    }

    public static boolean isStopped() {
        return stopped;
    }
}
