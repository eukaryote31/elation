package eukaryote.elation.db;

import java.io.File;

import org.skife.jdbi.v2.DBI;

import eukaryote.elation.Message;
import eukaryote.elation.crypto.HashFunctions;

public class DBPersistenceProvider implements PersistenceProvider {

	private DBI dbi;
	private MessageDBO mdbo;

	public DBPersistenceProvider(File dbfile) {
		dbi = new DBI("jdbc:sqlite:" + ((dbfile == null) ? ":memory:" : dbfile.getAbsolutePath()));
		mdbo = dbi.open(MessageDBO.class);

		// initialize stuff
		mdbo.createTable();
		mdbo.createRoomIndex();
		mdbo.createTimestampIndex();
	}

	@Override
	public void putMessage(Message m) {
		mdbo.putMessage(HashFunctions.hash160(m.encodeAsBytes()), m.getPayload().getTimestamp(), m.getPayload().getContent(), m.getPayload().getRoom(),
				m.getPayload().getSender(), m.getPayload().getParent(), m.getPayload().getNonce(), m.getSignature());
	}

	@Override
	public Message getMessageByHash(byte[] hash) {
		return null;
	}

	@Override
	public Message[] getMessagesByRoom(String room, long lowertime, long uppertime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message[] getChildren(byte[] parenthash) {
		// TODO Auto-generated method stub
		return null;
	}

}
