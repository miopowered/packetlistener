package eu.miopowered.packetlistener.entity;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.joor.Reflect;

@Getter
@Accessors(fluent = true)
public class PacketPlayer {

    private static final String VERSION = Bukkit
            .getServer()
            .getClass()
            .getPackage()
            .getName()
            .substring("org.bukkit.craftbukkit".length() + 1);


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

    public void closeChannel() {
        this.channel.close();
    }

    public void sendActionbar(String text) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);

        this.sendPacket(Reflect.onClass(nmsClassName("PacketPlayOutChat"))
                .create(
                        Reflect.onClass(nmsClassName("IChatBaseComponent$ChatSerializer"))
                                .call("a", jsonObject.toString()).get(),
                        (byte) 2
                )
                .get());
    }

    public void sendFullTitle(String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        this.sendTitle(TitleAction.TITLE, title, fadeInTime, showTime, fadeOutTime);
        this.sendTitle(TitleAction.SUBTITLE, subtitle, fadeInTime, showTime, fadeOutTime);
    }

    public void sendTitle(TitleAction titleAction, String text, int fadeInTime, int showTime, int fadeOutTime) {

        JsonObject titleObject = new JsonObject();
        titleObject.addProperty("text", text);

        this.sendPacket(Reflect.onClass(nmsClassName("PacketPlayOutTitle"))
                .create(
                        Reflect.onClass(nmsClassName("PacketPlayOutTitle$EnumTitleAction"))
                                .field(titleAction.name())
                                .get(),
                        Reflect.onClass(nmsClassName("IChatBaseComponent$ChatSerializer"))
                                .call("a", titleObject.toString()).get(),
                        fadeInTime,
                        showTime,
                        fadeOutTime
                ));
    }

    private String nmsClassName(String className) {
        return "net.minecraft.server." + VERSION + "." + className;
    }

    public static enum TitleAction {
        TITLE,
        SUBTITLE,
        TIMES,
        CLEAR,
        RESET;
    }
}
