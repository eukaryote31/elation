package eukaryote.elation;

import eukaryote.elation.db.PersistenceProvider;
import lombok.Getter;

public class AppContext {
	@Getter
	PersistenceProvider persistenceProvider;
}
