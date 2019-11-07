package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 
 * 文件读写类
 */
public class FileOperate {
	
	
	/**
	 * 读取数据
	 * @param path
	 * @param split
	 * @return
	 */
	public static ArrayList<ArrayList<String>> loadData(String path, String split) {
		BufferedReader br = null;
		ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
		File file = new File(path);
		if(file.isFile() && file.exists()) {
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				br = new BufferedReader(read);
				String lineText = null;
				while((lineText = br.readLine()) != null) {
					String[] line = lineText.split(split);
					ArrayList<String> newLine = new ArrayList<String>();
					for (int i = 0; i < line.length; i++) {
						newLine.add(line[i]);
					}
					dataSet.add(newLine);
				}
				read.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("找不到文件路径！");
		}
		return dataSet;
	}
	
}
