package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.entity.PacketPlayer;
import eu.miopowered.packetlistener.event.PacketReceiveEvent;
import eu.miopowered.packetlistener.event.PacketSentEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketCoordinator implements Listener {

    private JavaPlugin plugin;

    PacketCoordinator(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    private void listen(Player player) {
        PacketPlayer packetPlayer = PacketPlayer.of(player);
        PacketTarget.listen(PacketListener
                .of(player)
                .sent((context, packet) -> {
                    PacketSentEvent event = new PacketSentEvent(packetPlayer, context, packet);
                    Bukkit.getPluginManager()
                            .callEvent(event);
                    return !event.isCancelled();
                })
                .receive((context, packet) -> {
                    PacketReceiveEvent event = new PacketReceiveEvent(packetPlayer, context, packet);
                    Bukkit.getPluginManager()
                            .callEvent(event);
                    return !event.isCancelled();
                })
        );
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        this.listen(event.getPlayer());
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        PacketTarget.remove(event.getPlayer());
    }

    @EventHandler
    public void handle(PluginEnableEvent event) {
        if (!event.getPlugin().equals(this.plugin)) return;

        Bukkit.getOnlinePlayers().forEach(this::listen);
    }

    @EventHandler
    public void handle(PluginDisableEvent event) {
        if (!event.getPlugin().equals(this.plugin)) return;

        Bukkit.getOnlinePlayers().forEach(PacketTarget::remove);
    }
}
