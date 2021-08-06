package eu.miopowered.packetlistener.reflection;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.joor.Reflect;

@Getter
@Accessors(fluent = true)
public class PacketPlayer {

    public static PacketPlayer of(Player player) {
        return new PacketPlayer(player);
    }

    private Player player;

    private Object playerConnection;
    private Channel channel;

    private PacketPlayer(Player player) {
        this.player = player;

        this.playerConnection = Reflect.on(this.player).call("getHandle").get("playerConnection");
        this.channel = Reflect.on(this.playerConnection).field("networkManager").get("channel");
    }

    public void sendPacket(Object packet) {
        Reflect.on(this.playerConnection).call("sendPacket", packet);
    }
}
