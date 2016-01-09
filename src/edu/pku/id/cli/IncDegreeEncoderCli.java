package edu.pku.id.cli;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.sat4j.reader.ParseFormatException;

import edu.pku.id.InconsistencyDegreeEncoder;
import edu.pku.id.MultiValuedSemantics;
import edu.pku.id.SATProblemType;

public class IncDegreeEncoderCli {

	final static String SEMANTICS = "semantics";
	final static String ENCODING = "encoding";
	static SATProblemType encoding = SATProblemType.PartialMaxSAT;
	static MultiValuedSemantics semantics = MultiValuedSemantics.Four;
	static InconsistencyDegreeEncoder translater = null;

	/**
	 * @param args
	 * @throws ParseFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException,
			ParseFormatException, ParseException {
		
//		for(String arg:args){
//			System.out.print(arg + " ");
//		}
//		System.out.println();
		
		
		CommandLineParser parser = new PosixParser();

		// create the Options
		Options options = new Options();

		options.addOption("s", SEMANTICS, true,
				"The semantics used for inconsistency measuring. 4 or Q");
		options
				.addOption(
						"e",
						ENCODING,
						true,
						"Encoding method. \n Partial MaxSAT: p \n Weighted MaxSAT: w \n Pseudo Booean Problem: p");

		// String[]
		// args = "-s 4 -e p 123 5".split(" ");

		// parse the command line arguments
		CommandLine line = parser.parse(options, args);

		List<?> argList = line.getArgList();
	
	//	System.out.println("**");
		//if (line.hasOption(SEMANTICS)) {
		//	System.out.println("has Semantics Option");
			
			//String sematicOptionValue = line.getOptionValue(SEMANTICS);
		String sematicOptionValue = args[1];
			if (sematicOptionValue.equalsIgnoreCase("4"))
				semantics = MultiValuedSemantics.Four;
			else if (sematicOptionValue.equalsIgnoreCase("Q"))
				semantics = MultiValuedSemantics.QC;
		//}

		//if (line.hasOption(ENCODING)) {
			//String encodingOptionValue = line.getOptionValue(ENCODING);
			String encodingOptionValue = args[3];
			
			if (encodingOptionValue.equalsIgnoreCase("p")) {
				encoding = SATProblemType.PartialMaxSAT;
			} else if (encodingOptionValue.equalsIgnoreCase("w")) {
				encoding = SATProblemType.WeightedMaxSAT;
			}
		//}

//		System.out.println("Semantics:" + semantics + " Encoding:" + encoding);

		String cnfFileName = (String) argList.get(0);

		translater = new InconsistencyDegreeEncoder(encoding, semantics);

		translater.translate(cnfFileName);

	}

}
