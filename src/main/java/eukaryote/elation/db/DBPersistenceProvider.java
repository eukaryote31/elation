package eukaryote.elation.db;

import java.io.File;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import eukaryote.elation.Message;

public class DBPersistenceProvider implements PersistenceProvider {

	private DBI dbi;
	private MessageDBO mdbo;
	private Handle handle;

	public DBPersistenceProvider(File dbfile) {
		if (!dbfile.isFile() && dbfile.exists())
			throw new IllegalArgumentException("File expected!");

		dbi = new DBI("jdbc:sqlite:" + ((dbfile == null) ? ":memory:" : dbfile.getAbsolutePath()));
		mdbo = dbi.open(MessageDBO.class);
		handle = dbi.open();

		// initialize stuff
		mdbo.createTable();
		mdbo.createRoomIndex();
		mdbo.createTimestampIndex();
	}

	@Override
	public boolean putMessage(Message m) {
		int affected = mdbo.putMessage(m.getHash(), m.getPayload().getTimestamp(), m.getPayload().getContent(),
				m.getPayload().getRoom(), m.getPayload().getSender(), m.getPayload().getParent(),
				m.getPayload().getNonce(), m.getSignature());
		
		return affected != 0;
	}

	@Override
	public Message getMessageByHash(byte[] hash) {
		return mdbo.getMessage(hash);
	}

	@Override
	public List<Message> getMessagesByRoom(String room, long lowertime, long uppertime) {
		return mdbo.getMessagesByRoom(room, lowertime, uppertime);
	}

	@Override
	public List<Message> getChildren(byte[] parenthash) {
		return mdbo.getChildren(parenthash);
	}

}
