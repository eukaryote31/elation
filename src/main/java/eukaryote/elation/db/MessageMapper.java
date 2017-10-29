package eukaryote.elation.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import eukaryote.elation.Message;

public class MessageMapper implements ResultSetMapper<Message> {

	@Override
	public Message map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Message(new Message.Payload(r.getLong("timestamp"), r.getString("content"), r.getString("room"),
				r.getBytes("sender"), r.getBytes("parent"), r.getInt("nonce")), r.getBytes("signature"));
	}

}
