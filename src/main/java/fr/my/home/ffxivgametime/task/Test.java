package fr.my.home.ffxivgametime.task;

import java.awt.Robot;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Test Task
 * 
 * @version 1.0
 */
public class Test implements Runnable {
	private static Logger logger = LogManager.getLogger(Test.class);

	@SuppressWarnings("unused")
	private Robot robot;
	@SuppressWarnings("unused")
	private TestUpdater testUpdater = null;
	@SuppressWarnings("unused")
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Constructor
	 */
	public Test(TestUpdater testUpdater) {
		super();
		// Set test updater
		this.testUpdater = testUpdater;
	}

	/**
	 * Run
	 */
	@Override
	public void run() {

		logger.info("[Task] Demarrage de la tache de test");

		//////

		//////

		logger.info("[Task] Arret de la tache de test");

	}

}
