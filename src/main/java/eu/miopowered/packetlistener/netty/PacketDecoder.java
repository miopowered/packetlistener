package eu.miopowered.packetlistener.netty;

import eu.miopowered.packetlistener.PacketListener;
import eu.miopowered.packetlistener.entity.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PacketDecoder extends MessageToMessageDecoder<Object> {

    private PacketListener packetListener;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object packet, List<Object> list) throws Exception {
        WrappedPacket wrappedPacket = WrappedPacket.from(packet);
        if (this.packetListener.validate(wrappedPacket) && !this.packetListener.receive().handle(channelHandlerContext, wrappedPacket)) {
            return;
        }
        list.add(packet);
    }
}
