package edu.pku.id;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;

public class CnfFileQCTranslater {
    public CnfFileQCTranslater() {
    }

    public void translate(String cnfFileName,String wcnfFileName) throws IOException, ParseFormatException {
        CnfFileReader reader = new CnfFileReader(cnfFileName);
        List<IVecInt> instance = reader.parseInstance();
        QCTranslater translater = new QCTranslater();
        translater.setClauses(instance);
        List<WeightedClause> weightedClauses = translater.getWeightedClauses();
        int nVars = translater.getNVars();
        ProblemWriter writer = new WeightedMaxSATWriter(weightedClauses, 4 * nVars,
                wcnfFileName);
        writer.write();
    }
}
