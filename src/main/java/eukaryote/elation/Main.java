package eukaryote.elation;

import static spark.Spark.*;

import eukaryote.elation.networking.IngressSocket;

public class Main {
	public static void main(String[] args) {
		port(7933);
		webSocket("/socket", IngressSocket.class);
	}
}
