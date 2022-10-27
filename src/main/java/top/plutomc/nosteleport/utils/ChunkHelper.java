package top.plutomc.nosteleport.utils;

import org.bukkit.Location;
import top.plutomc.nosteleport.NosTeleport;

public final class ChunkHelper {
    private ChunkHelper() {
    }

    public static void addTicket(Cord cord) {
        Location location = cord.toLocation();
        location.getChunk().addPluginChunkTicket(NosTeleport.getInstance());
    }

    public static void removeTicket(Cord cord) {
        Location location = cord.toLocation();
        location.getChunk().removePluginChunkTicket(NosTeleport.getInstance());
    }
}
