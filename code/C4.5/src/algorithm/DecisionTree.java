package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.Node;
import model.Example;

/**
 * 训练决策树C4.5算法执行类
 */
public class DecisionTree {

	/**
	 * 构造决策树
	 * @param node 树节点
	 */
	public void creatTree(Node node) {
		// 选择最优划分特征
		int bestFeature = selectBestFeature(node.getDataList());

		// 当前节点中包含的样本完全属于同一类别，无需划分（递归返回出口1）
		if (judgeWholeCnt(node)) {
			String type = node.getDataList().get(0).getFeatureIndex();
			node.setType(type);
			return;
		}
		// 当前特征集合为空，无法划分（递归返回出口2）
		if (judgeFeatureUsed(node)) {
			String type = majorityVote(node.getDataList());// 取样本数最多的类别
			node.setType(type);
			return;
		}
		// 当前节点包含的样本集合为空，不能划分,此时不会生成新的节点（递归返回出口3）
		if (node.getDataList().size() == 0) {
			return;
		}

		// 递归构造决策树，更新节点的孩子列表
		else {
			ArrayList<Node> childrenList = new ArrayList<Node>();// 创建孩子列表

			// 获取最佳划分方式的特征取值
			Map<String, Integer> featureKeyMap = getFeatureKeyMap(
					node.getDataList(), bestFeature);
			Iterator<String> it = featureKeyMap.keySet().iterator();
			while (it.hasNext()) {
				String featureName = it.next();
				ArrayList<Example> dataList = getSplitDataList(
						node.getDataList(), bestFeature, featureName);

				// 删除用过的属性，用“null”替代
				for (int i = 0; i < dataList.size(); i++) {
					ArrayList<String> xList = dataList.get(i).getFeatureList();
					xList.set(bestFeature, "null");
				}
				Node childTree = new Node(featureName, bestFeature, dataList);

				// 递归
				creatTree(childTree);
				childrenList.add(childTree);
			}
			node.setChildrenList(childrenList);
		}
	}

	/**
	 * 测试决策树分类器
	 * @param testList 测试集
	 * @param classification 决策树分类器
	 */
	public void test(ArrayList<Example> testList, Node classification) {
		String[] truth = new String[testList.size()];// 真实分类数组
		String[] prediction = new String[testList.size()];// 预测分类数组

		for (int i = 0; i < testList.size(); i++) {
			Example data = testList.get(i);
			truth[i] = data.getFeatureIndex();
			prediction[i] = judgeType(data, classification);
		}

		double accuracy = calcAccuracy(truth, prediction);
		System.out.println("正确率为：" + accuracy);
	}

	/**
	 * 选择最优划分特征
	 * @param tempDataList 临时数据集
	 * @return bestFeature 最佳特征索引
	 */
	public int selectBestFeature(ArrayList<Example> tempDataList) {
		int featureCount = tempDataList.get(0).getFeatureList().size();// 特征个数
		double infoGainRatioMax = 0;// 最大信息增益比
		int bestFeature = -1;// 划分方式的特征索引

		for (int i = 0; i < featureCount; i++) {
			double infoGainRatio = getInfoGainRatio(tempDataList, i);
			if (infoGainRatioMax < infoGainRatio) {
				infoGainRatioMax = infoGainRatio;
				bestFeature = i;
			}
		}
		return bestFeature;
	}

