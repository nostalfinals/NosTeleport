package top.plutomc.nosteleport;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_SHAPE;
import me.SuperRonanCraft.BetterRTP.references.rtpinfo.worlds.WorldLocations;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public final class ConfigManager {
    public static File configFile;
    public static YamlConfiguration config;
    public static String firstTeleport = "<red>这是你初次进入服务器，正在随机传送...";
    public static String randomTeleportAfterDying = "<red>请注意：如果你没有睡过床的话，死亡并重生之后会被随机传送到一个地方！";
    public static int teleportRadius = 5000;
    public static String world = "world";
    public static int centerX = 0;
    public static int centerZ = 0;

    public static WorldLocations worldLocations;

    private ConfigManager() {
    }

    public static void init() {
        configFile = new File(NosTeleport.getInstance().getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }

        config.options().copyDefaults(true);

        readConfig(ConfigManager.class, null);

        try {
            config.save(configFile);
        } catch (IOException ex) {
            NosTeleport.getInstance().getLogger().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    private static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        throw Throwables.propagate(ex.getCause());
                    } catch (Exception ex) {
                        NosTeleport.getInstance().getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }
    }

    private static void set(String path, Object val) {
        config.addDefault(path, val);
        config.set(path, val);
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    private static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return config.getList(path, config.getList(path));
    }

    public static Map<String, Object> getMap(String path, Map<String, Object> def) {
        if (def != null && config.getConfigurationSection(path) == null) {
            config.addDefault(path, def);
            return def;
        }
        return toMap(config.getConfigurationSection(path));
    }

    private static Map<String, Object> toMap(ConfigurationSection section) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Object obj = section.get(key);
                if (obj != null) {
                    builder.put(key, obj instanceof ConfigurationSection val ? toMap(val) : obj);
                }
            }
        }
        return builder.build();
    }

    private static void message() {
        firstTeleport = getString("messages.first-teleport", firstTeleport);
        randomTeleportAfterDying = getString("messages.random-teleport-after-dying", randomTeleportAfterDying);
    }

    private static void settings() {
        teleportRadius = getInt("settings.teleport-radius", teleportRadius);
        world = getString("settings.world", world);
        centerX = getInt("settings.center.X", centerX);
        centerZ = getInt("settings.center.Z", centerZ);

        worldLocations = new WorldLocations("NosTeleportWorldLocations");
        worldLocations.setCenterX(centerX);
        worldLocations.setCenterZ(centerZ);
        worldLocations.setMaxRadius(teleportRadius);
        worldLocations.setShape(RTP_SHAPE.CIRCLE);
        worldLocations.setWorld(Bukkit.getWorld(world));
    }
}
