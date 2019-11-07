package model;

/**
 * Node 抽象类，是所有数据实体类的父类
 */
public abstract class Node {

	/**
	 * 返回数据实体类的特征键集
	 * @return features 特征键集
	 */
	public abstract String[] getFeatures();
}