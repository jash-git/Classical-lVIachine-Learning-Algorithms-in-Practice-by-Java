package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Input;
import model.Parameters;
/**
 * 文件读写类
 */
public class FileOperate {
	/**
	 * 函数功能：读取文件
	 * @param dataPath 需要读取的文件的路径
	 * @param separator 文件内容分隔符
	 * @return List<Parameters> 参数列表
	 */
	public static List<Parameters> readParameters(String dataPath, String separator) {
		ArrayList<Parameters> parametersList = new ArrayList<Parameters>();
		String line = null;
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(dataPath));
			int index = 0;
			while ((line = bufferReader.readLine()) != null) {
				if (index == 0 || index == 1) {
					index++;
					continue;
				}
				String[] parameter = line.split(separator);
				Parameters parameters = new Parameters();
				parameters.setμΣψ(Double.parseDouble(parameter[0]),
						Double.parseDouble(parameter[1]),
						Double.parseDouble(parameter[2]));
				parametersList.add(parameters);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return parametersList;
	}

	/**
	 * @param datapath 写入文件路径
	 * @param input 算法输入数据
	 */
	public static void writeInputData(String dataPath, Input input) {
		try {
			FileWriter fileWritter = new FileWriter(dataPath);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

			int k = input.getClassify();
			List<Double> exampleList = input.getExamplelist();
			bufferWritter.write("算法输入数据：" + "\r\n");
			bufferWritter.write("观测数据所服从的高斯分布的个数：" + k + "\r\n");
			for (int i = 0; i < exampleList.size(); i++) {
				bufferWritter.write("观测数据：" + exampleList.get(i) + "\r\n");
			}
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("写入算法的输入数据完成！");
	}

	/**
	 * @param data_path 写入文件outParameters.txt路径
	 * @param result 参数估计结果
	 */
	public static void writeResultParameters(String dataPath,
			List<Parameters> result) {
		try {
			FileWriter fileWritter = new FileWriter(dataPath);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			// 写数据
			bufferWritter.write("分布参数估计结果：" + "\r\n");
			bufferWritter.write("期望	方差	多项分布参数" + "\r\n");
			for (int i = 0; i < result.size(); i++) {
				Parameters parameters = result.get(i);
				bufferWritter.write(parameters.getμ() + "	" + parameters.getΣ()
						+ "	" + parameters.getΨ() + "\r\n");
			}
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("写入参数估计结果完成！");
	}
}
