package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import model.DecisionStump;
import model.Node;

/**
 * FileOperate文件操作工具类
 */
public class FileOperate {

	/**
	 * 根据路径读文件
	 * @param path 路径
	 * @param delim 分隔符
	 * @return datas 原始数据
	 */
	public static List<List<String>> loadData(String path,String delim) {
		List<List<String>> datas = new ArrayList<List<String>>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String str = null;
			while ((str = br.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(str,delim);
				List<String> temp = new ArrayList<String>();
				while (tokenizer.hasMoreTokens()) {
					temp.add(tokenizer.nextToken().trim());
				}
				datas.add(temp);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 根据路径写文件
	 * @param path 路径
	 * @param dsList 基础分类器集
	 * @param curNodeModel 当前数据样本模型
	 */
	public static void writeData(String path, List<DecisionStump> dsList, Node curNodeModel, int rightCount,
			int errorCount, long beginTime, long endTime) {
		try {
			// 新建文件
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			// 写数据
			bw.write("总共用时：  " + (endTime - beginTime) + "ms\r\n");

			int i = 1;
			int dsCount = dsList.size();
			bw.write("共" + dsCount + "个分类器\r\n");
			while (i <= dsList.size()) {
				DecisionStump ds = dsList.get(i - 1);
				double threshold = ds.getThreshold();
				double alphaWeight = ds.getAlphaWeight();
				int featureIndex = ds.getFeatureIndex();
				String feature = curNodeModel.getFeatures()[featureIndex];
				DecimalFormat df = new DecimalFormat("#0.00");

				bw.write("第" + i + "个分类器为：\r\n");
				bw.write("取的特征为： " + feature + "，阈值为： " + df.format(threshold) + "\r\n");
				bw.write("  " + ds.getLtLabel() + ",\t" + feature + "<" + df.format(threshold) + "\r\n");
				bw.write("  " + ds.getGtLabel() + ",\t" + feature + ">=" + df.format(threshold) + "\r\n");
				bw.write("权重：  " + alphaWeight + "\r\n");
				bw.write("\r\n");

				i++;
			}
			bw
					.write("测试数据共" + (rightCount + errorCount) + "个，正确" + rightCount + "个，错误" + errorCount + "个");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("写入数据完成！");
	}

}