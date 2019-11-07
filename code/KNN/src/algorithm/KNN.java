package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import model.Node;
import util.AlgorithmUtil;

/**
 * knn算法过程及测试代码
 */
public class KNN {

	/**
	 * knn分类过程
	 * 
	 * @param node 要分类的实例
	 * @param trainSet 训练集
	 * @return 分类标签
	 */
	public String knn(Node node, ArrayList<Node> trainSet) {
		String label = null;
		for (int i = 0; i < trainSet.size(); i++) {
			double dis = AlgorithmUtil.disBetweenNode(node, trainSet.get(i));// 计算欧式距离
			trainSet.get(i).setDisFromNode(dis);
		}
		// 对距离从小到大排序
		Collections.sort(trainSet, new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				if (o1.getDisFromNode() > o2.getDisFromNode()) {
					return 1;
				} else if (o1.getDisFromNode() == o2.getDisFromNode()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();

		for (int i = 0; i < Configuration.K; i++) {// 对k个近邻用户统计他们的分类信息
			String neigborLabel = trainSet.get(i).getLabel();
			if (countMap.containsKey(neigborLabel)) {
				int count = countMap.get(neigborLabel) + 1;
				countMap.put(neigborLabel, count);
			} else {
				countMap.put(neigborLabel, 1);
			}
		}
		// 判别方式，多数服从少数
		int max = 0;
		Iterator<String> it = countMap.keySet().iterator();
		while (it.hasNext()) {
			String countKey = it.next();
			if (countMap.get(countKey) > max) {
				max = countMap.get(countKey);
				label = countKey;
			}
		}
		return label;
	}

	/**
	 * 测试分类器
	 * 
	 * @param testSet 测试集
	 * @param trainSet 训练集
	 */
	public void test(ArrayList<Node> testSet, ArrayList<Node> trainSet) {
		int errorRate = 0;
		for (int i = 0; i < testSet.size(); i++) {
			for (int j = 0; j < testSet.get(i).getProperty().size(); j++) {
				System.out.print(testSet.get(i).getProperty().get(j) + ",");
			}
			System.out.print("真实标签为: " + testSet.get(i).getLabel() + ",");

			String classificationLabel = knn(testSet.get(i), trainSet);// knn分类器得到分类标签
			System.out.println("测试结果为: " + classificationLabel);
			if (!classificationLabel.equals(testSet.get(i).getLabel())) {
				errorRate++;
			}
		}
		System.out.println("错误率:" + (double) errorRate / testSet.size());
		System.out.println("测试集数量" + testSet.size());
	}

}
