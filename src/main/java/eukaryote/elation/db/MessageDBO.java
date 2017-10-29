package eukaryote.elation.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import eukaryote.elation.Message;

public interface MessageDBO {
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `messages` (\n" + "	`hash`	BLOB NOT NULL UNIQUE,\n"
			+ "	`timestamp`	BIGINT NOT NULL,\n" + "	`content`	TEXT NOT NULL,\n" + "	`room`	TEXT NOT NULL,\n"
			+ "	`sender`	BLOB NOT NULL,\n" + "	`parent`	BLOB,\n" + "	`nonce`	INTEGER NOT NULL,\n"
			+ "	`signature`	BLOB NOT NULL,\n" + "	PRIMARY KEY(`hash`)\n" + ");")
	void createTable();

	@SqlUpdate("CREATE  INDEX IF NOT EXISTS `ix_messages_timestamp` ON `messages` (`timestamp` DESC);")
	void createTimestampIndex();

	@SqlUpdate("CREATE  INDEX IF NOT EXISTS `ix_messages_room` ON `messages` (`room` );")
	void createRoomIndex();

	@SqlUpdate("INSERT OR IGNORE INTO `messages`(`hash`,`timestamp`,`content`,`room`,`sender`,`parent`,`nonce`,`signature`) "
			+ "VALUES (:hash,:timestamp,:content,:room,:sender,:parent,:nonce,:signature);")
	void putMessage(@Bind("hash") byte[] hash, @Bind("timestamp") long timestamp, @Bind("content") String content,
			@Bind("room") String room, @Bind("sender") byte[] sender, @Bind("parent") byte[] parent,
			@Bind("nonce") int nonce, @Bind("signature") byte[] signature);

	@Mapper(MessageMapper.class)
	@SqlQuery("SELECT * FROM messages WHERE hash = :hash")
	Message getMessage(@Bind("hash") byte[] hash);

	@Mapper(MessageMapper.class)
	@SqlQuery("SELECT * FROM messages WHERE room = :room AND timestamp > :lowertime AND timestamp < :uppertime")
	List<Message> getMessagesByRoom(@Bind("room") String room, @Bind("lowertime") long lowertime,
			@Bind("uppertime") long uppertime);

	@Mapper(MessageMapper.class)
	@SqlQuery("SELECT * FROM messages WHERE room = :room AND timestamp > :lowertime AND timestamp < :uppertime AND parent IS NULL")
	List<Message> getRootMessagesByRoom(@Bind("room") String room, @Bind("lowertime") long lowertime,
			@Bind("uppertime") long uppertime);

	@Mapper(MessageMapper.class)
	@SqlQuery("SELECT * FROM messages WHERE parent = :hash")
	List<Message> getChildren(@Bind("hash") byte[] hash);

	void close();
}
