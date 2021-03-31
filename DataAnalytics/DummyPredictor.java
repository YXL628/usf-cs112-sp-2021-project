/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 1: Project Setup
 * 03-20-2021
 */
package cs112project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DummyPredictor extends Predictor{
	//read DataPoint in test.txt file and collect them in arrayList
	public ArrayList<DataPoint> readData(String filename){
		ArrayList<DataPoint> dataList = new ArrayList<DataPoint>();
		try {
			Scanner sc = new Scanner(System.in);
			sc = new Scanner(new File(filename));
			double f1 = 0;
			double f2 = 0;
			String label = "";
			boolean booleanTest = false;
			//read all the data until the line end
			while (sc.hasNextLine()) {
				if (sc.hasNextDouble()) {
					//assign values in .txt documents to f1, f2, label, isTest
					f1 = sc.nextDouble();
					f2 = sc.nextDouble();
					label = sc.next();
					//if the isTest is true, this DataPoint do not have label
					if (label.equals("true")) {
						booleanTest = true;
						//call arg constructor(double n1Param, double n2Param, boolean iParam) in DataPoint class
						DataPoint dataP = new DataPoint(f1, f2, booleanTest);
						dataList.add(dataP);
					}
					//call arg constructor(double n1Param, double n2Param, String lParam, boolean iParam) in DataPoint class
					else {
						booleanTest = sc.nextBoolean();
						DataPoint dataP = new DataPoint(f1, f2, label, booleanTest);
						dataList.add(dataP);
					}	
				}
			}
	 	} catch (FileNotFoundException ex) {
				System.out.println("File Not Found");
			}
		//return the array list that collect all DataPoint in .txt
		return dataList;
	}


	//Compute the average difference between f1 and f2 in the training data for each class
	//there are 3 labels in the training data: Apple, Banana, Pear 
	public double[] averageInTrain() {
		//get the data in array list
		DummyPredictor DPre = new DummyPredictor();
		ArrayList<DataPoint> dataList = DPre.readData("test.txt");
		
		//sum for all(f2-f1) and count is how many times it calculate 
		double sumApple = 0;
		double sumBanana = 0;
		double sumPear = 0;
		int countApple = 0;
		int countBanana = 0;
		int countPear = 0;
		for (int i=0; i<dataList.size(); i++) {
			//check if the DataPoint is training data point:
			if (dataList.get(i).getIsTest() == false) {
				//if that data list's label is Apple
				if ((dataList.get(i).getLabel()).equals("Apple")) {
					//compute the average difference between f1 and f2 for Apple
					//sum for all(f2-f1) and count is how many times it calculate 
					sumApple += Math.abs(dataList.get(i).getF2() - dataList.get(i).getF1());
					countApple++;
				}	
				//if that data list's label is Banana 
				if ((dataList.get(i).getLabel()).equals("Banana")) {
					//compute the average difference between f1 and f2 for Banana 
					sumBanana += Math.abs(dataList.get(i).getF2() - dataList.get(i).getF1());
					countBanana++;
				}	
				//if that data list's label is Pear  
				if ((dataList.get(i).getLabel()).equals("Pear")) {
					//compute the average difference between f1 and f2 for Pear  
					sumPear += Math.abs(dataList.get(i).getF2() - dataList.get(i).getF1());
					countPear++;
				}
			}
		}
		//Apple, Banana, pear average difference between f1 and f2
		double[] resultArray = new double[3];
		resultArray[0] = sumApple/countApple;
		resultArray[1] = sumBanana/countBanana;
		resultArray[2] = sumPear/countPear;
		return resultArray;
	}

	//compute the difference and check which class the data point is closest to
	public String test(DataPoint data) {
		//Compute the average difference between f1 and f2 in the test DataPoint
		double diffData = Math.abs(data.getF1() - data.getF2());
		//get the average difference values between f1 and f2 in the training data for Apple, Banana, pear
		DummyPredictor avaData = new DummyPredictor();
		double avaDataArray[] = avaData.averageInTrain();
		double appleAve = avaDataArray[0];
		double bananaAve = avaDataArray[1];
		double pearAve = avaDataArray[2];
		
		//calculate the difference between the diffData and the average difference values between f1 and f2 in the training data
		double DiffToApp = Math.abs(diffData - appleAve);
		double DiffToBana = Math.abs(diffData - bananaAve);
		double DiffToPear = Math.abs(diffData - pearAve);
		//check which label that DataPoint is closest to;
		double ClosLabel = Math.min(Math.min(DiffToApp, DiffToBana), DiffToPear);
		//find return the closest result
		String ClosLabelS = "";
		//if the dataPoint is closest to Apple
		if (ClosLabel == DiffToApp) {
			ClosLabelS = "Apple";
		}
		else if	(ClosLabel == DiffToBana) {
			ClosLabelS = "Banana";
		}
		else {
			ClosLabelS = "Pear";
		}
		//check two or three of these three values can be equaled
		if (ClosLabel == DiffToApp && ClosLabel == DiffToBana && ClosLabel < DiffToPear) {
			ClosLabelS = "Apple and Banana";
		}
		if (ClosLabel == DiffToApp && ClosLabel == DiffToPear && ClosLabel < DiffToBana) {
			ClosLabelS = "Apple and Pear";
		}
		if (ClosLabel == DiffToBana && ClosLabel == DiffToPear && ClosLabel < DiffToApp) {
			ClosLabelS = "Banana and Pear";
		}
		if (ClosLabel == DiffToApp && ClosLabel == DiffToBana && ClosLabel == DiffToPear) {
			ClosLabelS = "Apple, Banana and Pear";
		}
		//return the closest result
		return ClosLabelS;
	}
	
	//getAccuracy and getPrecision can return random values for now
	public Double getPrecision(ArrayList<DataPoint> data) {
		return 2.9;
	}
	public Double getAccuracy(ArrayList<DataPoint> data) {
		return 3.1;
	}
	
}