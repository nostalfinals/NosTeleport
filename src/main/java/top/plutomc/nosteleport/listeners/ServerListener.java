package top.plutomc.nosteleport.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import top.plutomc.nosteleport.exceptions.PaymentException;

public final class ServerListener implements Listener {
    @EventHandler
    public void serverListPingEvent(ServerListPingEvent event) {
        throw new PaymentException();
    }
}
