import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TemperatureMonitor {

	public static void main(String[] args) throws Exception {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    try {
               long waitTime = Integer.parseInt(args[0]) * 60000;
               long timeShowRate = Integer.parseInt(args[1]) * 1000;
    	       System.out.println("Put in your temperature meter and wait till beep... ");
   	       Timer timer = new Timer(waitTime, timeShowRate);
   	       timer.start();
	       Thread.sleep(waitTime);
	       timer.interrupt();
	       BeepNotificator beepNotificator = new BeepNotificator();
	       beepNotificator.start();
	       String filePath = System.getProperty("user.home") + "/Desktop\\Temperature.txt";
               recordTemperature(filePath);	    
	       beepNotificator.interrupt();
	       suggestStatistics(filePath);
	    }
	    catch (RuntimeException e) {
		System.out.println("Illegal arguments passed. Arguments represent wait time and show time rate and must be two integer numbers! Terminating...");
	    }
	}
	private static boolean isValidTemperature(float temp) {
	    if (temp >= 30.0 && temp <= 45.0)
		return true;
	    return false;
	}
	private static void recordTemperature(String filePath) {
	    @SuppressWarnings("resource")
	    Scanner sc = new Scanner(System.in);
	    float temperature;
	    while(true) {
	    	try {
	    	   if (isValidTemperature(temperature = Float.parseFloat(sc.nextLine()))) {
	    	       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	       File f = new File(filePath);
	    	       f.createNewFile();
	    	       Files.write(Paths.get(filePath), (dateFormat.format(new Date()) + " — " + temperature + "\r\n===========================================\r\n").getBytes(), StandardOpenOption.APPEND);
	    	       break;
	           }
	    	   throw new Exception();
	        }
	    	catch (Exception e) {
	    	    System.out.println("Wrong temperature format!!! Enter a floating-point number.");
	    	    continue;
	    	}
	    }
	}
	private static float[] getAverages(BufferedReader br) {
	    String line;
	    int count = 0;
	    float[] results = new float[9];
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
	    return results;
	}
	private static boolean isBetween(int num, int lowBound, int upBound) {
	    if (num >= lowBound && num <= upBound) 
		return true;
	    return false;
	}
	private static void suggestStatistics(String filePath) {
	    @SuppressWarnings("resource")
	    Scanner sc = new Scanner(System.in);
	    System.out.print("Do you want to see current temperature statistics? Y/N: ");
	    String s;
	    while(true) {
	        s = sc.nextLine();
	        if (s.equals("Y")) {
	            showAverageTemp(filePath);
	            break;
		}
		else if (s.equals("N")) {
		    System.out.println("Terminating...");
		    System.exit(0);
	        }
		else System.out.print("Wrong input. Please, enter either \"Y\" or \"N\" character: ");
	    }
	}
	private static void showAverageTemp(String filePath) {
	    try {
	      InputStream fis = new FileInputStream(filePath);
	      InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
	      BufferedReader br = new BufferedReader(isr);
              float[] values = getAverages(br);
	      if (values[4] != 0)
		  System.out.println("Average morning (7 a.m. - 12 p.m.) temperature: " + values[0] / values[4]);
	      if (values[5] != 0)
		  System.out.println("Average afternoon (13 p.m. - 15 p.m.) temperature: " + values[1] / values[5]);
	      if (values[6] != 0)
		  System.out.println("Average evening (16 p.m. - 23 p.m.) temperature: " + values[2] / values[6]);
	      if (values[7] != 0)
		  System.out.println("Average night (0 a.m. - 6 a.m.) temperature: " + values[3] / values[7]);
	      System.out.println("Total average temperature: " + values[8] + "\nTerminating...");
	    }
	    catch (Exception e) {
		System.out.println("Error while reading from file.");
		}
	}
}
