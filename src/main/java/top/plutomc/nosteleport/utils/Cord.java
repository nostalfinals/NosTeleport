package top.plutomc.nosteleport.utils;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import top.plutomc.nosteleport.managers.ConfigManager;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Cord {
    private static final Random FAST_RANDOM = new FastRandom();
    World world;
    int x;
    int y;
    int z;

    private Cord(int x, int z) {
        this.world = ConfigManager.bukkitWorld;
        this.x = x;
        this.z = z;
        this.y = world.getHighestBlockYAt(x, z);
    }

    public static boolean badBlock(int x, int z) {
        CompletableFuture<Chunk> chunk = PaperLib.getChunkAtAsync(ConfigManager.bukkitWorld, x, z);

        AtomicBoolean isAir = new AtomicBoolean(false);
        AtomicBoolean hasGravity = new AtomicBoolean(false);
        AtomicBoolean isSolid = new AtomicBoolean(false);

        chunk.thenAccept(chunk1 -> {
            isAir.set(chunk1.getBlock(x, ConfigManager.bukkitWorld.getHighestBlockYAt(x, z), z).getType().isAir());
            hasGravity.set(chunk1.getBlock(x, ConfigManager.bukkitWorld.getHighestBlockYAt(x, z), z).getType().hasGravity());
            isSolid.set(chunk1.getBlock(x, ConfigManager.bukkitWorld.getHighestBlockYAt(x, z), z).getType().isSolid());
        });

        return !isAir.get()
                && !hasGravity.get() && isSolid.get()
                && ResidenceApi.getResidenceManager().getByLoc(new Location(ConfigManager.bukkitWorld, x, ConfigManager.bukkitWorld.getHighestBlockYAt(x, z), z)) == null;
    }

    private static int randomX() {
        return FAST_RANDOM.nextInt(ConfigManager.minX, ConfigManager.maxX);
    }

    private static int randomZ() {
        return FAST_RANDOM.nextInt(ConfigManager.minZ, ConfigManager.maxZ);
    }

    public static Cord generateNewCord() {
        while (true) {
            int ranX = randomX();
            int ranZ = randomZ();
            if (!badBlock(ranX, ranZ)) {
                return new Cord(ranX, ranZ);
            }
        }
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
