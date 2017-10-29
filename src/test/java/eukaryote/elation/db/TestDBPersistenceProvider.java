package eukaryote.elation.db;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import eukaryote.elation.Message;

public class TestDBPersistenceProvider {
	@Rule
	public TemporaryFolder tf = new TemporaryFolder();
	
	@Test
	public void testMessagePutGet() throws IOException {
		File dbfile = tf.newFile();
		
		PersistenceProvider pp = new DBPersistenceProvider(dbfile);

		Message.Payload payload = new Message.Payload("content", "room", new byte[] { 0, 1, 2 }, new byte[] { 0, 1, 2, 4 });
		Message p = new Message(payload, new byte[] { 0, 1, 2, 99 });
		
		pp.putMessage(p);
		
		Message p2 = pp.getMessageByHash(p.getHash());
		
		assertEquals(p, p2);
	}
}
