package eukaryote.elation.crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

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
	public void testPubkeyDeterministic() throws Exception {
		File keyfile = new File(testdir, "keyfile1");

		KeyManager km = new KeyManager();
		byte[] pubkey = km.getPublicKey().getEncoded();

		km.writeToFile(keyfile);

		KeyManager km2 = new KeyManager(keyfile);
		byte[] pubkey2 = km2.getPublicKey().getEncoded();

		// ensure public key is deterministic
		assertArrayEquals(pubkey, pubkey2);
	}
	
	@Test
	public void testSignatures() throws Exception {
		File keyfile = new File(testdir, "keyfile2");

		byte[] message = {0, 1, 2, 3};
		
		KeyManager km = new KeyManager();
		km.writeToFile(keyfile);

		byte[] signature = km.sign(message);
		
		assertTrue(km.verify(message, signature));
		
		// modify message
		message[0]++;
		assertFalse(km.verify(message, signature));
		
	}
}
