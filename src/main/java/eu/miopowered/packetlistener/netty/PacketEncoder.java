package eu.miopowered.packetlistener.netty;

import eu.miopowered.packetlistener.PacketListener;
import eu.miopowered.packetlistener.entity.WrappedPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PacketEncoder extends MessageToMessageEncoder<Object> {

    private static final String VALIDATE_PACKET = "packet(handshake|status|login|play)(in|out).*";

    private PacketListener packetListener;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object packet, List<Object> list)
            throws Exception {

        String packetName = packet.getClass().getSimpleName().toLowerCase();
        
        try{
        WrappedPacket wrappedPacket = WrappedPacket.from(packet);
        if (packetName.matches(VALIDATE_PACKET)
                && this.packetListener.validate(wrappedPacket)
                && !this.packetListener.sent().handle(channelHandlerContext, wrappedPacket)) {
            return;
        }
         }catch(Exception e) {
        }

        list.add(packet);
    }
}
