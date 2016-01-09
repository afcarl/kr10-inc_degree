package edu.pku.id.mus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.sat4j.pb.OPBStringSolver;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;

import edu.pku.id.CnfFileReader;

public class SAT2PBTranslater_QC {

    //OPBStringSolver opbStringSolver = new OPBStringSolver();

    List<IVecInt> clauses;

    int varsInSATCount;

    int varsInPBCount;

    int product;

    int sizeproduct;

    StringBuilder pbProblemBuilder = new StringBuilder();

    public String translatCnfFile(String cnffileName) throws IOException,
            ParseFormatException {
        CnfFileReader reader = new CnfFileReader(cnffileName);
        reader.parseInstance();
        this.setClauses(reader.getClauses(), reader.getNbOfVars());
        String pb = translate();
        return pb;
    }

    public void translateCnfFile(String cnffileName, String opbFileName)
            throws IOException, ParseFormatException {
        String pb = translatCnfFile(cnffileName);
        PrintWriter writer = new PrintWriter(new File(opbFileName));

        writer.print(pb);
        //System.out.println(pb);
        //writer.println(pb);
        writer.close();
    }

    public void setClauses(List<IVecInt> clauses, int varsInSATCount) {
        this.clauses = clauses;
        this.varsInSATCount = varsInSATCount;
        this.varsInPBCount = 2 * varsInSATCount;
    }

    public String translate() {
        String meta = getMeta();
        pbProblemBuilder.append(meta);
        String object;
        object = getObject();
        pbProblemBuilder.append(object + "\n");
        for (IVecInt clause : clauses) {
            pbProblemBuilder.append(translateClause(clause) + "\n");
        }
        return pbProblemBuilder.toString();

    }

    private String getMeta() {
        calcProduct();
        String meta = String.format(
                "* #variable= %d #constraint= %d #product= %d sizeproduct= %d\n",
                varsInPBCount, clauses.size(), product, sizeproduct);
        return meta;
    }

    private String getObject() {
        String object;
        //min: 1 x1 x2 +1 x3 x4 +1 x5 x6 ;
        StringBuilder objectBuilder = new StringBuilder("min: ");
        for (int lit = 1; lit <= varsInSATCount; lit++) {
            objectBuilder.append(String.format("1 x%d x%d ", translateLiteral(lit),
                    translateLiteral(-lit)));
        }
        objectBuilder.append(";");
        object = objectBuilder.toString();
        return object;
    }

    private void calcProduct() {
        product = 0;

        for (int i = 0; i < clauses.size(); i++) {
            IVecInt clause = clauses.get(i);
            product += clause.size() + 1;
        }

        //sizeproduct = varsInPBCount;
        sizeproduct = 2*varsInPBCount;
    }

    public String translateClause(IVecInt clause) {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < clause.size(); i++) {
            int lit = clause.get(i);
            int pLit = translateLiteral(lit);
            int nLit = translateLiteral(-lit);
            out.append(String.format("1 x%d ~x%d ", pLit, nLit));
        }

        out.append("1 ");
        for (int i = 0; i < clause.size(); i++) {
            int lit = clause.get(i);
            int pLit = translateLiteral(lit);
            int nLit = translateLiteral(-lit);
            out.append(String.format("x%d x%d ", pLit, nLit));
        }

        out.append(">= 1;");
        return out.toString();
    }

    public int translateLiteral(int lit) {
        int var = Math.abs(lit);
        if (lit < 0) {
            return 2 * var - 1;
        } else {
            return 2 * var;
        }
    }

    //int
}
