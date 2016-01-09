package edu.pku.id;

import java.util.List;

import org.sat4j.specs.IVecInt;

public abstract class MultiValuedTranslater {

	int nVars = 0;

	List<IVecInt> clauses;

	List<WeightedClause> weightedClauses;

	boolean translated = false;

	public void countVars() {
		for (IVecInt clause : clauses) {
			for (int i = 0; i < clause.size(); i++) {
				nVars = Math.max(nVars, Math.abs(clause.get(i)));
			}
		}
	}

	public List<WeightedClause> getWeightedClauses() {
		if (!translated) {
			translate();
		}
		return weightedClauses;
	}

	abstract void translate();

	protected int translateLiteral(int literal) {
		int symbol = Math.abs(literal);
		if (literal < 0) {
			return symbol * 4;
		} else
			return symbol * 4 - 1;

	}

	public void setClauses(List<IVecInt> clauses) {
		this.clauses = clauses;

	}

	public int getNVars() {
		return nVars;
	}
	
	public static  MultiValuedTranslater create(MultiValuedSemantics semantics){
		if(semantics == MultiValuedSemantics.QC){
			return new QCTranslater();
		}else 
			return new FourValuedTranslater();		
	}

}