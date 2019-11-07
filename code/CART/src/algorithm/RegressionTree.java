package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Node;
import model.Example;

/**
 * CART算法执行类
 * */
public class RegressionTree {

	/**
	 * 构造回归树
	 * @param exampleList 训练集
	 * @param E 容许的误差下降值
	 * @param N 切分的最少样本数
	 * @return node 树的根节点
	 */
	public Node constructTree(ArrayList<Example> exampleList) {
		HashMap<String, Object> partition = selectBestPartition(exampleList);// 划分特征
		int feature = (Integer) partition.get("feature");// 特征
		double value = (Double) partition.get("value");// 特征值
		Node node = new Node();// 结点

		// 停止条件：为叶子节点时
		if (feature == -1) {
			node.setFeature(feature);
			node.setValue(value);
			return node;
		}

		// 得到划分后的子数据集
		HashMap<String, ArrayList<Example>> dataSetMap = divideDataSet(exampleList,
				feature, value);
		ArrayList<Example> leftList = dataSetMap.get("leftList");
		ArrayList<Example> rightList = dataSetMap.get("rightList");

		// 递归构建树
		Node leftNode = constructTree(leftList);// 构建左子树
		Node rightNode = constructTree(rightList);// 构建右子树
		node.setLeftNode(leftNode);
		node.setRightNode(rightNode);
		node.setFeature(feature);
		node.setValue(value);

		return node;
	}

	/**
	 * 二元切分
	 * @param exampleList 训练集
	 * @param feature 特征
	 * @param value 特征值
	 * @return dataSetMap 封装的左右子集
	 */
	public HashMap<String, ArrayList<Example>> divideDataSet(
			ArrayList<Example> exampleList, int feature, double value) {
		HashMap<String, ArrayList<Example>> dataSetMap = new HashMap<String, ArrayList<Example>>();// 存储左右子树
		ArrayList<Example> leftList = new ArrayList<Example>();// 左子树
		ArrayList<Example> rightList = new ArrayList<Example>();// 右子树

		// 二元划分类别
		for (int i = 0; i < exampleList.size(); i++) {
			Example example = exampleList.get(i);
			if (example.getX().get(feature) > value) {// 大于value时
				leftList.add(example);// 添加进左子树
			} else {// 小于等于value时
				rightList.add(example);// 添加进右子树
			}
		}

		// 装载子树
		dataSetMap.put("leftList", leftList);
		dataSetMap.put("rightList", rightList);

		return dataSetMap;
	}

	/**
	 * 选择最佳划分
	 * @param exampleList 训练集
	 * @param E 容许的误差下降值
	 * @param N 切分的最少样本数
	 * @return partition 最佳的划分方式
	 */
	public HashMap<String, Object> selectBestPartition(
			ArrayList<Example> exampleList) {
		HashMap<String, Object> partition = new HashMap<String, Object>();// 返回值
		int feature = 0;// 特征
		double value = 0;// 特征值
		double error = 0;// 基础误差
		double minError = Double.MAX_VALUE;// 最小误差
		int length = 0;// 特征长度
		HashMap<String, ArrayList<Example>> dataSetMap = new HashMap<String, ArrayList<Example>>();// 数据集字典
		ArrayList<Example> leftList = new ArrayList<Example>();// 左子树
		ArrayList<Example> rightList = new ArrayList<Example>();// 右子树

		// 停止条件：只有一种剩余结果
		if (judgeNum(exampleList)) {
			feature = -1;
			value = computeMean(exampleList);
			partition.put("feature", feature);
			partition.put("value", value);
			return partition;
		}

		// 循环计算最佳特征和特征取值
		error = computeError(exampleList);// 基础误差
		length = exampleList.get(0).getX().size();// 特征长度
		for (int i = 0; i < length; i++) {// 第i个特征
			for (int j = 0; j < exampleList.size(); j++) {// 第j个数据
				double devideValue = exampleList.get(j).getX().get(i);
				dataSetMap = divideDataSet(exampleList, i, devideValue);// 二元划分
				leftList = dataSetMap.get("leftList");
				rightList = dataSetMap.get("rightList");

				// 不满足条件时
				if ((leftList.size() < Configuration.N) || (rightList.size() < Configuration.N))
					continue;

				double newError = computeError(leftList)
						+ computeError(rightList);// 划分后的误差
				if (newError < minError) {
					feature = i;
					value = devideValue;
					minError = newError;
				}
			}
		}

		// 停止条件：误差下降太少
		if (error < minError ||(error - minError) < Configuration.E) {
			feature = -1;
			value = computeMean(exampleList);
			partition.put("feature", feature);
			partition.put("value", value);
			return partition;
		}

		// 停止条件：切分后的数据集太小
		if ((leftList.size() < Configuration.N) || (rightList.size() < Configuration.N)) {
			feature = -1;
			value = computeMean(exampleList);
			partition.put("feature", feature);
			partition.put("value", value);
			return partition;
		}

		partition.put("feature", feature);
		partition.put("value", value);
		return partition;
	}

