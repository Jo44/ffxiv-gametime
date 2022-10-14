package fr.my.home.ffxivgametime.task;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.controller.MacroController;
import fr.my.home.ffxivgametime.tools.GlobalTools;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.application.Platform;

/**
 * Macro Task
 * 
 * @version 1.3
 */
public class Macro implements Runnable {
	private static Logger logger = LogManager.getLogger(Macro.class);

	// Attributes

	private Robot robot;
	private MacroUpdater macroUpdater = null;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private LocalDateTime marker1 = null;
	private LocalDateTime marker2 = null;
	private String timeLeft = null;
	private int kbClose = 0;
	@SuppressWarnings("unused")
	private int kbConfirm = 0;
	private List<String> craft = null;
	private List<String> setUp = null;
	private List<String> food = null;
	private List<String> repair = null;
	private List<String> materia = null;

	// UI values

	private String focusApp = null;
	private int macroDelay = 0;
	private String craftFilePath = "";
	private int macroStep = 0;
	private int macroLeftIteration = 0;
	private boolean cbAdvancedValue = false;
	private String setUpFilePath = "";
	private boolean cbFoodValue = false;
	private int foodStep = 0;
	private int foodLeftIteration = 0;
	private String foodFilePath = "";
	private boolean cbRepairValue = false;
	private int repairStep = 0;
	private int repairLeftIteration = 0;
	private String repairFilePath = "";
	private boolean cbMateriaValue = false;
	private int materiaStep = 0;
	private int materiaLeftIteration = 0;
	private String materiaFilePath = "";

	/**
	 * Constructor
	 * 
	 * @param macroUpdater
	 */
	public Macro(MacroUpdater macroUpdater) {
		super();
		// Set macro updater
		this.macroUpdater = macroUpdater;
		// Get screen size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
	}

