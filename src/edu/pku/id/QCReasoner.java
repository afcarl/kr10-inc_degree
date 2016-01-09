/**
 * @author xiao
 */
package edu.pku.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

/**
 * Quasi Classical Logic Reasoner 准经典逻辑推理机 <br/>
 * 算法基于文献 Marquis, P., and Porquet, N. 2001. Computational aspects of
 * quasi-classical entailment. <i>Journal of Applied Non-classical Logics</i>
 * 11:295-312. <br/>
 * 同时使用了用引入新变量来避免变成CNF范式时的公式长度的指数膨胀的技巧
 */
public class QCReasoner implements IQCReasoner {

	private static final int Z = 3;

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.pkutcl.IQCReasoner#canQCEntail(java.util.List,
	 * org.sat4j.specs.IVecInt)
	 */
	@Override
	public boolean qcEntails(List<IVecInt> kb, IVecInt consequence) {

		// 先将前提进行强变换
		List<IVecInt> kb1 = strong(kb);
		// 再将结论进行弱变换,将取反
		List<IVecInt> gamma1 = negWeak(consequence);
		// 将结论取反加入前提中
		kb1.addAll(gamma1);
		// 如果kb1经典不可满足,则QC蕴含关系成立
		return !areClausesSatisfiable(kb1);

	}

	/**
	 * 将一个子句集进行强变换 S(\Sigma) = \cup_{clause \gamma in \Sigma} S(\gamma)
	 *
	 * @param clauses
	 *            子句集
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
	 * S(l_1\vee \ldots \vee l_n) = \vee_{i=1}^n(+l_i\wedge \neg - l_i) \vee
	 * \wedge_{i=1}^n(+l_i\wedge -l_i)<br/>
	 * 引入新变量,来避免变换后的长度发生指数爆炸<br/>
	 * y_i <- +l_i\wedge \neg - l_i, i = 1, \ldots, n<br/>
	 * z <- \wedge_{i=1}^n(+l_i\wedge -l_i)<br/>
	 * 这样在保持可满足性下,可以变换为<br/>
	 * (\vee_{i=1}^n y_i \vee z) \wedge \wedge_{i=1}^n(\neg y_i \vee +l_i)
	 * \wedge \wedge_{i=1}^n(\neg y_i \vee \neg -l_i) \wedge_{i=1}^n(\neg z \vee
	 * +l_i) \wedge \wedge_{i=1}^n(\neg z \vee - l_i)
	 *
	 * 保证可满足性的变换中引入的 y_i = +l_i \wedge \neg - l_i的编码为4x+2 引入的z = \wedge_{i=1}^n
	 * (+l_i \wedge -l_i) 的编码为3
	 *
	 * @param clause
	 *            要变换的子句
	 * @return 强变换的结果
	 */
	private List<IVecInt> strong(IVecInt clause) {
		int n = clause.size();
		List<IVecInt> result = new ArrayList<IVecInt>(n * 4 + 1);

		IVecInt v;

		// (\vee_{i=1}^n y_i \vee z)
		v = new VecInt(n + 1);
		for (int i = 0; i < n; i++) {
			v.push(4 * i + 2);
		}
		v.push(Z);
		result.add(v);

		for (int i = 0; i < n; i++) {
			// \neg y_i \vee +l_i
			v = new VecInt(2);
			v.push(-(4 * i + 2));
			v.push(literalPositiveTransform(clause.get(i)));
			result.add(v);

			// \neg y_i \vee \neg -l_i
			v = new VecInt(2);
			v.push(-(4 * i + 2));
			v.push(-literalNegativeTransform(clause.get(i)));
			result.add(v);

			// \neg z \vee +l_i
			v = new VecInt(2);
			v.push(-Z);
			v.push(literalPositiveTransform(clause.get(i)));
			result.add(v);

			// \neg z \vee -l_i
			v = new VecInt(2);
			v.push(-Z);
			v.push(literalNegativeTransform(clause.get(i)));
			result.add(v);
		}

		return result;
	}

	/**
	 * 弱变换<br/>
	 * W(l_1, \ldots, l_n) = \vee_{i=1}^n +l_i <br/>
	 * 然后将其取反,由De Morgan律,变换为n个子句 \neg +l_i, i = 1, .. ,n
	 *
	 * @param clause
	 * @return 弱变换的结果
	 */
	private List<IVecInt> negWeak(IVecInt clause) {
		List<IVecInt> result = new ArrayList<IVecInt>(clause.size());
		for (int i = 0; i < clause.size(); i++) {
			IVecInt v = new VecInt(
					new int[] { -literalPositiveTransform(clause.get(i)) });
			result.add(v);
		}
		return result;
	}

	/**
	 * 正原子a的编码如果是x +a的编码为4x -a的编码为4x+1
	 *
	 * l -> +l a -> +a \neg a -> -a
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
	 * l -> -l a -> -a \neg a -> +a
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
	 *
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
			// clauses are not satisfiable
			return false;

		} catch (org.sat4j.specs.TimeoutException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean isQcModel(List<IVecInt> kb, List<TruthValue> model) {

		for (IVecInt clause : kb) {
			if (isQcModel(clause, model)) {
				return true;
			}
		}

		return true;
	}

	/**
	 *
	 * @param model
	 *            a QC interpretation
	 * @return a classical model
	 *         <ul>
	 *         <li>B -> (t,t)</li>
	 *         <li>t -> (t,f)</li>
	 *         <li>f -> (f,t)</li>
	 *         <li>N -> (N,f)</li>
	 *         </ul>
	 */
	private List<Boolean> asClassicModel(List<TruthValue> model) {
		List<Boolean> classicModel = new ArrayList<Boolean>(model.size() * 2);
		for (int i = 0; i < model.size(); i++) {
			TruthValue value = model.get(i);
			switch (value) {
			case Both:
				classicModel.set(2 * i, true);
				classicModel.set(2 * i + 1, true);
				break;
			case False:
				classicModel.set(2 * i, false);
				classicModel.set(2 * i + 1, true);
				break;
			case None:
				classicModel.set(2 * i, false);
				classicModel.set(2 * i + 1, false);
				break;
			case True:
				classicModel.set(2 * i, true);
				classicModel.set(2 * i + 1, false);
				break;

			}

		}
		return classicModel;
	}

	/**
	 *
	 * @param clause
	 * @param interpretation
	 *
	 *            I |-Q l_1\vee \cdots \vee l_n if I |- \bigvee (+l_i \wedge
	 *            \neg -l_i) \vee (\wedge +l_i \wedge -l_i)
	 *
	 * @return whether <code>interpretation</code> is a QC model of
	 *         <code>clause</code>
	 */
	public boolean isQcModel(IVecInt clause, List<TruthValue> interpretation) {

		for (int i = 0; i < clause.size(); i++) {
			int literal = clause.get(i);
			int var = Math.abs(literal);
			if (literal > 0) {
				if (interpretation.get(var - 1) == TruthValue.True) {
					return true;
				}
			} else if (literal < 0) {
				if (interpretation.get(var - 1) == TruthValue.False) {
					return true;
				}
			}
		}

		boolean ok = true;

		for (int i = 0; i < clause.size(); i++) {
			int literal = clause.get(i);
			int var = Math.abs(literal);
			if (interpretation.get(var - 1) != TruthValue.Both) {
				ok = false;
			}
		}

		return ok;
	}
}