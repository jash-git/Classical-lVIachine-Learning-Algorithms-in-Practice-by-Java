package model;
/**
 * 数据类
 * */
public class Example {
	
	private double[] attributes;//数据集中每个样本所包含的属性
	private int index;//数据集中每个样本的标识位，表示该样本属于哪个簇
	
	public double[] getAttributes() {
		return attributes;
	}
	public void setAttributes(double[] attributes) {
		this.attributes = attributes;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}	
}
