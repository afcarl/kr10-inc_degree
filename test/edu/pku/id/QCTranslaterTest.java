package edu.pku.id;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVecInt;

public class QCTranslaterTest {

    private QCTranslater translater;

    @Before
    public void setUp() {
        this.translater = new QCTranslater();
    }

    @Test
    public void testFourValuedTranslater() {

    }
    
    @Test
    public void testTranslateLiteral(){
    	int a = 1;    	
    	assertEquals(3, translater.translateLiteral(a));
    	assertEquals(4, translater.translateLiteral(-a));
    }
    
    @Test
    public void testTranslatePositveLiteral(){
    	int a = 1;    	
    	assertEquals(3, translater.literalPositiveTransform(a));
    	assertEquals(4, translater.literalPositiveTransform(-a));
    }
    
    @Test
    public void testTranslateNegativeLiteral(){
    	int a = 1;    	
    	assertEquals(4, translater.literalNegativeTransform(a));
    	assertEquals(3, translater.literalNegativeTransform(-a));
    }

    @Ignore
    @Test
    public void testCountVars() {
        List<IVecInt> clauses = new ArrayList<IVecInt>();
        clauses.add(new VecInt(new int[] { 1, 2, -3 }));
        clauses.add(new VecInt(new int[] { 1, 2, 4 }));
        translater.setClauses(clauses);
        translater.countVars();
        assertEquals(translater.getNVars(), 4);
    }

    @Ignore
    @Test
    public void testTranslate1() {
        List<IVecInt> clauses = new ArrayList<IVecInt>();
        clauses.add(new VecInt(new int[] { 1, 2, -3 }));
        clauses.add(new VecInt(new int[] { 1, 4, 3 }));
        translater.setClauses(clauses);
        List<WeightedClause> weightedClauses = translater.getWeightedClauses();
        for(WeightedClause wc:weightedClauses){
            System.out.println(wc);
        }
        
//        
//        assertEquals(4 + 2, weightedClauses.size());
//        assertEquals(4 + 1, weightedClauses.get(0).getWeight());
//        assertEquals(weightedClauses.get(0).getClause(), new VecInt(new int[] { 1 * 4,
//                2 * 4, 3 * 4 - 1 }));
//        assertEquals(weightedClauses.get(1).getClause(), new VecInt(new int[] { 1 * 4,
//                4 * 4, 3 * 4 }));
//        assertEquals(weightedClauses.get(2).getClause(), new VecInt(new int[] { -1 * 4,
//                -(1 * 4 - 1) }));
//        assertEquals(weightedClauses.get(3).getClause(), new VecInt(new int[] { -2 * 4,
//                -(2 * 4 - 1) }));
    }

}
