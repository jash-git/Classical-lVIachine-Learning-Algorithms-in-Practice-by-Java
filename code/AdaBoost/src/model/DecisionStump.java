package model;

/**
 * DecisionStump决策树桩类（基础分类器）
 */
public class DecisionStump {

	private int featureIndex;//所选特征的索引号
	private double threshold;//阈值
	private int ltLabel;//小于阈值的类别
	private int gtLabel;//大于阈值的类别
	private double alphaWeight;//分类器的权重

	public DecisionStump() {
	}

	/**
	 * 带参构造决策树桩
	 * @param featureIndex 所选特征的索引号
	 * @param threshold 阈值
	 * @param ltLabel 小于阈值的类别
	 * @param gtLabel 大于阈值的类别
	 * @param alphaWeight 分类器的权重
	 */
	public DecisionStump(int featureIndex, double threshold, int ltLabel, int gtLabel, double alphaWeight) {
		this.featureIndex = featureIndex;
		this.threshold = threshold;
		this.ltLabel = ltLabel;
		this.gtLabel = gtLabel;
		this.alphaWeight = alphaWeight;
	}

	public int getFeatureIndex() {
		return featureIndex;
	}

	public void setFeatureIndex(int featureIndex) {
		this.featureIndex = featureIndex;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getLtLabel() {
		return ltLabel;
	}

	public void setLtLabel(int ltLabel) {
		this.ltLabel = ltLabel;
	}

	public int getGtLabel() {
		return gtLabel;
	}

	public void setGtLabel(int gtLabel) {
		this.gtLabel = gtLabel;
	}

	public double getAlphaWeight() {
		return alphaWeight;
	}

	public void setAlphaWeight(double alphaWeight) {
		this.alphaWeight = alphaWeight;
	}
}