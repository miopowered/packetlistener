package eu.miopowered.packetlistener.filter;

import eu.miopowered.packetlistener.entity.WrappedPacket;

public interface PacketFilter {

    static PacketFilter name(String name) {
        return packet -> packet.name().equals(name);
    }

    boolean validate(WrappedPacket packet);

}
