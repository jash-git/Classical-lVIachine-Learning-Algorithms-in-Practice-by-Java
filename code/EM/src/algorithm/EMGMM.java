package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import util.FileOperate;
import model.Input;
import model.Parameters;

/**
 * 算法核心方法类
 */
public class EMGMM {
	/**
	 * 函数功能：根据actualParameters.txt中的参数，生成观测数据，从而获取算法输入数据
	 * @return Input 算法输入
	 */
	public Input obtainInput() {
		Random random = new Random();
		Input input = new Input();
		// 读取用于生成观测数据的参数列表
		List<Parameters> parametersList = FileOperate.readParameters(
				Configuration.ACTUAL_PARAMETERS_PATH, "\t");
		// 根据不同高斯分布（参数）生成观测数据
		for (int j = 0; j < parametersList.size(); j++) {
			double ψ = parametersList.get(j).getΨ();
			for (int i = 0; i < Configuration.OBSERVED_DATA_NUMBER * ψ; i++) {
				double example = Math.sqrt(parametersList.get(j).getΣ())
						* random.nextGaussian() + parametersList.get(j).getμ();
				input.addExample(example);
			}
		}
		input.setClassify(parametersList.size());
		// 为便于观察算法具体的输入数据，这里将观测数据以及分布数写入文件observedData.txt
		FileOperate.writeInputData(Configuration.INPUT_DATA_PATH, input);
		return input;
	}

	/**
	 * 函数功能：从文件initParameters.txt获取初始化参数
	 * @return List<Parameters> 初始化完毕的待估参数
	 */
	public List<Parameters> obtainInitParameters(){
		List<Parameters> parametersList = FileOperate.readParameters(
				Configuration.INIT_PARAMETERS_PATH, "\t");
		return parametersList;
	}
	
	/**
	 * 函数功能：待估参数的迭代更新
	 * @param input 算法的输入
	 * @param parametersList 待估参数
	 * @return List<Parameters> 待估参数的估计结果
	 */
	public List<Parameters> updateParameters(Input input,
			List<Parameters> parametersList) {
		List<Double> exampleList = input.getExamplelist();//算法输入中的观测数据
		int k = input.getClassify();//算法输入中的高斯分布个数
		// 迭代次数
		int iter = 1;
		// 进入待估参数的迭代更新
		while (iter <= Configuration.MAX_ITER) {
			System.out.println("----------------------第" + iter + "次更新！");
			// E步：
			Map<Integer, List<Double>> mulDisMap = this.eStep(exampleList,
					parametersList, k);
			// M步：
			double change = this.mStep(exampleList, mulDisMap, parametersList,
					k);
			// 判断是否满足收敛条件或者达到最大迭代次数
			if (change < Configuration.CONVERGENCE_CONDITION)
				break;
			iter++;
		}
		return parametersList;
	}

	/**
	 * E步：对每一个样本，获取其来自第k个高斯分布的后验概率ω
	 * @param exampleList 观测数据
	 * @param parametersList 待估参数列表
	 * @param k 观测数据所服从的所有高斯分布的个数
	 * @return Map<Integer, List<Double> 各样本来自各个高斯分布的后验概率更新后的列表
	 */
	public Map<Integer, List<Double>> eStep(List<Double> exampleList,
			List<Parameters> parametersList, int k) {
		Map<Integer, List<Double>> mulDisMap = new HashMap<Integer, List<Double>>();
		// 对每个高斯分布分布
		for (int j = 0; j < k; j++) {
			List<Double> mulDisList = new ArrayList<Double>();
			// 对每个样本数据
			for (int i = 0; i < exampleList.size(); i++) {
				double temp1 = parametersList.get(j).getΨ()
						* Math.exp(-Math.pow(exampleList.get(i)
								- parametersList.get(j).getμ(), 2)
								/ (2 * parametersList.get(j).getΣ()))
						/ Math.sqrt(Math.abs(2 * Math.PI
								* parametersList.get(j).getΣ()));
				double temp2 = 0;
				// 对每个高斯分布（样本数据分别来自每个分布的情况）
				for (int l = 0; l < k; l++) {
					temp2 = temp2
							+ parametersList.get(l).getΨ()
							* Math.exp(-Math.pow(exampleList.get(i)
									- parametersList.get(l).getμ(), 2)
									/ (2 * parametersList.get(l).getΣ()))
							/ Math.sqrt(Math.abs(2 * Math.PI
									* parametersList.get(l).getΣ()));
				}
				// 获取当前样本来自第j个高斯分布的后验概率ω
				double ω = temp1 / temp2;
				mulDisList.add(ω);
			}
			// 每个样本来自各高斯分布的后验概率ω列表
			mulDisMap.put(j, mulDisList);
		}
		return mulDisMap;
	}

	/**
	 * M步：更新各高斯分布参数，同时获取参数更新前后的变化量
	 * @param exampleList 观测数据列表
	 * @param mulDisMap 样本i来自第j个高斯分布的后验概率列表
	 * @param parametersList 待估参数列表
	 * @param k 观测数据所服从的所有高斯分布的个数
	 * @return double 参数更新前后变化量
	 */
	public double mStep(List<Double> exampleList,
			Map<Integer, List<Double>> mulDisMap,
			List<Parameters> parametersList, int k) {
		double change = 0;
		// 每个分布
		for (int j = 0; j < k; j++) {
			double sumψ = 0;
			double sumμ = 0;
			double sumΣ = 0;
			// 每个样本数据
			for (int i = 0; i < exampleList.size(); i++) {
				sumψ = sumψ + mulDisMap.get(j).get(i);
				sumμ = sumμ + mulDisMap.get(j).get(i) * exampleList.get(i);
			}
			// 更新样本i来自第j个高斯分布的概率
			double newψ = sumψ / exampleList.size();
			// 更新第j个高斯分布的期望
			double newμ = sumμ / sumψ;
			// 第j个高斯分布的方差更新过程
			for (int i = 0; i < exampleList.size(); i++) {
				sumΣ = sumΣ + mulDisMap.get(j).get(i)
						* Math.pow(Math.abs(exampleList.get(i) - newμ), 2);
			}
			// 更新第j个高斯分布的方差
			double newΣ = sumΣ / sumψ;
			System.out.println(j + "   newμ:" + newμ
					+ "   newΣ:" + newΣ + ":   newψ:" + newψ);
			// 计算更新前后参数变化量
			change += Math.abs(parametersList.get(j).getΨ() - newψ)
					+ Math.abs(parametersList.get(j).getμ() - newμ)
					+ Math.abs(parametersList.get(j).getΣ() - newΣ);
			// 更新参数列表
			parametersList.get(j).setμΣψ(newμ, newΣ, newψ);
		}
		return change;
	}

	/**
	 * 函数功能：打印参数估计结果
	 * @param result 算法运行结果
	 */
	public void printResult(List<Parameters> result) {
		FileOperate.writeResultParameters(Configuration.RESULT_PARAMETER_PATH, result);
		System.out.println("参数估计结果：");
		for (int i = 0; i < result.size(); i++) {
			Parameters parameters = result.get(i);
			System.out.println(parameters.getΨ() + "  " + parameters.getμ()
					+ "  " + parameters.Σ);
		}
	}
}
