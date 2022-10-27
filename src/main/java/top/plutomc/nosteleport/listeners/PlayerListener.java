package top.plutomc.nosteleport.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import top.plutomc.nosteleport.NosTeleport;
import top.plutomc.nosteleport.managers.ConfigManager;
import top.plutomc.nosteleport.managers.TaskManager;
import top.plutomc.nosteleport.tasks.RandomTeleportTask;

public final class PlayerListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (ConfigManager.teleportWhenJoinFirst) {
            Bukkit.getScheduler().runTaskLater(
                    NosTeleport.getInstance(),
                    () -> {
                        if (!event.getPlayer().hasPlayedBefore()) {
                            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(ConfigManager.msgFirstTeleport));
                            TaskManager.submit(new RandomTeleportTask(event.getPlayer()));
                        }
                    },
                    2 * 20L
            );
        }
    }

    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent event) {
        if (ConfigManager.teleportAfterRespawning) {
            Location worldSpawn = ConfigManager.bukkitWorld.getSpawnLocation();
            Location playerSpawn = event.getPlayer().getPotentialBedLocation();

            // Check if player has a respawn point
            if (worldSpawn.equals(playerSpawn)) {
                event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(ConfigManager.msgRandomTeleportAfterRespawning));
                TaskManager.submit(new RandomTeleportTask(event.getPlayer()));
            }
        }
    }
}