package edu.pku.id;

import static edu.pku.id.FileAssert.assertFileEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sat4j.core.VecInt;

public class FileWriterTestWcnf {

    @Test
    public void test1() throws IOException {
        List<WeightedClause> weightedClauses = new ArrayList<WeightedClause>();
        weightedClauses.add(new WeightedClause(1, new VecInt(new int[] { 1, 2, 3 })));
        weightedClauses.add(new WeightedClause(3, new VecInt(new int[] { 1, -2, 4 })));
        weightedClauses.add(new WeightedClause(2, new VecInt(new int[] { 4, 1, -2 })));
        String actualFileName = "./tmp/actual1.wcnf";
        ProblemWriter writer = new WeightedMaxSATWriter(weightedClauses, 4, actualFileName);
        writer.write();

        String expectedFileName = "./tmp/expected1.wcnf";
        assertFileEquals(actualFileName, expectedFileName);
    }

}
