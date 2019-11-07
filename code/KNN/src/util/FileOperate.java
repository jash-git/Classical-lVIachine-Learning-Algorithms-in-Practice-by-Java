package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Node;

/**
 * 文件读写类
 */
public class FileOperate {

	/**
	 * 读取数据文件
	 * 
	 * @param data_path 数据文件路径
	 * @return
	 */
	public static ArrayList<Node> loadData(String data_path, String splitSymbol) {
		ArrayList<Node> dataList = new ArrayList<Node>();

		BufferedReader in = null;
		String message = null;
		try {
			in = new BufferedReader(new FileReader(data_path));
			while ((message = in.readLine()) != null) {
				String[] tokens = message.split(splitSymbol);
				double[] property = new double[tokens.length - 1];
				String label = tokens[tokens.length - 1];
				for (int i = 0; i < tokens.length - 1; i++) {
					if (tokens[i].length() == 0)
						continue;
					property[i] = Double.parseDouble(tokens[i]);
				}
				Node node = new Node(property, label);
				dataList.add(node);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}

}
