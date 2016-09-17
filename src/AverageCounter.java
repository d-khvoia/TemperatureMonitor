import java.io.*;
import java.nio.charset.Charset;

public class AverageCounter {
	
    private BufferedReader br;
    private float[] results;
    private String filePath;
	
    public AverageCounter(String filePath) {
    	this.filePath = filePath;
    } 
    
    private void getAverages() {
	String line;
	int count = 0;
	results = new float[9];
        try {
	   while ((line = br.readLine()) != null) {
	       if (++count % 2 == 0) continue;
	       int hours = Integer.parseInt(line.substring(11, 13));
	       float temp = Float.parseFloat(line.substring(22));
               if (isBetween(hours, 0, 6)) {
            	   results[7]++;
            	   results[3] += temp;
               }
               else if (isBetween(hours, 7, 12)) {
            	   results[4]++;
            	   results[0] += temp;
               }
               else if (isBetween(hours, 13, 15)) {
            	   results[5]++;
            	   results[1] += temp;
               }
               else if (isBetween(hours, 16, 23)) {
            	   results[6]++;
            	   results[2] += temp;
               }
           }
		   results[8] = (results[0] + results[1] + results[2] + results[3]) / (results[4] + results[5] + results[6] + results[7]);
        }
	catch (Exception e) {
	    System.out.println("Error while reading from file. Terminating...");
	}
    }
    private boolean isBetween(int num, int lowBound, int upBound) {
	if (num >= lowBound && num <= upBound) 
            return true;
	return false;
    }
    public void showAverageTemp() {
	try {
	   InputStream fis = new FileInputStream(filePath);
	   InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
	   br = new BufferedReader(isr);
           getAverages();
	   if (results[4] != 0)
	       System.out.println("Average morning (7 a.m. - 12 p.m.) temperature: " + results[0] / results[4]);
	   if (results[5] != 0)
	       System.out.println("Average afternoon (13 p.m. - 15 p.m.) temperature: " + results[1] / results[5]);
	   if (results[6] != 0)
	       System.out.println("Average evening (16 p.m. - 23 p.m.) temperature: " + results[2] / results[6]);
	   if (results[7] != 0)
	       System.out.println("Average night (0 a.m. - 6 a.m.) temperature: " + results[3] / results[7]);
	   System.out.println("Total average temperature: " + results[8]);
	}
	catch (Exception e) {
	    System.out.println("Error while reading from file.");
	}
    }
}
