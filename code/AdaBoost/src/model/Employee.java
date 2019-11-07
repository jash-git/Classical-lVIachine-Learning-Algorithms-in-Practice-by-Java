package model;

/**
 * Employee 实体类
 */
public class Employee extends Node {

	private int body;//身体
	private int bussiness;//业务
	private int talent;//潜力
	private int label;//标签
	private String[] features = new String[] { "body", "bussiness", "talent" };//特征键集

	public Employee() {
	}

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public int getBussiness() {
		return bussiness;
	}

	public void setBussiness(int bussiness) {
		this.bussiness = bussiness;
	}

	public int getTalent() {
		return talent;
	}

	public void setTalent(int talent) {
		this.talent = talent;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	@Override
	public String[] getFeatures() {
		return features;
	}

}