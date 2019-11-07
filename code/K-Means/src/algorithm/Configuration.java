package algorithm;

/**
 * 配置类
 * */
public class Configuration {
	 /** 数据集路径 */
	public static final String DATA_PATH ="data/testSet.txt";
	
	 /** 聚成的簇数 */
	public static final int K=4;
	
	 /** 阈值。注意不要过小 */
	public static final double THRESHOLD=0.0001;
	 
	/** 迭代次数 */
	public static final int MAX_ITER = 200;
}
