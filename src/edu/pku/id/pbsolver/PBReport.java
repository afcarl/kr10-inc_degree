package edu.pku.id.pbsolver;

import java.util.Arrays;

public class PBReport {

	@Override
	public String toString() {
		return "PBReport [object=" + object + ", solution=" + solution
				+ ", values=" + Arrays.toString(values) + ", milleseconds="
				+ milleseconds + "]";
	}

	int object;
	//String solution;
	
	Solution solution;
	
	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	boolean[] values;
	long milleseconds;

	public int getObject() {
		return object;
	}

	public void setObject(int object) {
		this.object = object;
	}

	

	public boolean[] getValues() {
		return values;
	}

	public void setValues(boolean[] values) {
		this.values = values;
	}

	public long getMilleseconds() {
		return milleseconds;
	}

	public void setMilleseconds(long milleseconds) {
		this.milleseconds = milleseconds;
	}
	
	public enum Solution{
		SATISFIABLE, OPTIMUM_FOUND, UNSATISFIABLE, UNKNOWN,
	}
	

	public static final String SATISFIABLE = "SATISFIABLE";
	public static final String OPTIMUM_FOUND = "OPTIMUM FOUND";
	public static final String UNSATISFIABLE = "UNSATISFIABLE";
	public static final String UNKNOWN = "UNKNOWN";
}
