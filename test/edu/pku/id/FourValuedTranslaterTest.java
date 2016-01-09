package edu.pku.id;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVecInt;

public class FourValuedTranslaterTest {

    private MultiValuedTranslater translater;

    @Before
    public void setUp() {
        this.translater = new FourValuedTranslater();
    }

    @Test
    public void testFourValuedTranslater() {

    }

    @Test
    public void testCountVars() {
        List<IVecInt> clauses = new ArrayList<IVecInt>();
        clauses.add(new VecInt(new int[] { 1, 2, -3 }));
        clauses.add(new VecInt(new int[] { 1, 2, 4 }));
        translater.setClauses(clauses);
        translater.countVars();
        assertEquals(translater.getNVars(), 4);
    }

    @Test
    public void testTranslate1() {
        List<IVecInt> clauses = new ArrayList<IVecInt>();
        clauses.add(new VecInt(new int[] { 1, 2, -3 }));
        clauses.add(new VecInt(new int[] { 1, 4, 3 }));
        translater.setClauses(clauses);
        List<WeightedClause> weightedClauses = translater.getWeightedClauses();
        assertEquals(4 + 2, weightedClauses.size());
        assertEquals(4 + 1, weightedClauses.get(0).getWeight());
        assertEquals(weightedClauses.get(0).getClause(), new VecInt(new int[] { 1 * 4,
                2 * 4, 3 * 4 - 1 }));
        assertEquals(weightedClauses.get(1).getClause(), new VecInt(new int[] { 1 * 4,
                4 * 4, 3 * 4 }));
        assertEquals(weightedClauses.get(2).getClause(), new VecInt(new int[] { -1 * 4,
                -(1 * 4 - 1) }));
        assertEquals(weightedClauses.get(3).getClause(), new VecInt(new int[] { -2 * 4,
                -(2 * 4 - 1) }));
    }

}