	/**
	 * 计算给定特征的信息增益比
	 * @param tempDataList 临时数据集
	 * @param featureIndex 特征索引
	 * @return infoGainRatio 给定特征的信息增益比
	 */
	public double getInfoGainRatio(ArrayList<Example> tempDataList,
			int featureIndex) {
		double ent = calcEnt(tempDataList);// 计算熵
		double conditionEnt = 0;// 计算条件熵
		double featureEnt = 0;// 计算特征值的熵

		Map<String, Integer> featureKeyMap = getFeatureKeyMap(tempDataList,
				featureIndex);// 获取该特征的不同取值
		Iterator<String> it = featureKeyMap.keySet().iterator();
		while (it.hasNext()) {
			String featureName = it.next();
			ArrayList<Example> splitDataList = getSplitDataList(tempDataList,
					featureIndex, featureName);// 划分数据集
			double p = (double) splitDataList.size()
					/ (double) tempDataList.size();
			double splitDataListEnt = p * calcEnt(splitDataList);// 计算划分后数据集的条件熵
			double featureEntForOne = -p * Math.log(p) / Math.log(2.0);// 计算划分数据集的特征值的熵
			conditionEnt += splitDataListEnt;
			featureEnt += featureEntForOne;
		}
		double infoGainRatio = (ent - conditionEnt) / featureEnt;// 计算信息增益比
		return infoGainRatio;
	}

