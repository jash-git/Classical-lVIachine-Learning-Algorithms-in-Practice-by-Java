package util;

import java.util.ArrayList;
import model.Example;

/**
 * 算法工具类
 */
public class AlgorithmUtil {
	/**
	 * 计算样本点到质心之间的距离
	 * @param element 样本点
	 * @param center 质心
	 * @return 样本点到质心的距离
	 */
	public static double distance(Example element, Example center) {
		double distance = 0.0f;
		double sum = 0;
		if ((element.getAttributes().length == 0 && center.getAttributes().length == 0)
				|| element.getAttributes().length != center.getAttributes().length) {
			System.out.println("数据集有误！");
		} else {
			for (int i = 0; i < element.getAttributes().length; i++) {
				double dis = (double) Math.pow(element.getAttributes()[i] - center.getAttributes()[i], 2);
				sum += dis;
			}
			distance = (double) Math.sqrt(sum);
		}

		return distance;
	}

	/**
	 * 获得数据集中距离质心最近的样本点位置
	 * @param distance 样本点到各个质心的距离
	 * @return minLocation 距离最小的点的坐标
	 */
	public static int minDistance(double[] distance) {
		double minDistance = distance[0];
		int minLocation = 0;
		
		for (int i = 0; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			}
		}
		return minLocation;
	}

	/**
	 * 计算最小平方误差之和
	 * @param cluster 簇
	 * @param center 质心
	 * @param sse 误差平方和
	 * @return sse 误差平方和
	 */
	public static ArrayList<Double> countRule(ArrayList<ArrayList<Example>> cluster, ArrayList<Example> center,
			ArrayList<Double> sse) {
		double jcF = 0;

		for (int i = 0; i < cluster.size(); i++) {
			for (int j = 0; j < cluster.get(i).size(); j++) {
				jcF += errorSquare(cluster.get(i).get(j), center.get(i));
			}
		}
		sse.add(jcF);
		return sse;

	}

	/**
	 * 计算最小平方误差
	 * @param element 样本点
	 * @param center 质心
	 * @return errorSquare 样本点到质心的平方误差
	 */
	public static double errorSquare(Example element, Example center) {
		double errorSquare = 0.0f;
		if (element.getAttributes().length == 0 && center.getAttributes().length == 0
				|| element.getAttributes().length != center.getAttributes().length) {
			System.out.println("数据集有误！");
		} else {
			for (int i = 0; i < element.getAttributes().length; i++) {
				double dis = (double) Math.pow(element.getAttributes()[i] - center.getAttributes()[i], 2);
				errorSquare += dis;
			}
		}
		return errorSquare;
	}

	/**
	 * 打印结果
	 * @param dataArray 每个簇中的数据点
	 * @param dataArrayName 簇的名称
	 */
	public static void printDataArray(ArrayList<Example> dataArray, String dataArrayName) {
		System.out.println(dataArrayName + ":");
		for (int i = 0; i < dataArray.size(); i++) {
			for (int j = 0; j < dataArray.get(i).getAttributes().length; j++) {
				System.out.print(dataArray.get(i).getAttributes()[j] + " ");
			}
			System.out.println();
		}
		System.out.println("==================================");
	}
}
