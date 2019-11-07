package algorithm;

/**
 * 配置类
 */
public class Configuration {
	/**
	 * 实际参数读取路径
	 */
	public static final String ACTUAL_PARAMETERS_PATH = "data/actualParameters.txt";
	/**
	 * 初始化参数读取路径
	 */
	public static final String INIT_PARAMETERS_PATH = "data/initParameters.txt";
	/**
	 * 观测数据存储路径
	 */
	public static final String INPUT_DATA_PATH = "data/inputData.txt";
	/**
	 * 参数估计结果存储路径
	 */
	public static final String RESULT_PARAMETER_PATH = "data/outParameters.txt";
	/**
	 * 观测样本个数
	 */
	public static final int OBSERVED_DATA_NUMBER = 1000;
	/**
	 * 收敛阈值，可调
	 */
	public static final double CONVERGENCE_CONDITION = 0.0000001;
	/**
	 * 最大迭代次数
	 */
	public static final int MAX_ITER = 1000;

}
