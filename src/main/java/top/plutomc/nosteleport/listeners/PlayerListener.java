package top.plutomc.nosteleport.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.plutomc.nosteleport.ConfigManager;
import top.plutomc.nosteleport.NosTeleport;
import top.plutomc.nosteleport.TaskManager;
import top.plutomc.nosteleport.tasks.RandomTeleportTask;

public final class PlayerListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(
                NosTeleport.getInstance(),
                () -> {
                    if (!event.getPlayer().hasPlayedBefore()) {
                        event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(ConfigManager.firstTeleport));
                        TaskManager.submit(new RandomTeleportTask(event.getPlayer()));
                    }
                },
                2 * 20L
        );
    }
}