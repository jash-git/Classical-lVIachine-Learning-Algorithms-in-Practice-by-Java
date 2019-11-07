package model;

import java.util.ArrayList;
import java.util.List;

/**
 *EM_GMM算法的输入数据
 */
public class Input {

	public int classify;//观测数据所服从的高斯分布个数
	public List<Double> exampleList = new ArrayList<Double>();//观测数据列表

	public void setClassify(int classify) {
		this.classify = classify;
	}
	public void addExample(Double example) {
		this.exampleList.add(example);
	}
	public int getClassify() {
		return classify;
	}
	public List<Double> getExamplelist() {
		return exampleList;
	}

}