	/**
	 * Run
	 */
	@Override
	public void run() {

		try {

			// Set parameters
			boolean closeRequired = false;
			boolean setUpRequired = false;
			timeLeft = "";
			kbClose = Settings.getKeybindClose();
			kbConfirm = Settings.getKeybindConfirm();
			craft = new ArrayList<String>();
			setUp = new ArrayList<String>();
			food = new ArrayList<String>();
			repair = new ArrayList<String>();
			materia = new ArrayList<String>();
			focusApp = Settings.getFocusApp();
			macroDelay = MacroController.getMacroDelay();
			craftFilePath = MacroController.getCraftFilePath();
			macroStep = MacroController.getMacroStep();
			this.macroLeftIteration = macroStep;
			cbAdvancedValue = MacroController.getCbAdvanced();
			setUpFilePath = MacroController.getSetUpFilePath();
			cbFoodValue = MacroController.getCbFood();
			foodStep = MacroController.getFoodStep();
			this.foodLeftIteration = foodStep;
			foodFilePath = MacroController.getFoodFilePath();
			cbRepairValue = MacroController.getCbRepair();
			repairStep = MacroController.getRepairStep();
			this.repairLeftIteration = repairStep;
			repairFilePath = MacroController.getRepairFilePath();
			cbMateriaValue = MacroController.getCbMateria();
			materiaStep = MacroController.getMateriaStep();
			this.materiaLeftIteration = materiaStep;
			materiaFilePath = MacroController.getMateriaFilePath();

			// Init robot
			robot = new Robot();
			robot.setAutoDelay(100);

			// Wait macroDelay (in seconds)
			logger.info("[Task] Demarrage de Macro dans " + macroDelay + " secondes");
			Thread.sleep(macroDelay * 1000);

			// Read macro files
			boolean craftReaded = false;
			boolean setUpReaded = false;
			boolean foodReaded = false;
			boolean repairReaded = false;
			boolean materiaReaded = false;
			if (craftFilePath != null && !craftFilePath.isEmpty()) {
				craftReaded = readFile(craftFilePath, MacroType.CRAFT);
				if (cbAdvancedValue) {
					if (setUpFilePath != null && !setUpFilePath.isEmpty()) {
						setUpReaded = readFile(setUpFilePath, MacroType.SETUP);
					}
					if (cbFoodValue && foodFilePath != null && !foodFilePath.isEmpty()) {
						foodReaded = readFile(foodFilePath, MacroType.FOOD);
					}
					if (cbRepairValue && repairFilePath != null && !repairFilePath.isEmpty()) {
						repairReaded = readFile(repairFilePath, MacroType.REPAIR);
					}
					if (cbMateriaValue && materiaFilePath != null && !materiaFilePath.isEmpty()) {
						materiaReaded = readFile(materiaFilePath, MacroType.MATERIA);
					}
				}
			}

			// Check macro commands
			boolean craftChecked = false;
			boolean setUpChecked = false;
			boolean foodChecked = false;
			boolean repairChecked = false;
			boolean materiaChecked = false;
			if (craftReaded) {
				craftChecked = checkMacro(craft);
				if (cbAdvancedValue) {
					if (setUpReaded) {
						setUpChecked = checkMacro(setUp);
					}
					if (cbFoodValue && foodReaded) {
						foodChecked = checkMacro(food);
					}
					if (cbRepairValue && repairReaded) {
						repairChecked = checkMacro(repair);
					}
					if (cbMateriaValue && materiaReaded) {
						materiaChecked = checkMacro(materia);
					}
				}
			}

			// Exec
			if (craftChecked) {
				if (!cbAdvancedValue) {
					// Advanced OFF
					// Execute action .. (while action isn't stopped and remaining iteration)
					while (!MacroController.getStopMacro() && macroLeftIteration > 0) {

						// Time marker #1
						marker1 = LocalDateTime.now();
						// Execute macro
						execMacro(craft);
						// Update iteration
						macroLeftIteration--;
						// Time marker #2
						marker2 = LocalDateTime.now();
						// Set UI values
						timeLeft = GlobalTools.formatTimeLeft(totalCraftDuration());
						macroUpdater.setValues(macroLeftIteration, timeLeft);
						Platform.runLater(macroUpdater);

					}
				} else if (setUpChecked) {
					// Advanced ON
					// Execute action .. (while action isn't stopped and remaining iteration)
					while (!MacroController.getStopMacro() && macroLeftIteration > 0) {

						// Reset close UI parameter
						closeRequired = true;

						// Food ON
						if (cbFoodValue && foodChecked && foodLeftIteration == 0) {

							// Close UI if needed
							closeRequired = closeUI(closeRequired);
							// Execute macro
							execMacro(food);
							// Reset Iteration
							this.foodLeftIteration = foodStep;
							// New set up required
							setUpRequired = true;

						}

						// Repair ON
						if (cbRepairValue && repairChecked && repairLeftIteration == 0) {

							// Close UI if needed
							closeRequired = closeUI(closeRequired);
							// Execute macro
							execMacro(repair);
							// Reset Iteration
							this.repairLeftIteration = repairStep;
							// New set up required
							setUpRequired = true;

						}

						// Materia ON
						if (cbMateriaValue && materiaChecked && materiaLeftIteration == 0) {

							// Close UI if needed
							closeRequired = closeUI(closeRequired);
							// Execute macro
							execMacro(materia);
							// Reset Iteration
							this.materiaLeftIteration = materiaStep;
							// New set up required
							setUpRequired = true;

						}

						// Check new set up
						if (setUpRequired) {

							// Execute macro
							execMacro(setUp);
							// Reset set up parameter
							setUpRequired = false;
							// Reset close UI parameter
							closeRequired = true;

						}

						// Time marker #1
						marker1 = LocalDateTime.now();
						// Execute macro
						execMacro(craft);
						// Update iterations
						macroLeftIteration--;
						foodLeftIteration--;
						repairLeftIteration--;
						materiaLeftIteration--;
						// Time marker #2
						marker2 = LocalDateTime.now();
						// Set UI values
						timeLeft = GlobalTools.formatTimeLeft(totalCraftDuration());
						macroUpdater.setValues(macroLeftIteration, timeLeft);
						Platform.runLater(macroUpdater);

					}
				}
			}

			logger.info("[Task] Arret de Macro");

		} catch (AWTException | InterruptedException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			// Turn off task when finished
			MacroController.setStopMacro(true);
			// Set UI values
			macroUpdater.setValues(0, "0 min");
			Platform.runLater(macroUpdater);
		}

	}

