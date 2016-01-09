package edu.pku.id.mus;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sat4j.core.VecInt;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;

import edu.pku.id.mus.SAT2PBTranslater_QC;

public class SAT2PBTranslater_QCTest {

    @Test
    public void testTranslateClause() {
        SAT2PBTranslater_QC translater = new SAT2PBTranslater_QC();
        String constraint = translater.translateClause(new VecInt(new int[] { 1, 2 }));
        System.out.println(constraint);
    }

    @Test
    public void testTranslateLiteral() {

    }

    @Test
    public void testTranslate() {
        List<IVecInt> clauses = new ArrayList<IVecInt>();
        clauses.add(new VecInt(new int[] { -1 }));
        clauses.add(new VecInt(new int[] { 1, 2 }));
        clauses.add(new VecInt(new int[] { -2 }));
        clauses.add(new VecInt(new int[] { 3 }));
        SAT2PBTranslater_QC translater = new SAT2PBTranslater_QC();
        translater.setClauses(clauses, 3);
        String pb = translater.translate();
        System.out.println(pb);
    }

    @Test
    public void testTranslateFile() throws IOException, ParseFormatException {
        ///IncDegree_WeightedMaxSat/data/UUF100.430.1000/uuf100-01.cnf
        ///IncDegree_WeightedMaxSat/data/DC_unsat/unsat/C208_FC_SZ_128.cnf
        //String cnfFileName = "data/UUF50.218.1000/uuf50-010.cnf";
        //String cnfFileName = "data/UUF100.430.1000/uuf100-01.cnf";
        //        String cnfFileName = "data/aim/aim-100-1_6-no-1.cnf";
        //        String opbFileName = "data/aim/aim-100-1_6-no-1.opb";
        //        /C208_FA_SZ_87
        String cnfFileName = "data/DC_unsat/unsat/C208_FA_SZ_87.cnf";
        String opbFileName = "data/DC_unsat/unsat/C208_FA_SZ_87.opb";
        
        
        SAT2PBTranslater_QC translater = new SAT2PBTranslater_QC();
        translater.translateCnfFile(cnfFileName, opbFileName);
    }

}
