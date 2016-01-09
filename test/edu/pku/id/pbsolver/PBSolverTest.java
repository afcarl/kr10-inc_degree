/**
 * 
 */
package edu.pku.id.pbsolver;

import org.junit.Test;

import edu.pku.id.pbsolver.PBReport;
import edu.pku.id.pbsolver.PBSolver;

/**
 * @author xiao
 *
 */
public class PBSolverTest {

	
	@Test
	public void testSat4j() throws Exception {
		PBSolver solver = PBSolver.createSat4jSolver();
		String opbFile = PBSolver.class.getResource("res/1.opb").getFile();
		
		PBReport report = solver.solve(opbFile.toString());
		
		System.out.println(report);
		
	}
	
	@Test public void testClasp() throws Exception{
		PBSolver solver = PBSolver.createClaspSolver();
		String opbFile = PBSolver.class.getResource("res/1.opb").getFile();
		
		PBReport report = solver.solve(opbFile.toString());
		
		System.out.println(report);
	}

}
