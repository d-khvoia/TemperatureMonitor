import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class TemperatureMonitor {

	public static void main(String[] args) throws Exception {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    args = prepareArgs(args);
	    try {
               long waitTime = Integer.parseInt(args[0]) * 60000;
               long timeShowRate = Integer.parseInt(args[1]) * 1000;
               String filePath = inputFilePath();
    	       recordTemperature(filePath, waitTime, timeShowRate);
	       suggestStatistics(filePath);
	       terminate();
	    }
	    catch (RuntimeException e) {
		System.out.println("Illegal arguments passed. Arguments represent wait time and show time rate and must be two integer numbers! Terminating...");
	    }
	}
	private static void recordTemperature(String filePath, long waitTime, long timeShowRate)  throws InterruptedException {
  	    System.out.println("Put in your temperature meter and wait till beep... ");
	    Timer timer = new Timer(waitTime, timeShowRate);
	    timer.start();
	    Thread.sleep(waitTime);
	    timer.interrupt();
	    Notificator n = new Notificator();
	    n.start();
            new Serializer(filePath).inputTemperature();
            n.interrupt();
	}
	private static String[] prepareArgs(String[] args) {
	    if (args.length == 0) {
		System.out.println("No arguments passed. Default values of 10 minutes and 30 seconds will be used.");
		args = new String[2];
		args[0] = "10";
		args[1] = "30";
	    }
	    else if (args.length == 1) {
		System.out.println("Only one argument passed. Default value of time show rate, 30 seconds, will be used.");
		String temp = args[0];
		args = new String[2];
		args[0] = temp;
		args[1] = "30";
	    }
	    return args;
	}
	private static String inputFilePath() {
	    String filePath = System.getProperty("user.home") + "/Desktop\\Temperature.tmp";
	    @SuppressWarnings("resource")
	    Scanner sc = new Scanner(System.in);
	    System.out.print("Default file path is the path to the desktop of your user. Do you want to change it? Y/N: ");
	    String s;
	    while(true) {
	        s = sc.nextLine();
		if (s.equals("Y") || s.equals("y")) {
		    while(true) {
		        System.out.print("Enter new file path: ");
		        try {
		           filePath = sc.nextLine();
		           File f = new File(filePath);
		           f.createNewFile();
		           return filePath;
		        }
		        catch (IOException e){
		            System.out.print("Invalid file path.\nPlease, enter correct file path: ");
		        }
		    }
		}
		else if (s.equals("N") || s.equals("n")) {
		    return filePath;
		}
		else System.out.print("Wrong input. Please, enter either \"Y\" or \"N\" character: ");
	    }
	}
	private static void suggestStatistics(String filePath) {
	    @SuppressWarnings("resource")
	    Scanner sc = new Scanner(System.in);
	    System.out.print("Do you want to see current temperature statistics? Y/N: ");
	    String s;
	    while(true) {
	        s = sc.nextLine();
	        if (s.equals("Y") || s.equals("y")) {
	            new AverageCounter(filePath).showAverageTemp();
	            break;
		}
		else if (s.equals("N") || s.equals("n")) {
		    System.out.println("Terminating...");
		    System.exit(0);
	        }
		else System.out.print("Wrong input. Please, enter either \"Y\" or \"N\" character: ");
	    }
	}
	private static void terminate() throws IOException {
	    System.out.print("Any user input will exit the application: ");
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    in.readLine();
            System.out.println("Terminating...");  
	}
}
