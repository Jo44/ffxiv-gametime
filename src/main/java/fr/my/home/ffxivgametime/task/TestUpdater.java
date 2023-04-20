package fr.my.home.ffxivgametime.task;

/**
 * Test Updater
 * 
 * @version 1.0
 */
public class TestUpdater implements Runnable {

	protected String message = "";

	/**
	 * Set new value
	 * 
	 * @param newMessage
	 */
	public void setValue(String newMessage) {
		message = newMessage;
	}

	/**
	 * Run
	 */
	@Override
	public void run() {}

}