	/**
	 * 计算平均值 不再切分数据时，得到目标变量均值
	 * @param exampleList 计算均值的数据集
	 * @return mean 平均值
	 */
	public double computeMean(ArrayList<Example> exampleList) {
		double mean = 0;

		// 计算均值
		for (int i = 0; i < exampleList.size(); i++) {
			mean += exampleList.get(i).getY();
		}
		mean /= exampleList.size();

		return mean;
	}

	/**
	 * 计算总方差 总方差越小，表示样本点离散程度越小。
	 * @param exampleList 计算均值的数据集
	 * @return variance 总方差
	 */
	public double computeError(ArrayList<Example> exampleList) {
		double variance = 0;// 方差
		double mean = computeMean(exampleList);// 平均值

		// 求总方差
		for (int i = 0; i < exampleList.size(); i++) {
			variance += Math.pow(exampleList.get(i).getY() - mean, 2);
		}

		return variance;
	}

	/**
	 * 判断剩余结果是否只有一种
	 * @param exampleList 计算均值的数据集
	 * @return flag 只有一种剩余结果为true，否则为false
	 */
	public boolean judgeNum(ArrayList<Example> exampleList) {
		boolean flag = true;// 只有一种剩余结果
		HashSet<Double> resultSet = new HashSet<Double>();// 结果集

		for (int i = 0; i < exampleList.size(); i++) {
			if (resultSet.size() > 1) {// 存在一种以上剩余结果
				flag = false;
				break;
			} else {
				resultSet.add(exampleList.get(i).getY());
			}
		}

		return flag;
	}

	/**
	 * 测试回归树
	 * @param exampleList 测试集
	 * @param node 根节点
	 */
	public void testRT(ArrayList<Example> exampleList, Node node) {
		double error = 0;// 误差

		// 对每一个点进行测试
		for (int i = 0; i < exampleList.size(); i++) {
			Example example = exampleList.get(i);
			double prediction = predict(example, node);// 预测值
			double reality = example.getY();// 真实值
			error += Math.abs(prediction - reality);// 误差
			System.out.println("真实值：" + reality + " 预测值：" + prediction);
		}

		System.out.println("平均误差：" + (error / exampleList.size()));
	}

	/**
	 * 回归树预测
	 * @param example 被预测的点
	 * @param node 根节点
	 * @return value 预测值
	 */
	public double predict(Example example, Node node) {
		double feature = node.getFeature();// 获取特征编号
		double value = node.getValue();// 获取值
		Node leftNode = node.getLeftNode();// 获取左结点
		Node rightNode = node.getRightNode();// 获取左结点

		// 为叶子结点时
		if (feature == -1)
			return value;

		double x = example.getX().get((int) feature);
		if (x > value) {//进入左分支
			value = predict(example, leftNode);
		} else {//进入右分支
			value = predict(example, rightNode);
		}

		return value;
	}
}