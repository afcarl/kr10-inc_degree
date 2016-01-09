package edu.pku.id;

import java.util.ArrayList;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVecInt;

public class FourValuedTranslater extends MultiValuedTranslater {

    public FourValuedTranslater() {
    }

    public void translate() {
        countVars();
        this.weightedClauses = new ArrayList<WeightedClause>(nVars + clauses.size());
        for (IVecInt clauseIn : clauses) {
            IVecInt clauseOut = translateClause(clauseIn);
            weightedClauses.add(new WeightedClause(nVars + 1, clauseOut));
        }

        for (int var = 1; var <= nVars; var++) {
            weightedClauses.add(new WeightedClause(1, new VecInt(new int[] {
                    -translateLiteral(var), -translateLiteral(-var) })));
        }
    }

    // a -> +a (2a)
    // \neg a -> -a (2a-1)
    public IVecInt translateClause(IVecInt clauseIn) {
        IVecInt clauseOut = new VecInt(clauseIn.size());
        for (int i = 0; i < clauseIn.size(); i++) {
            clauseOut.push(translateLiteral(clauseIn.get(i)));
        }
        return clauseOut;
    }

}
