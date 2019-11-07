package model;

import java.util.ArrayList;

/**
 *	关联规则类
 */
public class Rule {
	private ArrayList<String> cand;
	
	private ArrayList<String> left;//规则左边
	
	private ArrayList<String> right;//规则右边
	
	private double conf;//置信度
	
	public Rule() {
		cand = new ArrayList<String>();
		conf = 0;
		left = new ArrayList<String>();
		right = new ArrayList<String>();
	}
	
	public ArrayList<String> getLeft() {
		return left;
	}
	public void setLeft(ArrayList<String> left) {
		this.left = left;
	}
	public ArrayList<String> getRight() {
		return right;
	}
	public void setRight(ArrayList<String> right) {
		this.right = right;
	}

	public double getConf() {
		return conf;
	}

	public void setConf(double conf) {
		this.conf = conf;
	}
	
	/**
	 *  计算置信度
	 * @param dataSet 数据集
	 */
	public void calcConf(ArrayList<ArrayList<String>> dataSet) {
		//计算每个规则的置信度
		int joinp = 0;
		int p = 0;
		for (int j = 0; j < dataSet.size(); j++) {
			//若包含联合候选集，则同时也包含边缘候选集
			if(dataSet.get(j).containsAll(cand)) {
				joinp += 1;
				p += 1;
			}else if(dataSet.get(j).containsAll(right)) {
				p += 1;
			}
		}
		conf = (double)joinp/p;
	}

	public void setCand(ArrayList<String> cand) {
		this.cand = cand;
		
	}
	
}
