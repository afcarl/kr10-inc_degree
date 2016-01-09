package edu.pku.id.mus;

import java.io.IOException;

import org.junit.Test;
import org.sat4j.reader.ParseFormatException;

import static org.junit.Assert.*;

public class IDQ_MUSTest {

	@Test
	public void test() throws IOException, ParseFormatException {
		//String instance = "data/paper/C168_FW_UT_855";
		String instance = "data/paper/C168_FW_UT_855";
		//String instance = "data/my/t";

		//String musFile = instance + ".hycam.mus";
		String musFile = instance + ".mcs";
		String cnfFile = instance + ".cnf";

		System.out.println(new IDQ_MUS().getIDQ(cnfFile,musFile));
	}
}
