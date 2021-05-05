/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 3: Visualize Your Algorithm
 * 05-01-2021
 */
package cs112project3;

import java.util.ArrayList;

public abstract class Predictor{
	public abstract ArrayList<DataPoint> readData(String filename);
	abstract String test(DataPoint data);
	abstract Double getAccuracy(ArrayList<DataPoint> data);
	abstract Double getPrecision(ArrayList<DataPoint> data);
}