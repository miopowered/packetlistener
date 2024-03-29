package eu.miopowered.packetlistener.filter;

import eu.miopowered.packetlistener.entity.WrappedPacket;

public enum PacketState implements PacketFilter {

    HANDSHAKE,
    STATUS,
    LOGIN,
    PLAY;

    @Override
    public boolean validate(WrappedPacket packet) {
        return packet.state().equals(this);
    }
}
