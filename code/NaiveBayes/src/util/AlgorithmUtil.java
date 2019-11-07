package util;

import java.util.ArrayList;
import java.util.regex.Pattern;

import model.Example;

/**
 * 工具类
 */
public class AlgorithmUtil {

	/**
	 * 清洗数据，去掉非字母的字符，和字节长度小于2的单词
	 * @param info 一条样本
	 * @return info 清洗后一条样本
	 */
	public static String cleanData(String info) {

		// 去标点
		Pattern p = Pattern.compile("\\W");
		String[] tempInfo = p.split(info);
		info = "";

		// 去停用词
		// 去除长度小于3的单词
		for (int i = 0; i < tempInfo.length; i++) {
			if (tempInfo[i].length() >= 3)
				info += tempInfo[i] + " ";
		}

		// 去大写
		info = info.toLowerCase();
		if (info.equals("")) {// 经观察，处理后有25条数据为空
			return null;
		} else {
			return info;
		}
	}

	/**
	 * 按比例把数据集分为训练集和测试集
	 * @param dataSetList 数据集
	 * @param trainRate 训练集占数据集的比例
	 * @return trainTestSet 包含训练集和测试集的集合
	 */
	public static ArrayList<ArrayList<Example>> dataProcessing(ArrayList<Example> dataSetList, double trainRate) {
		System.out.println(dataSetList.size());
		ArrayList<ArrayList<Example>> trainTestSet = new ArrayList<ArrayList<Example>>();
		ArrayList<Example> trainSet = new ArrayList<Example>();
		ArrayList<Example> testSet = new ArrayList<Example>();
		int dataSize = dataSetList.size();
		int trainLength = (int) (dataSize * trainRate);
		int[] sum = new int[dataSize];
		for (int i = 0; i < dataSize; i++) {
			sum[i] = i;
		}
		int num = dataSize;
		
		for (int i = 0; i < trainLength; i++) {
			int temp = (int) (Math.random() * (num--));
			trainSet.add(dataSetList.get(sum[temp]));
			sum[temp] = sum[num];
		}
		trainTestSet.add(trainSet);
		System.out.println(trainSet.size());
		
		for (int i = 0; i < dataSize - trainLength; i++) {
			testSet.add(dataSetList.get(sum[i]));
		}
		trainTestSet.add(testSet);
		System.out.println(testSet.size());
		
		return trainTestSet;
	}

}
