package algorithm;

import java.util.ArrayList;

import model.Classification;
import model.Example;
import util.FileOperate;
import util.AlgorithmUtil;

/**
 * 主函数类
 */
public class MainClass {

	public static void main(String[] args) {

		// 1、初始化分类器
		Classification classification = new Classification();

		// 2、读取数据
		ArrayList<Example> dataSetList = FileOperate.loadData(Configuration.DATA_PATH, "\t");

		// 3、按一定比例分测试集和训练集
		ArrayList<ArrayList<Example>> trainTestSet = AlgorithmUtil.dataProcessing(dataSetList, Configuration.TRAIN_RATE);
		ArrayList<Example> trainingList = trainTestSet.get(0);
		ArrayList<Example> testList = trainTestSet.get(1);

		// 4、训练朴素贝叶斯分类器
		NaiveBayes naiveBayes = new NaiveBayes();
		classification = naiveBayes.training(trainingList);

		// 5、测试
		naiveBayes.test(testList, classification);		
	}
}
