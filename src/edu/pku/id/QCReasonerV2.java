/**
 * @author xiao
 */
package edu.pku.id;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

/**
 * Quasi Classical Logic Reasoner
 * 准经典逻辑推理机
 * <br/>
 * 算法基于文献
 * Marquis, P., and Porquet, N. 2001. Computational aspects of quasi-classical entailment.
 * <i>Journal of Applied Non-classical Logics</i> 11:295-312.
 * <br/>
 *
 */
public class QCReasonerV2 implements IQCReasoner {

	private static final int Z = 3;

	/* (non-Javadoc)
	 * @see edu.pkutcl.IQCReasoner#canQCEntail(java.util.List, org.sat4j.specs.IVecInt)
	 */
	@Override
	public boolean qcEntails(List<IVecInt> kb, IVecInt consequence) {

		//先将前提进行强变换
		List<IVecInt> kb1 = strong(kb);
		//再将结论进行弱变换,将取反
		List<IVecInt> gamma1 = negWeak(consequence);
		//将结论取反加入前提中
		kb1.addAll(gamma1);
		//如果kb1经典不可满足,则QC蕴含关系成立
		return !areClausesSatisfiable(kb1);

	}

	/**
	 * 将一个子句集进行强变换
	 * S(\Sigma) = \cup_{clause \gamma in \Sigma} S(\gamma)
	 * @param clauses 子句集
	 * @return 强变换的结果
	 */
	private List<IVecInt> strong(List<IVecInt> clauses) {
		List<IVecInt> result = new ArrayList<IVecInt>();
		for (IVecInt vecInt : clauses) {
			result.addAll(strong(vecInt));
		}
		return result;
	}

	/**
	 * 将一个子句进行强变换<br/>
	 * 算法如下:<br/>
	 * S(l_1,\ldots,l_n) =
	 * (+l_1 \vee +l_2 \ldots \vee +l_n)
	 * \vee (\neg -l_1 \vee +l_2 \ldots \vee +l_n)
	 * \vee (\l_1 \vee \neg -l_2 \ldots \vee +l_n)
	 * \vee \ldots
	 * \vee (\l_1 \vee +l_2 \ldots \vee \neg -l_n)
	 * @param clause 要变换的子句
	 * @return 强变换的结果
	 */
	private List<IVecInt> strong(IVecInt clause) {
		int n = clause.size();
		List<IVecInt> result = new ArrayList<IVecInt>(n + 1);

		IVecInt v;

		//(+l_1 \vee +l_2 \ldots \vee +l_n)
		v = new VecInt(n);
		for (int i = 0; i < n; i++) {
			v.push(literalPositiveTransform(clause.get(i)));
		}
		result.add(v);

		if (n > 1) {
			for (int i = 0; i < n; i++) {

				v = new VecInt(n);
				for (int j = 0; j < n; j++) {
					if (i == j) {
						v.push(-literalNegativeTransform(clause.get(j)));
					} else {
						v.push(literalPositiveTransform(clause.get(j)));
					}
				}
				result.add(v);

			}
		}

		return result;
	}

	/**
	 * 弱变换<br/>
	 * W(l_1, \ldots, l_n) = \vee_{i=1}^n +l_i <br/>
	 * 然后将其取反,由De Morgan律,变换为n个子句
	 * \neg +l_i, i = 1, .. ,n
	 * @param clause
	 * @return 弱变换的结果
	 */
	private List<IVecInt> negWeak(IVecInt clause) {
		List<IVecInt> result = new ArrayList<IVecInt>(clause.size());
		for (int i = 0; i < clause.size(); i++) {
			IVecInt v = new VecInt(new int[] { -literalPositiveTransform(clause.get(i)) });
			result.add(v);
		}
		return result;
	}

	/**
	 * 正原子a的编码如果是x
	 * 	 +a的编码为4x
	 * 	 -a的编码为4x+1
	 *
	 * l -> +l
	 * a -> +a
	 * \neg a -> -a
	 */
	private int literalPositiveTransform(int literal) {
		int symbol = Math.abs(literal);
		if (literal < 0) {
			return 4 * symbol + 1;
		} else {
			return 4 * symbol;
		}
	}

	/**
	 * l -> -l
	 * a -> -a
	 * \neg a -> +a
	 */
	private int literalNegativeTransform(int literal) {
		int symbol = Math.abs(literal);
		if (literal < 0) {
			return 4 * symbol;
		} else {
			return 4 * symbol + 1;
		}
	}

	/**
	 * 使用SAT4J推理机来检查子句集clauses的可满足性
	 * @param clauses
	 * @return 子句集clauses的可满足性
	 */
	private boolean areClausesSatisfiable(List<IVecInt> clauses) {
		ISolver solver = SolverFactory.newDefault();
		solver.newVar(400);
		solver.setExpectedNumberOfClauses(clauses.size());

		try {
			for (IVecInt clause : clauses) {
				solver.addClause(clause);
			}
			return solver.isSatisfiable();
		} catch (ContradictionException e) {
			// when empty clause is found, the
			// clauses is not satisfiable
			return false;

		} catch (org.sat4j.specs.TimeoutException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void main(String[] args){
		QCReasonerV2 qcReasoner = new QCReasonerV2();
		int a = 1;
		int b = 2;
		List<IVecInt> kb = new ArrayList<IVecInt>();
		kb.add(new VecInt(new int[] { a }));
		kb.add(new VecInt(new int[] { -a }));
		List<IVecInt> kb1 = qcReasoner.strong(kb);
		for(IVecInt v : kb1){
			System.out.println(v);
		}
		IVecInt gamma = new VecInt(new int[] { b });
		assertFalse(qcReasoner.qcEntails(kb, gamma));
	}

	@Override
	public boolean isQcModel(List<IVecInt> kb, List<TruthValue> model) {
		// TODO Auto-generated method stub
		return false;
	}
}
