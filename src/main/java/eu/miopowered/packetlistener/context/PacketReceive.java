package eu.miopowered.packetlistener.context;

import eu.miopowered.packetlistener.reflection.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;

public interface PacketReceive {

    void handle(ChannelHandlerContext context, WrappedPacket packet);

}
