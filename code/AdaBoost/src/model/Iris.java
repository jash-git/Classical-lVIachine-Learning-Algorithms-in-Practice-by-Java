package model;

/**
 * Iris 实体类
 */
public class Iris extends Node {

	private double sepalLength;//花萼长度
	private double sepalWidth;//花萼宽度
	private double petalLength;//花瓣长度
	private double petalWidth;//花瓣宽度
	private int label;//标签
	private String[] features = new String[] { "sepalLength", "sepalWidth", "petalLength", "petalWidth" };//特征键集

	public double getSepalLength() {
		return sepalLength;
	}

	public void setSepalLength(double sepalLength) {
		this.sepalLength = sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public void setSepalWidth(double sepalWidth) {
		this.sepalWidth = sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public void setPetalLength(double petalLength) {
		this.petalLength = petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public void setPetalWidth(double petalWidth) {
		this.petalWidth = petalWidth;
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