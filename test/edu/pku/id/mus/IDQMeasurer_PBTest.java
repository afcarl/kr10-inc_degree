package edu.pku.id.mus;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Ignore;
import org.junit.Test;

import edu.pku.id.mus.IDQMeasurer_PB;

public class IDQMeasurer_PBTest {

	@Test
	public void testMeasure001() throws Exception {
		IDQMeasurer_PB measurer = new IDQMeasurer_PB();
		measurer.measure("data/my/t.cnf");
	}

	@Test
	@Ignore
	public void testMeasure_KSEM() throws Exception {
		IDQMeasurer_PB measurer = new IDQMeasurer_PB();
		String root = "data/KSEM09";
		String[] files = (new File(root)).list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cnf");
			}
		});

		for (String file : files) {
			System.out.println(file);

			measurer.measure(root + "/" + file);
		}
	}

	@Test
	public void testMeasure_KR() throws Exception {
		IDQMeasurer_PB measurer = new IDQMeasurer_PB();
//		measurer.measure("data/paper/C168_FW_SZ_107.cnf");
//		measurer.measure("data/paper/C168_FW_SZ_128.cnf");
//		measurer.measure("data/paper/C168_FW_SZ_41.cnf");
//		measurer.measure("data/paper/C168_FW_SZ_66.cnf");
//		measurer.measure("data/paper/C168_FW_SZ_75.cnf");
//		measurer.measure("data/paper/C168_FW_UT_2463.cnf");
//		measurer.measure("data/paper/C168_FW_UT_2468.cnf");
//		measurer.measure("data/paper/C168_FW_UT_2469.cnf");
//		measurer.measure("data/paper/C168_FW_UT_714.cnf");
//		measurer.measure("data/paper/C168_FW_UT_851.cnf");
//		measurer.measure("data/paper/C168_FW_UT_852.cnf");
//		measurer.measure("data/paper/C168_FW_UT_854.cnf");
//		measurer.measure("data/paper/C168_FW_UT_855.cnf");
		measurer.measure("data/paper/C170_FR_SZ_58.cnf");
		measurer.measure("data/paper/C170_FR_SZ_92.cnf");
		measurer.measure("data/paper/C170_FR_SZ_95.cnf");
		measurer.measure("data/paper/C170_FR_SZ_96.cnf");
	}

	@Test
	public void testMeasure_Bad() throws Exception {
		IDQMeasurer_PB measurer = new IDQMeasurer_PB();

		measurer.measure("data/my/bad.cnf");
		measurer.measure("data/my/bad1.cnf");

	}
}
