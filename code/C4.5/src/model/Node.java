package model;

import java.util.ArrayList;

/**
 * 树节点类
 */
public class Node {

	private String featureName;// 划分树节点的特征取值
	private int featureIndex;// 划分树节点的特征
	private ArrayList<Example> dataList;// 树节点存储的数据
	private ArrayList<Node> childrenList;// 孩子列表
	private String type;// 节点分类（只有叶子节点有节点分类）

	/**
	 * 构造方法
	 * @param featureName 划分树节点的特征取值
	 * @param featureIndex 节点的最优特征（-1代表根节点的最优特征）
	 * @param dataList 树节点存储的数据
	 */
	public Node(String featureName, int featureIndex,
			ArrayList<Example> dataList) {
		this.featureName = featureName;
		this.featureIndex = featureIndex;
		this.dataList = dataList;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public int getFeatureIndex() {
		return featureIndex;
	}

	public void setFeatureIndex(int featureIndex) {
		this.featureIndex = featureIndex;
	}

	public ArrayList<Example> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<Example> dataList) {
		this.dataList = dataList;
	}

	public ArrayList<Node> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(ArrayList<Node> childrenList) {
		this.childrenList = childrenList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
