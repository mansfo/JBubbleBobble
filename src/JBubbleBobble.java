
import controller.Controller;
import view.BubbleBobbleFrame;

/**
 * class that represents the main function of the application:
 * running this, the main controller and the main frame are initialized 
 */
public class JBubbleBobble {
	public static void main(String[] args) {
		System.out.println("Starting application...");
		new Controller(new BubbleBobbleFrame());
	}
}
