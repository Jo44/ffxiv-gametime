package fr.my.home.ffxivgametime.task;

import fr.my.home.ffxivgametime.controller.type.GearStatus;

/**
 * Macro Updater
 * 
 * @version 1.2
 */
public class MacroUpdater implements Runnable {

	protected int iterationLeft = 0;
	protected String timeLeft = "";
	protected boolean foodStatus;
	protected GearStatus[] gearStatus;

	/**
	 * Set new values
	 * 
	 * @param newIterationLeft
	 * @param newTimeLeft
	 * @param newFoodStatus
	 * @param newGearStatus
	 */
	public void setValues(int newIterationLeft, String newTimeLeft, boolean newFoodStatus, GearStatus[] newGearStatus) {
		iterationLeft = newIterationLeft;
		timeLeft = newTimeLeft;
		foodStatus = newFoodStatus;
		gearStatus = newGearStatus;
	}

	/**
	 * Run
	 */
	@Override
	public void run() {}

}
