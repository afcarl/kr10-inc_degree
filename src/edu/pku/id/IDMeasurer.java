package edu.pku.id;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import org.sat4j.AbstractLauncher;
import org.sat4j.maxsat.GenericOptLauncher;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

public class IDMeasurer {

	static MultiValuedSemantics semantics = MultiValuedSemantics.QC;

	//static MultiValuedSemantics semantics = MultiValuedSemantics.Four;

	//static  SATProblemType problemType = SATProblemType.WeightedMaxSAT;
	static SATProblemType problemType = SATProblemType.PartialMaxSAT;

	public IDMeasurer(String fileName) throws FileNotFoundException {
		// this.fileName = fileName;
		problemReader = new CnfFileReader(fileName);
	}

	public IDMeasurer(File file) throws FileNotFoundException {
		// this.fileName = fileName;
		problemReader = new CnfFileReader(new LineNumberReader(new BufferedReader(new FileReader(file))));
	}

	private int nbVars;

	CnfFileReader problemReader;

	static PrintStream console = System.out;

	double measure() throws IOException, ParseFormatException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException,
			TimeoutException {
		List<IVecInt> clauses = problemReader.parseInstance();
		nbVars = problemReader.getNbOfVars();

		MultiValuedTranslater translater = null;
		int nbVarsInTargetProblem = 0;
		if (semantics == MultiValuedSemantics.Four) {
			translater = new FourValuedTranslater();
			nbVarsInTargetProblem = 4 * nbVars;
		} else if (semantics == MultiValuedSemantics.QC) {
			translater = new QCTranslater();
			nbVarsInTargetProblem = 4 * nbVars + clauses.size();
		}
		translater.setClauses(clauses);

		List<WeightedClause> weightedClauses = translater.getWeightedClauses();
		String wcnfProblemFileName = "problem.wcnf";

		ProblemWriter writer = null;
		if (problemType == SATProblemType.WeightedMaxSAT) {
			writer = new WeightedMaxSATWriter(weightedClauses, nbVarsInTargetProblem, wcnfProblemFileName);
		} else if (problemType == SATProblemType.PartialMaxSAT) {
			writer = new PartialMaxSATWriter(weightedClauses, nbVarsInTargetProblem, wcnfProblemFileName);
		}
		writer.write();

		String outFileName = "out.wcnf";
		PrintStream oldOut = setOut(outFileName);

		String[] args = new String[] { "-t", "60", "problem.wcnf" };

		// WeightedMaxSatDecorator asolver = new
		// WeightedMaxSatDecorator(SolverFactory
		// .newLight());
		//
		// for (IVecInt newClause : newClauses) {
		// try {
		// asolver.addClause(newClause);
		// } catch (ContradictionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//        
		// boolean b = asolver.isSatisfiable();
		//        
		// int[] model = asolver.model();

		GenericOptLauncher genericoptlauncher = new GenericOptLauncher();
		// genericoptlauncher.setLogWriter(new PrintWriter(outFileName));
		genericoptlauncher.run(args);

		int[] model = getModelFromLauncher(genericoptlauncher);

		int confCount = count(model);

		setOut(oldOut);

		// System.out.println("confCount = " + confCount);
		// System.out.println("nbVars = " + nbVars);

		return (double) confCount / (double) nbVars;

	}

	// genericoptlauncher.solver.model();
	private int[] getModelFromLauncher(AbstractLauncher launcher) throws SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Field solverField = AbstractLauncher.class.getDeclaredField("solver");
		solverField.setAccessible(true);
		ISolver solver = (ISolver) solverField.get(launcher);
		return solver.model();
	}

	private PrintStream setOut(PrintStream out) {
		System.out.flush();
		PrintStream oldOut = System.out;
		System.setOut(out);
		return oldOut;

	}

	private PrintStream setOut(String outFileName) {
		System.out.flush();
		PrintStream oldOut = System.out;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outFileName, false);
		} catch (FileNotFoundException e) {

		}
		PrintStream ps = new PrintStream(out);
		System.setOut(ps);
		return oldOut;

	}

	private int count(int[] model) {
		int c = 0;
		final int length = model.length;
		// for (int i = 0; i < length; i++) {
		// console.print(model[i] + " ");
		// }
		// console.println();

		for (int i = 0; i < 4 * nbVars; i = i + 4) {
			if (model[i + 2] > 0 && model[i + 3] > 0) {
				c++;
			}
		}

		return c;
	}

	public static void main(String[] args) throws Exception {
		// IDMeasurer measurer = new IDMeasurer("problem.cnf");

		 //File dataPath = new File("./data/UUF50.218.1000");
		// File dataPath = new File("./data/UUF75.325.100");
		// File dataPath = new File("./data/UUF100.430.1000");
		// File dataPath = new File("./data/UUF250.1065.100");
		 //File dataPath = new File("./data/DC_unsat/unsat");
		// File dataPath = new File("./data/my");
		// File dataPath = new File("./data/maxsat_crafted/bipartite/maxcut-140-630-0.7");
		//File dataPath = new File("./data/maxsat_crafted/bipartite/maxcut-140-630-0.8");
		//File dataPath = new File("./data/random-net-100-1"); 
		File dataPath = new File("./data/KSEM09");
		
		
		File[] files = dataPath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("001.cnf");
			}
		});

		final int n = 1000;
		console.println("Using Partial MaxSAT");
		problemType = SATProblemType.PartialMaxSAT;
		testFiles(files, n);
		console.println("Using Weighted MaxSAT");
		problemType = SATProblemType.WeightedMaxSAT;
		testFiles(files, n);
		
	}

	private static void testFiles(File[] files, final int n) throws FileNotFoundException, IOException, ParseFormatException, NoSuchFieldException,
			IllegalAccessException, TimeoutException {
		for (int i = 0; i < n && i < files.length; i++) {
			testOne(files[i]);
		}
	}

	private static void testOne(File file) throws FileNotFoundException, IOException, ParseFormatException, NoSuchFieldException, IllegalAccessException,
			SecurityException, IllegalArgumentException, TimeoutException {
		System.gc();
		long t1 = System.currentTimeMillis();
		IDMeasurer measurer = new IDMeasurer(file);
		double id = measurer.measure();
		long t2 = System.currentTimeMillis();
		System.out.println(String.format("%s & %f & %f \\\\ \\hline", file.getName(), id, (t2 - t1) / 1000.0));

	}
}
