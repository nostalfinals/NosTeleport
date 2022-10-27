package top.plutomc.nosteleport;

import org.bukkit.plugin.java.JavaPlugin;

public final class NosTeleport extends JavaPlugin {
    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
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

        getLogger().info("Plugin loaded.");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        getLogger().info("Plugin disabled.");
    }
}
