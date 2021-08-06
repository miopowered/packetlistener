package eu.miopowered.packetlistener.context;

import eu.miopowered.packetlistener.entity.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;

public interface PacketContext {
    boolean handle(ChannelHandlerContext context, WrappedPacket packet);
}
