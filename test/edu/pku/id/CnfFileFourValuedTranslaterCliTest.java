package edu.pku.id;

import java.io.IOException;

import org.junit.Test;
import org.sat4j.reader.ParseFormatException;

import edu.pku.id.cli.MultiValuedTranslaterCli;

public class CnfFileFourValuedTranslaterCliTest {
    @Test
    public void test() throws IOException, ParseFormatException{
        String file = "./data/UUF50.218.1000/uuf50-01.cnf";
        MultiValuedTranslaterCli.main(new String[]{"-m","4",file,"./tmp/problem.wcnf"});
    }
}
