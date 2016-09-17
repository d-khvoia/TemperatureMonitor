import java.io.File;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Serializer {
	
    private String filePath;
	
    public Serializer(String filePath) {
    	this.filePath = filePath;
    }
    public void recordTemperature() {
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	float temperature;
	while(true) {
	    try {
	       System.out.print("Enter temperature value: ");
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
	        System.out.println("Wrong temperature value!!! Enter a floating-point number between 30.0 and 45.0!");
	        continue;
	    }
	}
    }
    private boolean isValidTemperature(float temp) {
	if (temp >= 30.0 && temp <= 45.0)
     	    return true;
	return false;
    }
}
