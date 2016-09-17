import java.util.Scanner;

public class TemperatureMonitor {

	public static void main(String[] args) throws Exception {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    args = prepareArgs(args);
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
               new Serializer(filePath).recordTemperature();	    
	       beepNotificator.interrupt();
	       suggestStatistics(filePath);
	    }
	    catch (RuntimeException e) {
		System.out.println("Illegal arguments passed. Arguments represent wait time and show time rate and must be two integer numbers! Terminating...");
	    }
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
}
