package eukaryote.elation;

import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
		port(7933);
		webSocket("/socket", IncomingSocket.class);
	}
}
