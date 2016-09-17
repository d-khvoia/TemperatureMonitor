import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;

public class Notificator extends Thread {
    
    private Window win;
    
    public void run() {   	
	try {
	   @SuppressWarnings("serial")
	   Window w = new Window(null) {
	      @Override
	      public void paint(Graphics g) {
	          final Font font = getFont().deriveFont(100f);
	          g.setFont(font);
	          g.setColor(Color.RED);
	          final String message = "Please, enter temperature value...";
	          FontMetrics metrics = g.getFontMetrics();
	          g.drawString(message, (getWidth() - metrics.stringWidth(message)) / 2, (getHeight() - metrics.getHeight()) / 2);
	      }
	      @Override
	      public void update(Graphics g) {
	          paint(g);
	      }
	   };
	   w.setAlwaysOnTop(true);
	   w.setBounds(w.getGraphicsConfiguration().getBounds());
	   w.setBackground(new Color(0, true));
	   w.setVisible(true);
	   win = w;
           while (true) {
	       Toolkit.getDefaultToolkit().beep();
	       Thread.sleep(400);
           }
	} 
	catch (InterruptedException e) {
	    System.out.println("Temperature value has been recorded.");
	    win.dispose();
	}
    }
}
