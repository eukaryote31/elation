package eukaryote.elation.db;

import java.util.List;

import eukaryote.elation.Message;

public interface PersistenceProvider {
	public boolean putMessage(Message m);

	public Message getMessageByHash(byte[] hash);

	public List<Message> getMessagesByRoom(String room, long lowertime, long uppertime);

	public List<Message> getChildren(byte[] parenthash);
}
