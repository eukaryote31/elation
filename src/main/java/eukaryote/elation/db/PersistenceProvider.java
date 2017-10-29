package eukaryote.elation.db;

import eukaryote.elation.Message;

public interface PersistenceProvider {
	public void putMessage(Message m);
	
	public Message getMessageByHash(byte[] hash);

	public Message[] getMessagesByRoom(String room, long lowertime, long uppertime);
	
	public Message[] getChildren(byte[] parenthash);
}
