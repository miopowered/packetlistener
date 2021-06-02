package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.context.PacketReceive;
import eu.miopowered.packetlistener.context.PacketSent;
import eu.miopowered.packetlistener.filter.PacketFilter;
import eu.miopowered.packetlistener.reflection.PacketPlayer;
import eu.miopowered.packetlistener.reflection.WrappedPacket;
import org.bukkit.entity.Player;

public interface PacketListener {

    static PacketListener of(Player player) {
        return new PacketListenerImpl(player);
    }

    PacketPlayer player();

    PacketReceive receive();

    PacketSent sent();

    PacketListener filter(PacketFilter... filter);

    PacketListener receive(PacketReceive receive);

    PacketListener sent(PacketSent sent);

    boolean validate(WrappedPacket packet);

}
