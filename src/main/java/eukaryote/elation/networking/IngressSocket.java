package eukaryote.elation.networking;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import eukaryote.elation.AppContext;
import eukaryote.elation.networking.packet.IllegalPacketException;
import eukaryote.elation.networking.packet.Packet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket
public class IngressSocket {
	@Getter
	private Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
	private AppContext ctx;
	private ConnectionPool pool;

	public IngressSocket(ConnectionPool pool, AppContext ctx) {
		this.pool = pool;
		this.ctx = ctx;
	}

	@OnWebSocketConnect
	public void connected(Session session) {
		log.info("Remote node {} connected.", session.getRemoteAddress());
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		log.info("Remote node {} disconnected.", session.getRemoteAddress());
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, byte[] message) throws IOException {
		pool.onMessage(message);
	}

	public void broadcast(byte[] b) {
		sessions.forEach((session) -> {
			try {
				session.getRemote().sendBytes(ByteBuffer.wrap(b));
			} catch (IOException e) {
				log.error("Error broadcasting message! ", e);
			}
		});
	}
}
