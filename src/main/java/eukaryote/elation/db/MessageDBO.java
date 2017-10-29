package eukaryote.elation.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface MessageDBO {
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `messages` (\n" + 
			"	`hash`	BLOB NOT NULL UNIQUE,\n" + 
			"	`timestamp`	BIGINT NOT NULL,\n" + 
			"	`content`	TEXT NOT NULL,\n" + 
			"	`room`	TEXT NOT NULL,\n" + 
			"	`sender`	BLOB NOT NULL,\n" + 
			"	`parent`	BLOB,\n" + 
			"	`nonce`	INTEGER NOT NULL,\n" + 
			"	`signature`	BLOB NOT NULL,\n" + 
			"	PRIMARY KEY(`hash`)\n" + 
			");")
	void createTable();
	
	@SqlUpdate("CREATE  INDEX IF NOT EXISTS `ix_messages_timestamp` ON `messages` (`timestamp` DESC);")
	void createTimestampIndex();

	@SqlUpdate("CREATE  INDEX IF NOT EXISTS `ix_messages_room` ON `messages` (`room` );")
	void createRoomIndex();

	@SqlUpdate("INSERT INTO `messages`(`hash`,`timestamp`,`content`,`room`,`sender`,`parent`,`nonce`,`signature`) "
			+ "VALUES (:hash,:timestamp,:content,:room,:sender,:parent,:nonce,:signature);")
	void putMessage(@Bind("hash") byte[] hash, @Bind("timestamp") long timestamp, @Bind("content") String content,
			@Bind("room") String room, @Bind("sender") byte[] sender, @Bind("parent") byte[] parent,
			@Bind("nonce") int nonce, @Bind("signature") byte[] signature);

	void close();
}
