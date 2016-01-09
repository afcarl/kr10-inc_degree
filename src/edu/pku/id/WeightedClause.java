package edu.pku.id;

import org.sat4j.specs.IVecInt;

public class WeightedClause {
    int weight;

    IVecInt clause;

    public WeightedClause(int weight, IVecInt clause) {
        super();
        this.weight = weight;
        this.clause = clause;
    }

    public WeightedClause() {

    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public IVecInt getClause() {
        return clause;
    }

    public void setClause(IVecInt clause) {
        this.clause = clause;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(weight);
        sb.append(",[");
        for (int i = 0; i < clause.size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append(clause.get(i));
        }
        sb.append("]>");
        return sb.toString();
    }
}
