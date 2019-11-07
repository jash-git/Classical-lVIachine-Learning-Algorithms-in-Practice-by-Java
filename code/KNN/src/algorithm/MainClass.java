package algorithm;

import java.util.ArrayList;
import model.Node;
import util.AlgorithmUtil;
import util.FileOperate;

/**
 * 程序入口
 */
public class MainClass {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		// 1.读取数据
		ArrayList<Node> dataList = FileOperate.loadData(Configuration.DATA_PATH, ",");
		
		// 2.对数据归一化处理
		AlgorithmUtil.normData(dataList);

		// 3.划分训练集和测试集
		ArrayList<ArrayList<Node>> splitSet = AlgorithmUtil.dataProcessing(dataList, Configuration.TRAINR_RATIO);
		ArrayList<Node> trainSet = splitSet.get(0);// 训练集
		ArrayList<Node> testSet = splitSet.get(1);// 测试集
        
		// 4.测试分类器
		KNN knn = new KNN();
		knn.test(testSet, trainSet);

		long end = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (end - start));
		

	}

}
