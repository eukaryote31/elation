package eukaryote.elation.networking.packet;

import org.msgpack.core.MessageUnpacker;

public interface PacketFactory<T extends Packet> {
	public T build(MessageUnpacker unpacker);
}
