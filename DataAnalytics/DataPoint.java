/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 1: Project Setup
 * 03-20-2021
 */
package cs112project1;

public class DataPoint{
	private double f1;
	private double f2;
	private String label;
	private boolean isTest;
	
	//no-arg constructor
	public DataPoint() {
		this.f1 = 0;
		this.f2 = 0;
		this.label = "";
		this.isTest = false;
	}
	
	//4 arg constructors 
	public DataPoint(double n1Param, double n2Param) {
		this.f1 = n1Param;
		this.f2 =n2Param;
	}
	public DataPoint(double n1Param, double n2Param, String lParam) {
		this.f1 = n1Param;
		this.f2 =n2Param;
		this.label = lParam;
	}
	public DataPoint(double n1Param, double n2Param, String lParam, boolean iParam) {
		this.f1 = n1Param;
		this.f2 =n2Param;
		this.label = lParam;
		this.isTest = iParam;
	}
	public DataPoint(double n1Param, double n2Param, boolean iParam) {
		this.f1 = n1Param;
		this.f2 =n2Param;
		this.isTest = iParam;
	}

	//Accessor
	public double getF1() {
		return this.f1;
	}
	public double getF2() {
		return this.f2;
	}
	public String getLabel() {
		return this.label;
	}
	public boolean getIsTest() {
		return this.isTest;
	}

	//Mutator
	public void setF1(double n1Param) {
		this.f1 = n1Param;
	}
	public void setF2(double n2Param) {
		this.f1 = n2Param;
	}
	public void setLabel(String lParam) {
		this.label = lParam;
	}
	public void setIsTest(boolean iParam) {
		this.isTest = iParam;
	}
	
}