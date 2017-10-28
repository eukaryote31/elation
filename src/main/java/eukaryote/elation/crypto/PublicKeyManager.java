package eukaryote.elation.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import lombok.Getter;
import lombok.Setter;

public class PublicKeyManager {
	@Getter
	@Setter
	PublicKey publicKey;

	public PublicKeyManager(byte[] pubkey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(pubkey);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		publicKey = kf.generatePublic(spec);
	}

	public PublicKeyManager() {
	}

	public boolean verify(byte[] message, byte[] signature)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signer = Signature.getInstance("SHA256withRSA");
		signer.initVerify(publicKey);
		signer.update(message);

		return signer.verify(signature);
	}
}
