package edu.pku.id;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVecInt;

import edu.pku.id.IQCReasoner;
import edu.pku.id.QCReasonerV2;

public class QCReasonerV2Test {

	@Before
	public void setUp() {
		Object o = QCReasonerV2.class;
	}

	// a |-_qc a
	@Test
	public void testQCEntail1() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		IVecInt gamma = new VecInt(new int[] { a });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}

	// a,b |-_qc a
	@Test
	public void testQCEntail2() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		kb.add(new VecInt(new int[] { b }));
		IVecInt gamma = new VecInt(new int[] { a });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}

	// a,~a |-_qc a
	@Test
	public void testQCEntail3() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		kb.add(new VecInt(new int[] { -a }));
		IVecInt gamma = new VecInt(new int[] { a });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}

	// not a,~a |-_qc b
	@Test
	public void testQCEntail4() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		kb.add(new VecInt(new int[] { -a }));
		IVecInt gamma = new VecInt(new int[] { b });
		assertFalse(qcReasoner.qcEntails(kb, gamma));
	}

	// a,~a |-_qc a v b
	@Test
	public void testQCEntail5() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		kb.add(new VecInt(new int[] { -a }));
		IVecInt gamma = new VecInt(new int[] { a, b });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}

	// avb,~avc |-_qc b v c
	@Test
	public void testQCEntail6() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		int c = 3;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a, b }));
		kb.add(new VecInt(new int[] { -a, c }));
		IVecInt gamma = new VecInt(new int[] { b, c });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}

	// avb,~avc |-_qc b v c v d
	@Test
	public void testQCEntail7() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		int c = 3;
		int d = 4;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a, b }));
		kb.add(new VecInt(new int[] { -a, c }));
		IVecInt gamma = new VecInt(new int[] { b, c, d });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}

	// avb,~avc,~bv~c |-_qc avb
	@Test
	public void testQCEntail8() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		int c = 3;
		int d = 4;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a, b }));
		kb.add(new VecInt(new int[] { -a, c }));
		kb.add(new VecInt(new int[] { -b, -c }));
		IVecInt gamma = new VecInt(new int[] { a, b });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
	}
	
	@Test
	public void testEx6() {
		IQCReasoner qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		int c = 3;
		int d = 4;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		kb.add(new VecInt(new int[] { -a }));
		kb.add(new VecInt(new int[] { b }));
		kb.add(new VecInt(new int[] { -b, c }));
		IVecInt gamma;
		gamma = new VecInt(new int[] { c });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { c, d });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { a });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { a, -a });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { a, d });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { -a, d });
		assertTrue(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { d });
		assertFalse(qcReasoner.qcEntails(kb, gamma));
		gamma = new VecInt(new int[] { d, -d });
		assertFalse(qcReasoner.qcEntails(kb, gamma));
	}

}
