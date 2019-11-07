package algorithm;

import model.Iris;
import model.Node;

/**
 * Configuration 参数配置类
 */
public class Configuration {

	/**
	 * 读数据的路径
	 */
	public static final String DATA_PATH = "data/iris.txt";

	/**
	 * 写数据的路径
	 */
	public static final String RESULT_PATH = "data/result.txt";

	/**
	 * 数据样本类别标签所在的列号
	 */
	public static final int LABEL_INDEX = 5;

	/**
	 * 所选数据样本开始的编号
	 */
	public static final int BEGIN = 1;

	/**
	 * 所选数据样本结束的编号
	 */
	public static final int END = 100;

	/**
	 * 算法最大迭代次数
	 */
	public static final int MAX_ITER = 500;

	/**
	 * 训练数据样本占全部样本的比例，范围：0.5<=percent<=0.9
	 */
	public static final double PERCENT = 0.8;

	/**
	 * 实例化数据实体类
	 */
	public static final Node NODEMODEL = new Iris();
	// public static final Node NODEMODEL = new Wine();
	// public static final Node NODEMODEL=new SimpleDataSet();
	// public static final Node NODEMODEL=new Employee();
}