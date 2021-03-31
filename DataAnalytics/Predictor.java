/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 1: Project Setup
 * 03-20-2021
 */
package cs112project1;

import java.util.ArrayList;

public abstract class Predictor{
	public abstract ArrayList<DataPoint> readData(String filename);
	abstract String test(DataPoint data);
	abstract Double getAccuracy(ArrayList<DataPoint> data);
	abstract Double getPrecision(ArrayList<DataPoint> data);
}