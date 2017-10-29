package eukaryote.elation.networking.packet;

import java.io.IOException;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import eukaryote.elation.AppContext;
import eukaryote.elation.Message;
import eukaryote.elation.PackingException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessagePacket extends Packet {
	Message msg;

	@Override
	public void executePacket(AppContext ctx) {
		ctx.getPersistenceProvider().putMessage(msg);

	}

	@Override
	public byte[] serialize() {
		try (MessageBufferPacker ret = MessagePack.newDefaultBufferPacker()) {
			byte[] payload = this.msg.getEncoded();

			ret.packString("msg");
			
			ret.packBinaryHeader(payload.length);
			ret.addPayload(payload);

			byte[] bytes = ret.toByteArray();

			ret.close();

			return bytes;
		} catch (IOException e) {
			throw new PackingException();
		}
	}
	
	static {
		Packet.packetfactories.put("msg", new MessagePacketFactory());
	}

	private static class MessagePacketFactory implements PacketFactory<MessagePacket> {
		
		@Override
		public MessagePacket build(MessageUnpacker unpacker) throws IllegalPacketException {
			MessagePacket ret;
			try {
				ret = new MessagePacket(new Message(unpacker.readPayload(unpacker.unpackBinaryHeader())));
			} catch (IOException e) {
				throw new IllegalPacketException();
			}

			return ret;
		}
	}

}
