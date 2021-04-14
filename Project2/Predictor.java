/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 2: Implementing KNN Algorithm
 * 04-02-2021
 */
package cs112project2;

import java.util.ArrayList;

public abstract class Predictor{
	public abstract ArrayList<DataPoint> readData(String filename);
	abstract String test(DataPoint data);
	abstract Double getAccuracy(ArrayList<DataPoint> data);
	abstract Double getPrecision(ArrayList<DataPoint> data);
}