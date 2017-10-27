package eukaryote.elation.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBase58 {
	@Test
	public void testEncodeDecode() {
		byte[] original = new byte[] {(byte) 0x00, (byte) 0x62, (byte) 0xE9, (byte) 0x07, (byte) 0xB1, (byte) 0x5C, (byte) 0xBF, (byte) 0x27, (byte) 0xD5, (byte) 0x42, (byte) 0x53, (byte) 0x99, (byte) 0xEB, (byte) 0xF6, (byte) 0xF0, (byte) 0xFB, (byte) 0x50, (byte) 0xEB, (byte) 0xB8, (byte) 0x8F, (byte) 0x18, (byte) 0xC2, (byte) 0x9B, (byte) 0x7D, (byte) 0x93};
		String encoded = Base58.encode(original);
		assertEquals("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", encoded);
		
		byte[] decoded = Base58.decode(encoded);
		assertArrayEquals(original, decoded);
	}
}
