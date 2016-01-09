package edu.pku.id.cli;

import java.io.IOException;

import org.sat4j.reader.ParseFormatException;

import edu.pku.id.InconsistencyDegreeEncoder;

import gnu.getopt.Getopt;

public class MultiValuedTranslaterCli {

    /**
     * @param args
     * @throws ParseFormatException 
     * @throws IOException 
     */
    public static void main(String[] argv) throws IOException, ParseFormatException {
        //argv = new String[] { "-m", "abc" };
        Getopt g = new Getopt("testprog", argv, "m:");
        //
        int c;
        String arg;

        InconsistencyDegreeEncoder translater = null;

        while ((c = g.getopt()) != -1) {
            switch (c) {
            case 'm':
                arg = g.getOptarg();

                if (arg.equals("4"))
                    translater = new InconsistencyDegreeEncoder();
                else if (arg.equals("q")) {
                    //translater = new QCTranslater();
                }

                //System.out.print("-m " + arg);
                break;

            default:
                translater = new InconsistencyDegreeEncoder();

            }
        }
        
        int optind = g.getOptind();
        String cnfFileName = argv[optind];
        String wcnfFileName = argv[optind+1];
        translater.translate(cnfFileName, wcnfFileName);
    }

}
