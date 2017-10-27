package eukaryote.elation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import lombok.Getter;

public class Message {
	
	long timestamp;
	
	String content;
	
	String room;
	
	byte[] sender;
	
	byte[] parent;
	
	int nonce;
	
	byte[] signature;
	
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

		ret.packBinaryHeader(signature.length);
		ret.addPayload(signature);
		
		byte[] bytes = ret.toByteArray();
		
		ret.close();
		
		return bytes;
	}
	
	
	/**
	 * Unpack message from bytes
	 */
	public Message(byte[] encoded) throws IOException {
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(encoded);
		
		nonce = unpacker.unpackInt();
		sender = unpacker.readPayload(unpacker.unpackBinaryHeader());
		parent = unpacker.readPayload(unpacker.unpackBinaryHeader());
		timestamp = unpacker.unpackLong();
		room = unpacker.unpackString();
		content = unpacker.unpackString();
		signature = unpacker.readPayload(unpacker.unpackBinaryHeader());
		
	}
}
