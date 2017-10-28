package eukaryote.elation.crypto;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TestKeyManager {
	
	@Rule
	public TemporaryFolder tf = new TemporaryFolder();
	
	File testdir;
	
	@Before
	public void init() throws IOException {
		testdir = tf.newFolder();
	}
	
	@Test
	public void test() throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		File keyfile = new File(testdir, "keyfile");
		
		KeyManager km = new KeyManager();
		byte[] pubkey = km.getPublicKey().getEncoded();

		km.writeToFile(keyfile);
		
		KeyManager km2 = new KeyManager(keyfile);
		byte[] pubkey2 = km2.getPublicKey().getEncoded();
		
		// ensure public key is deterministic
		assertArrayEquals(pubkey, pubkey2);
	}
}
