package edu.pku.id.cli;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.sat4j.reader.ParseFormatException;

public class IncDegreeEncoderCliTest {

	//@Test
	public void testMain() throws IOException, ParseFormatException, ParseException {
		// String dataFile =
		// "./data/maxsat_industrial/SeanSafarpour/b14_opt_bug2_vec1-gate-0.dimacs.seq.filtered.cnf";
		// String dataFile =
		// "./data/maxsat_crafted/MAXCUT/SPINGLASS/t3pm3-6.spn.cnf";

		//String dataRoot = "./data/maxsat_crafted/MAXCUT/SPINGLASS";
		String dataRoot = "./data/my";
		
		final File[] dataFiles = new File(dataRoot).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cnf");
			}
		});

		int i = 0;
		long t0 = System.currentTimeMillis();
		for (File file : dataFiles) {
			i++;
			String[] args = ("-s;4;-e;w;" + file.getAbsolutePath()).split(";");
			IncDegreeEncoderCli.main(args);
			long t1 = System.currentTimeMillis();
			long dt = t1 - t0;
			System.out.println(file + ":" + dt + "ms");
			t0 = t1;
			
		}

	}
	
	@Test
	public void test() throws IOException, ParseFormatException, ParseException{
		String[] args = "-s 4 -e w ./data/paper/uuf75-014.cnf ./data/paper/uuf75-014.w.wcnf".split(" ");
		IncDegreeEncoderCli.main(args);
	}
}