	/**
	 * 计算给定数据集的熵
	 * @param tempDataList 临时数据集
	 * @return ent 给定数据集的熵
	 */
	public double calcEnt(ArrayList<Example> tempDataList) {
		double ent = 0;// 初始熵

		// 计算熵
		Map<String, Integer> labelCount = getLabelCount(tempDataList);// 记录各个类别包含样本数的map表
		Iterator<String> it = labelCount.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			int count = labelCount.get(key);
			double p = (double) count / (double) tempDataList.size();
			ent -= p * Math.log(p) / Math.log(2.0);
		}
		return ent;
	}

	/**
	 * 获取给定数据集可能分类的出现个数
	 * @param tempDataList 临时数据集
	 * @return labelCount 记录各个类别包含样本数的map表
	 */
	public Map<String, Integer> getLabelCount(ArrayList<Example> tempDataList) {
		Map<String, Integer> labelCount = new HashMap<String, Integer>();// 记录各个类别包含样本数的map表

		for (int i = 0; i < tempDataList.size(); i++) {
			if (labelCount.size() == 0) {
				labelCount.put(tempDataList.get(i).getFeatureIndex(), 1);
			} else {
				if (labelCount.containsKey(tempDataList.get(i)
						.getFeatureIndex())) {
					int count = labelCount.get(tempDataList.get(i)
							.getFeatureIndex()) + 1;
					labelCount
							.put(tempDataList.get(i).getFeatureIndex(), count);
				} else {
					labelCount.put(tempDataList.get(i).getFeatureIndex(), 1);
				}
			}
		}
		return labelCount;
	}

	/**
	 * 获取给定特征的不同取值 
	 * @param tempDataList 临时数据集
	 * @param featureIndex 特征索引
	 * @return featureKeyMap 记录给定特征的所有可能取值的map表
	 */
	public Map<String, Integer> getFeatureKeyMap(ArrayList<Example> tempDataList,
			int featureIndex) {
		Map<String, Integer> featureKeyMap = new HashMap<String, Integer>();// 记录给定特征的所有可能取值的map表

		for (int i = 0; i < tempDataList.size(); i++) {
			Example data = tempDataList.get(i);
			ArrayList<String> featureList = data.getFeatureList();
			featureKeyMap.put(featureList.get(featureIndex), 0);
		}
		return featureKeyMap;
	}

	/**
	 * 依据给定特征的取值划分数据集
	 * @param tempDataList 临时数据集
	 * @param featureIndex 给定特征索引
	 * @param featureName 特征的取值
	 * @return splitDataList 划分后的数据集
	 */
	public ArrayList<Example> getSplitDataList(ArrayList<Example> tempDataList,
			int featureIndex, String featureName) {
		ArrayList<Example> splitDataList = new ArrayList<Example>();// 依据该特征取值划分后的数据集

		for (int i = 0; i < tempDataList.size(); i++) {
			Example data = tempDataList.get(i);
			ArrayList<String> featureList = data.getFeatureList();
			String featureKey = featureList.get(featureIndex);

			// 根据特征划分数据集
			if (featureKey.equals(featureName)) {
				splitDataList.add(data);
			}
		}
		return splitDataList;
	}

	/**
	 * 判断子节点的样本类别是否为同一分类
	 * @param node 树节点
	 * @return flag 样本是否同一分类标志位
	 */
	public boolean judgeWholeCnt(Node node) {
		boolean flag = false;// 标志位
		ArrayList<Example> nodeDataList = node.getDataList();
		Map<String, Integer> labelCount = getLabelCount(nodeDataList);
		if (labelCount.size() == 1) {
			flag = true;
		} 
		return flag;
	}

	/**
	 * 判断分类特征是否用完
	 * @param node 树节点
	 * @return flag 特征是否用完标志位
	 */
	public boolean judgeFeatureUsed(Node node) {
		boolean flag = true;// 标志位
		ArrayList<Example> dataList = node.getDataList();

		// 取一行数据，如果所有的属性都被置“null”，则代表属性用完
		ArrayList<String> featureList = dataList.get(0).getFeatureList();
		boolean[] tempFlag = new boolean[featureList.size()];// 每个属性的标志位
		for (int i = 0; i < featureList.size(); i++) {
			if (featureList.get(i).equals("null")) {
				tempFlag[i] = true;
			}
		}
		for (int i = 0; i < featureList.size(); i++) {
			flag = flag && tempFlag[i];
		}
		return flag;
	}

	/**
	 * 如果所有分类特征都被用完，则采用多数表决方法决定节点类别
	 * @param tempDataList 临时数据集
	 * @return type 节点类别
	 */
	public String majorityVote(ArrayList<Example> tempDataList) {
		Map<String, Integer> labelCount = getLabelCount(tempDataList);// 记录各个类别包含样本数的map表

		int maxCount = 0;
		String type = null;
		Iterator<String> it = labelCount.keySet().iterator();
		while (it.hasNext()) {
			String tempType = it.next();
			int tempCount = labelCount.get(tempType);
			if (maxCount < tempCount) {
				maxCount = tempCount;
				type = tempType;
			}
		}
		return type;
	}

	/**
	 * 打印决策树路径（先序）
	 * @param node 树节点
	 */
	public void printTreePath(Node node) {
		// 如果是叶子节点，则打印叶子节点类别并返回
		if (node.getType() != null) {
			System.out.print(":" + node.getType());
			return;
		}
		// 打印孩子列表的内容
		else {
			ArrayList<Node> childrenList = node.getChildrenList();
			for (int i = 0; i < childrenList.size(); i++) {
				System.out.print("{" + childrenList.get(i).getFeatureIndex()
						+ "——" + childrenList.get(i).getFeatureName() + "——>");
				printTreePath(childrenList.get(i));
				System.out.print("}");
			}
		}
	}

	/**
	 * 判断一条测试数据的类别
	 * @param data 一条测试数据
	 * @param node 树节点
	 * @return String 数据类别
	 */
	public String judgeType(Example data, Node node) {
		// 如果是叶子节点，则返回叶子节点类别并返回
		if (node.getType() != null) {
			return node.getType();
		}
		// 遍历节点的孩子列表，找到对应特征以及对应的值
		else {
			ArrayList<String> featureList = data.getFeatureList();
			ArrayList<Node> childrenList = node.getChildrenList();
			for (int i = 0; i < childrenList.size(); i++) {
				Node childNode = childrenList.get(i);
				int featureIndex = childNode.getFeatureIndex();
				String featureName = childNode.getFeatureName();
				if (featureList.get(featureIndex).equals(featureName)) {
					return judgeType(data, childNode);
				}
			}
		}
		return null;
	}

	/**
	 * 计算决策树分类器的准确率
	 * @param truth 真实分类
	 * @param prediction 预测分类
	 * @return accuracy 准确率
	 */
	public double calcAccuracy(String[] truth, String[] prediction) {
		double accuracy = 0;// 准确率

		int n = truth.length;
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (truth[i].equals(prediction[i])) {
				count++;
			}
		}
		accuracy = (double) count / (double) n;
		return accuracy;
	}

}
