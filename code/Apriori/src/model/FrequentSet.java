package model;

import java.util.ArrayList;


/**
 * 
 * 候选项集类
 */
public class FrequentSet {
	//候选项集
	private ArrayList<String> cand;
	//候选项支持度
	private double support;
	
	public FrequentSet() {
		setSupport(0);
		cand = new ArrayList<String>();
	}

	public ArrayList<String> getCand() {
		return cand;
	}

	public void setCand(ArrayList<String> cand) {
		this.cand = cand;
	}

	
	/**
	 *  计算支持度
	 * @param dataSet 数据集
	 */
	public void calcSupport(ArrayList<ArrayList<String>> dataSet) {
		int count = 0;
		for (int i = 0; i < dataSet.size(); i++) {
			if(dataSet.get(i).containsAll(cand)) {
				//增加该候选集的计数
				count++;
			}
		}
		support = (double)count/dataSet.size();
	}
	
	

	public double getSupport() {
		return support;
	}

	public void setSupport(int support) {
		this.support = support;
	}

}
