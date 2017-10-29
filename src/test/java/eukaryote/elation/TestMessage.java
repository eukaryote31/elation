package eukaryote.elation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import eukaryote.elation.Message.Payload;

public class TestMessage {
	@Test
	public void testPayloadSerialize() throws IOException {
		Message.Payload p = new Message.Payload("content", "room", new byte[] { 0, 1, 2 }, new byte[] { 0, 1, 2, 4 });

		Payload p2 = new Message.Payload(p.getEncoded());

		assertEquals(p, p2);
	}

	@Test
	public void testMessageSerialize() throws IOException {
		Message.Payload payload = new Message.Payload("content", "room", new byte[] { 0, 1, 2 },
				new byte[] { 0, 1, 2, 4 });
		Message p = new Message(payload, new byte[] { 0, 1, 2, 99 });

		Message p2 = new Message(p.getEncoded());

		assertEquals(p, p2);
	}
}
