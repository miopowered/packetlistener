package eu.miopowered.packetlistener.filter;

import eu.miopowered.packetlistener.reflection.WrappedPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PacketType implements PacketFilter {

    OUT,
    IN;

    @Override
    public boolean validate(WrappedPacket packet) {
        return packet.type().equals(this);
    }
}
