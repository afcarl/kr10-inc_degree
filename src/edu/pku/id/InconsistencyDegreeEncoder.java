package edu.pku.id;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;

public class InconsistencyDegreeEncoder {

	SATProblemType problemType = SATProblemType.WeightedMaxSAT;
	MultiValuedSemantics semantics = MultiValuedSemantics.Four;

	public InconsistencyDegreeEncoder() {
	}
	
	

	public InconsistencyDegreeEncoder(SATProblemType problemType,
			MultiValuedSemantics semantics) {
		this.problemType = problemType;
		this.semantics = semantics;
	}

	public void translate(String cnfFileName, String wcnfFileName)
			throws IOException, ParseFormatException {
		CnfFileReader reader = new CnfFileReader(cnfFileName);
		List<IVecInt> instance = reader.parseInstance();
		MultiValuedTranslater translater = MultiValuedTranslater
				.create(semantics);

		// if (semantics == MultiValuedSemantics.Four) {
		// // 4
		// translater = new FourValuedTranslater();
		// } else if (semantics == MultiValuedSemantics.QC) {
		// translater = new QCTranslater();
		// }

		translater.setClauses(instance);
		List<WeightedClause> weightedClauses = translater.getWeightedClauses();
		int nVars = translater.getNVars();
		ProblemWriter writer = null;
		int newNbVars = 4 * nVars + weightedClauses.size();
		
		//System.out.println("newNbVars = " +newNbVars);
		
		if (problemType == SATProblemType.WeightedMaxSAT) {
			writer = new WeightedMaxSATWriter(weightedClauses, newNbVars,
					wcnfFileName);
		} else if (problemType == SATProblemType.PartialMaxSAT) {
			writer = new PartialMaxSATWriter(weightedClauses, newNbVars,
					wcnfFileName);
		}

		writer.write();
	}

	public void translate(String cnfFileName) throws IOException,
			ParseFormatException {
		final int index = cnfFileName.lastIndexOf(".");
		final String name = cnfFileName.substring(0, index);
		String ext = "";
		if (problemType == SATProblemType.PartialMaxSAT) {
			ext = ".p.wcnf";
		} else if (problemType == SATProblemType.WeightedMaxSAT) {
			ext = ".w.wcnf";
		}
		
		String wcnfFileName = name + ext;
		//System.out.println(cnfFileName + "->" + wcnfFileName);
		translate(cnfFileName, wcnfFileName);
	}
}
