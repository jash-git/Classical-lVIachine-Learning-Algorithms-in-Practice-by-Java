package model;

/**
 * Wine 实体类
 */
public class Wine extends Node {

	private double alcohol;
	private double malicAcid;
	private double ash;
	private double alcalinityOfAsh;
	private double magnesium;
	private double totalPhenols;
	private double flavanoids;
	private double nonFlavanoidPhenols;
	private double proanthocyanins;
	private double colorIntensity;
	private double hue;
	private double dilutedWines;
	private double proline;
	private int label;
	private String[] features = new String[] { "alcohol", "malicAcid", "ash", "alcalinityOfAsh", "magnesium",
			"totalPhenols", "flavanoids", "nonFlavanoidPhenols", "proanthocyanins", "colorIntensity", "hue",
			"dilutedWines", "proline" };

	public Wine() {
	}

	public double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(double alcohol) {
		this.alcohol = alcohol;
	}

	public double getMalicAcid() {
		return malicAcid;
	}

	public void setMalicAcid(double malicAcid) {
		this.malicAcid = malicAcid;
	}

	public double getAsh() {
		return ash;
	}

	public void setAsh(double ash) {
		this.ash = ash;
	}

	public double getAlcalinityOfAsh() {
		return alcalinityOfAsh;
	}

	public void setAlcalinityOfAsh(double alcalinityOfAsh) {
		this.alcalinityOfAsh = alcalinityOfAsh;
	}

	public double getMagnesium() {
		return magnesium;
	}

	public void setMagnesium(double magnesium) {
		this.magnesium = magnesium;
	}

	public double getTotalPhenols() {
		return totalPhenols;
	}

	public void setTotalPhenols(double totalPhenols) {
		this.totalPhenols = totalPhenols;
	}

	public double getFlavanoids() {
		return flavanoids;
	}

	public void setFlavanoids(double flavanoids) {
		this.flavanoids = flavanoids;
	}

	public double getNonFlavanoidPhenols() {
		return nonFlavanoidPhenols;
	}

	public void setNonFlavanoidPhenols(double nonFlavanoidPhenols) {
		this.nonFlavanoidPhenols = nonFlavanoidPhenols;
	}

	public double getProanthocyanins() {
		return proanthocyanins;
	}

	public void setProanthocyanins(double proanthocyanins) {
		this.proanthocyanins = proanthocyanins;
	}

	public double getColorIntensity() {
		return colorIntensity;
	}

	public void setColorIntensity(double colorIntensity) {
		this.colorIntensity = colorIntensity;
	}

	public double getHue() {
		return hue;
	}

	public void setHue(double hue) {
		this.hue = hue;
	}

	public double getDilutedWines() {
		return dilutedWines;
	}

	public void setDilutedWines(double dilutedWines) {
		this.dilutedWines = dilutedWines;
	}

	public double getProline() {
		return proline;
	}

	public void setProline(double proline) {
		this.proline = proline;
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