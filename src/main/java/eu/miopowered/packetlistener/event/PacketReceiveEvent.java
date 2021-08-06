package eu.miopowered.packetlistener.event;

import eu.miopowered.packetlistener.entity.PacketPlayer;
import eu.miopowered.packetlistener.entity.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class PacketReceiveEvent extends PacketEvent {
    public PacketReceiveEvent(PacketPlayer packetPlayer, ChannelHandlerContext context, WrappedPacket packet) {
        super(packetPlayer, context, packet);
    }
}
