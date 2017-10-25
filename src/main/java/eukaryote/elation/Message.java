package eukaryote.elation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

public class Message {
	long timestamp;
	String content;
	String room;
	byte[] sender;
	byte[] parent;
	int nonce;
	byte[] signature;
	
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
}
