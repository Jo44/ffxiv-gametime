package fr.my.home.ffxivgametime.task;

import fr.my.home.ffxivgametime.controller.type.GearStatus;

/**
 * Macro Updater
 * 
 * @version 2.0
 */
public class MacroUpdater implements Runnable {

	protected int iterationLeft = 0;
	protected String timeLeft = "";
	protected String message = "";
	protected boolean foodStatus;
	protected GearStatus[] gearStatus;

	/**
	 * Set new values
	 * 
	 * @param newIterationLeft
	 * @param newTimeLeft
	 * @param newMessage
	 * @param newFoodStatus
	 * @param newGearStatus
	 */
	public void setValues(int newIterationLeft, String newTimeLeft, String newMessage, boolean newFoodStatus, GearStatus[] newGearStatus) {
		iterationLeft = newIterationLeft;
		timeLeft = newTimeLeft;
		if (newMessage != null) {
			message = newMessage;
		}
		foodStatus = newFoodStatus;
		gearStatus = newGearStatus;
	}

	/**
	 * Run
	 */
	@Override
	public void run() {}

}
