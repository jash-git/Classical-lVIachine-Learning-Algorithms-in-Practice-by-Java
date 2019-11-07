package algorithm;

import java.util.ArrayList;
import java.util.Random;

import model.Example;
import util.AlgorithmUtil;

/**
 * K-Means类
 */
public class KMeans {

	/**
	 * 初始化随机质心
	 * @param dataList 数据集
	 * @param k 聚成的簇数
	 * @return center 初始化的随机质心
	 */
	public ArrayList<Example> initCenter(ArrayList<Example> dataList, int k) {
		ArrayList<Example> center = new ArrayList<Example>();
		boolean flag;
		int j;// 用于判断前后生成的随机坐标是否重复
		Random random = new Random();
		int[] randoms = new int[k];
		int dataListLength = dataList.size();
		// 实际上随机的是dataList中样本的下标
		int temp = random.nextInt(dataListLength);
		randoms[0] = temp;
		for (int i = 1; i < k; i++) {
			flag = true;
			while (flag) {
				temp = random.nextInt(dataListLength);
				j = 0;
				// 判断随机生成的下标是否重复
				while (j < i) {
					if (temp == randoms[j])
						break;
					j++;
				}
				if (j == i) {
					flag = false;
				}
			}
			randoms[i] = temp;
		}

		for (int i = 0; i < k; i++) {
			center.add(dataList.get(randoms[i]));// 生成初始化中心链表
		}
		// 输出初始的随机质心,m为样本点的属性个数
		System.out.println("初始化的随机质心为：");
		for (int i = 0; i < center.size(); i++) {
			for (int m = 0; m < center.get(0).getAttributes().length; m++) {
				System.out.print(center.get(i).getAttributes()[m] + " ");
			}
			System.out.println();
		}
		return center;
	}

	/**
	 * 初始化簇的集合
	 * @param k 聚成的簇数
	 * @return cluster 初始化的簇
	 */
	public ArrayList<ArrayList<Example>> initCluster(int k) {
		ArrayList<ArrayList<Example>> cluster = new ArrayList<ArrayList<Example>>();
		for (int i = 0; i < k; i++) {
			cluster.add(new ArrayList<Example>());
		}
		return cluster;
	}

	/**
	 * 将当前元素放到与最小距离中心相关的簇中
	 * @param dataList 数据集
	 * @param center 质心
	 * @param k 聚成的簇数
	 * @return cluster 分好的各个簇
	 */

	public ArrayList<ArrayList<Example>> setCluster(ArrayList<Example> dataList, ArrayList<Example> center, int k) {
		ArrayList<ArrayList<Example>> cluster = initCluster(k);// 重置簇
		double[] distance = new double[k];

		// 将每个数据集元素划分到不同的簇中
		for (int i = 0; i < dataList.size(); i++) {
			for (int j = 0; j < k; j++) {
				distance[j] = AlgorithmUtil.distance(dataList.get(i), center.get(j));
			}
			int minLocation = AlgorithmUtil.minDistance(distance);
			cluster.get(minLocation).add(dataList.get(i));
		}
		return cluster;
	}

	/**
	 * 更新质心
	 * @param dataList 数据集
	 * @param k 聚成的簇数
	 * @param cluster 簇
	 * @return center 更新后的质心
	 */
	public ArrayList<Example> setNewCenter(ArrayList<Example> dataList, int k, ArrayList<ArrayList<Example>> cluster) {
		ArrayList<Example> center = new ArrayList<>();

		//i表示第i个簇。clusterLength表示每个簇的长度。attrLength表示样本点属性的个数
		for (int i = 0; i < k; i++) {
			Example newCenter = new Example();
			int clusterLength = cluster.get(i).size();
			if (clusterLength != 0) {
				int attrLength = dataList.get(0).getAttributes().length;
				double[] attrList = new double[attrLength];
				for (int j = 0; j < attrLength; j++) {
					for (int x = 0; x < clusterLength; x++) {
						// 计算各点的特征属性之和。例如第i个簇中，求取x个点的第j个属性的和。
						attrList[j] += cluster.get(i).get(x).getAttributes()[j];
					}
					attrList[j] /= clusterLength;//求取平均值
				}
				newCenter.setAttributes(attrList);
				center.add(newCenter);
			}
		}

		System.out.println("更新后的质心为：");
		for (int i = 0; i < center.size(); i++) {
			for (int j = 0; j < center.get(0).getAttributes().length; j++) {
				System.out.print(center.get(i).getAttributes()[j] + " ");
			}
			System.out.println();
		}

		return center;

	}

	/**
	 * 执行K-Means算法
	 * @param k 聚成的簇数
	 * @param dataList 数据集
	 */
	public void excute(ArrayList<Example> dataList, int k) {
		ArrayList<ArrayList<Example>> cluster = new ArrayList<>();// 簇
		ArrayList<Double> SSE = new ArrayList<Double>();// 误差平方和
		ArrayList<Example> center = initCenter(dataList, k);// 初始化质心
		int iter = 0;// iter为迭代次数
		
		// 迭代，直至误差平方和不发生变化或超过最大迭代次数
		while (iter <= Configuration.MAX_ITER) {
			cluster = setCluster(dataList, center, k);
			SSE = AlgorithmUtil.countRule(cluster, center, SSE);// 计算当前的误差平方和
			// 若误差平方和不变则完成聚类
			// iter的非零判断
			if (iter != 0) {
				System.out.println("平方误差之和为：" + SSE.get(iter));
				System.out.println();
				if (Math.abs(SSE.get(iter) - SSE.get(iter - 1)) <= Configuration.THRESHOLD) {
					break;// 如果前后两次的平方误差小于所设定的阈值
				}
			}
			center = setNewCenter(dataList, k, cluster);// 更新质心
			iter++;
			System.out.println("当前为第" + iter + "次迭代");
		}

		for (int i = 0; i < cluster.size(); i++) {
			AlgorithmUtil.printDataArray(cluster.get(i), "cluster[" + i + "]");// 输出每个簇中的点
		}
		System.out.println("note:the times of repeat:iter=" + iter);// 输出迭代次数

	}

}
