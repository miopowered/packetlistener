package eu.miopowered.packetlistener;

import eu.miopowered.packetlistener.netty.PacketDecoder;
import eu.miopowered.packetlistener.netty.PacketEncoder;
import eu.miopowered.packetlistener.entity.PacketPlayer;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PacketTarget {

    private static final String PACKET_ENCODER = "packetlistener-encoder";
    private static final String PACKET_DECODER = "packetlistener-decoder";

    public static void listen(PacketListener handler) {
        ChannelPipeline pipeline = handler.player().channel().pipeline();
        pipeline.addAfter("encoder", PACKET_ENCODER, new PacketEncoder(handler));
        pipeline.addAfter("decoder", PACKET_DECODER, new PacketDecoder(handler));
    }

    public static void remove(Player player) {
        ChannelPipeline pipeline = PacketPlayer.of(player).channel().pipeline();
        if (Objects.nonNull(pipeline.get(PACKET_ENCODER))) pipeline.remove(PACKET_ENCODER);
        if (Objects.nonNull(pipeline.get(PACKET_DECODER))) pipeline.remove(PACKET_DECODER);
    }

}
