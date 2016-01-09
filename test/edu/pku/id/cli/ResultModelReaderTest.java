
package edu.pku.id.cli;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.sat4j.reader.ParseFormatException;


public class ResultModelReaderTest {
	@Test
	public void test() throws IOException, ParseFormatException{
		final String fileName = "./data/my/0.model";
		//ResultModelReader reader = new ResultModelReader(fileName);
		ResultModelReader.main(new String[]{fileName});
	}
}
