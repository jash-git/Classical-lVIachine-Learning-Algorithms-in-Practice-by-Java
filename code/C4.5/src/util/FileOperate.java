package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Example;

/**
 * 文件读写类
 */
public class FileOperate {

	/**
	 * 读取数据样本集
	 * @param data_path 数据存放路径
	 * @param label 分隔符
	 * @return dataList 数据集
	 */
	public static ArrayList<Example> loadData(String data_path, String label) {
		ArrayList<Example> dataList = new ArrayList<Example>();
		String message = new String();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(data_path));
			while ((message = input.readLine()) != null) {
				String[] info = message.split(label);

				// 装载数据
				ArrayList<String> featureList = new ArrayList<String>();
				for (int i = 0; i < info.length - 1; i++) {
					featureList.add(info[i]);
				}
				String featureIndex = info[info.length - 1];
				Example data = new Example(featureList, featureIndex);
				dataList.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}

}
