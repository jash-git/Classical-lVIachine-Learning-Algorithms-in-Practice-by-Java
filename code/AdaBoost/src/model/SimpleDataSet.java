package model;

/**
 * SimpleDataSet 实体类
 */
public class SimpleDataSet extends Node {

	private double xAis;//x轴
	private double yAis;//y轴
	private int label;//标签
	private String[] features = new String[] { "xAis", "yAis" };//特征键集

	public double getxAis() {
		return xAis;
	}

	public void setxAis(double xAis) {
		this.xAis = xAis;
	}

	public double getyAis() {
		return yAis;
	}

	public void setyAis(double yAis) {
		this.yAis = yAis;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	@Override
	public String[] getFeatures() {
		return features;
	}

}