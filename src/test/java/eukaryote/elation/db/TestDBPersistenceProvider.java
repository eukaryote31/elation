package eukaryote.elation.db;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import eukaryote.elation.Message;
import eukaryote.elation.Message.Payload;

public class TestDBPersistenceProvider {
	@Rule
	public TemporaryFolder tf = new TemporaryFolder();

	@Test
	public void testMessagePutGet() throws IOException {
		File dbfile = tf.newFile();

		PersistenceProvider pp = new DBPersistenceProvider(dbfile);

		Payload payload = new Payload("content", "room", new byte[] { 0, 1, 2 }, new byte[] { 0, 1, 2, 4 });
		Message p = new Message(payload, new byte[] { 0, 1, 2, 99 });

		pp.putMessage(p);

		Message p2 = pp.getMessageByHash(p.getHash());

		assertEquals(p, p2);
	}

	@Test
	public void testGetByParent() throws IOException {
		File dbfile = tf.newFile();

		PersistenceProvider pp = new DBPersistenceProvider(dbfile);

		Message p = new Message(new Payload("content", "room", "sender".getBytes(), "parent".getBytes()),
				"signature".getBytes());
		Message p2 = new Message(new Payload("content2", "room", "sender2".getBytes(), "parent".getBytes()),
				"signature".getBytes());
		
		assertNotEquals(p, p2);

		pp.putMessage(p);
		pp.putMessage(p2);
		
		List<Message> children = pp.getChildren("parent".getBytes());

		assertTrue(children.contains(p));
		assertTrue(children.contains(p2));
		assertEquals(2, children.size());
	}
}
