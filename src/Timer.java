import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timer extends Thread {
	
    private long endTime;
    private long currentTime;
    private long timeShowRate;
	
    public Timer(long waitTime, long timeShowRate) {
    	this.timeShowRate = timeShowRate;
    	currentTime = new Date().getTime();
    	endTime = currentTime + waitTime;
    }
    
    public void run() {
    	long diff;
	try {
          while (true) {
             Thread.sleep(timeShowRate);
             currentTime += timeShowRate;
             diff = endTime - currentTime;
             long minLeft = TimeUnit.MILLISECONDS.toMinutes(diff);
             long secLeft = diff / 1000 - minLeft * 60;
             System.out.println("Time left: " + minLeft + " minutes " + secLeft + " seconds. Wait...");
          }
	} 
	catch (InterruptedException e) {
			
	}
    }
}
