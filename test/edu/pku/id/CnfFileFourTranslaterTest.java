package edu.pku.id;

import static edu.pku.id.FileAssert.assertFileEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;

public class CnfFileFourTranslaterTest {
    @Test
    public void testAll() throws IOException, ParseFormatException {
     
        InconsistencyDegreeEncoder translater = new InconsistencyDegreeEncoder();
        String cnfFileName = "./tmp/problem1.cnf";
        String wcnfFileName = "./tmp/problem1_actual.wcnf";
        translater.translate(cnfFileName, wcnfFileName);
        String expectedFileName = "./tmp/problem1_expected.wcnf";
        assertFileEquals(wcnfFileName, expectedFileName);
    }
}
