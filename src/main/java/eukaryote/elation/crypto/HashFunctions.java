package eukaryote.elation.crypto;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

public class HashFunctions {

	public static String pubkeyToId(byte[] pubkey) {
		return Base58.encode(hash160(pubkey));
	}
	
	public static byte[] hash160(byte[] data) {
		RIPEMD160Digest d = new RIPEMD160Digest();
		d.update(data, 0, data.length);
		byte[] hash = new byte[d.getDigestSize()];
		d.doFinal(hash, 0);
		return hash;
	}
}
