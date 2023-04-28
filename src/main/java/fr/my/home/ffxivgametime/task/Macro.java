package fr.my.home.ffxivgametime.task;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.controller.MacroController;
import fr.my.home.ffxivgametime.controller.type.AdvancedStatus;
import fr.my.home.ffxivgametime.controller.type.GearStatus;
import fr.my.home.ffxivgametime.controller.type.MacroType;
import fr.my.home.ffxivgametime.tools.GlobalTools;
import fr.my.home.ffxivgametime.tools.KeyboardStrokeMap;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.application.Platform;

/**
 * Macro Task
 * 
 * @version 1.8
 */
public class Macro implements Runnable {
	private static Logger logger = LogManager.getLogger(Macro.class);

	private Robot robot;
	private MacroUpdater macroUpdater = null;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private LocalDateTime marker1 = null;
	private LocalDateTime marker2 = null;
	private boolean closeRequired = false;
	private boolean setUpRequired = false;
	private final int kbClose = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindClose());
	private String timeLeft = "calcul ...";
	private AdvancedStatus advancedStatus = AdvancedStatus.ALL_GOOD;
	private boolean foodStatus = false;
	private final GearStatus[] gearStatus = new GearStatus[12];
	private final boolean gearMod = Settings.getGearMod();
	private final int gearFromX = Settings.getGearFromX();
	private final int gearFromY = Settings.getGearFromY();
	private final int gearOffsetX = Settings.getGearOffsetX();
	private final int gearOffsetY = Settings.getGearOffsetY();
	private final List<String> craft = new ArrayList<String>();
	private final List<String> setUp = new ArrayList<String>();
	private final List<String> food = new ArrayList<String>();
	private final List<String> repair = new ArrayList<String>();
	private final List<String> materia = new ArrayList<String>();

	// UI values

	private final int macroDelay = MacroController.getMacroDelay();
	private final String craftFilePath = MacroController.getCraftFilePath();
	private final int macroStep = MacroController.getMacroStep();
	private int macroLeftIteration = macroStep;
	private final boolean cbAdvancedValue = MacroController.getCbAdvanced();
	private final boolean cbAutoValue = MacroController.getCbAuto();
	private final String setUpFilePath = MacroController.getSetUpFilePath();
	private final boolean cbFoodValue = MacroController.getCbFood();
	private final int foodStep = MacroController.getFoodStep();
	private int foodLeftIteration = foodStep;
	private final String foodFilePath = MacroController.getFoodFilePath();
	private final boolean cbRepairValue = MacroController.getCbRepair();
	private final int repairStep = MacroController.getRepairStep();
	private int repairLeftIteration = repairStep;
	private final String repairFilePath = MacroController.getRepairFilePath();
	private final boolean cbMateriaValue = MacroController.getCbMateria();
	private final int materiaStep = MacroController.getMateriaStep();
	private int materiaLeftIteration = materiaStep;
	private final String materiaFilePath = MacroController.getMateriaFilePath();

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

						// Update UI
						macroUpdater.setValues(macroLeftIteration, timeLeft, false, null);
						Platform.runLater(macroUpdater);
						// Time marker #1
						marker1 = LocalDateTime.now();
						// Execute macro
						execMacro(craft);
						// Time marker #2
						marker2 = LocalDateTime.now();
						// Set UI values
						timeLeft = GlobalTools.formatTimeLeft(totalCraftDuration());
						// Update iteration
						macroLeftIteration--;

					}
				} else if (setUpChecked) {

					// Advanced ON
					// Execute action .. (while action isn't stopped and remaining iteration)
					while (!MacroController.getStopMacro() && macroLeftIteration > 0) {

						// Reset close UI parameter
						closeRequired = true;
						// Update UI
						if (cbAutoValue) {
							// Check status
							checkAdvancedStatus();
							macroUpdater.setValues(macroLeftIteration, timeLeft, foodStatus, gearStatus);
						} else {
							macroUpdater.setValues(macroLeftIteration, timeLeft, false, null);
						}
						Platform.runLater(macroUpdater);

						// Repair ON
						if (cbRepairValue && repairChecked && (advancedStatus == AdvancedStatus.NEED_REPAIR || repairLeftIteration == 0)) {

							logger.info("[Task] Repair");
							// Close UI if needed
							closeRequired = closeUI(closeRequired);
							// Execute macro
							execMacro(repair);
							// Update UI
							if (cbAutoValue) {
								// Check status
								checkAdvancedStatus();
								macroUpdater.setValues(macroLeftIteration, timeLeft, foodStatus, gearStatus);
							}
							Platform.runLater(macroUpdater);
							// Reset Iteration
							this.repairLeftIteration = repairStep;
							// New set up required
							setUpRequired = true;

						}

						// Materia ON
						if (cbMateriaValue && materiaChecked && (advancedStatus == AdvancedStatus.NEED_MATERIA || materiaLeftIteration == 0)) {

							logger.info("[Task] Materialisation");
							// Close UI if needed
							closeRequired = closeUI(closeRequired);
							// Execute macro
							execMacro(materia);
							// Update UI
							if (cbAutoValue) {
								// Check status
								checkAdvancedStatus();
								macroUpdater.setValues(macroLeftIteration, timeLeft, foodStatus, gearStatus);
							}
							Platform.runLater(macroUpdater);
							// Reset Iteration
							this.materiaLeftIteration = materiaStep;
							// New set up required
							setUpRequired = true;

						}

						// Food ON
						if (cbFoodValue && foodChecked && (advancedStatus == AdvancedStatus.NEED_FOOD || foodLeftIteration == 0)) {

							logger.info("[Task] Food");
							// Close UI if needed
							closeRequired = closeUI(closeRequired);
							// Execute macro
							execMacro(food);
							// Update UI
							if (cbAutoValue) {
								// Check status
								checkAdvancedStatus();
								macroUpdater.setValues(macroLeftIteration, timeLeft, foodStatus, gearStatus);
							}
							Platform.runLater(macroUpdater);
							// Reset Iteration
							this.foodLeftIteration = foodStep;
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
						// Time marker #2
						marker2 = LocalDateTime.now();
						// Update iterations
						macroLeftIteration--;
						if (!cbAutoValue) {
							foodLeftIteration--;
							repairLeftIteration--;
							materiaLeftIteration--;
						}
						// Set UI values
						timeLeft = GlobalTools.formatTimeLeft(totalCraftDuration());

					}
				}
			}

			logger.info("[Task] Arret de Macro");

		} catch (AWTException | InterruptedException ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		} finally {
			// Turn off task when finished
			MacroController.setStopMacro(true);
			// Set UI values
			macroUpdater.setValues(0, "0 min", false, null);
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
			logger.error(ioe.toString());
			ioe.printStackTrace();
		} finally {
			// Close reader
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					logger.error(ioe.toString());
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
		// keypress([modifier,]key)
		// keypresstime([modifier,]key,time)
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
						// No modifier
						if (KeyboardStrokeMap.getKeyEvent(keyCombination[0]) != 0) {
							Integer.parseInt(keyCombination[1]);
							valid = true;
						}
					} else if (keyCombination.length == 3) {
						// Modifier
						if (KeyboardStrokeMap.getKeyEvent(keyCombination[0]) != 0 && KeyboardStrokeMap.getKeyEvent(keyCombination[1]) != 0) {
							Integer.parseInt(keyCombination[2]);
							valid = true;
						}
					}
				} else if (cmd.startsWith("keypress(")) {
					// Cmd keypress
					String keys = cmd.substring(9, cmd.indexOf(")"));
					if (!keys.contains(",") && KeyboardStrokeMap.getKeyEvent(keys) != 0) {
						// No modifier
						valid = true;
					} else {
						// Modifier
						String[] keyCombination = keys.split("\\,");
						if (keyCombination.length == 2 && KeyboardStrokeMap.getKeyEvent(keyCombination[0]) != 0
								&& KeyboardStrokeMap.getKeyEvent(keyCombination[1]) != 0) {
							valid = true;
						}
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
				// Get command
				String cmd = list.get(i);
				// Cmd mousemove
				if (cmd.startsWith("mousemove(")) {
					String[] coordinates = cmd.substring(10, cmd.indexOf(")")).split(",");
					int x = Integer.parseInt(coordinates[0]);
					int y = Integer.parseInt(coordinates[1]);
					// Action
					mouseMove(x, y);
				} else if (cmd.startsWith("mouseclick(")) {
					// Cmd mouseclick
					String side = cmd.substring(11, cmd.indexOf(")"));
					// Action
					mouseClick(side);
				} else if (cmd.startsWith("keypresstime(")) {
					// Cmd keypress time
					String keys = cmd.substring(13, cmd.indexOf(")"));
					String[] keyCombination = keys.split("\\,");
					int time = Integer.parseInt(keyCombination[keyCombination.length - 1]);
					if (keyCombination.length == 2) {
						// No modifier
						int key = KeyboardStrokeMap.getKeyEvent(keyCombination[0]);
						// Action
						keyPressTime(key, time);
					} else if (keyCombination.length == 3) {
						// Modifier
						int modifier = KeyboardStrokeMap.getKeyEvent(keyCombination[0]);
						int key = KeyboardStrokeMap.getKeyEvent(keyCombination[1]);
						// Action
						keyPressTime(modifier, key, time);
					}
				} else if (cmd.startsWith("keypress(")) {
					// Cmd keypress
					String keys = cmd.substring(9, cmd.indexOf(")"));
					if (!keys.contains(",")) {
						// No modifier
						int key = KeyboardStrokeMap.getKeyEvent(keys);
						// Action
						keyPress(key);
					} else {
						// Modifier
						String[] keyCombination = keys.split("\\,");
						int modifier = KeyboardStrokeMap.getKeyEvent(keyCombination[0]);
						int key = KeyboardStrokeMap.getKeyEvent(keyCombination[1]);
						// Action
						keyPress(modifier, key);
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
	 * Vérifie le status avancé food/repair/materia
	 * 
	 * @throws InterruptedException
	 */
	private void checkAdvancedStatus() throws InterruptedException {

		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		// Speed-up robot for checking
		robot.setAutoDelay(0);

		// Init values
		advancedStatus = AdvancedStatus.ALL_GOOD;
		Point[] pTable = new Point[12];

		// Get all points of comparaison (gear status)
		Point point = null;
		for (int i = 0; i < 12; i++) {
			if (i < 6) {
				point = new Point(gearFromX, gearFromY + (i * gearOffsetY));
			} else {
				if (gearMod) {
					point = new Point(gearFromX + gearOffsetX, gearFromY + ((i - 6) * gearOffsetY));
				} else {
					// Add offset Y if no gear mod
					point = new Point(gearFromX + gearOffsetX, gearFromY + gearOffsetY + ((i - 6) * gearOffsetY));
				}
			}
			pTable[i] = point;
		}

		// Check gear for all points
		Color pixelColor = null;

		for (int i = 0; i < 12; i++) {
			pixelColor = robot.getPixelColor(pTable[i].x, pTable[i].y);
			// If red => need repair
			if (pixelColor.getRed() > 200 && pixelColor.getGreen() < 100 && pixelColor.getBlue() < 100) {
				gearStatus[i] = GearStatus.REPAIR;
			} else if (pixelColor.getRed() > 200 && pixelColor.getGreen() > 200 && pixelColor.getBlue() > 200) {
				// If white => need materia
				gearStatus[i] = GearStatus.MATERIA;
			} else {
				// If other => no need
				gearStatus[i] = GearStatus.GOOD;
			}
		}

		// Check & set AdvancedStatus
		boolean needFood = needFood();
		if (needRepair(gearStatus)) {
			advancedStatus = AdvancedStatus.NEED_REPAIR;
		} else if (needMateria(gearStatus)) {
			advancedStatus = AdvancedStatus.NEED_MATERIA;
		} else if (needFood) {
			advancedStatus = AdvancedStatus.NEED_FOOD;
		} else {
			advancedStatus = AdvancedStatus.ALL_GOOD;
		}

		// Slow-down robot after checking
		robot.setAutoDelay(100);

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

	/**
	 * Check if gear need repair
	 * 
	 * @param gearStatus
	 * @return boolean
	 */
	private boolean needRepair(GearStatus[] gearStatus) {
		boolean needRepair = false;
		// Check if any gear need repair
		for (int i = 0; i < 12; i++) {
			if (gearStatus[i] == GearStatus.REPAIR) {
				needRepair = true;
				break;
			}
		}
		return needRepair;
	}

	/**
	 * Check if gear need materia
	 * 
	 * @param gearStatus
	 * @return boolean
	 */
	private boolean needMateria(GearStatus[] gearStatus) {
		boolean needMateria = false;
		// Check if any gear need repair
		for (int i = 0; i < 12; i++) {
			if (gearStatus[i] == GearStatus.MATERIA) {
				needMateria = true;
				break;
			}
		}
		return needMateria;
	}

	/**
	 * Check if need food
	 * 
	 * @return boolean
	 */
	private boolean needFood() {
		// Get screenshot
		BufferedImage mainImage = robot.createScreenCapture(new Rectangle(screenWidth, screenHeight));
		try {
			// Get comparaison image
			BufferedImage subImage = ImageIO.read(Macro.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/buff_food_check.png"));
			// Precision threshold
			int threshold = 100;
			// Search ...
			foodStatus = existInsideImage(mainImage, subImage, threshold);
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
			foodStatus = true;
		}
		return !foodStatus;
	}

	/**
	 * Determines if the sub image is present in the main image
	 * 
	 * @param mainImage
	 * @param subImage
	 * @param threshold
	 * @return boolean
	 */
	private boolean existInsideImage(BufferedImage mainImage, BufferedImage subImage, int threshold) {
		return scanImage(mainImage, subImage, (rgb0, rgb1) -> {
			int difference = computeDifference(rgb0, rgb1);
			if (difference > threshold) {
				return 1;
			}
			return 0;
		});
	}

	/**
	 * Compute difference between RGB values
	 * 
	 * @param rgb0
	 * @param rgb1
	 * @return int
	 */
	private int computeDifference(int rgb0, int rgb1) {
		int r0 = (rgb0 & 0x00FF0000) >> 16;
		int g0 = (rgb0 & 0x0000FF00) >> 8;
		int b0 = (rgb0 & 0x000000FF);

		int r1 = (rgb1 & 0x00FF0000) >> 16;
		int g1 = (rgb1 & 0x0000FF00) >> 8;
		int b1 = (rgb1 & 0x000000FF);

		int dr = Math.abs(r0 - r1);
		int dg = Math.abs(g0 - g1);
		int db = Math.abs(b0 - b1);

		return dr + dg + db;
	}

	/**
	 * Scan main image pixel by pixel
	 * 
	 * @param mainImage
	 * @param subImage
	 * @param rgbComparator
	 * @return boolean
	 */
	private boolean scanImage(BufferedImage mainImage, BufferedImage subImage, IntBinaryOperator rgbComparator) {
		int width = mainImage.getWidth();
		int height = mainImage.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (isSubImageAt(mainImage, x, y, subImage, rgbComparator)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines if the sub image is present at this position
	 * 
	 * @param mainImage
	 * @param x
	 * @param y
	 * @param subImage
	 * @param rgbComparator
	 * @return boolean
	 */
	private boolean isSubImageAt(BufferedImage mainImage, int x, int y, BufferedImage subImage, IntBinaryOperator rgbComparator) {
		int width = subImage.getWidth();
		int height = subImage.getHeight();
		if (x + width > mainImage.getWidth()) {
			return false;
		}
		if (y + height > mainImage.getHeight()) {
			return false;
		}
		for (int ix = 0; ix < width; ix++) {
			for (int iy = 0; iy < height; iy++) {
				int mainRgb = mainImage.getRGB(x + ix, y + iy);
				int subRgb = subImage.getRGB(ix, iy);
				if (rgbComparator.applyAsInt(mainRgb, subRgb) != 0) {
					return false;
				}
			}
		}
		return true;
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
	 * @throws InterruptedException
	 */
	private void mouseMove(int x, int y) throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		// Mouse move
		robot.mouseMove(x, y);

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

	/**
	 * Mouse click
	 * 
	 * @param side
	 * @throws InterruptedException
	 */
	private void mouseClick(String side) throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		if (side.equals("right")) {
			// Right click
			robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
		} else {
			// Left click
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

	/**
	 * Key press on the given key
	 * 
	 * @param key
	 * @throws InterruptedException
	 */
	private void keyPress(int key) throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		// Keypress
		robot.keyPress(key);
		robot.keyRelease(key);

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

	/**
	 * Key press on the given modifier + key
	 * 
	 * @param modifier
	 * @param key
	 * @throws InterruptedException
	 */
	private void keyPress(int modifier, int key) throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		// Keypress with modifier
		robot.keyPress(modifier);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(modifier);

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
	}

	/**
	 * Key press on the given key for a specified time
	 * 
	 * @param key
	 * @param time
	 * @throws InterruptedException
	 */
	private void keyPressTime(int key, int time) throws InterruptedException {
		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		// Keypress
		robot.keyPress(key);
		Thread.sleep(time);
		robot.keyRelease(key);

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
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
		// Get application focus
		GlobalTools.getAppFocus(MacroController.getCbNotif(), MacroController.getSlVolume());

		// Keypress with modifier
		robot.keyPress(modifier);
		robot.keyPress(key);
		Thread.sleep(time);
		robot.keyRelease(key);
		robot.keyRelease(modifier);

		// Restore Windows focus
		GlobalTools.restoreWindowsFocus();
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
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return duration;
	}

}
