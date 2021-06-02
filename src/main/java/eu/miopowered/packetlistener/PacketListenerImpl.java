package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.context.PacketReceive;
import eu.miopowered.packetlistener.context.PacketSent;
import eu.miopowered.packetlistener.filter.PacketFilter;
import eu.miopowered.packetlistener.reflection.PacketPlayer;
import eu.miopowered.packetlistener.reflection.WrappedPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Accessors(fluent = true)
class PacketListenerImpl implements PacketListener {

    @Setter
    private PacketReceive receive;
    @Setter
    private PacketSent sent;

    private PacketPlayer player;
    private List<PacketFilter> filters;

    PacketListenerImpl(Player player) {
        this.player = PacketPlayer.of(player);
        this.filters = new ArrayList<>();
    }

    @Override
    public PacketListener filter(PacketFilter... packetFilters) {
        this.filters.addAll(Arrays.asList(packetFilters));
        return this;
    }

    @Override
    public boolean validate(WrappedPacket packet) {
        return this.filters.stream().allMatch(filter -> filter.validate(packet));
    }
}
