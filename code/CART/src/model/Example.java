package model;

import java.util.ArrayList;

/**
 * 数据类
 * */
public class Example {

	/**
	 * @param x 数据属性值
	 * @param y 数据结果值
	 */
	private ArrayList<Double> x;// 属性值
	private double y;// 目标值

	public Example() {
		
	}

	public Example(ArrayList<Double> x, double y) {
		this.x = x;
		this.y = y;
	}

	public ArrayList<Double> getX() {
		return x;
	}

	public void setX(ArrayList<Double> x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}