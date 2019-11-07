package util;

import java.util.ArrayList;

import model.Example;

/**
 * 工具类
 */
public class AlgorithmUtil {

	/**
	 * 将数据集按比例分配给训练样本列表和测试样本列表
	 * @param trainRate 训练样本比例
	 * @param dataList 数据集
	 * @return dataListSet 数据样本列表的集合
	 */
	public static ArrayList<ArrayList<Example>> dataProcessing(
			double trainRate, ArrayList<Example> dataList) {
		ArrayList<ArrayList<Example>> dataListSet = new ArrayList<ArrayList<Example>>();// 数据样本列表的集合
		ArrayList<Example> trainList = new ArrayList<Example>();// 训练集样本列表
		ArrayList<Example> testList = new ArrayList<Example>();// 测试集样本集列表

		int dataListSize = dataList.size();
		int trainLength = (int) (dataListSize * trainRate);
		int[] trainRandom = new int[trainLength];// 训练样本编号数组
		int[] testRandom = new int[dataListSize - trainLength];// 测试样本编号数组

		int[] sum = new int[dataListSize];
		for (int i = 0; i < dataListSize; i++) {
			sum[i] = i;
		}
		for (int a = 0; a < trainLength; a++) {
			int temp = (int) (Math.random() * (dataListSize--));
			trainRandom[a] = sum[temp];
			trainList.add(dataList.get(trainRandom[a]));
			sum[temp] = sum[dataListSize];
		}
		for (int i = 0; i < testRandom.length; i++) {
			testRandom[i] = sum[i];
			testList.add(dataList.get(testRandom[i]));
		}
		dataListSet.add(trainList);
		dataListSet.add(testList);
		return dataListSet;
	}

}
