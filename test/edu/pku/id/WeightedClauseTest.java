package edu.pku.id;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.sat4j.core.VecInt;

public class WeightedClauseTest {

    @Test
    public void testConstructor() {
        WeightedClause wc = new WeightedClause();
        wc.setWeight(1);
        wc.setClause(new VecInt(new int[] { 1, 2, -3 }));
        assertEquals(wc.getWeight(), 1);
        assertEquals(wc.getClause(), new VecInt(new int[] { 1, 2, -3 }));
    }

    @Test
    public void testToString() {
        WeightedClause wc = new WeightedClause();
        wc.setWeight(1);
        wc.setClause(new VecInt(new int[] { 1, 2, -3 }));
        String actual = wc.toString();
        String expected = "<1,[1,2,-3]>";
        assertEquals(actual, expected);
    }

}
