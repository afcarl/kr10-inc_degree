package edu.pku.id;

import java.util.List;

import org.sat4j.specs.IVecInt;

public interface IQCReasoner {

	/**
	 * 检测kb是否能够QC蕴含gamma
	 *
	 * @param kb 前提知识库,表示为子句的集合
	 * @param consequence
	 * 			结论,表示为子句
	 * @return 是否能够QC蕴含
	 */
	public abstract boolean qcEntails(List<IVecInt> kb, IVecInt consequence);

	public abstract boolean isQcModel(List<IVecInt> kb, List<TruthValue> model);
}