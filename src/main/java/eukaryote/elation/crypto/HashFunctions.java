package eukaryote.elation.crypto;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

public class HashFunctions {

	public static String pubkeyToId(byte[] pubkey) {
		RIPEMD160Digest d = new RIPEMD160Digest();
		d.update(pubkey, 0, pubkey.length);
		byte[] hash = new byte[d.getDigestSize()];
		d.doFinal(hash, 0);
		return Base58.encode(hash);
	}
}
