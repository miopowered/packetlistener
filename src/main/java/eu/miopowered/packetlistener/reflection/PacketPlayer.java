package eu.miopowered.packetlistener.reflection;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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

        try {
            Object entityPlayer = PacketReflection.obcClass("entity.CraftPlayer").getMethod("getHandle").invoke(player);
            Field playerConnectionField = entityPlayer.getClass().getField("playerConnection");
            this.playerConnection = playerConnectionField.get(entityPlayer);
            Object networkManager = this.playerConnection.getClass().getField("networkManager").get(this.playerConnection);
            this.channel = (Channel) networkManager.getClass().getField("channel").get(networkManager);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Object packet) {
        try {
            this.playerConnection.getClass().getMethod("sendPacket").invoke(this.playerConnection, packet);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
