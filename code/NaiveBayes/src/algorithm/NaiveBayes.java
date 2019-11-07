package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Classification;
import model.Example;


/**
 * 训练朴素贝叶斯算法执行类
 */
public class NaiveBayes {

	/**
	 * 训练朴素贝叶斯分类器	  
	 * @param trainingList 训练集
	 * @return classification 训练完毕的分类器
	 */
	public Classification training(ArrayList<Example> trainingList) {
		HashMap<String, Double> hamMap = new HashMap<String, Double>();// 非垃圾短信单词表
		HashMap<String, Double> spamMap = new HashMap<String, Double>();// 垃圾短信单词表
		HashSet<String> trainingSet = new HashSet<String>();// 此长度为训练集不重复单词数
		ArrayList<String> hamWords = new ArrayList<String>();// 非垃圾短信所有词
		ArrayList<String> spamWords = new ArrayList<String>();// 垃圾短信所有词

		// 1、建立单词集合
		HashMap<String, Double> parameterList = constructWordSet(trainingList, hamWords, spamWords, trainingSet);// 用MAP

		// 2、建立单词映射表
		constructWordMap(hamWords, spamWords, hamMap, spamMap);

		// 3、构建分类器
		Classification classification = constructClassification(parameterList, trainingSet, hamMap, spamMap);

		return classification;
	}

	/**
	 * 建立单词集合
	 * @param trainingList 训练集
	 * @param hamWords 非垃圾短信所有词
	 * @param spamWords 垃圾短信所有词
	 * @param trainingSet 训练集中不重复单词集合
	 * @return parameterList 训练过程中的参数
	 */
	public HashMap<String, Double> constructWordSet(ArrayList<Example> trainingList, ArrayList<String> hamWords,
			ArrayList<String> spamWords, HashSet<String> trainingSet) {
		double hamCount = 0;// 非垃圾短信数量
		double spamCount = 0;// 垃圾短信数量
		double hamWordsCount = 0;// 非垃圾短信单词总数
		double spamWrodsCount = 0;// 垃圾短信单词总数
		
		for (int i = 0; i < trainingList.size(); i++) {// 针对（矩阵）所有单词
			Example data = trainingList.get(i);// 一条短信
			String type = data.getType();// 短信类别
			String[] words = data.getMessage().split("\\s+");
			if (type.equals("ham")) {
				hamCount++;// 短信数量加1
				hamWordsCount += words.length;// 单词数量加
				for (int j = 0; j < words.length; j++) {// 赋值
					hamWords.add(words[j]);
					trainingSet.add(words[j]);
				}
			} else if (type.equals("spam")) {
				spamCount++;// 短信数量加1
				spamWrodsCount += words.length;// 单词数量加
				for (int k = 0; k < words.length; k++) {
					spamWords.add(words[k]);
					trainingSet.add(words[k]);
				}
			}
		}
		
		HashMap<String, Double> parameterList = new HashMap<String, Double>();
		parameterList.put("hamCount", hamCount);
		parameterList.put("spamCount", spamCount);
		parameterList.put("hamWordsCount", hamWordsCount);
		parameterList.put("spamWrodsCount", spamWrodsCount);
		
		return parameterList;
	}

	/**
	 * 建立单词映射表
	 * @param hamWords 非垃圾短信所有词
	 * @param spamWords 垃圾短信所有词
	 * @param hamMap 非垃圾短信单词表
	 * @param spamMap 垃圾短信单词表
	 */
	public void constructWordMap(ArrayList<String> hamWords, ArrayList<String> spamWords,
			HashMap<String, Double> hamMap, HashMap<String, Double> spamMap) {
		for (int i = 0; i < hamWords.size(); i++) {
			String word = hamWords.get(i);
			if (hamMap.containsKey(word)) {
				hamMap.put(word, hamMap.get(word) + 1);
			} else {
				hamMap.put(word, 1.0);
			}
		}
		
		for (int i = 0; i < spamWords.size(); i++) {
			String word = spamWords.get(i);
			if (spamMap.containsKey(word)) {
				spamMap.put(word, spamMap.get(word) + 1);
			} else {
				spamMap.put(word, 1.0);
			}
		}		
	}

	/**
	 * 构造分类器
	 * @param parameterList 参数列表
	 * @param trainingSet 训练集中不重复单词集合
	 * @param hamMap 非垃圾短信单词表
	 * @param spamMap 垃圾短信单词表
	 * @return classification 训练完毕的分类器
	 */
	public Classification constructClassification(HashMap<String, Double> parameterList, HashSet<String> trainingSet,
			HashMap<String, Double> hamMap, HashMap<String, Double> spamMap) {
		double hamCount = parameterList.get("hamCount");// 非垃圾短信数
		double spamCount = parameterList.get("spamCount");// 垃圾短信数量
		double hamWordsCount = parameterList.get("hamWordsCount");// 非垃圾短信单词总数
		double spamWrodsCount = parameterList.get("spamWrodsCount");// 垃圾短信单词总数
		int count = trainingSet.size();//训练集单词类别总数
		
		double hamProbability = hamCount / (hamCount + spamCount);// 非垃圾短信概率
		double spamProbability = spamCount / (hamCount + spamCount);// 垃圾短信概率
		
		
		Classification classification = new Classification(hamMap, spamMap, hamProbability, spamProbability,
				hamWordsCount, spamWrodsCount, count);
		
		return classification;
	}

	/**
	 * 测试朴素贝叶斯分类器
	 * @param testList 测试集
	 * @param classification 训练完毕的分类器
	 */
	public void test(ArrayList<Example> testList, Classification classification) {
		HashMap<String, Double> hamMap = classification.getHamMap();// 非垃圾短信单词表
		HashMap<String, Double> spamMap = classification.getSpamMap();// 垃圾短信单词表
		double correctCount = 0;// 正确数量
		double rate = 0;// 正确率
		
		for (int i = 0; i < testList.size(); i++) {// 所有测试数据
			Example data = testList.get(i);
			double hamRate = 1;// 非垃圾短信概率
			double spamRate = 1;// 垃圾短信概率
			String type = new String();// 预测类型
			String[] words = data.getMessage().split("\\s+");

			// 1、计算概率
			for (int j = 0; j < words.length; j++) {// 一条测试数据
				String word = words[j];
				if (hamMap.containsKey(word)) {// 计算成为非垃圾短信概率
					hamRate *= (hamMap.get(word) + 1) / (classification.getHamWordsCount() + classification.getCount());
				} else {
					hamRate *= 1 / (classification.getHamWordsCount() + classification.getCount());
				}
				if (spamMap.containsKey(word)) {// 计算成为垃圾短信概率
					spamRate *= (spamMap.get(word) + 1) / (classification.getSpamWordsCount() + classification.getCount());
				} else {
					spamRate *= 1 / (classification.getSpamWordsCount() + classification.getCount());
				}
			}			
			hamRate *= classification.getHamProbability();
			spamRate *= classification.getSpamProbability();
			hamRate = Math.log(hamRate);
			spamRate = Math.log(spamRate);

			// 2、比较概率
			if (hamRate > spamRate) {
				type = "ham";
			} else if (hamRate < spamRate) {
				type = "spam";
			} else if (hamRate == spamRate) {
				type = "unknown";
			}
			if (type.equals(data.getType()))
				correctCount++;
		}
		
		rate = correctCount / testList.size();
		System.out.println("测试集数量：" + testList.size());
		System.out.println("预测正确的数量：" + correctCount);
		System.out.println("预测错误的数量：" + (testList.size() - correctCount));
		System.out.println("正确率：" + rate);
	}
}
