package model;

/**
 * 节点类
 * */
public class Node {

	/**
	 * @param feature 表示用于划分的特征,等于-1表示无特征可划分
	 * @param value 特征值
	 * @param leftNode 左子结点
	 * @param rightNode 右子结点
	 */
	private double feature;// 特征
	private double value;// 特征值
	private Node leftNode;// 左子结点
	private Node rightNode;// 右子结点

	public Node() {
		
	}

	public Node(double feature, double value, Node leftNode, Node rightNode) {
		this.feature = feature;
		this.value = value;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	public double getFeature() {
		return feature;
	}

	public void setFeature(double feature) {
		this.feature = feature;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
}