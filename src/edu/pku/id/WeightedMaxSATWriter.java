package edu.pku.id;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.sat4j.specs.IVecInt;

public class WeightedMaxSATWriter implements ProblemWriter {

	protected List<WeightedClause> weightedClauses;
	protected int nbVars;
	FileWriter writer;

	public WeightedMaxSATWriter(List<WeightedClause> weightedClauses, int nbVars, String fileName) throws IOException {
		this.weightedClauses = weightedClauses;
		this.nbVars = nbVars;
		writer = new FileWriter(fileName);
	}

	/* (non-Javadoc)
	 * @see edu.pku.id.ProblemWriter#write()
	 */
	public void write() throws IOException {
		writeHeader();
		writeProblem();
		writeWeightedClauses();
		writer.flush();
		writer.close();
	}

	private void writeWeightedClauses() throws IOException {
		// 2 16 31 -58 0
		for (WeightedClause weightedClause : weightedClauses) {
			writeWeightedClause(weightedClause);
		}
	}

	private void writeWeightedClause(WeightedClause weightedClause) throws IOException {
		// 2 16 31 -58 0
		writer.write(String.format("%d ", weightedClause.getWeight()));
		IVecInt clause = weightedClause.getClause();
		for (int i = 0; i < clause.size(); i++) {
			writer.write(String.format("%d ", clause.get(i)));
		}
		writer.write("0\n");
	}

	protected  void writeProblem() throws IOException {
		// p wcnf 70 300
		writer.write(String.format("p wcnf %d %d\n", nbVars, weightedClauses.size()));

	}

	private void writeHeader() throws IOException {
		writer.write("c A temporal KB for measuring ID\n");
	}

}
