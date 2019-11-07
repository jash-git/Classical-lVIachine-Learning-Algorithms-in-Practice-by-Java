package util;

/**
 * AlgorithmUtil 算法工具类
 */
public class AlgorithmUtil {

	public AlgorithmUtil() {
	}

	/**
	 * 获取一个数值型数组的最大值
	 * @param array 数值型数组
	 * @return max 该数组的最大值
	 */
	public static double getMax(double[] array) {
		double max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max)
				max = array[i];
		}
		return max;
	}

	/**
	 * 获取一个数值型数组的最小值
	 * @param array 数值型数组
	 * @return min 该数组的最小值
	 */
	public static double getMin(double[] array) {
		double min = array[1];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min)
				min = array[i];
		}
		return min;
	}

	/**
	 * 数组转置
	 * @param array 数值型数组
	 * @return result 转置后的数组
	 */
	public static double[][] getTransArray(double[][] array) {
		if (array == null)
			return array;
		double[][] result = new double[array[0].length][array.length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				result[j][i] = array[i][j];
			}
		}
		return result;
	}
}