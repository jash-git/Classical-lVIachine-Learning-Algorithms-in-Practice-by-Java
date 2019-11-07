package algorithm;

import java.util.ArrayList;
import java.util.List;

import classfier.DecisionStumpClassfier;
import model.DecisionStump;

/**
 * Adaboost 算法核心类
 */
public class AdaBoost {

	private double[][] trainDataArray;//训练数据集
	private double[][] testDataArray;//测试数据集
	private int[] trainLabelArray;//训练标签集
	private double[] weightArray;//样本权重集

	public AdaBoost() {
	}

	/**
	 * 构造Adaboost类
	 * @param trainDataArray 训练数据集
	 * @param testDataArray 测试数据集
	 * @param trainLabelArray 训练标签集
	 * @param weightArray 样本权重集
	 */
	public AdaBoost(double[][] trainDataArray, double[][] testDataArray, int[] trainLabelArray, double[] weightArray) {
		this.trainDataArray = trainDataArray;
		this.testDataArray = testDataArray;
		this.trainLabelArray = trainLabelArray;
		this.weightArray = weightArray;
	}

	/**
	 * 构建多个基础分类器的组合
	 * @return dsList 基础分类器集
	 */
	public List<DecisionStump> constructClassfier() {
		List<DecisionStump> dsList = new ArrayList<DecisionStump>();
		int i = 1;
		DecisionStumpClassfier dsc = new DecisionStumpClassfier();
		while (i <= Configuration.MAX_ITER) {
			i++;
			// 获取该次的分类器并加入基础分类器集
			DecisionStump ds = dsc.constructDecisionStump(this, trainDataArray);
			if(ds==null){
				continue;
			}else{
				dsList.add(ds);
				// 获取多个分类器boost后得到的预测标签集，1表示采用训练数据集
				int[] frstLableArray = this.getFrstLableArray(dsList, 1);
				// 比较预测标签集和训练数据真实标签集
				boolean flag = true;
				int trainDataCount = trainLabelArray.length;
				for (int j = 0; j < trainDataCount; j++) {
					if (frstLableArray[j] != trainLabelArray[j]) {
						flag = false;
						break;
					}
				}
				// 如果都预测正确，结束迭代
				if (flag) {
					break;
				}
				// 否则更新数据样本权重并继续获取下一个分类器
				else {
					weightArray = this.updateDataWeight(ds);
				}
			}
		}
		return dsList;
	}

	/**
	 * 计算当前所选特征下的误差率
	 * @param tmpArray 当前特征数组
	 * @param threshold 阈值
	 * @param ltLabel 小于阈值的标签
	 * @param gtLabel 大于阈值的标签
	 * @return error 误差率
	 */
	public double getError(double[] tmpArray, double threshold, int ltLabel, int gtLabel) {
		double error = 0.0;
		for (int i = 0; i < tmpArray.length; i++) {
			// 小于阈值
			if (tmpArray[i] < threshold) {
				// 预测标签不等于真实标签，增加误差率
				if (trainLabelArray[i] != ltLabel)
					error += weightArray[i];
			} else {
				// 同上
				if (trainLabelArray[i] != gtLabel)
					error += weightArray[i];
			}
		}
		return error;
	}

