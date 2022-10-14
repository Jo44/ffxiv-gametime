package fr.my.home.ffxivgametime.task;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.controller.AntiAfkController;
import fr.my.home.ffxivgametime.tools.GlobalTools;
import fr.my.home.ffxivgametime.tools.Settings;

/**
 * Anti-Afk Task
 * 
 * @version 1.1
 */
public class AntiAfk implements Runnable {
	private static Logger logger = LogManager.getLogger(AntiAfk.class);

	// Attributes

	private Robot robot;
	private int kbAfkAction = 0;

	// UI values

	private String focusApp = null;
	private int antiAfkDelay = 0;
	private int antiAfkMin = 0;
	private int antiAfkMax = 0;
	private int antiAfkMethod = 0;

	/**
	 * Constructor
	 */
	public AntiAfk() {
		super();
	}

	/**
	 * Run
	 */
	@Override
	public void run() {

		try {

			// Set parameters
			kbAfkAction = Settings.getKeybindAntiAfkAction();
			focusApp = Settings.getFocusApp();
			antiAfkDelay = AntiAfkController.getAntiAfkDelay();
			antiAfkMin = AntiAfkController.getAntiAfkMin();
			antiAfkMax = AntiAfkController.getAntiAfkMax();
			antiAfkMethod = AntiAfkController.getAntiAfkMethod();

			// Init robot
			robot = new Robot();
			robot.setAutoDelay(100);

			// Wait antiAfkDelay (in seconds)
			logger.info("[Task] Demarrage de l'Anti-Afk dans " + antiAfkDelay + " secondes");
			Thread.sleep(antiAfkDelay * 1000);

			// Execute action .. (while action isn't stopped)
			while (!AntiAfkController.getStopAntiAfk()) {

				// Get window focus
				GlobalTools.setFocusToWindowsApp(focusApp);

				// Execute action with selected method
				switch (antiAfkMethod) {
					// Action
					case 0:
						action(kbAfkAction);
						break;
					// Mix (random action or move)
					case 1:
						if (GlobalTools.getRandomIntBetween1and2() == 1) {
							action(kbAfkAction);
						} else {
							move();
						}
						break;
					// Move
					case 2:
						move();
						break;
				}

				// Set waiting time (in seconds) between minAfk and maxAfk (in minutes) before next action
				int totalWaitTime = (int) (GlobalTools.getRandomFloat((float) antiAfkMin, (float) antiAfkMax) * 60);
				int elapsedTime = 0;

				logger.info("Attends " + String.valueOf(totalWaitTime) + " secondes ..");
				// Wait .. (check every seconds if action is stopped)
				while (!AntiAfkController.getStopAntiAfk() && elapsedTime < totalWaitTime) {
					Thread.sleep(1000);
					elapsedTime++;
				}
			}

			logger.info("[Task] Arret de l'Anti-Afk");

		} catch (AWTException | InterruptedException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}

	}

	/**
	 * Press action key
	 * 
	 * @param actionAfk
	 */
	private void action(int actionAfk) {
		robot.keyPress(actionAfk);
		robot.keyRelease(actionAfk);
		logger.info("-> Touche Action appuyee");
	}

	/**
	 * Press direction key (random Z, S, A or E)
	 */
	private void move() {
		switch (GlobalTools.getRandomIntBetween1and4()) {
			case 1:
				robot.keyPress(KeyEvent.VK_Z);
				robot.keyRelease(KeyEvent.VK_Z);
				logger.info("-> Touche Z appuyee");
				break;
			case 2:
				robot.keyPress(KeyEvent.VK_S);
				robot.keyRelease(KeyEvent.VK_S);
				logger.info("-> Touche S appuyee");
				break;
			case 3:
				robot.keyPress(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_A);
				logger.info("-> Touche A appuyee");
				break;
			case 4:
				robot.keyPress(KeyEvent.VK_E);
				robot.keyRelease(KeyEvent.VK_E);
				logger.info("-> Touche E appuyee");
				break;
		}
	}

}
