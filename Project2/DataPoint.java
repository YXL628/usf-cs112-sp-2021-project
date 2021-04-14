/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 2: Implementing KNN Algorithm
 * 04-02-2021
 */
package cs112project2;

public class DataPoint{
	private double f1;
	private double f2;
	private double f3;
	private double f4;
	private String label;
	private boolean isTest;
	
	//no-arg constructor
	public DataPoint() {
		this.f1 = 0;
		this.f2 = 0;
		this.f3 = 0;
		this.f4 = 0;
		this.label = "";
		this.isTest = false;
	}
	
	//4 arg constructors 
	public DataPoint(double n1Param, double n2Param, double n3Param, double n4Param) {
		this.f1 = n1Param;
		this.f2 = n2Param;
		this.f3 = n3Param;
		this.f4 = n4Param;
	}
	public DataPoint(double n1Param, double n2Param, double n3Param, double n4Param, String lParam) {
		this.f1 = n1Param;
		this.f2 =n2Param;
		this.f3 = n3Param;
		this.f4 = n4Param;
		this.label = lParam;
	}
	public DataPoint(double n1Param, double n2Param, double n3Param, double n4Param, String lParam, boolean iParam) {
		this.f1 = n1Param;
		this.f2 =n2Param;
		this.f3 = n3Param;
		this.f4 = n4Param;
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
	public double getF3() {
		return this.f3;
	}
	public double getF4() {
		return this.f4;
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
	public void setF3(double n3Param) {
		this.f3 = n3Param;
	}
	public void setF4(double n4Param) {
		this.f4 = n4Param;
	}
	public void setLabel(String lParam) {
		this.label = lParam;
	}
	public void setIsTest(boolean iParam) {
		this.isTest = iParam;
	}
}