package eukaryote.elation.networking;

import java.io.IOException;
import java.net.URI;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import eukaryote.elation.networking.packet.Packet;

public class EgressConnection {
	private WebSocket websocket;

	public EgressConnection(URI uri) throws IOException {
		WebSocketFactory factory = new WebSocketFactory();
		websocket = factory.createSocket(uri);
	}
	
	public void sendPacket(Packet p) {
		websocket.sendBinary(p.serialize());
	}
}
