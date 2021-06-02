package eu.miopowered.packetlistener.netty;

import eu.miopowered.packetlistener.PacketListener;
import eu.miopowered.packetlistener.reflection.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PacketEncoder extends MessageToMessageEncoder<Object> {

    private PacketListener packetListener;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object packet, List<Object> list) throws Exception {
        WrappedPacket wrappedPacket = WrappedPacket.from(packet);
        if (this.packetListener.validate(wrappedPacket)) {
            this.packetListener.sent().handle(channelHandlerContext, wrappedPacket);
        }
        list.add(packet);
    }
}
