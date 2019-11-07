package algorithm;

/**
 * 配置类
 * */
public class Configuration {
	/**
	 * @param DATA_PATH 数据路径
	 * @param E 允许的误差下降值
	 * @param N 允许切分的最少样本数
	 * @param proportion 训练集比例
	 */
	public static final String DATA_PATH = "data/airfoil_self_noise.txt";
	public static final double E = 0.01;
	public static final double N = 4;
	public static final double proportion = 0.8;
}