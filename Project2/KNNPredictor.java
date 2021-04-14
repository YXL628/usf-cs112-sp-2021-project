/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 2: Implementing KNN Algorithm
 * 04-02-2021
 */
package cs112project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;


public class KNNPredictor extends Predictor{
	
	//K as a private int for constructor
	private int K;
	//Add two more instance variable that stores the number of passengers 
	//in each type of label and set them as zero
	private int survivedN = 0;
	private int diedN = 0;
	//Add a new constructor that takes in a value for int K
	public KNNPredictor(int kParam) {
		this.K = kParam;
	}
	
	// Helper function to split the line by commas and
	// return the values as a List of String
	private List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}
	//Create an array list for readData method to return
	ArrayList<DataPoint> dataList = new ArrayList<DataPoint>();
	/**c. i. Read through the file and create a DataPoint for each line **/
	public ArrayList<DataPoint> readData(String filename){
		//f1:pclass, f2:sex, f3:age f4:fare
		double f1 = 0;
		double f2 = 0;
		double f3 = 0;
		double f4 = 0;
		String label = "";
		boolean isTest = false;
		try (Scanner scanner = new Scanner(new File(filename));) {
			//Skip the first line, because they are titles that can't be read
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				List<String> records = getRecordFromLine(scanner.nextLine());
				// TODO: Select the columns from the records and create a DataPoint object
				//Create iterator to read each columns of data 
				//Store the pclass and survival that in the first and second column
				Iterator<String> iter = records.iterator();
				String dataA = iter.next();
				f1 = Double.parseDouble(dataA); 
				dataA = iter.next();
				label = dataA;
				//Read the data in the "sex" column and store it as f2, 0 for female, 1 for male
				dataA = iter.next();
				dataA = iter.next();
				dataA = iter.next();
				if (dataA.equals("female")) {
					f2 = 0;
				}
				if (dataA.equals("male")) {
					f2 = 1;
				}
				//Read the data in the "age" column and store it as f3
				dataA = iter.next();
				//If there is no value for age, set f3 as -1 and this data will not be used
				if (dataA.equals("")) {
					f3 = -1;
				} else {
					f3 = Double.valueOf(dataA);
				}
				//Read the data in the "fare" column and store it as f4
				//If there is no value for fare, set f4 as -1 and this data will not be used
				if (iter.hasNext()) {
					dataA = iter.next();
					f4 = Double.valueOf(dataA);
				} else {
					f4= -1;
				}
				
				/**ii. Set the DataPoint as ¡°train¡± type (i.e. set isTest as false) 90% of the time \
				and ¡°test¡± type the other 10% of the time **/
				Random rand = new Random();
				double randNum = rand.nextDouble();
				// 90% of the data is reserved for training
				if (randNum < 0.9) {
					// Set the type of DataPoint as ¡°train¡± and put into the Collection
					isTest = false;
				} else {
					// Set the type of DataPoint as ¡°test¡± and put into the Collection
					isTest = true;
				}
				
				/**iii. Count the number of each label (¡°survived¡± = 0 || 1) and store the count of
				   each label into the instance variable for train type only. **/
				//Incomplete data won't be used(f3 = -1 or f4 = -1)
				if((isTest==false) && (f3 != -1) && (f4 != -1)) {
					if(label.equals("1")) {
						survivedN++;
					}
					else if(label.equals("0")) {
						diedN++;
					}
				}
				// TODO: Store the DataPoint object in a collection
				/**iv. Add to an arraylist of DataPoints **/
				//Incomplete data won't be stored(f3 = -1 or f4 = -1) 
				if((f3 != -1.0) && (f4 != -1.0)) {
					DataPoint dataP = new DataPoint(f1, f2, f3, f4, label, isTest);
					dataList.add(dataP);
				}
			}	
				
			/**v. Print the number of training DataPoint for each type of label **/
			System.out.println("Number of Survival in training DataPoint: " + survivedN);
			System.out.println("Number of Dead in training DataPoint: " + diedN);

				
				} catch (FileNotFoundException ex) {
					System.out.println("File Not Found");
				}
			/**vi. Return the arraylist after all lines have been read **/
			return dataList;
		}
	/**Extra Credit (15%): Extend DataPoint class to 4-dimensions **/
	/**d. Implement a helper method within KNNPredictor with the below signature that returns
	   the between two points using the distance formula: sqrt( (x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2 + (k1-k2)^2) **/
	private double getDistance(DataPoint p1, DataPoint p2) {
		return Math.sqrt(Math.pow(p1.getF1() - p2.getF1(),2) + Math.pow(p1.getF2() - p2.getF2(),2) + Math.pow(p1.getF3() - p2.getF3(),2) + Math.pow(p1.getF4() - p2.getF4(),2));
	}
	
	//Create an array to obtain result of test method
	ArrayList<String> testResult  = new ArrayList<String>();
	public String test(DataPoint data) {
		//Frequency of 0 and 1
		int n0 = 0;
		int n1 = 0;
		/**e. i. Retrieve the DataPoint from the input and check if it has the type == ¡°test¡± **/
		if (data.getIsTest() == true) {
			/** ii. Compute the distance between the input point versus every train point in the
				data set and store the distance and the label into a 2D Array, {distance, label}. **/
			//Get the train data list
			ArrayList<DataPoint> trainDataList = new ArrayList<DataPoint>();
			//Number of training data
			int trainN = 0;
			for (int i=0; i<dataList.size(); i++) {
				//If a data in the dataList is a training data
				if (dataList.get(i).getIsTest() == false) {
					trainDataList.add(dataList.get(i));
					//Number of training data plus 1 
					trainN++;
				}
			}
			Double[][] arr = new Double[trainN][2];   
			for (int i=0; i<trainN; i++) {
				double pointDis = getDistance(data, trainDataList.get(i));
				//use the label in the double format 1.0 for survival an 0.0 for dead
				double labelN = 0.0;
				if (trainDataList.get(i).getLabel().equals("1")) {
					labelN = 1.0;
				}
					arr[i][0] = pointDis;
					arr[i][1] = labelN;	
			}			
				/**iii. Sort the 2D array storing the distance and the label **/
				java.util.Arrays.sort(arr, new java.util.Comparator<Double[]>() {
					public int compare(Double[] a, Double [] b) {
					return a[0].compareTo(b[0]);
					}
				});
				/**iv. Find the labels of the first ¡°K¡± elements in the 2D array and return the label that 
				   occurs the most in String format (¡°1¡± or ¡°0¡±) **/
				for (int i=0; i<K; i++) {
					if (arr[i][1] == 0) {
						n0++;
					}else {
						n1++;
					}
				}
		}
		//K must be an odd number to break ties, so n0 should not be equal to n1
		if (n0 < n1) {
			testResult.add("1");
			return "1";
		}else {
			testResult.add("0");
			return "0";
		}
	}

	/** f. Implement getAccuracy and getPrecision methods as follows. **/
	double truePositive = 0;
	double falsePositive = 0;
	double falseNegative = 0;
	double trueNegative = 0;
	public Double getAccuracy(ArrayList<DataPoint> data) {
		for(int i=0; i<testResult.size(); i++) {
			/**ii. If the label from the test method returns ¡°1¡± and the label of the DataPoint is ¡°1¡± add 1 to 
		  	   a truePositive variable **/
			if((testResult.get(i).equals("1")) && (data.get(i).getLabel().equals("1"))) {
				truePositive++;
			}
			/**iii. If the label from the test method returns ¡°1¡± and the label of the DataPoint is
			   ¡°0¡± add 1 to a falsePositive variable **/
			if((testResult.get(i).equals("1")) && (data.get(i).getLabel().equals("0"))) {
				falsePositive++;
			}
			/**iv. If the label from the test method returns ¡°0¡± and the label of the DataPoint is
			  ¡°1¡± add 1 to a falseNegative variable **/
			if((testResult.get(i).equals("0")) && (data.get(i).getLabel().equals("1"))) {
				falseNegative++;
			}
			/**v. If the label from the test method returns ¡°0¡± and the label of the DataPoint is
			  ¡°0¡± add 1 to a trueNegative variable **/
			if((testResult.get(i).equals("0")) && (data.get(i).getLabel().equals("0"))) {
				trueNegative++;
			}
		}
		/**vi. For getAccuracy return (truePositive + trueNegative) / (truePositive +
		   trueNegative + falsePositive + falseNegative) **/
		return (truePositive + trueNegative)/(truePositive + trueNegative + falsePositive + falseNegative);
	}
	
	public Double getPrecision(ArrayList<DataPoint> data) {
		/**vii. For getPrecision return truePositive / (truePositive + falseNegative) **/
		return truePositive/(truePositive + falseNegative);
	}
}
