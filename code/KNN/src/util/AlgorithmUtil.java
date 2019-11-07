package util;

import java.util.ArrayList;

import model.Node;

/**
 * 算法工具类
 */
public class AlgorithmUtil {

	/**
	 * 对数值归一化处理
	 * 
	 * @param oldValue 需归一化的值
	 * @param min 归一化的值所在数据集的最小值
	 * @param max 归一化的值所在数据集的最大值
	 * @return 归一化后的值
	 */
	public static double normNum(double oldValue, double min, double max) {
		return (double) (oldValue - min) / (max - min);
	}

	/**
	 * 计算两个样本之间的距离
	 * 
	 * @param o1 样本1
	 * @param o2 样本2
	 * @return 样本1与样本2的距离
	 */
	public static double disBetweenNode(Node o1, Node o2) {
		double distance;
		double sum = 0;
		for (int i = 0; i < o1.getProperty().size(); i++) {
			sum += Math.pow((o1.getProperty().get(i) - o2.getProperty().get(i)), 2);
		}
		distance = Math.pow(sum, 0.5);
		return distance;
	}

	/**
	 * 将数据集归一化
	 * 
	 * @param dataList 数据集
	 */
	public static void normData(ArrayList<Node> dataList) {
		if (dataList.size() == 0)
			return;
		int propertyDimensionSize = dataList.get(0).getProperty().size();// 每个特征的维度大小
		double[] min = new double[propertyDimensionSize];
		double[] max = new double[propertyDimensionSize];
		// 初始化数组
		for (int i = 0; i < propertyDimensionSize; i++) {
			min[i] = Double.MAX_VALUE;
			max[i] = Double.MIN_VALUE;
		}
		for (int i = 0; i < dataList.size(); i++) {
			ArrayList<Double> property = dataList.get(i).getProperty();
			for (int j = 0; j < property.size(); j++) {
				if (property.get(j) < min[j]) {
					min[j] = property.get(j);
				}
				if (property.get(j) > max[j]) {
					max[j] = property.get(j);
				}
			}
		}
		// 对数据集归一化处理
		for (int i = 0; i < dataList.size(); i++) {
			ArrayList<Double> property = dataList.get(i).getProperty();// 特征向量
			ArrayList<Double> newProperty = new ArrayList<Double>();// 归一化后的特征向量
			for (int j = 0; j < property.size(); j++) {
				double newValue = normNum(property.get(j), min[j], max[j]);// 归一化过程
				newProperty.add(newValue);
			}
			dataList.get(i).setProperty(newProperty);// 更新归一化后的值
		}
	}

	/**
	 * 划分训练集和测试集
	 * 
	 * @param dataSetList 数据集 
	 * @param trainRate 训练集比例
	 * @return 训练集和测试集
	 */
	public static ArrayList<ArrayList<Node>> dataProcessing(ArrayList<Node> dataSetList, double trainRate) {
		ArrayList<ArrayList<Node>> trainTestSet = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> trainSet = new ArrayList<Node>();
		ArrayList<Node> testSet = new ArrayList<Node>();
		int dataSize = dataSetList.size();
		int trainLength = (int) (dataSize * trainRate);
		int[] sum = new int[dataSize];
		for (int i = 0; i < dataSize; i++) {
			sum[i] = i;
		}
		int num = dataSize;
		for (int i = 0; i < trainLength; i++) {
			int temp = (int) (Math.random() * (num--));
			trainSet.add(dataSetList.get(sum[temp]));
			sum[temp] = sum[num];
		}
		trainTestSet.add(trainSet);
		for (int i = 0; i < dataSize - trainLength; i++) {
			testSet.add(dataSetList.get(sum[i]));			
		}
		trainTestSet.add(testSet);				
		return trainTestSet;
	}

}
