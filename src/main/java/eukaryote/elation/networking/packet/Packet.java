package eukaryote.elation.networking.packet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import eukaryote.elation.AppContext;

public abstract class Packet {
	public static final Map<String, PacketFactory<?>> packetfactories = new HashMap<>();

	public abstract void executePacket(AppContext ctx);
	
	public abstract byte[] serialize();

	public static Packet deserialize(byte[] packet) throws IOException, IllegalPacketException {
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(packet);
		String packetType = unpacker.unpackString();

		return packetfactories.get(packetType).build(unpacker);
	}
}
