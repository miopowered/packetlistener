package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.context.PacketContext;
import eu.miopowered.packetlistener.filter.PacketFilter;
import eu.miopowered.packetlistener.entity.PacketPlayer;
import eu.miopowered.packetlistener.entity.WrappedPacket;
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
    private PacketContext receive;
    @Setter
    private PacketContext sent;

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
