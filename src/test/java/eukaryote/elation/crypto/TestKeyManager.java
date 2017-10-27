package eukaryote.elation.crypto;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TestKeyManager {
	
	@Rule
	TemporaryFolder tf = new TemporaryFolder();
	
	File testdir;
	
	@Before
	public void init() throws IOException {
		testdir = tf.newFolder();
	}
	
	@Test
	public void test() throws IOException {
		
		
	}
}
