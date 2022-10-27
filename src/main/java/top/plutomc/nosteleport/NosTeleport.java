package top.plutomc.nosteleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.plutomc.nosteleport.commands.NosTeleportCommand;
import top.plutomc.nosteleport.listeners.PlayerListener;
import top.plutomc.nosteleport.managers.ConfigManager;
import top.plutomc.nosteleport.managers.TaskManager;

public final class NosTeleport extends JavaPlugin {
    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static void reload() {
        Bukkit.getScheduler().cancelTasks(NosTeleport.getInstance());
        TaskManager.stop();
        ConfigManager.init();
        TaskManager.init();
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

        ConfigManager.init();
        TaskManager.init();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getServer().getPluginCommand("nosteleport").setExecutor(new NosTeleportCommand());
        getServer().getPluginCommand("nosteleport").setTabCompleter(new NosTeleportCommand());

        getLogger().info("Plugin loaded.");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        TaskManager.stop();
        getLogger().info("Plugin disabled.");
    }
}
