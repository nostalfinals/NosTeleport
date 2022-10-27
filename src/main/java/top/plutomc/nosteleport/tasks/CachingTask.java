package top.plutomc.nosteleport.tasks;

import top.plutomc.nosteleport.managers.CacheManager;
import top.plutomc.nosteleport.managers.ConfigManager;
import top.plutomc.nosteleport.utils.Cord;

public final class CachingTask implements Task {
    @Override
    public void run() {
        while (!CacheManager.isStopped()) {
            if (CacheManager.size() < ConfigManager.maxCacheSize) {
                CacheManager.add(Cord.generateNewCord());
            }
        }
    }
}
