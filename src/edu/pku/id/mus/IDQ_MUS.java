package edu.pku.id.mus;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pku.id.CnfFileReader;
import edu.pku.id.file.MUSFileReader;

public class IDQ_MUS {

	static final Logger logger = LoggerFactory.getLogger(IDQ_MUS.class);

	public double getIDQ(String cnfFile, String musFile) throws IOException,
			ParseFormatException {

		CnfFileReader cnfFileReader = new CnfFileReader(cnfFile);

		cnfFileReader.parseInstance();

		List<IVecInt> clauses = cnfFileReader.getClauses();

		MUSFileReader musFileReader = new MUSFileReader(musFile);

		List<List<Integer>> muses = musFileReader.read();

		Set<Integer> contradicts = new HashSet<Integer>();

		Set<Integer> clauseIndciesInMUSes = new HashSet<Integer>();

		for (List<Integer> mus : muses) {

			logger.info("<-------------MUS {}-------------->", mus);

			for (Integer i : mus) {

				clauseIndciesInMUSes.add(i);

				IVecInt clause = clauses.get(i - 1);

				logger.info("CNF[" + i + "]= " + clause);

				for (int j = 0; j < clause.size(); j++) {

					int lit = clause.get(j);
					int v = Math.abs(lit);

					if (!contradicts.contains(v)) {
						// logger.info("add " +v);
					}

					contradicts.add(v);

				}

				// logger.info("size(contradicts) = " + contradicts.size());
			}
			// logger.info("------------>");

		}

		int nbOfVars = cnfFileReader.getNbOfVars();

		logger.info("clauses indices in MUSes : {}", clauseIndciesInMUSes);

		logger.info("contradicts = " + contradicts);

		return contradicts.size() / (double) nbOfVars;

	}
}
