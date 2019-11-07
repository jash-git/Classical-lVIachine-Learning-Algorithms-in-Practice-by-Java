package algorithm;

import java.util.ArrayList;

import model.FrequentSet;
import model.Rule;

/**
 * 关联规则生成类
 */
public class RulesGen {
	//包含可信度的规则候选列表
	private ArrayList<Rule> bigRuleList;
	//k长度的频繁项集
	private ArrayList<FrequentSet> candidates;
	
	public RulesGen(ArrayList<FrequentSet> candidates) {
		this.candidates = candidates;
		bigRuleList = new ArrayList<Rule>();
	}
	
	public ArrayList<Rule> getBigRuleList() {
		return bigRuleList;
	}
	
	/**
	 * 生成关联规则
	 * @param dataSet 数据集
	 */
	public void generateRules(ArrayList<ArrayList<String>> dataSet) {
		//得到初始规则
		ArrayList<Rule> rules = new ArrayList<Rule>();
		for (int i = 0; i < candidates.size(); i++) {
			ArrayList<String> cand = candidates.get(i).getCand();
			Rule rule = convertToRule(cand, new ArrayList<String>());
			rules.add(rule);
		}
		//生成规则保存在bigList中
		for (int i = 0; i < rules.size(); i++) {
			if(rules.get(i).getLeft().size() >= 2) {
				rulesFormConseq(rules.get(i), dataSet); 
			}
		}
	}

	
	/**
	 *  根据rule递归生成新rule
	 *  并按照置信度过滤
	 * @param rule 传入规则
	 * @param dataSet 数据集
	 */
	private void rulesFormConseq(Rule rule, ArrayList<ArrayList<String>> dataSet) {
		ArrayList<String> left = new ArrayList<String>();
		left.addAll(rule.getLeft());
		//依次将左项原因项移到右项结果
		if (rule.getLeft().size() > 1) {
			for (int j = 0; j < left.size(); j++) {
				String temp = left.get(j);
				ArrayList<String> tempL = new ArrayList<String>();
				ArrayList<String> tempR = new ArrayList<String>();
				tempR.addAll(rule.getRight());
				//将temp加入右项结果
				tempR.add(temp);
				tempL.addAll(left);
				//将temp从左项移除
				tempL.remove(temp);
				Rule rule1 = new Rule();
				rule1 = convertToRule(tempL,tempR);
				rule1.calcConf(dataSet);
				if (rule1.getConf() > Configuration.MIN_CONF)
					bigRuleList.add(rule1);
				rulesFormConseq(rule1, dataSet);
			}
		}
		
	}
	
	/**
	 * 将tempL和tempR封装成Rule对象
	 * @param tempL 生成规则原因
	 * @param tempR 生成规则结果
	 * @return
	 */
	private Rule convertToRule(ArrayList<String> tempL, ArrayList<String> tempR) {
		ArrayList<String> cand = new ArrayList<String>();
		Rule rule = new Rule();
		rule.setLeft(tempL);
		rule.setRight(tempR);
		cand.addAll(tempL);
		cand.addAll(tempR);
		rule.setCand(cand);
		return rule;
	}
	
	public void print() {
		for (int i = 0; i < bigRuleList.size(); i++) {
			Rule rule = bigRuleList.get(i);
			for (int j = 0; j < rule.getLeft().size(); j++) {
				System.out.print(rule.getLeft().get(j));
			}
			System.out.print("-->");
			for (int j = 0; j < rule.getRight().size(); j++) {
				System.out.print(rule.getRight().get(j));
			}
			System.out.println();
		}
	}
	
}
