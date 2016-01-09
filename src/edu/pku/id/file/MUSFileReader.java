package edu.pku.id.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.sat4j.specs.IVecInt;

public class MUSFileReader {
	private LineNumberReader in;

	private List<List<Integer>> muses;

	public MUSFileReader(String fileName) throws FileNotFoundException {
		this(new LineNumberReader(new BufferedReader(new FileReader(fileName))));
	}

	public MUSFileReader(LineNumberReader reader) {
		this.in = reader;
		this.muses = new ArrayList<List<Integer>>();
	}

	public List<List<Integer>> read() throws IOException {
		String line;
		while ((line = in.readLine()) != null) {
			String[] ints = line.split("\\s");
			List<Integer> mus = new ArrayList<Integer>(ints.length);
			for (int i = 0; i < ints.length; i++) {
				mus.add(Integer.parseInt(ints[i]));
			}
			muses.add(mus);
		}
		return muses;
	}

}
