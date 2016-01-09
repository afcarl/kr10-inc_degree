package edu.pku.id;

import java.io.IOException;
import java.util.List;

public class PartialMaxSATWriter extends WeightedMaxSATWriter implements ProblemWriter {

	public PartialMaxSATWriter(List<WeightedClause> weightedClauses, int nbVars, String fileName) throws IOException {
		super(weightedClauses, nbVars, fileName);
	}

	// In Partial Max-SAT, the parameters line is "p wcnf nbvar nbclauses  top".
	// We associate a weight with each clause, which is the first integer in the
	// clause. Weights must be greater than or equal to 1, and smaller than 220.
	// Hard clauses have weight top and soft clauses have weight 1. We assure
	// that top is a weight always greater than the sum of the weights of
	// violated soft clauses.
	@Override
	protected void writeProblem() throws IOException {
		//p wcnf nbvar nbclauses  top
		int top = weightedClauses.get(0).weight;
		writer.write(String.format("p wcnf %d %d %d\n", nbVars, weightedClauses.size(), top+1));

	}
}
