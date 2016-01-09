package edu.pku.id;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.sat4j.core.VecInt;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.IVecInt;

public class WcnfFileReader {
    LineNumberReader in;

    protected final String formatString = "cnf";

    int expectedNbOfConstr;

    int nbOfVars;

    List<IVecInt> clauses;

    int realNbOfConstr = 0;

    public WcnfFileReader(String fileName) throws FileNotFoundException {
        this(new LineNumberReader(new BufferedReader(new FileReader(fileName))));
    }

    public WcnfFileReader(LineNumberReader reader) {
        this.in = reader;
        clauses = new ArrayList<IVecInt>();
    }

    public List<IVecInt> parseInstance() throws IOException, ParseFormatException {
        skipComments();
        readProblemLine();
        readConstrs();
        in.close();
        return clauses;
    }

    public int getNbOfVars() {
        return nbOfVars;
    }

    public List<IVecInt> getClauses() {
        return clauses;
    }

    void skipComments() throws IOException {
        int c;

        do {
            in.mark(4);
            c = in.read();
            if (c == 'c') {
                in.readLine();
            } else {
                in.reset();
            }
        } while (c == 'c');
    }

    protected void readProblemLine() throws IOException, ParseFormatException {

        String line = in.readLine();

        if (line == null) {
            throw new ParseFormatException(
                    "premature end of file: <p cnf ...> expected  on line "
                            + in.getLineNumber());
        }
        StringTokenizer stk = new StringTokenizer(line);

        if (!(stk.hasMoreTokens() && stk.nextToken().equals("p") && stk.hasMoreTokens() && stk
                .nextToken().equals(formatString))) {
            throw new ParseFormatException("problem line expected (p cnf ...) on line "
                    + in.getLineNumber());
        }

        // reads the max var id
        nbOfVars = Integer.parseInt(stk.nextToken());
        assert nbOfVars > 0;

        // reads the number of clauses
        expectedNbOfConstr = Integer.parseInt(stk.nextToken());
        assert expectedNbOfConstr > 0;
    }

    protected void readConstrs() throws IOException, ParseFormatException {
        String line;

        IVecInt literals = new VecInt();

        while (true) {
            line = in.readLine();

            if (line == null) {
                // end of file
                if (literals.size() > 0) {
                    // no 0 end the last clause
                    clauses.add(literals);
                    realNbOfConstr++;
                }

                break;
            }

            if (line.startsWith("c ")) {
                // ignore comment line
                continue;
            }
            if (line.startsWith("%") && expectedNbOfConstr == realNbOfConstr) {
                //System.out.println("Ignoring the rest of the file (SATLIB format");
                break;
            }
            boolean added = handleConstr(line, literals);
            if (added) {
                realNbOfConstr++;
            }
        }

    }

    protected boolean handleConstr(String line, IVecInt literals) {
        int lit;
        boolean added = false;
        Scanner scan;
        scan = new Scanner(line);
        while (scan.hasNext()) {
            lit = scan.nextInt();

            if (lit == 0) {
                if (literals.size() > 0) {
                    addClause(literals);
                    added = true;
                }
            } else {
                literals.push(lit);
            }
        }
        return added;
    }

    private void addClause(IVecInt literals) {
        IVecInt clause = new VecInt();
        for (int i = 0; i < literals.size(); i++) {
            clause.push(literals.get(i));
        }

        clauses.add(clause);
        literals.clear();
    }

}
