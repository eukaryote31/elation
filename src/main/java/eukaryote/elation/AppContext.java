package eukaryote.elation;

import eukaryote.elation.db.PersistenceProvider;
import eukaryote.elation.networking.EgressPool;
import eukaryote.elation.networking.IngressSocket;
import lombok.Getter;

public class AppContext {
	@Getter
	PersistenceProvider persistenceProvider;
	
	@Getter
	IngressSocket ingress;
	
	@Getter
	EgressPool egress;
	
	public AppContext(PersistenceProvider pp) {
		this.persistenceProvider = pp;
	}
}
