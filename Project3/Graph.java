/* Yuxing Lu
 * Professor Sean Choi
 * CS112 Project Part 3: Visualize Your Algorithm
 * 05-01-2021
 */
package cs112project3;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Graph extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color red = new Color(255, 0, 0);
    private Color blue = new Color(0, 0, 255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255, 255, 0);


    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 9;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private ArrayList<DataPoint> data;

    // TODO: Add a private KNNPredictor variable
    KNNPredictor KNNP;
    
	/**
	 * Constructor method
	 */

    public Graph(int K, String fileName) {
/**    	
        // Generate random data point
        Random random = new Random();
        int maxDataPoints = 20;
        // Max value of f1 and f2 that is arbitrarily set
        int maxF1 = 8;
        int maxF2 = 300;

        // Generates random DataPoints
        for (int i = 0; i < maxDataPoints; i++) {
            double f1 = (random.nextDouble() * maxF1);
            double f2 = (random.nextDouble() * maxF2);
        	data.add(new DataPoint(f1, f2, "Random", "Random"));
        }
       this.data = data;   **/

        // TODO: Remove the above logic where random data is generated
        // TODO: instantiate the KNNPredictor variable
        // TODO: Run readData using input filename to split the data to test and training
        // TODO: Set this.data as the output of readData
        ArrayList<DataPoint> data = new ArrayList<>();
        KNNP = new KNNPredictor(K);
        data = KNNP.readData(fileName);
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {

        final int MESSAGE_X = 20;
        final int MESSAGE_Y = 30;

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);

        double truePositive = 0;
        double falsePositive = 0;
        double falseNegative = 0;
        double trueNegative = 0;

        //Create an ArrayList to store testing data
    	ArrayList<DataPoint> testData = new ArrayList<DataPoint>();
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF3() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF4()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value 
            // and compare against the true label.
            // Note that depending on how you implemented "test" method, you may need to 
            // modify KNNPredictor to store the output from readData.
            // You can also optimize further to compute accuracy and precision in a single
            // iteration.

            // blue represents "truePositive", cyan represents "falsePositive", yellow represents "falseNegative", and red represents "trueNegative"
            if (data.get(i).getIsTest()) {
            	testData.add(data.get(i));
                if((KNNP.test(data.get(i)).equals("1")) && (data.get(i).getLabel().equals("1"))) {
                    g2.setColor(blue);
                    truePositive++;
                }
                if((KNNP.test(data.get(i)).equals("1")) && (data.get(i).getLabel().equals("0"))) {
                    g2.setColor(cyan);
                    falsePositive++;
                }
                if((KNNP.test(data.get(i)).equals("0")) && (data.get(i).getLabel().equals("1"))) {
                   g2.setColor(yellow);
                    falseNegative++;
                }
                if((KNNP.test(data.get(i)).equals("0")) && (data.get(i).getLabel().equals("0"))) {
                    g2.setColor(red);
                    trueNegative++;
                }
                g2.fillOval(x, y, ovalW, ovalH);
            }

        }

        double accuracy = (truePositive + trueNegative)/(truePositive + trueNegative + falsePositive + falseNegative);
        double precision = truePositive/(truePositive + falseNegative);
        long accuracyR = Math.round(accuracy*100);
        long precisionR = Math.round(precision*100);
        g2.setColor(pointColor);
        g2.drawString("Accuracy: " + accuracyR + "%", MESSAGE_X, MESSAGE_Y);
        g2.drawString("Precision: " + precisionR + "%", MESSAGE_X + 100, MESSAGE_Y);
    }


    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF3());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF4());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF3());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF4());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(ArrayList<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui(int K, String fileName) {


	    /* Main panel */
        Graph mainPanel = new Graph(K, fileName);

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */
        JFrame frame = new JFrame("Yuxing Lu Project Part 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        /** Extra Credit (25%): Add an extra JPanel that displays the coordinates once you click on a point */
        JLabel label = new JLabel();  //Create a Label object
        JPanel panel = new JPanel();
        panel.add(label);
        frame.add(panel);   //Add the label to the window
        frame.addMouseListener(new MouseListener() {     //Add a mouse event listener to the window
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                if(e.getButton()==e.BUTTON1){
                    int x = e.getX();
                    int y = e.getY();
                    double k1 = 0.137931;
                    double b1 = -11.862069;
                    double k2 = -1.066667;
                    double b2 = 586.666667;
                    double x1 = k1 * x + b1;
                    double y1 = k2 * y + b2;
                    String xx = String.format("%.2f", x1);
                    String yy = String.format("%.2f", y1);
                    String s = "coordinate:(" + xx + "," + yy + ")";
                    label.setText(s);
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {
            	
            }
        });


    }
      
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
        int K = 7; // A value of K selected
        String fileName = "titanic.csv"; // TODO: Change this to titanic.csv
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(K,fileName);
            }
        });
    }
}
