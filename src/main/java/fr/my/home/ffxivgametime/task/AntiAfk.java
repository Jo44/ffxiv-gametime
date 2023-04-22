package fr.my.home.ffxivgametime.task;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.controller.AntiAfkController;
import fr.my.home.ffxivgametime.controller.type.AntiAfkMethod;
import fr.my.home.ffxivgametime.tools.GlobalTools;
import fr.my.home.ffxivgametime.tools.KeyboardStrokeMap;
import fr.my.home.ffxivgametime.tools.Settings;

/**
 * Anti-Afk Task
 * 
 * @version 1.3
 */
public class AntiAfk implements Runnable {
	private static Logger logger = LogManager.getLogger(AntiAfk.class);

	private Robot robot;
	private final int kbAfkAction = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindAntiAfkAction());

	// UI values

	private final int antiAfkDelay = AntiAfkController.getAntiAfkDelay();
	private final int antiAfkMin = AntiAfkController.getAntiAfkMin();
	private final int antiAfkMax = AntiAfkController.getAntiAfkMax();
	private final AntiAfkMethod antiAfkMethod = AntiAfkController.getAntiAfkMethod();

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

			// Init robot
			robot = new Robot();
			robot.setAutoDelay(100);

			// Wait antiAfkDelay (in seconds)
			logger.info("[Task] Demarrage de l'Anti-Afk dans " + antiAfkDelay + " secondes");
			Thread.sleep(antiAfkDelay * 1000);

			// Execute action .. (while action isn't stopped)
			while (!AntiAfkController.getStopAntiAfk()) {

				// Execute action with selected method
				switch (antiAfkMethod) {
					// Action
					case ACTION:
						action(kbAfkAction);
						break;
					// Mix (random action or move)
					case MIX:
						if (GlobalTools.getRandomIntBetween1and2() == 1) {
							action(kbAfkAction);
						} else {
							move();
						}
						break;
					// Move
					case MOVE:
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
			logger.error(ex.toString());
			ex.printStackTrace();
		}

	}

	/**
	 * Press action key
	 * 
	 * @param actionAfk
	 * @throws InterruptedException
	 */
	private void action(int actionAfk) throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus();

		// Keypress
		robot.keyPress(actionAfk);
		robot.keyRelease(actionAfk);
		logger.info("-> Touche Action appuyee");

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

	/**
	 * Press direction key (random Z, S, A or E)
	 * 
	 * @throws InterruptedException
	 */
	private void move() throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus();

		// Random keypress
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

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

}
