/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 2: Implementing KNN Algorithm
 * 04-02-2021
 */
package cs112project2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Project2Driver {
	public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            JFrame frame = new myFrame();
            frame.setTitle("YuxingLuCs112Project2");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
	
	public String[] dataArrangement(){
		//Create an Array to transfer what we want to print in the JFrame
		String result[] = new String[10];
    	/**Extra Credit (5%): Enable user input for the value of K.
    	   the program should use the input if it is valid, if not it should print an error. **/
    	int K = 0;
    	do {
    		System.out.println("Enter a number for K(odd number between 1 and 90): ");
    		Scanner scanner = new Scanner(System.in);
    		if (scanner.hasNextInt()) {
    			K = scanner.nextInt();
    		}
    		if (K<1 || K>90 || (K%2 == 0)) {
    			System.out.println("Invalid input, try again.");
    		}
    	}while (K<1 || K>90 || (K%2 == 0));
    
		
    	/**b. Read titanic.csv into an ArrayList of DataPoints as using readData. **/
    	ArrayList<DataPoint> data = new ArrayList<DataPoint>();
    	KNNPredictor KNNP = new KNNPredictor(K);
    	data = KNNP.readData("titanic.csv");
    	//Create an ArrayList to store testing data
    	ArrayList<DataPoint> testData = new ArrayList<DataPoint>();
    	for(int i=0; i<data.size(); i++) {
    		//Check if the dataPoint is a test data
    		if(data.get(i).getIsTest() == true) {
    			//Run test method
    			KNNP.test(data.get(i));
    			testData.add(data.get(i));
    		}
    	}
    	double accuracy = 0.0;
    	double precision = 0.0;
    	/**c. Compute the precision and accuracy using the ArrayList of DataPoints. **/
    	accuracy = KNNP.getAccuracy(testData);
    	precision = KNNP.getPrecision(testData);
    	long accuracyR = Math.round(accuracy*100);
    	long precisionR = Math.round(precision*100);
    	
    	result[0] = "Accuracy: " + accuracyR + "%";
    	result[1] = "Precision: " + precisionR + "%";
    	//return the information that will be printed by JFrame
    	return result;
    	
    }
    
}

class myFrame extends JFrame{
    public myFrame(){
        add(new myFrameComponent());
        pack();
    }
}

class myFrameComponent extends JComponent{
    public static final int MESSAGE_X = 20;
    public static final int MESSAGE_Y = 30;
    private static final int DEFAULT_WIDTH = 550;
    private static final int DEFAULT_HEIGHT = 340;
    public void paintComponent(Graphics g){
    	Project2Driver result = new Project2Driver();
    	String[]printResult = result.dataArrangement();
    	/**d. Display the precision and accuracy in a JFrame. **/
    	for (int i=0; i<2; i++) {
    		g.drawString(printResult[i], MESSAGE_X, MESSAGE_Y*(i+1));	
    	}
    }
    public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }
}