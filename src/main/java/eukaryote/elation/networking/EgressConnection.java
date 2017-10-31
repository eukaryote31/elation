package eukaryote.elation.networking;

import java.io.IOException;
import java.net.URI;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketListener;

import eukaryote.elation.networking.packet.Packet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EgressConnection {
	private WebSocket websocket;

	public EgressConnection(ConnectionPool pool, URI uri) throws IOException {
		WebSocketFactory factory = new WebSocketFactory();
		websocket = factory.createSocket(uri);
		
		websocket.addListener(new WebSocketAdapter() {
			@Override
			public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
				pool.onMessage(binary);
			}
		});
		
		log.info("Connected to {}", uri);
	}
	
	public void sendPacket(byte[] p) {
		websocket.sendBinary(p);
	}
}
