package algorithm;

import java.util.ArrayList;

import model.Example;
import model.Node;
import util.AlgorithmUtil;
import util.FileOperate;

/**
 * 主函数类
 */
public class MainClass {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		// 1、读取数据
		ArrayList<Example> dataList = FileOperate.loadData(
				Configuration.DATA_PATH, ",");

		// 2、随机分配训练集和测试集
		ArrayList<ArrayList<Example>> dataListSet = AlgorithmUtil.dataProcessing(
				Configuration.TRAIN_RATE, dataList);
		ArrayList<Example> trainList = dataListSet.get(0);
		ArrayList<Example> testList = dataListSet.get(1);

		// 3、训练决策树分类器
		DecisionTree decisionTree = new DecisionTree();
		Node classification = new Node("root", -1, trainList);
		decisionTree.creatTree(classification);
		System.out.println("生成的决策树：");
		decisionTree.printTreePath(classification);// 打印决策树分类器
		System.out.println();

		// 4、测试分类器性能
		decisionTree.test(testList, classification);

		long end = System.currentTimeMillis();
		System.out.println("运行时间：" + (end - start) + " ms");
	}

}
