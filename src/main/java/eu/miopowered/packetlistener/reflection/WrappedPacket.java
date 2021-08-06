package eu.miopowered.packetlistener.reflection;

import eu.miopowered.packetlistener.filter.PacketState;
import eu.miopowered.packetlistener.filter.PacketType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.joor.Reflect;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WrappedPacket {

    public static WrappedPacket from(Object packet) {
        return new WrappedPacket(packet);
    }

    private Object packet;
    private Reflect reflect;

    private PacketState state;
    private PacketType type;
    private String name;

    public WrappedPacket(Object packet) {
        this.packet = packet;
        this.reflect = Reflect.on(this.packet);

        String[] parts = this.packetName().split("(?=[A-Z])", 4);
        this.state = PacketState.valueOf(parts[1].toUpperCase());
        this.type = PacketType.valueOf(parts[2].toUpperCase());
        this.name = parts[3];
    }

    public String packetName() {
        return this.packet.getClass().getSimpleName();
    }

}
