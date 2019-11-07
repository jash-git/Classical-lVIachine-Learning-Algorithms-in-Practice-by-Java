package algorithm;

import java.util.ArrayList;

import algorithm.RegressionTree;

import model.Node;
import model.Example;

import util.AlgorithmUtil;
import util.FileOperate;
/**
 * 主函数类
 * */
public class MainClass {

	public static void main(String[] args) {
		//1、读取数据
		ArrayList<Example> exampleList = FileOperate.loadData(Configuration.DATA_PATH, "\t");//读取
		
		//2、数据分类
		ArrayList<ArrayList<Example>> data = AlgorithmUtil.dataProcess(exampleList);
		ArrayList<Example> trainingList = data.get(0);//训练集
		ArrayList<Example> testList = data.get(1);//测试集
		
		//3、构造回归树
		RegressionTree regressionTree = new RegressionTree();
		Node RTroot = regressionTree.constructTree(trainingList);
		
		//4、打印
		AlgorithmUtil.printRTTree(RTroot);
		
		//5、测试
		regressionTree.testRT(testList, RTroot);
	}
}