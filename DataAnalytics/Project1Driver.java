/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 1: Project Setup
 * 03-20-2021
 */
package cs112project1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Project1Driver {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            JFrame frame = new myFrame();
            frame.setTitle("YuxingLuCs112Project1");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
	
	public String[] dataArrangement(){
		//Create an Array to transfer what we want to print in the JFrame
		String result[] = new String[10];
		//Instantiate a DummyPredictor class
		//read the random data
		DummyPredictor DPre = new DummyPredictor();
		ArrayList<DataPoint> dataList = DPre.readData("test.txt");
		//and run test with a set of test data
		DataPoint dataEx = new DataPoint(2.0, 78.1, true);
		String testResult = DPre.test(dataEx);
		result[0] = "The test data point ("+ dataEx.getF1()+" "+dataEx.getF2()+" "+dataEx.getIsTest()+") is closest to "+ testResult +" in training data.";
				
		//run getPrecision and getAccuracy 
		double accuracy = DPre.getAccuracy(dataList);
		result[1] = "The value of getAccuracy is: " + Double.toString(accuracy);
		double precision = DPre.getPrecision(dataList);
		result[2] = "The value of getPrecision is: " + Double.toString(precision);
		
		//print out the average difference values between f1 and f2 in the training data for Apple, Banana and Pear
		result[3] = "The average difference values for f1 and f2 in training data:";
		double averageArray[] = DPre.averageInTrain();
		result[4] = "Apple: " + averageArray[0];
		result[5] = "Banana: " + averageArray[1];
		result[6] = "Pear: " + averageArray[2] ;
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
    	Project1Driver result = new Project1Driver();
    	String[]printResult = result.dataArrangement();
    	for (int i=0; i<=6; i++) {
    		g.drawString(printResult[i], MESSAGE_X, MESSAGE_Y*(i+1));	
    	}
    }
    public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }
}


