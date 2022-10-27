package top.plutomc.nosteleport.managers;

import top.plutomc.nosteleport.utils.Cord;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CacheManager {
    private static List<Cord> locationCache;
    private static boolean stopped = true;

    private CacheManager() {
    }

    public static void init() {
        // Use a thread safe list
        locationCache = new CopyOnWriteArrayList<>();
        stopped = false;
    }

    public static List<Cord> getLocationCache() {
        return locationCache;
    }

    public static int size() {
        return locationCache.size();
    }

    public static void add(Cord location) {
        locationCache.add(location);
    }

    public static Cord get() {
        if (size() > 0) {
            Cord location = locationCache.get(size() - 1);
            locationCache.remove(size() - 1);
            return location;
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
