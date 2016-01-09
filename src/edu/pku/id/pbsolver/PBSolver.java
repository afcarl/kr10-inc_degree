package edu.pku.id.pbsolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author xiao
 * 
 */
public class PBSolver {

	static final Logger logger = LoggerFactory.getLogger(PBSolver.class);

	private Runtime runtime = Runtime.getRuntime();

	PBReport report = new PBReport();

	String[] solverPath;

	public PBSolver(String[] solverPath) {
		this.solverPath = solverPath;
	}

	public static PBSolver createSat4jSolver() {
		return new PBSolver(new String[]{"java", "-jar",
				"solvers/sat4j-pb/sat4j-pb.jar"});

	}

	public static PBSolver createClaspSolver() {
		return new PBSolver(new String[]{"solvers/clasp/clasp"});
	}

	public PBReport solve(String problemFileName) throws Exception {

		long startTime = System.currentTimeMillis();

		// java -jar solvers/sat4j-pb/sat4j-pb.jar

		try {

			int n = solverPath.length;
			String[] params = Arrays.copyOf(solverPath, n + 1);
			params[n] = problemFileName;
//			params = new String[] { "java", "-jar",
//					"solvers/sat4j-pb/sat4j-pb.jar", problemFileName };

			// String[] params = { "/Users/xiao/local/bin/clasp",
			// problemFileName };

			logger.debug("Run PB Solver with parameters:\n {}",
					Arrays.toString(params));
			Process process = this.runtime.exec(params);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {

				if (line.length() < 2 || line.startsWith("c ")) {
					// empty line or comment line
					// do nothing
				} else {
					String message = line.substring(2).trim();
					if (line.startsWith("s ")) {
						if (message.equals("SATISFIABLE")) {
							report.setSolution(PBReport.Solution.SATISFIABLE);
						} else if (message.equals("OPTIMUM FOUND")) {
							report.setSolution(PBReport.Solution.OPTIMUM_FOUND);
						} else if (message.equals("UNSATISFIABLE")) {
							report.setSolution(PBReport.Solution.UNSATISFIABLE);
						} else if (message.equals("UNKNOWN")) {
							report.setSolution(PBReport.Solution.UNKNOWN);
						}
					} else if (line.startsWith("o ")) {
						report.setObject(Integer.parseInt(message));
					} else if (line.startsWith("v ")) {
						String[] valuesStr = message.split("\\s");
						boolean[] values = new boolean[valuesStr.length];
						for (int i = 0; i < values.length; i++) {
							// xi -> true
							// -xi -> false
							values[i] = valuesStr[i].startsWith("x");
						}
						report.setValues(values);
					}
				}

				logger.debug("output: " + line);
			}

			BufferedReader errorReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));

			String message = "";

			while ((line = errorReader.readLine()) != null) {
				message += line + "\n";
			}

			if (message.length() > 0) {
				logger.error("PB Solver Error: {}", message);
				throw new Exception("An error is occurred " + message);
			}

			process.waitFor();
		} catch (IOException ex) {
			throw new Exception("An error is occurred: " + ex.getMessage());
		} catch (InterruptedException ex) {
			throw new Exception("An error is occurred: " + ex.getMessage());
		}

		report.setMilleseconds(System.currentTimeMillis() - startTime);

		return report;

	}

}
