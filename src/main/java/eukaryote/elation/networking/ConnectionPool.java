package eukaryote.elation.networking;

import static spark.Spark.init;
import static spark.Spark.port;
import static spark.Spark.webSocket;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import eukaryote.elation.AppContext;
import eukaryote.elation.networking.packet.IllegalPacketException;
import eukaryote.elation.networking.packet.Packet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionPool {
	IngressSocket ingress;

	List<EgressConnection> egress = new ArrayList<>();

	private AppContext ctx;

	public ConnectionPool(AppContext ctx, int listenport) {
		this.ctx = ctx;
		ingress = new IngressSocket(this, this.ctx);
		
		port(listenport);
		webSocket("/", ingress);
		init();
	}
	
	public void broadcast(Packet p) {
		byte[] message = p.serialize();

		// broadcast to nodes listening to this one
		ingress.broadcast(message);

		// broadcast to nodes we're connected to
		egress.forEach((egress) -> {
			egress.sendPacket(message);
		});
	}
	
	public void connect(URI uri) throws IOException {
		EgressConnection connection = new EgressConnection(this, uri);
		egress.add(connection);
	}
	
	public void onMessage(byte[] message) {
		try {
			Packet.deserialize(message).executePacket(ctx);
		} catch (IOException e) {
			log.error("IO Error deserializing packet!", e);
		} catch (IllegalPacketException e) {
			log.warn("Recieved an illegal packet!", e);
		}
	}
}
