package edu.pku.id.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;

public class DataStepFilter {
	private int stepV;
	private int stepT;
	private String file;

	public DataStepFilter(String file, int stepT, int stepV) {
		this.file = file;
		this.stepT = stepT;
		this.stepV = stepV;
	}

	public void filter() throws IOException {
		LineNumberReader reader = new LineNumberReader(new BufferedReader(
				new FileReader(file)));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file + ".filterred")));

		String line;
		int prevT = Integer.MIN_VALUE / 10;
		int prevV = Integer.MIN_VALUE / 10;

		while ((line = reader.readLine()) != null) {
			String[] split = line.split("\\s");
			int t = Integer.parseInt(split[0]);
			int m = Integer.parseInt(split[1]);
			int v = Integer.parseInt(split[2]);

			if (t - prevT >= stepT || v - prevV >= stepV) {
				writer.format("%d\t%d\t%d\n", t, m, v);
				prevT = t;
				prevV = v;
			}

		}
		writer.close();
	}

}
