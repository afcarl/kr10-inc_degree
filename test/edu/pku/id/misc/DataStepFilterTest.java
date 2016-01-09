package edu.pku.id.misc;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DataStepFilterTest {

	@Test
	public void testFilter() throws IOException {
		new DataStepFilter("data/paper/C168_FW_SZ_107.cnf.idmus.log",20000000,4).filter();
		new DataStepFilter("data/paper/C168_FW_SZ_128.cnf.idmus.log",20000000,4).filter();
		new DataStepFilter("data/paper/C168_FW_SZ_41.cnf.idmus.log",20000000,4).filter();
		new DataStepFilter("data/paper/C168_FW_SZ_66.cnf.idmus.log",20000000,4).filter();
		new DataStepFilter("data/paper/C168_FW_SZ_75.cnf.idmus.log",20000000,4).filter();
	}

}
