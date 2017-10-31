package eukaryote.elation;

import java.io.File;

import eukaryote.elation.db.DBPersistenceProvider;

public class Main {
	public static void main(String[] args) {
		new AppContext(new DBPersistenceProvider(new File("data.db")));
	}
}
