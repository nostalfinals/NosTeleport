package top.plutomc.nosteleport.tasks;

import me.SuperRonanCraft.BetterRTP.BetterRTP;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTPSetupInformation;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_TYPE;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.plutomc.nosteleport.ConfigManager;

public final class RandomTeleportTask implements Task {
    final Player PLAYER;

    public RandomTeleportTask(Player player) {
        PLAYER = player;
    }

    @Override
    public void run() {
        RTPSetupInformation rtpSetupInformation = new RTPSetupInformation(
                Bukkit.getWorld(ConfigManager.world),
                Bukkit.getConsoleSender(),
                PLAYER,
                true,
                null,
                false,
                RTP_TYPE.ADDON,
                ConfigManager.worldLocations,
                false
        );

        BetterRTP.getInstance().getRTP().start(rtpSetupInformation);
    }
}
