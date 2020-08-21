package co.aurasphere.ffx;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import de.androidpit.colorthief.ColorThief;

// Application for dodging automatically thunders in FFX for PS4. 
// Usage: connect to your PS4 through remote play and run this script. 
// Note: If the script doesn't work correctly, try to reduce the network latency 
// by disconnecting other devices or connecting through cable instead of WiFi.
// Trivia: I've estimated the time window of the button pressing after the flash
// to be something around 150-160 ms.
public class ThunderDodger {

	// If all the RGB components will be over this threshold, the color is
	// considered to be a white shade and the input will be triggered.
	private static final int WHITE_THRESHOLD = 180;

	// Delay after which the button will be released, in milliseconds.
	private static final long BUTTON_RELEASE_DELAY = 150;

	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		// Startup delay to change the window.
		Thread.sleep(2000);
		// Instead of checking the whole screen color, we just use a small square to
		// improve performances.
		Rectangle rectangle = new Rectangle(400, 400, 200, 200);
		Robot robot = new Robot();
		while (true) {

			// Captures a screenshot.
			BufferedImage screen = robot.createScreenCapture(rectangle);

			// Checks the prevalent color.
			int[] colors = ColorThief.getColor(screen, 10, false);

			// If prevalent color is white, presses and releases the enter button.
			if (colors[0] > WHITE_THRESHOLD && colors[1] > WHITE_THRESHOLD && colors[2] > WHITE_THRESHOLD) {
				System.out.println("Lightning!");
				robot.keyPress(KeyEvent.VK_ENTER);
				// Very important: we add some delay between the button release, otherwise the
				// input will not be registered correctly.
				Thread.sleep(BUTTON_RELEASE_DELAY);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
		}
	}

}
