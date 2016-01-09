package edu.pku.id.cli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.sat4j.reader.ParseFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pku.id.CnfFileReader;

public class ResultModelReader {

	static final Logger logger = LoggerFactory.getLogger(ResultModelReader.class);


	private BufferedReader modelReader;

	CnfFileReader cnfFileReader;

	public ResultModelReader(String fileName) throws FileNotFoundException {
		this.modelReader = new BufferedReader(new FileReader(fileName));
		String cnfFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".cnf";
		cnfFileReader = new CnfFileReader(cnfFileName);
		System.out.println(cnfFileName);
	}

	List<Integer> getModel() throws IOException {
		List<Integer> model = new ArrayList<Integer>();
		String line;
		while ((line = modelReader.readLine()) != null) {
			Scanner scanner = new Scanner(line);
			String v = scanner.next();
			if (v.equals("v")) {
				int literal;
				while (scanner.hasNextInt() && (literal = scanner.nextInt()) != 0) {
					model.add(literal);
				}
			}
		}
		return model;
	}

	public double getIncDegree() throws IOException, ParseFormatException {
		int c = 0;
		int nVars = getVarsCount();
		List<Integer> model = getModel();
		Iterator<Integer> iterator = model.iterator();
		int index = 0;
		while (iterator.hasNext() && index < nVars) {
			iterator.next(); // skip two auxiliary vars
			iterator.next();
			int p_plus = iterator.next();
			int p_neg = iterator.next();
			if (p_plus > 0 && p_neg > 0) {
				c++;
				//logger.info("Find contradicts: {},{}", p_plus, p_neg );
			}
			index++;
		}
		logger.info("ID={}/{}", c, nVars );
		return (double) c / (double) nVars;
	}

	private int getVarsCount() throws IOException, ParseFormatException {
		cnfFileReader.parseInstance();
		return cnfFileReader.getNbOfVars();

	}

	public static void main(String args[]) throws IOException, ParseFormatException {
		ResultModelReader reader = new ResultModelReader(args[0]);
		double id = reader.getIncDegree();
		System.out.println("id = " + id);

	}
}
