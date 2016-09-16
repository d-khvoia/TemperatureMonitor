import java.awt.Toolkit;

public class BeepNotificator extends Thread {
	
    public void run() {   	
		try {
          while (true) {
			 Toolkit.getDefaultToolkit().beep();
			 Thread.sleep(400);
          }
		} 
		catch (InterruptedException e) {
			System.out.println("Temperature value has been recorded.");
		}
    }
}
