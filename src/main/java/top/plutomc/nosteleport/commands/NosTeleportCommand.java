package top.plutomc.nosteleport.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.plutomc.nosteleport.NosTeleport;
import top.plutomc.nosteleport.managers.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public final class NosTeleportCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            NosTeleport.reload();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(ConfigManager.msgReloadCompleted));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> content = new ArrayList<>();

        if (args.length >= 1) {
            content.add("reload");
        }

        return null;
    }
}
