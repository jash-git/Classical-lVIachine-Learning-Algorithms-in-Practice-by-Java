package algorithm;

import java.util.ArrayList;

import util.FileOperate;

public class MainClass {
	public static void main(String[] args) {
		ArrayList<ArrayList<String>> dataSet = FileOperate.loadData(Configuration.DATA_PATH, "\\s");
		long start = System.currentTimeMillis();
		FreqSetGen fsGen = new FreqSetGen();
		//生成频繁项集
		fsGen.freqSetGen(dataSet);
		//打印频繁项集
		fsGen.printCandidates();
		RulesGen generate = new RulesGen(fsGen.getCandidates());
		//生成关联规则
		generate.generateRules(dataSet);
		//打印
		generate.print();
		long end = System.currentTimeMillis();
		System.out.println("运行用时："+(end - start)+" ms");
	}
}
