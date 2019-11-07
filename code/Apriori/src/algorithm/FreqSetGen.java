package algorithm;


/**
 * 频繁项集生成类
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import model.FrequentSet;
import util.AlgorithmUtil;
import util.FileOperate;


public class FreqSetGen {
	//保存k长度的频繁项候选集
	private ArrayList<FrequentSet> candidates;
	
	public FreqSetGen() {
		candidates = new ArrayList<FrequentSet>();
	}
	

	/**
	 *  根据最小支持度过滤频繁项集
	 * @param candidates  候选集
	 * @param dataSet 数据集
	 * @return
	 */
	private ArrayList<FrequentSet> scanData(ArrayList<FrequentSet> candidates, ArrayList<ArrayList<String>> dataSet) {
		ArrayList<FrequentSet> cand = new ArrayList<FrequentSet>();
		for (int j = 0; j < candidates.size(); j++) {
			candidates.get(j).calcSupport(dataSet);
			if (candidates.get(j).getSupport() > Configuration.MIN_SUPPORT) {
				cand.add(candidates.get(j));
			}
		}
		return cand;
	}
	
	/**
	 *  创建长度为1的候选项集
	 * @param dataSet 数据集
	 * @return
	 */
	public ArrayList<FrequentSet> createCand1(ArrayList<ArrayList<String>> dataSet) {
		ArrayList<FrequentSet> cand = new ArrayList<FrequentSet>();
		ArrayList<String> cand1 = new ArrayList<String>();
		//找到所有不重复的长度为1的候选集
		for (int i = 0; i < dataSet.size(); i++) {
			ArrayList<String> row = dataSet.get(i);
			for (int j = 0; j < row.size(); j++) {
				String col = row.get(j);
				if(!cand1.contains(col))
					cand1.add(col);
			}
		}
		//转换为候选集对象
		for (int i = 0; i < cand1.size(); i++) {
			FrequentSet candidate = new FrequentSet();
			ArrayList<String> candList = new ArrayList<String>();
			//将String转成集合
			candList.add(cand1.get(i));
			candidate.setCand(candList);
			cand.add(candidate);
		}
		return cand;
	}
	
	/**
	 * 生成频繁项集
	 * @param dataSet 数据集
	 */
	public void freqSetGen(ArrayList<ArrayList<String>> dataSet) {
		//得到符合最小支持度的C1
		ArrayList<FrequentSet> c1 = createCand1(dataSet);
		ArrayList<FrequentSet> l1 = scanData(c1,dataSet);
		candidates.addAll(l1);
		ArrayList<FrequentSet> ck = new ArrayList<FrequentSet>();
		ck.addAll(l1);
		int k = 2;
		while(ck.size() > 0) {
			//新生成ck+1
			ck = freqSetGen(ck, k++);
			ArrayList<FrequentSet> lk = scanData(ck,dataSet);
			candidates.addAll(lk);
		}
	}
	
	/**
	 * 生成长度为k+1的频繁项集
	 * @param inputCand 第k次
	 * @param lenLk k+1
	 * @return
	 */
	private ArrayList<FrequentSet> freqSetGen(ArrayList<FrequentSet> inputCand,int lenLk) {
		ArrayList<FrequentSet> retList = new ArrayList<FrequentSet>();
		for (int i = 0; i < inputCand.size(); i++) {
			List<String> tempL1 = new ArrayList<String>();
			List<String> tempL2 = new ArrayList<String>();
			for (int j = i+1; j < inputCand.size(); j++) {
				//如果不是L1
				if (lenLk > 2) {
					FrequentSet c1 = inputCand.get(i);
					FrequentSet c2 = inputCand.get(j);
					tempL1 = c1.getCand().subList(0, lenLk - 2);
					tempL2 = c2.getCand().subList(0, lenLk - 2);
					if(AlgorithmUtil.compare(tempL1, tempL2)) {
						//取交集
						ArrayList<String> temp = new ArrayList<String>();
						temp.addAll(c1.getCand());
						temp.addAll(c2.getCand().subList(lenLk - 2, c2.getCand().size()));
						FrequentSet cand = new FrequentSet();
						cand.setCand(temp);
						retList.add(cand);
					}
				}else {
					//如果为L1，则直接两两拼接
					ArrayList<String> L2 = new ArrayList<String>();
					L2.add(inputCand.get(i).getCand().get(0));
					L2.add(inputCand.get(j).getCand().get(0));
					FrequentSet cand2 = new FrequentSet();
					cand2.setCand(L2);
					retList.add(cand2);
				}
			}
		}
		return retList;
	}
	
	public void printCandidates() {
		for (int i = 0; i < candidates.size(); i++) {
					FrequentSet fs = candidates.get(i);
					System.out.print("{");
					for (int k = 0; k < fs.getCand().size(); k++) {
						System.out.print(fs.getCand().get(k)+",");
					}
					System.out.print("}");
					System.out.println();
		}
	}
	

	public ArrayList<FrequentSet> getCandidates() {
		return candidates;
	}

	
}
