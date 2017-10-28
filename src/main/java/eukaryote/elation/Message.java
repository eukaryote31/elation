package eukaryote.elation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import eukaryote.elation.crypto.KeyManager;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Message {

	/**
	 * Everything but the signature in the message
	 */
	@EqualsAndHashCode
	@AllArgsConstructor
	@ToString
	public static class Payload {

		long timestamp;
		String content;
		String room;
		byte[] sender;
		byte[] parent;
		int nonce;

		public Payload(KeyManager km, String content, String room, byte[] parent) {
			this(content, room, km.getPublicKey().getEncoded(), parent);
		}

		public Payload(String content, String room, byte[] sender, byte[] parent) {
			this(System.currentTimeMillis(), content, room, sender, parent, 0);
		}

		/**
		 * Unpack message from bytes
		 */
		public Payload(byte[] encoded) throws IOException {
			MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(encoded);

			nonce = unpacker.unpackInt();
			sender = unpacker.readPayload(unpacker.unpackBinaryHeader());
			parent = unpacker.readPayload(unpacker.unpackBinaryHeader());
			timestamp = unpacker.unpackLong();
			room = unpacker.unpackString();
			content = unpacker.unpackString();

		}

		/**
		 * Encode to bytes
		 */
		public byte[] encodeAsBytes() throws IOException {
			MessageBufferPacker ret = MessagePack.newDefaultBufferPacker();

			ret.packInt(nonce);

			ret.packBinaryHeader(sender.length);
			ret.addPayload(sender);

			ret.packBinaryHeader(parent.length);
			ret.addPayload(parent);

			ret.packLong(timestamp);

			ret.packString(room);

			ret.packString(content);

			byte[] bytes = ret.toByteArray();

			ret.close();

			return bytes;
		}

		public void doPOW() {
			// TODO: implement pow
		}

	}

	Payload payload;

	byte[] signature;

	public Message(KeyManager km, String content, String room, byte[] parent)
			throws InvalidKeyException, SignatureException, IOException {
		payload = new Payload(km, content, room, parent);
		payload.doPOW();
		signature = km.sign(payload.encodeAsBytes());
	}
}
