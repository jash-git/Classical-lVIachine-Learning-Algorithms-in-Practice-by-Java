package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.Configuration;
import model.Node;

/**
 * DataUtil 数据工具类
 */
public class DataUtil {

	public DataUtil() {
	}

	/**
	 * 把原始数据划分为训练数据、测试数据、训练标签和测试标签
	 * @param datas 原始数据
	 * @param begin 随机数的开始数
	 * @param end 随机数的结束数
	 * @param percent 随机数所占比例
	 * @return dataAndLabelMap 训练数据、测试数据、训练标签和测试标签
	 */
	public static Map<String, Object> divideTrainAndTestData(List<List<String>> datas, int begin, int end,
			double percent) {
		Map<String, Object> dataAndLabelMap = new HashMap<String, Object>();

		Node curNodeModel = Configuration.NODEMODEL;

		// 原始数据拆分为数据集和标签集
		int dataCount = datas.size();
		int featureCount = curNodeModel.getFeatures().length;
		int trainCount = (int) Math.floor((end - begin + 1) * percent);
		int testCount = dataCount - trainCount;

		double[][] dataArray = DataUtil.getDataArray(datas, dataCount, featureCount);
		int[] labelArray = DataUtil.getLabelArray(datas, dataCount, Configuration.LABEL_INDEX);

		double[][] trainDataArray = new double[trainCount][featureCount];
		double[][] testDataArray = new double[testCount][featureCount];
		int[] trainLabelArray = new int[trainCount];
		int[] testLabelArray = new int[testCount];

		// 划分训练数据、测试数据、训练标签和测试标签
		if (end < begin) {
			System.out.println("end must egt begin");
			return null;
		}

		Set<Integer> randoms = new HashSet<Integer>();
		int tmp1 = 0;
		while (randoms.size() < trainCount) {
			int tmpSize = trainCount - randoms.size();
			for (int i = 0; i < tmpSize; i++) {
				// 产生随机数，并从原始数据中提取出训练数据
				int num = (int) (Math.random() * (end - begin)) + begin;
				if (!randoms.contains(num)) {
					randoms.add(num);
					trainDataArray[tmp1] = dataArray[num];
					trainLabelArray[tmp1] = labelArray[num];
					tmp1++;
				}
			}
		}

		// 剩下的数据为测试数据
		int tmp2 = 0;
		for (int i = begin; i <= end; i++) {
			if (!randoms.contains(i)) {
				testDataArray[tmp2] = dataArray[i - begin];
				testLabelArray[tmp2] = labelArray[i - begin];
				tmp2++;
			}
		}

		// 数组转置
		trainDataArray = AlgorithmUtil.getTransArray(trainDataArray);
		testDataArray = AlgorithmUtil.getTransArray(testDataArray);

		dataAndLabelMap.put("trainData", trainDataArray);
		dataAndLabelMap.put("testData", testDataArray);
		dataAndLabelMap.put("trainLabel", trainLabelArray);
		dataAndLabelMap.put("testLabel", testLabelArray);

		return dataAndLabelMap;
	}

	/**
	 * 从全部数据样本中取出不含标签的数据集
	 * @param datas 原始数据
	 * @param dataCount 数据总数
	 * @param featureCount 属性总数
	 * @return dataArray 不含标签的数据集
	 */
	public static double[][] getDataArray(List<List<String>> datas, int dataCount, int featureCount) {
		double[][] dataArray = new double[dataCount][featureCount];
		for (int i = 0; i < datas.size(); i++) {
			int j = 0;
			int k=0;
			while (k <= featureCount) {
				// 剔除标签那一列
				if(k!=(Configuration.LABEL_INDEX-1)){
					dataArray[i][j] = Double.parseDouble(datas.get(i).get(k));
					j++;
				}
				k++;
			}
		}
		return dataArray;
	}

	/**
	 * 从全部数据样本中取出标签集
	 * @param datas 原始数据
	 * @param dataCount 数据总数
	 * @param labelColIndex 标签所在列
	 * @return labelArray 原始数据的标签集
	 */
	public static int[] getLabelArray(List<List<String>> datas, int dataCount, int labelColIndex) {
		int[] labelArray = new int[dataCount];
		for (int i = 0; i < datas.size(); i++) {
			labelArray[i] = Integer.parseInt(datas.get(i).get(labelColIndex - 1));
		}
		return labelArray;
	}

	/**
	 * 初始化训练数据样本权重
	 * @param trainDataCount 训练数据样本的数量
	 * @return weightArray 训练数据样本的权重
	 */
	public static double[] initWeightArray(int trainDataCount) {
		double[] weightArray = new double[trainDataCount];
		for (int i = 0; i < trainDataCount; i++) {
			weightArray[i] = 1.0 / trainDataCount;
		}
		return weightArray;
	}

}