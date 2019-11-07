package algorithm;

import java.util.ArrayList;

import model.Example;
import util.FileOperate;

/**
 * 主函数类
 * */

public class MainClass {
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		//1、加载数据集，数据集以"\\s"分隔开
		ArrayList<Example> dataList = FileOperate.loadData(Configuration.DATA_PATH,"\\s");

		//2、执行k-means算法
		KMeans kmeans = new KMeans();
		kmeans.excute(dataList, Configuration.K);
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
	
	
}
