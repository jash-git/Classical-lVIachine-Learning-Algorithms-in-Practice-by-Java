package model;

/** 
 * 待估参数类
 */
public class Parameters {

	public double μ;//高斯分布参数：期望
	public double Σ;//高斯分布参数：方差
	public double ψ;//多项分布参数

	public void setμΣψ(double μ, double Σ, double ψ) {
		this.μ = μ;
		this.Σ = Σ;
		this.ψ = ψ;
	}
	public double getμ() {
		return μ;
	}
	public double getΣ() {
		return Σ;
	}
	public double getΨ() {
		return ψ;
	}
	public void setμ(double μ) {
		this.μ = μ;
	}
	public void setΣ(double Σ) {
		this.Σ = Σ;
	}
	public void setΨ(double ψ) {
		this.ψ = ψ;
	}
}
