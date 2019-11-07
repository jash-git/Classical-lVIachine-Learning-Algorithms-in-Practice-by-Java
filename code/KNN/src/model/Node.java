package model;

import java.util.ArrayList;

/**
 * 描述一条数据
 */
public class Node {

	private ArrayList<Double> property = new ArrayList<Double>();// 特征向量
	private String label;// 样本标签
	private double disFromNode;

	public Node(double[] b, String label) {
		for (int i = 0; i < b.length; i++) {
			property.add(b[i]);
		}
		this.label = label;
	}

	public ArrayList<Double> getProperty() {
		return property;
	}

	public String getLabel() {
		return label;
	}

	public void setProperty(ArrayList<Double> property) {
		this.property = property;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getDisFromNode() {
		return disFromNode;
	}

	public void setDisFromNode(double disFromNode) {
		this.disFromNode = disFromNode;
	}

}
