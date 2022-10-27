package top.plutomc.nosteleport.listeners;

import io.papermc.lib.PaperLib;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import top.plutomc.nosteleport.managers.CacheManager;
import top.plutomc.nosteleport.managers.ConfigManager;
import top.plutomc.nosteleport.utils.ChunkHelper;
import top.plutomc.nosteleport.utils.Cord;

import java.util.concurrent.CompletableFuture;

public final class PlayerListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            while (CacheManager.size() == 0) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            player.sendMessage(MiniMessage.miniMessage().deserialize(ConfigManager.msgFirstTeleport));
            Cord cord = CacheManager.get();
            CompletableFuture<Chunk> chunk = PaperLib.getChunkAtAsync(ConfigManager.bukkitWorld, cord.getX(), cord.getZ());
            chunk.thenAccept(chunk1 -> {
                player.teleportAsync(cord.toLocation());
                ChunkHelper.removeTicket(cord);
            });
        }
    }

    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        while (CacheManager.size() == 0) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (player.getPotentialBedLocation() == null) {
            Cord cord = CacheManager.get();
            player.sendMessage(MiniMessage.miniMessage().deserialize(ConfigManager.msgRandomTeleportAfterRespawning));
            event.setRespawnLocation(cord.toLocation());
            ChunkHelper.removeTicket(cord);
        }
    }
}