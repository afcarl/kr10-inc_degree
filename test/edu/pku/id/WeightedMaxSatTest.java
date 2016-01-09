package edu.pku.id;

import java.io.IOException;

import org.sat4j.maxsat.GenericOptLauncher;

public class WeightedMaxSatTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    
	    GenericOptLauncher.main(new String[]{"./problem.wcnf"});
	    System.out.println("HELLO");
	    System.in.read();
	}


}