	/**
	 * 更新每个数据样本的权重
	 * @param ds 当前构造的分类器
	 * @return weightArray 更新后的数据样本权重
	 */
	public double[] updateDataWeight(DecisionStump ds) {
		int featureIndex = ds.getFeatureIndex();//所选特征的索引号
		double threshold = ds.getThreshold();//阈值
		int ltLabel = ds.getLtLabel();//小于阈值的类别
		int gtLabel = ds.getGtLabel();//大于阈值的类别
		double alphaWeight = ds.getAlphaWeight();//当前分类器的权重

		double[] tmpArray = trainDataArray[featureIndex];
		// 计算规范化因子
		double Z = 0.0;
		for (int i = 0; i < tmpArray.length; i++) {
			// 小于阈值
			if (tmpArray[i] < threshold) {
				// 预测标签不等于真实标签
				if (trainLabelArray[i] != ltLabel)
					Z += weightArray[i] * Math.pow(Math.E, alphaWeight);
				else
					Z += weightArray[i] * Math.pow(Math.E, -alphaWeight);
			} else {
				// 同上
				if (trainLabelArray[i] != gtLabel)
					Z += weightArray[i] * Math.pow(Math.E, alphaWeight);
				else
					Z += weightArray[i] * Math.pow(Math.E, -alphaWeight);
			}
		}
		// 更新数据样本权重
		for (int i = 0; i < tmpArray.length; i++) {
			// 小于阈值
			if (tmpArray[i] < threshold) {
				// 预测标签不等于真实标签
				if (trainLabelArray[i] != ltLabel)
					weightArray[i] = weightArray[i] * Math.pow(Math.E, alphaWeight);
				else
					weightArray[i] = weightArray[i] * Math.pow(Math.E, -alphaWeight);
			}
			// 大于等于阈值
			else {
				// 预测标签不等于真实标签
				if (trainLabelArray[i] != gtLabel)
					weightArray[i] = weightArray[i] * Math.pow(Math.E, alphaWeight);
				else
					weightArray[i] = weightArray[i] * Math.pow(Math.E, -alphaWeight);
			}
			weightArray[i] = weightArray[i] / Z;
		}
		return weightArray;
	}

	/**
	 * 综合每次所得到的分类器，带入原始数据计算最终预测结果
	 * @param dsList 分类器集
	 * @param dataFlag 数据标识，1代表训练数据，2代表测试数据
	 * @return frstLables 预测标签集
	 */
	public int[] getFrstLableArray(List<DecisionStump> dsList, int dataFlag) {
		double[] tmpLabels = new double[trainDataArray[0].length]; // double型results
		int[] frstLables = new int[trainDataArray[0].length]; // int型results

		double[][] curDataArray = null;
		switch (dataFlag) {
		case 1:
			curDataArray = trainDataArray;
			break;
		case 2:
			curDataArray = testDataArray;
			break;
		default:
			break;
		}

		for (int i = 0; i < dsList.size(); i++) {
			DecisionStump ds = dsList.get(i);
			int featureIndex = ds.getFeatureIndex();
			double threshold = ds.getThreshold();
			int ltLabel = ds.getLtLabel();
			int gtLabel = ds.getGtLabel();
			double alphaWeight = ds.getAlphaWeight();

			double[] tmpArray = curDataArray[featureIndex];
			for (int j = 0; j < tmpArray.length; j++) {
				if (tmpArray[j] < threshold) {
					tmpLabels[j] += alphaWeight * ltLabel;
				} else {
					tmpLabels[j] += alphaWeight * gtLabel;
				}
			}

			// sign函数实现二分
			for (int k = 0; k < frstLables.length; k++) {
				frstLables[k] = (int) Math.signum(tmpLabels[k]);
			}
		}

		return frstLables;
	}

	/**
	 * 测试分类器
	 * @param dsList 分类器
	 * @param testLabelArray 测试标签集
	 * @return result 测试结果（正确和错误数）
	 */
	public int[] test(List<DecisionStump> dsList, int[] testLabelArray) {
		int[] result = new int[2];
		int rightCount = 0;
		int errorCount = 0;
		if (Configuration.PERCENT <= 0.9) {
			int testDataCount = testLabelArray.length;
			// 获取多个分类器boost后得到的预测标签集，2表示采用测试数据集
			int[] frstLableArray = this.getFrstLableArray(dsList, 2);
			for (int i = 0; i < testDataCount; i++) {
				if (frstLableArray[i] == testLabelArray[i])
					rightCount++;
				else
					errorCount++;
			}
		}
		result[0] = rightCount;
		result[1] = errorCount;
		return result;
	}
}