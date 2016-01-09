package edu.pku.id.mus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pku.id.pbsolver.PBReport;
import edu.pku.id.pbsolver.PBSolver;

public class IDQMeasurer_PB {

	static final Logger logger = LoggerFactory.getLogger(IDQMeasurer_PB.class);

	public double measure(String cnfFile) throws Exception {

		// logger.info("Instance Name: " + cnfFile);

		System.out.print(cnfFile);

		long start = System.currentTimeMillis();

		double result = 0.0;

		SAT2PBTranslater_QC translater = new SAT2PBTranslater_QC();

		String opbFileName = cnfFile.concat(".opb");
		translater.translateCnfFile(cnfFile, opbFileName);

		long t1 = System.currentTimeMillis();

		// System.out.print("\t"+ (t1 - start));
		// logger.info("encoding time consumed {} ms", t1 - start);

		PBSolver pb;

		// pb = PBSolver.createClaspSolver();
		pb = PBSolver.createSat4jSolver();

		PBReport report = pb.solve(opbFileName);

		// logger.info(report.toString());

		boolean[] values = report.getValues();

		int b = report.getObject();

		// for (int i = 0; i < values.length; i += 2) {
		// if (values[i] && values[i + 1]) {
		// b += 1;
		// }
		// }

		System.out.print("\t" + b);

		double idq = 2.0 * b / values.length;

		System.out.print("\t" + idq);

		long t2 = System.currentTimeMillis();

		System.out.print("\t" + (t1 - start));

		System.out.print("\t" + (t2 - t1));

		// logger.info("pb time consumed {} ms", t2 - t1);

		System.out.print("\t" + (t2 - start));

		System.out.println();
		// logger.info("total time consumed {} ms", t2 - start);

		// logger.info("------------------------------------------------");

		return result;
	}
}
