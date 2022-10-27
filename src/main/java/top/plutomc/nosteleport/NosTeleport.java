package top.plutomc.nosteleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.plutomc.nosteleport.commands.NosTeleportCommand;
import top.plutomc.nosteleport.listeners.PlayerListener;
import top.plutomc.nosteleport.managers.CacheManager;
import top.plutomc.nosteleport.managers.ConfigManager;
import top.plutomc.nosteleport.managers.TaskManager;
import top.plutomc.nosteleport.tasks.CachingTask;
import top.plutomc.nosteleport.tasks.ChunkKeepingTask;

public final class NosTeleport extends JavaPlugin {
    private static JavaPlugin instance;
    private static boolean serverFullyLoaded;
    private static boolean beingStop = false;

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static boolean isBeingStop() {
        return beingStop;
    }

    public static void reload() {
        Bukkit.getScheduler().cancelTasks(NosTeleport.getInstance());

        TaskManager.stop();
        CacheManager.stop();

        beingStop = true;

        ConfigManager.init();
        TaskManager.init();
        CacheManager.init();

        beingStop = false;

        TaskManager.submit(new CachingTask());
        TaskManager.submit(new ChunkKeepingTask());
    }

    public static boolean isServerFullyLoaded() {
        return serverFullyLoaded;
    }

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("NosTeleport By nostal");
        getLogger().info("QQ -> 545847685");
        getLogger().info("Discord -> nostal#5126");

        // Check if user is running Paper.
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
        } catch (ClassNotFoundException e) {
            getLogger().info("This plugin is only available for Paper and its forks!");
            return;
        }

        try {
            Class.forName("cc.keyimc.keyi.KeyiConfig");
            getLogger().info("Thank you for using KeYi!");
            getLogger().info("Love from the developer of KeYi. <3");
        } catch (ClassNotFoundException ignored) {
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        ConfigManager.init();
        TaskManager.init();
        CacheManager.init();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getServer().getPluginCommand("nosteleport").setExecutor(new NosTeleportCommand());
        getServer().getPluginCommand("nosteleport").setTabCompleter(new NosTeleportCommand());

        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            serverFullyLoaded = true;
            TaskManager.submit(new CachingTask());
        });

        getLogger().info("Plugin loaded.");
    }

    @Override
    public void onDisable() {
        beingStop = true;
        serverFullyLoaded = false;

        getServer().getScheduler().cancelTasks(this);

        TaskManager.stop();
        CacheManager.stop();

        getLogger().info("Plugin disabled.");
    }
}
