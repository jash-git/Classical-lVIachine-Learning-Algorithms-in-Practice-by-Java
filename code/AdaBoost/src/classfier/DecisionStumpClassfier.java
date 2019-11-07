package classfier;

import java.text.DecimalFormat;
import java.util.List;

import algorithm.AdaBoost;
import model.DecisionStump;
import model.Node;
import util.AlgorithmUtil;

/**
 * DecisionStumpClassfier 决策树桩分类器
 */
public class DecisionStumpClassfier {

	public DecisionStumpClassfier() {
	}

	/**
	 * 构建当次决策树桩
	 * @param adaBoost 算法对象
	 * @param trainDataArray 训练数据
	 * @return ds 当次的决策树桩（为空表示当前最低错误率大于0.5，使得无法构建决策树桩）
	 */
	public DecisionStump constructDecisionStump(AdaBoost adaBoost,double[][] trainDataArray) {
		int featureIndex = 1;//所选特征的索引号
		double threshold = 0.0;//阈值
		int ltLabel = 0;//小于阈值的类别
		int gtLabel = 0;//大于阈值的类别
		double minError = Double.MAX_VALUE;//误差率
		double alphaWeight = 0.0;//当前分类器的权重

		// 选取误差率最小的特征和阈值
		int featureCount = trainDataArray.length;
		int dataCount = trainDataArray[0].length;
		int i = 1;
		while (i <= featureCount) {
			double[] tmpArray = trainDataArray[i - 1];//数据样本的当前所选特征集合
			double max = AlgorithmUtil.getMax(tmpArray);
			double min = AlgorithmUtil.getMin(tmpArray);
			double step = (max - min) / dataCount;
			double tmpThreshold = min;
			double tmpError1 = Double.MAX_VALUE;
			double tmpError2 = Double.MAX_VALUE;
			// 从min到max不断增加步长，计算当前阈值下的失误率
			while (tmpThreshold <= max) {
				tmpArray = trainDataArray[i - 1];
				tmpError1 = adaBoost.getError(tmpArray, tmpThreshold, -1, 1);
				tmpError2 = adaBoost.getError(tmpArray, tmpThreshold, 1, -1);
				if (tmpError1 < tmpError2) {
					if (tmpError1 < minError) {
						featureIndex = i - 1;
						threshold = tmpThreshold;
						ltLabel = -1;
						gtLabel = 1;
						minError = tmpError1;
					}
				} else {
					if (tmpError2 < minError) {
						featureIndex = i - 1;
						threshold = tmpThreshold;
						ltLabel = 1;
						gtLabel = -1;
						minError = tmpError2;
					}
				}
				tmpThreshold = tmpThreshold + step;
			}
			i++;
		}
		// 最低错误率应满足0<minError<0.5
		if (minError > 0 && minError < 0.5) {
			// 计算当前分类器权重
			alphaWeight = 0.5 * Math.log((1 - minError) / minError);
			// System.out.println("本次分类器最低错误率: "+minError);
			// System.out.println("本次分类器权重: "+alphaWeight);

			// 构建当前决策树桩
			DecisionStump ds = new DecisionStump(featureIndex, threshold, ltLabel, gtLabel, alphaWeight);
			return ds;
		}else{
			return null;
		}
	}

	/**
	 * 打印决策树桩分类器
	 * @param dsList 决策树桩集
	 * @param curNodeModel 当前数据实体
	 */
	public void printDecisionStumpClassfier(List<DecisionStump> dsList, Node curNodeModel) {
		int i = 1;
		int dsCount = dsList.size();
		DecimalFormat df = new DecimalFormat("#0.00");
		System.out.println("共" + dsCount + "个决策树桩");
		System.out.println();
		while (i <= dsList.size()) {
			DecisionStump ds = dsList.get(i - 1);
			double threshold = ds.getThreshold();
			double alphaWeight = ds.getAlphaWeight();
			int featureIndex = ds.getFeatureIndex();
			String feature = curNodeModel.getFeatures()[featureIndex];
			// 打印每一个决策树桩
			System.out.println("第" + i + "个决策树桩为：");
			System.out.println("取的特征为：  " + feature + "，阈值为：  " + df.format(threshold));
			System.out.println("  " + ds.getLtLabel() + ",\t" + feature + "<" + df.format(threshold));
			System.out.println("  " + ds.getGtLabel() + ",\t" + feature + ">=" + df.format(threshold));
			System.out.println("权重：  " + alphaWeight);
			System.out.println();
			i++;
		}
	}
}