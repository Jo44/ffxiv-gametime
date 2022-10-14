package fr.my.home.ffxivgametime.task;

/**
 * Macro Updater
 * 
 * @version 1.1
 */
public class MacroUpdater implements Runnable {

	// Attributes

	protected int iterationLeft = 0;
	protected String timeLeft = "";

	/**
	 * Set new values
	 * 
	 * @param newIterationLeft
	 * @param newTimeLeft
	 */
	public void setValues(int newIterationLeft, String newTimeLeft) {
		iterationLeft = newIterationLeft;
		timeLeft = newTimeLeft;
	}

	/**
	 * Run
	 */
	@Override
	public void run() {}

}
