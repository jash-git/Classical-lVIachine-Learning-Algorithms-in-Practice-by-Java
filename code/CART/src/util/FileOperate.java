package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Example;

/**
 * 文件读写类
 * */
public class FileOperate {

	/**
	 * 读取txt数据
	 * @param path 读取的文件路径
	 * @param split 分隔符类型
	 * @return exampleList 封装的数据
	 * */
	public static ArrayList<Example> loadData(String path, String split) {
		ArrayList<Example> exampleList = new ArrayList<Example>();
		String message = new String();
		BufferedReader read = null;
		try {
			read = new BufferedReader(new FileReader(path));
			while ((message = read.readLine()) != null) {
				String[] info = message.split(split);

				// 赋值
				double y = Double.parseDouble(info[info.length - 1]);// y值
				ArrayList<Double> x = new ArrayList<Double>();// x值
				for (int i = 0; i < info.length - 1; i++) {
					double xi = Double.parseDouble(info[i]);
					x.add(xi);
				}

				// 装载数据
				Example example = new Example(x, y);
				exampleList.add(example);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} finally {
			try {
				if (read != null)
					read.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return exampleList;
	}
}