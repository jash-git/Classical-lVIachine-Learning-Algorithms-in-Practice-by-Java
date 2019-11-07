package util;

import java.util.ArrayList;

import algorithm.Configuration;

import model.Node;
import model.Example;

/**
 * 工具类
 * */
public class AlgorithmUtil {

	/**
	 * 随机获取训练集和测试集
	 * @param exampleList 数据集
	 * @return data 划分后的数据集
	 */
	public static ArrayList<ArrayList<Example>> dataProcess(ArrayList<Example> exampleList){
		int length = exampleList.size();
		int[] idList = new int[length];
		int trainLength = (int) (length * Configuration.proportion);
		ArrayList<Example> trainingList = new ArrayList<Example>();// 训练集
		ArrayList<Example> testList = new ArrayList<Example>();// 测试集
		ArrayList<ArrayList<Example>> data = new ArrayList<ArrayList<Example>>();
		
		for(int i = 0; i < length; i++){
			idList[i] = i;
		}
		for(int i = 0; i < trainLength; i++){
			int id = (int) (Math.random() * (length--));
			trainingList.add(exampleList.get(idList[id]));
			idList[id] = idList[length];
		}
		for (int i = 0; i < length; i++) {
			testList.add(exampleList.get(idList[i]));
		}
		
		data.add(trainingList);
		data.add(testList);
		return data;
	}
	
	/**
	 * 打印数据
	 * @param exampleList 封装的数据集
	 * */
	public static void printData(ArrayList<Example> exampleList) {
		for (int i = 0; i < exampleList.size(); i++) {
			ArrayList<Double> x = exampleList.get(i).getX();
			for (int j = 0; j < x.size(); j++) {
				System.out.print(x.get(j) + " ");
			}
			System.out.println(exampleList.get(i).getY());
		}
	}

	/**
	 * 打印回归树
	 * @param node 根节点
	 * */
	public static void printRTTree(Node node) {
		double feature = node.getFeature();
		double value = node.getValue();
		Node leftNode = node.getLeftNode();
		Node rightNode = node.getRightNode();

		// 打印特征
		// 叶子结点无特征
		if (feature != -1)
			System.out.print(feature + " : ");

		// 打印特征值
		// 叶子节点为取值
		System.out.println(value);

		// 打印右子树
		if (rightNode != null) {
			System.out.println("right:");
			printRTTree(rightNode);
		}

		// 打印左子树
		if (leftNode != null) {
			System.out.println("left:");
			printRTTree(leftNode);
		}
	}
}