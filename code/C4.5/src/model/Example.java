package model;

import java.util.ArrayList;

/**
 * 数据类
 */
public class Example {

	private ArrayList<String> featureList; // 特征列表
	private String featureIndex;// 所属类别索引

	public Example(ArrayList<String> featureList, String featureIndex) {
		this.featureList = featureList;
		this.featureIndex = featureIndex;
	}

	public ArrayList<String> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(ArrayList<String> featureList) {
		this.featureList = featureList;
	}

	public String getFeatureIndex() {
		return featureIndex;
	}

	public void setFeatureIndex(String featureIndex) {
		this.featureIndex = featureIndex;
	}

}
