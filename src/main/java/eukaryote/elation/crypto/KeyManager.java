package eukaryote.elation.crypto;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.io.FileUtils;


public class KeyManager {
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public KeyManager() throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		
		this.pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}
	
	public KeyManager(File pkeyfile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] pkeybytes = FileUtils.readFileToByteArray(pkeyfile);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pkeybytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		
		// read private key
		this.privateKey = kf.generatePrivate(spec);
		
		// read public key
		this.publicKey = kf.generatePublic(spec);
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(File pkeyfile) throws IOException {
		FileUtils.writeByteArrayToFile(pkeyfile, privateKey.getEncoded());
	}
}
