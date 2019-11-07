package util;

import java.io.BufferedReader;
import util.AlgorithmUtil;
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
	 * 读取数据
	 * @param data_path 数据路径
	 * @param label 分隔符
	 * @return dataList 封装后的样本集合
	 */
	public static ArrayList<Example> loadData(String data_path, String label) {
		ArrayList<Example> dataList = new ArrayList<Example>();
		String message = new String();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(data_path));
			while ((message = input.readLine()) != null) {
				String[] info = message.split(label);

				// 清理数据
				info[1] = AlgorithmUtil.cleanData(info[1]);

				// 装载数据
				if (info[1] != null) {
					Example data = new Example(info[0], info[1]);
					dataList.add(data);
				}
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
