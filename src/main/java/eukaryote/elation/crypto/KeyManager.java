package eukaryote.elation.crypto;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.FileUtils;

import lombok.Getter;
import lombok.SneakyThrows;

public class KeyManager extends PublicKeyManager {
	private KeyPair pair;

	@Getter
	private PrivateKey privateKey;

	public KeyManager() throws NoSuchAlgorithmException, NoSuchProviderException {
		super();

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);

		this.pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public KeyManager(File pkeyfile)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		super();

		byte[] pkeybytes = FileUtils.readFileToByteArray(pkeyfile);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pkeybytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		// read private key
		this.privateKey = kf.generatePrivate(spec);

		// read public key
		RSAPrivateCrtKey rsapub = (RSAPrivateCrtKey) privateKey;
		this.publicKey = kf.generatePublic(new RSAPublicKeySpec(rsapub.getModulus(), rsapub.getPublicExponent()));
	}

	@SneakyThrows(NoSuchAlgorithmException.class)
	public byte[] sign(byte[] message) throws SignatureException, InvalidKeyException {
		Signature sig = Signature.getInstance("SHA256withRSA");
		sig.initSign(privateKey);
		sig.update(message);

		return sig.sign();
	}

	public void writeToFile(File pkeyfile) throws IOException {
		FileUtils.writeByteArrayToFile(pkeyfile, privateKey.getEncoded());
	}
}
