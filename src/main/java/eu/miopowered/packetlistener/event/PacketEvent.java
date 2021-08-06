package eu.miopowered.packetlistener.event;

import eu.miopowered.packetlistener.entity.PacketPlayer;
import eu.miopowered.packetlistener.entity.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class PacketEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private PacketPlayer packetPlayer;
    private ChannelHandlerContext context;
    private WrappedPacket packet;

    @Setter
    @Accessors(fluent = false)
    private boolean cancelled;

    public PacketEvent(PacketPlayer packetPlayer, ChannelHandlerContext context, WrappedPacket packet) {
        this.packetPlayer = packetPlayer;
        this.context = context;
        this.packet = packet;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
