package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Example;

/**
 * 文件读写类
 */
public class FileOperate {
	/**
	 * 读取txt文件
	 * @param dataPath 数据集存放的路径            
	 * @return dataList 存放好的数据集
	 */
	public static ArrayList<Example> loadData(String dataPath,String separator) {

		String data = null;
		ArrayList<Example> dataList = new ArrayList<Example>();
		try {
			BufferedReader read = new BufferedReader(new FileReader(dataPath));
			while ((data = read.readLine()) != null) {
				String fields[] = data.split(separator);// 按分隔符分开
				double[] tmpList = new double[fields.length];
				for (int i = 0; i < fields.length; i++) {
					tmpList[i] = Double.parseDouble(fields[i]);
				}
				Example dataPoint = new Example();
				dataPoint.setAttributes(tmpList);// 添加样本点的属性
				dataList.add(dataPoint);// 将样本点放入dataList
			}
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;

	}
}
