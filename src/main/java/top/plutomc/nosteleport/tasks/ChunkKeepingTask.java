package top.plutomc.nosteleport.tasks;

import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import top.plutomc.nosteleport.NosTeleport;
import top.plutomc.nosteleport.managers.CacheManager;
import top.plutomc.nosteleport.managers.ConfigManager;
import top.plutomc.nosteleport.utils.Cord;

import java.util.concurrent.CompletableFuture;

public final class ChunkKeepingTask implements Task {
    @Override
    public void run() {
        while (!NosTeleport.isBeingStop()) {
            if (CacheManager.size() > 0) {
                Cord cord = CacheManager.getLocationCache().get(CacheManager.size() - 1);
                CompletableFuture<Chunk> chunk = PaperLib.getChunkAtAsync(ConfigManager.bukkitWorld, cord.getX(), cord.getZ());
            }
        }
    }
}
