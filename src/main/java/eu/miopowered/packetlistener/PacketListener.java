package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.context.PacketContext;
import eu.miopowered.packetlistener.entity.PacketPlayer;
import eu.miopowered.packetlistener.entity.WrappedPacket;
import eu.miopowered.packetlistener.filter.PacketFilter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface PacketListener {

    static PacketListener of(Player player) {
        return new PacketListenerImpl(player);
    }

    static void register(JavaPlugin javaPlugin) {
        new PacketCoordinator(javaPlugin);
    }

    PacketPlayer player();

    PacketContext receive();

    PacketContext sent();

    PacketListener filter(PacketFilter... filter);

    PacketListener receive(PacketContext receive);

    PacketListener sent(PacketContext sent);

    boolean validate(WrappedPacket packet);

}
