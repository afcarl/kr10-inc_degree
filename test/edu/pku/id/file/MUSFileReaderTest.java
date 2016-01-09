package edu.pku.id.file;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;


public class MUSFileReaderTest {

	@Test
	public void testMUSFileReaderString() {
	}

	@Test
	public void testMUSFileReaderLineNumberReader() {
	}

	@Test
	public void testRead() throws IOException {
		MUSFileReader musFileReader = new MUSFileReader("test/edu/pku/id/file/res/C168_FW_UT_855.mus");
		List<List<Integer>> muses = musFileReader.read();
		for(List<Integer> mus: muses){
			System.out.println(mus);
		}
	}

}