	/**
	 * Read the macro file and init the list with all commands
	 * 
	 * @param filePath
	 * @param type
	 * @return boolean
	 */
	private boolean readFile(String filePath, MacroType type) {
		boolean readed = false;
		BufferedReader reader = null;
		// Read file from filepath
		try {
			reader = new BufferedReader(new FileReader(filePath));
			// Line by line until the end of file
			String line = reader.readLine();
			while (line != null) {
				// Add command to the appropriate list
				switch (type) {
					case CRAFT:
						craft.add(line);
						break;
					case SETUP:
						setUp.add(line);
						break;
					case FOOD:
						food.add(line);
						break;
					case REPAIR:
						repair.add(line);
						break;
					case MATERIA:
						materia.add(line);
						break;
				}
				line = reader.readLine();
			}
			// File fully readed
			readed = true;
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			// Close reader
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage());
					ioe.printStackTrace();
				}
			}
		}
		return readed;
	}

	/**
	 * Check if all commands are valid
	 * 
	 * @param list
	 * @return boolean
	 */
	private boolean checkMacro(List<String> list) {

		// Available commands :
		// START
		// mousemove(x,y)
		// mouseclick(side)
		// keypress([modifier+]key)
		// keypresstime([modifier+]key,time)
		// sleep(time)
		// sleeprng(time)
		// STOP

		boolean allValid = true;
		// Check all commands
		try {
			for (int i = 0; i < list.size(); i++) {
				boolean valid = false;
				String cmd = list.get(i);
				// Macro should starts with START cmd and ends with STOP cmd
				if ((i == 0 && cmd.startsWith("START")) || (i == list.size() - 1 && cmd.startsWith("STOP"))) {
					valid = true;
				} else if (cmd.startsWith("mousemove(")) {
					// Cmd mousemove
					String[] coordinates = cmd.substring(10, cmd.indexOf(")")).split(",");
					if (coordinates.length == 2) {
						int x = Integer.parseInt(coordinates[0]);
						int y = Integer.parseInt(coordinates[1]);
						if (x >= 0 && x <= screenWidth && y >= 0 && y <= screenHeight) {
							valid = true;
						}
					}
				} else if (cmd.startsWith("mouseclick(")) {
					// Cmd mouseclick
					String side = cmd.substring(11, cmd.indexOf(")"));
					if (side.equals("left") || side.equals("right"))
						valid = true;
				} else if (cmd.startsWith("keypresstime(")) {
					// Cmd keypress time
					String keys = cmd.substring(13, cmd.indexOf(")"));
					String[] keyCombination = keys.split("\\,");
					if (keyCombination.length == 2) {
						Integer.parseInt(keyCombination[1]);
						if (keyCombination[0].contains("+")) {
							String[] keyCombination2 = keyCombination[0].split("\\+");
							if (keyCombination2.length == 2) {
								Integer.parseInt(keyCombination2[0]);
								Integer.parseInt(keyCombination2[1]);
								valid = true;
							}
						} else {
							Integer.parseInt(keyCombination[0]);
							valid = true;
						}
					}
				} else if (cmd.startsWith("keypress(")) {
					// Cmd keypress
					String keys = cmd.substring(9, cmd.indexOf(")"));
					if (keys.contains("+")) {
						String[] keyCombination = keys.split("\\+");
						if (keyCombination.length == 2) {
							Integer.parseInt(keyCombination[0]);
							Integer.parseInt(keyCombination[1]);
							valid = true;
						}
					} else {
						Integer.parseInt(keys);
						valid = true;
					}
				} else if (cmd.startsWith("sleeprng(")) {
					// Cmd sleeprng
					Float.parseFloat(cmd.substring(9, cmd.indexOf(")")));
					valid = true;
				} else if (cmd.startsWith("sleep(")) {
					// Cmd sleep
					Float.parseFloat(cmd.substring(6, cmd.indexOf(")")));
					valid = true;
				}
				// If the current cmd isn't valid
				if (!valid) {
					allValid = false;
				}
			}
		} catch (Exception ex) {
			allValid = false;
		}
		return allValid;
	}

	/**
	 * Execute macro
	 * 
	 * @param list
	 * @throws InterruptedException
	 */
	private void execMacro(List<String> list) throws InterruptedException {
		// Exec all commands
		for (int i = 0; i < list.size(); i++) {
			// Exec command .. (if action isn't stopped)
			if (!MacroController.getStopMacro()) {
				// Get window focus
				GlobalTools.setFocusToWindowsApp(focusApp);
				// Get command
				String cmd = list.get(i);
				// Cmd mousemove
				if (cmd.startsWith("mousemove(")) {
					String[] coordinates = cmd.substring(10, cmd.indexOf(")")).split(",");
					int x = Integer.parseInt(coordinates[0]);
					int y = Integer.parseInt(coordinates[1]);
					mouseMove(x, y);
				} else if (cmd.startsWith("mouseclick(")) {
					// Cmd mouseclick
					String side = cmd.substring(11, cmd.indexOf(")"));
					mouseClick(side);
				} else if (cmd.startsWith("keypresstime(")) {
					// Cmd keypress time
					String keys = cmd.substring(13, cmd.indexOf(")"));
					String[] keyCombination = keys.split("\\,");
					int time = Integer.parseInt(keyCombination[1]);
					if (keyCombination[0].contains("+")) {
						String[] keyCombination2 = keyCombination[0].split("\\+");
						int modifier = Integer.parseInt(keyCombination2[0]);
						int key = Integer.parseInt(keyCombination2[1]);
						keyPress(modifier, key);
						keyPressTime(modifier, key, time);
					} else {
						int key = Integer.parseInt(keyCombination[0]);
						keyPressTime(key, time);
					}
				} else if (cmd.startsWith("keypress(")) {
					// Cmd keypress
					String keys = cmd.substring(9, cmd.indexOf(")"));
					if (keys.contains("+")) {
						String[] keyCombination = keys.split("\\+");
						int modifier = Integer.parseInt(keyCombination[0]);
						int key = Integer.parseInt(keyCombination[1]);
						keyPress(modifier, key);
					} else {
						int key = Integer.parseInt(keys);
						keyPress(key);
					}
				} else if (cmd.startsWith("sleeprng(")) {
					// Cmd sleeprng
					int totalWaitTime = (int) GlobalTools.getRangeFloat(Float.parseFloat(cmd.substring(9, cmd.indexOf(")"))));
					int elapsedTime = 0;
					// Wait .. (check every seconds if action is stopped)
					while (!MacroController.getStopMacro() && (elapsedTime + 1000) < totalWaitTime) {
						sleep(1000);
						elapsedTime += 1000;
					}
					if (!MacroController.getStopMacro() && elapsedTime < totalWaitTime) {
						sleep(totalWaitTime - elapsedTime);
					}
				} else if (cmd.startsWith("sleep(")) {
					// Cmd sleep
					int totalWaitTime = (int) Float.parseFloat(cmd.substring(6, cmd.indexOf(")"))) * 1000;
					int elapsedTime = 0;
					// Wait .. (check every seconds if action is stopped)
					while (!MacroController.getStopMacro() && (elapsedTime + 1000) < totalWaitTime) {
						sleep(1000);
						elapsedTime += 1000;
					}
					if (!MacroController.getStopMacro() && elapsedTime < totalWaitTime) {
						sleep(totalWaitTime - elapsedTime);
					}
				}
			}
		}
	}

	/**
	 * Close UI
	 * 
	 * @param closeRequired
	 * @return boolean
	 * @throws InterruptedException
	 */
	private boolean closeUI(boolean closeRequired) throws InterruptedException {
		if (closeRequired) {
			Thread.sleep(3000);
			keyPress(kbClose);
			Thread.sleep(3000);
		}
		return false;
	}

	/**
	 * Mouse move to the given position
	 * 
	 * @param x
	 * @param y
	 */
	private void mouseMove(int x, int y) {
		robot.mouseMove(x, y);
	}

	/**
	 * Mouse click
	 * 
	 * @param side
	 */
	private void mouseClick(String side) {
		if (side.equals("right")) {
			robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
		} else {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
	}

	/**
	 * Key press on the given key
	 * 
	 * @param key
	 */
	private void keyPress(int key) {
		robot.keyPress(key);
		robot.keyRelease(key);
	}

	/**
	 * Key press on the given modifier + key
	 * 
	 * @param modifier
	 * @param key
	 */
	private void keyPress(int modifier, int key) {
		robot.keyPress(modifier);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(modifier);
	}

	/**
	 * Key press on the given key for a specified time
	 * 
	 * @param key
	 * @param time
	 * @throws InterruptedException
	 */
	private void keyPressTime(int key, int time) throws InterruptedException {
		robot.keyPress(key);
		Thread.sleep(time);
		robot.keyRelease(key);
	}

	/**
	 * Key press on the given modifier + key for a specified time
	 * 
	 * @param modifier
	 * @param key
	 * @param time
	 * @throws InterruptedException
	 */
	private void keyPressTime(int modifier, int key, int time) throws InterruptedException {
		robot.keyPress(modifier);
		robot.keyPress(key);
		Thread.sleep(time);
		robot.keyRelease(key);
		robot.keyRelease(modifier);
	}

	/**
	 * Sleep x ms
	 * 
	 * @param value
	 * @throws InterruptedException
	 */
	private void sleep(int value) throws InterruptedException {
		if (value >= 0) {
			Thread.sleep(value);
		}
	}

	/**
	 * Get total craft duration (in seconds)
	 * 
	 * @return long
	 */
	private long totalCraftDuration() {
		long duration = 0;
		try {
			duration = (Duration.between(marker1, marker2).getSeconds()) * macroLeftIteration;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		return duration;
	}

}
