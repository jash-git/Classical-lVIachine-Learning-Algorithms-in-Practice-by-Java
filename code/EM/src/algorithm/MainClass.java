package algorithm;

import java.util.List;
import model.Input;
import model.Parameters;
/**
 * 入口类
 */
public class MainClass {
	
	public static void main(String[] args) {

		EMGMM emgmm = new EMGMM();

		// 1. 生成观测数据，并获取算法的输入
		Input input = emgmm.obtainInput();

		// 2. 获取初始化参数
		List<Parameters> parametersList = emgmm.obtainInitParameters();
		
		// 3. 参数迭代更新
		List<Parameters> result = emgmm.updateParameters(input, parametersList);

		// 4.打印参数估计结果
		emgmm.printResult(result);

	}
}
