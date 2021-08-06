package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.context.PacketContext;
import eu.miopowered.packetlistener.context.PacketReceive;
import eu.miopowered.packetlistener.context.PacketSent;
import eu.miopowered.packetlistener.filter.PacketFilter;
import eu.miopowered.packetlistener.entity.PacketPlayer;
import eu.miopowered.packetlistener.entity.WrappedPacket;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface PacketListener {

    static PacketListener of(Player player) {
        return new PacketListenerImpl(player);
    }

    static PacketCoordinator register(JavaPlugin javaPlugin) {
        return new PacketCoordinator(javaPlugin);
    }

    PacketPlayer player();

    PacketReceive receive();

    PacketSent sent();

    PacketListener filter(PacketFilter... filter);

    PacketListener receive(PacketContext receive);

    PacketListener sent(PacketContext sent);

    boolean validate(WrappedPacket packet);

}
