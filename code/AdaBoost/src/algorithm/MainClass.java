package algorithm;

import java.util.List;
import java.util.Map;

import model.DecisionStump;
import util.DataUtil;
import util.FileOperate;

public class MainClass {

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();

		// step1 获取原始数据并划分训练集和测试集
		// 获取原始数据
		List<List<String>> datas = FileOperate.loadData(Configuration.DATA_PATH, "[\t|\\s+]");
		// 划分训练集和测试集
		Map<String, Object> dataAndLabelMap = DataUtil.divideTrainAndTestData(datas,Configuration.BEGIN,Configuration.END,Configuration.PERCENT);
		double[][] trainDataArray = (double[][]) dataAndLabelMap.get("trainData");
		double[][] testDataArray = (double[][]) dataAndLabelMap.get("testData");
		int[] trainLabelArray = (int[]) dataAndLabelMap.get("trainLabel");
		int[] testLabelArray = (int[]) dataAndLabelMap.get("testLabel");

		// step2 初始化训练数据样本权重
		int trainDataCount = trainLabelArray.length;
		double[] weightArray = DataUtil.initWeightArray(trainDataCount);

		// step3 核心步骤，构建多个基础分类器的组合
		AdaBoost adaBoost = new AdaBoost(trainDataArray, testDataArray, trainLabelArray, weightArray);
		List<DecisionStump> dsList = adaBoost.constructClassfier();

		// step4 测试
		int[] result=adaBoost.test(dsList, testLabelArray);
		int rightCount = result[0];
		int errorCount = result[1];

		long endTime = System.currentTimeMillis();

		// step5 打印输出最终分类器和测试结果到文件
		FileOperate.writeData(Configuration.RESULT_PATH, dsList, Configuration.NODEMODEL, rightCount, errorCount, beginTime,
				endTime);
	}
}