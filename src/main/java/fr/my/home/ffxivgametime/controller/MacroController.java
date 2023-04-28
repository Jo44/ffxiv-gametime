package fr.my.home.ffxivgametime.controller;

import java.awt.MouseInfo;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import fr.my.home.ffxivgametime.controller.type.GearStatus;
import fr.my.home.ffxivgametime.task.Macro;
import fr.my.home.ffxivgametime.task.MacroUpdater;
import fr.my.home.ffxivgametime.tools.GlobalTools;
import fr.my.home.ffxivgametime.tools.KeyboardStrokeMap;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

/**
 * MacroController
 * 
 * @version 1.6
 */
public class MacroController implements GlobalKeyListener {
	private static Logger logger = LogManager.getLogger(MacroController.class);

	private Thread taskThread;
	private GlobalKeyboardHook keyboardHook;
	private static boolean stopMacro = true;
	private static final int KB_MACRO_EXEC = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindMacroExec());
	private static final int KB_MACRO_MOUSE_POS = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindMacroMousePos());
	private static int macroDelay = 3;
	private static int macroStep = 30;
	private static boolean cbAdvancedValue = false;
	private static boolean cbAutoValue = false;
	private static boolean cbNotifValue = false;
	private static double slVolumeValue = 25.0;
	private static Pane[] gearPanes = new Pane[12];
	private static String craftFilePath = "";
	private static String setUpFilePath = "";
	private static boolean cbFoodValue = false;
	private static int foodStep = 30;
	private static String foodFilePath = "";
	private static boolean cbRepairValue = false;
	private static int repairStep = 30;
	private static String repairFilePath = "";
	private static boolean cbMateriaValue = false;
	private static int materiaStep = 30;
	private static String materiaFilePath = "";

	// Components

	@FXML
	private ToggleButton tbMacro;

	@FXML
	private Spinner<Integer> spMacroDelay;

	@FXML
	private Spinner<Integer> spMacroStep;

	@FXML
	private CheckBox cbAdvanced;

	@FXML
	private CheckBox cbAuto;

	@FXML
	private CheckBox cbNotif;

	@FXML
	private Pane notifPane;

	@FXML
	private Slider slVolume;

	@FXML
	private Pane buffPane;

	@FXML
	private Pane buffFood;

	@FXML
	private Pane gearPane;

	@FXML
	private Pane gear1;

	@FXML
	private Pane gear2;

	@FXML
	private Pane gear3;

	@FXML
	private Pane gear4;

	@FXML
	private Pane gear5;

	@FXML
	private Pane gear6;

	@FXML
	private Pane gear7;

	@FXML
	private Pane gear8;

	@FXML
	private Pane gear9;

	@FXML
	private Pane gear10;

	@FXML
	private Pane gear11;

	@FXML
	private Pane gear12;

	@FXML
	private Label lbMacroIterationLeft;

	@FXML
	private Label lbMacroTimeLeft;

	@FXML
	private Label lbCraftFilePath;

	@FXML
	private Button btnCraftFavGet;

	@FXML
	private Button btnCraftFavSet;

	@FXML
	private Pane advancedPane;

	@FXML
	private Label lbSetUpFilePath;

	@FXML
	private Button btnSetUpFavGet;

	@FXML
	private Button btnSetUpFavSet;

	@FXML
	private CheckBox cbFood;

	@FXML
	private Spinner<Integer> spFoodStep;

	@FXML
	private Label lbFoodStep;

	@FXML
	private Label lbFoodFilePath;

	@FXML
	private Button btnFoodFavGet;

	@FXML
	private Button btnFoodFavSet;

	@FXML
	private CheckBox cbRepair;

	@FXML
	private Spinner<Integer> spRepairStep;

	@FXML
	private Label lbRepairStep;

	@FXML
	private Label lbRepairFilePath;

	@FXML
	private Button btnRepairFavGet;

	@FXML
	private Button btnRepairFavSet;

	@FXML
	private CheckBox cbMateria;

	@FXML
	private Spinner<Integer> spMateriaStep;

	@FXML
	private Label lbMateriaStep;

	@FXML
	private Label lbMateriaFilePath;

	@FXML
	private Button btnMateriaFavGet;

	@FXML
	private Button btnMateriaFavSet;

	@FXML
	private Button btnMacroMenu;

	@FXML
	private Label lbMacroMousePosX;

	@FXML
	private Label lbMacroMousePosY;

	/**
	 * Constructor
	 */
	public MacroController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Macro <-");
		// Init Keyboard Hook
		initKeyboardHook();
		// Init gear panes
		gearPanes[0] = gear1;
		gearPanes[1] = gear2;
		gearPanes[2] = gear3;
		gearPanes[3] = gear4;
		gearPanes[4] = gear5;
		gearPanes[5] = gear6;
		gearPanes[6] = gear7;
		gearPanes[7] = gear8;
		gearPanes[8] = gear9;
		gearPanes[9] = gear10;
		gearPanes[10] = gear11;
		gearPanes[11] = gear12;
		// Init spinners listeners
		spMacroDelay.editorProperty().get().setAlignment(Pos.CENTER);
		spMacroStep.editorProperty().get().setAlignment(Pos.CENTER);
		spFoodStep.editorProperty().get().setAlignment(Pos.CENTER);
		spRepairStep.editorProperty().get().setAlignment(Pos.CENTER);
		spMateriaStep.editorProperty().get().setAlignment(Pos.CENTER);
		spMacroDelay.valueProperty().addListener((obs, oldValue, newValue) -> macroDelay = newValue);
		spMacroStep.valueProperty().addListener((obs, oldValue, newValue) -> macroStep = newValue);
		spFoodStep.valueProperty().addListener((obs, oldValue, newValue) -> foodStep = newValue);
		spRepairStep.valueProperty().addListener((obs, oldValue, newValue) -> repairStep = newValue);
		spMateriaStep.valueProperty().addListener((obs, oldValue, newValue) -> materiaStep = newValue);
		// Init notif
		cbNotif.setSelected(cbNotifValue);
		notifPane.setVisible(cbNotifValue);
		slVolume.setValue(slVolumeValue);
		slVolume.valueProperty().addListener((obs, oldValue, newValue) -> slVolumeValue = (double) newValue);
		// Init fav filepath
		craftFavGet();
		setUpFavGet();
		foodFavGet();
		repairFavGet();
		materiaFavGet();
	}

	/**
	 * Close
	 */
	public void shutdown() {
		// Stop task
		stopMacro = true;
		// Release Keyboard Hook
		releaseKeyboardHook();
	}

	/**
	 * Exec Macro
	 */
	@FXML
	private void macroExec() {
		if (tbMacro.isSelected()) {
			// If file path is valid
			if (!lbCraftFilePath.getText().equals("...")) {
				// UI updates
				lbMacroIterationLeft.setText(String.valueOf(macroStep));
				lbMacroIterationLeft.getStyleClass().clear();
				lbMacroIterationLeft.getStyleClass().add("font-green");
				lbMacroTimeLeft.setText("calcul ...");
				// Init Updater (for UI updates)
				MacroUpdater macroUpdater = new MacroUpdater() {
					@Override
					public void run() {
						updateUI(iterationLeft, timeLeft, foodStatus, gearStatus);
					}
				};
				// Thread execution controlled by boolean stopMacro
				stopMacro = false;
				// Check thread state
				if (taskThread == null || taskThread.getState() == State.TERMINATED) {
					// Launch dedicated daemon thread
					taskThread = new Thread(new Macro(macroUpdater));
					taskThread.setDaemon(true);
					taskThread.start();
				}
			} else {
				// Turn off toggle button
				tbMacro.setSelected(false);
			}
		} else {
			// Stop
			stopMacro = true;
		}
	}

	/**
	 * Update UI
	 * 
	 * @param iterationLeft
	 * @param timeLeft
	 * @param foodStatus
	 * @param gearStatus
	 */
	private void updateUI(int iterationLeft, String timeLeft, boolean foodStatus, GearStatus[] gearStatus) {
		// Set buff status
		if (foodStatus) {
			buffFood.setVisible(true);
		} else {
			buffFood.setVisible(false);
		}
		// Set gear status
		if (gearStatus != null) {
			for (int i = 0; i < 12; i++) {
				if (gearStatus[i] != null && gearStatus[i].equals(GearStatus.MATERIA)) {
					gearPanes[i].getStyleClass().clear();
					gearPanes[i].getStyleClass().add("gear-white");
				} else if (gearStatus[i] != null && gearStatus[i].equals(GearStatus.REPAIR)) {
					gearPanes[i].getStyleClass().clear();
					gearPanes[i].getStyleClass().add("gear-red");
				} else {
					gearPanes[i].getStyleClass().clear();
					gearPanes[i].getStyleClass().add("gear-green");
				}
			}
		} else {
			for (int i = 0; i < 12; i++) {
				gearPanes[i].getStyleClass().clear();
				gearPanes[i].getStyleClass().add("gear-green");
			}
		}
		// Set colors
		if (iterationLeft < 1) {
			lbMacroIterationLeft.getStyleClass().clear();
			lbMacroIterationLeft.getStyleClass().add("font-red");
		} else {
			lbMacroIterationLeft.getStyleClass().clear();
			lbMacroIterationLeft.getStyleClass().add("font-green");
		}
		// Set values
		lbMacroIterationLeft.setText(String.valueOf(iterationLeft));
		lbMacroTimeLeft.setText(timeLeft);
		// Set toggle button
		tbMacro.setSelected(!stopMacro);
	}

	/**
	 * Toggle Advanced
	 */
	@FXML
	private void toggleAdvanced() {
		if (cbAdvanced.isSelected()) {
			// Advanced ON
			cbAdvancedValue = true;
			// Enable auto
			cbAuto.setDisable(false);
			// Show advanced
			advancedPane.setVisible(true);
		} else {
			// Advanced OFF
			cbAdvancedValue = false;
			// Disable/hide auto
			cbAutoValue = false;
			cbAuto.setSelected(false);
			cbAuto.setDisable(true);
			// Hide status
			buffPane.setVisible(false);
			gearPane.setVisible(false);
			// Hide advanced
			advancedPane.setVisible(false);
			// Show quantity spinners + labels
			spFoodStep.setVisible(true);
			lbFoodStep.setVisible(true);
			spRepairStep.setVisible(true);
			lbRepairStep.setVisible(true);
			spMateriaStep.setVisible(true);
			lbMateriaStep.setVisible(true);
		}
	}

	/**
	 * Toggle Auto
	 */
	@FXML
	private void toggleAuto() {
		if (cbAuto.isSelected()) {
			// Auto ON
			cbAutoValue = true;
			// Activate food / repair / materia
			cbFoodValue = true;
			cbRepairValue = true;
			cbMateriaValue = true;
			cbFood.setSelected(true);
			cbRepair.setSelected(true);
			cbMateria.setSelected(true);
			// Show status
			buffPane.setVisible(true);
			gearPane.setVisible(true);
			// Hide quantity spinners + labels
			spFoodStep.setVisible(false);
			lbFoodStep.setVisible(false);
			spRepairStep.setVisible(false);
			lbRepairStep.setVisible(false);
			spMateriaStep.setVisible(false);
			lbMateriaStep.setVisible(false);
		} else {
			// Auto OFF
			cbAutoValue = false;
			// Hide status
			buffPane.setVisible(false);
			gearPane.setVisible(false);
			// Show quantity spinners + labels
			spFoodStep.setVisible(true);
			lbFoodStep.setVisible(true);
			spRepairStep.setVisible(true);
			lbRepairStep.setVisible(true);
			spMateriaStep.setVisible(true);
			lbMateriaStep.setVisible(true);
		}
	}

	/**
	 * Toggle Notif
	 */
	@FXML
	private void toggleNotif() {
		if (cbNotif.isSelected()) {
			cbNotifValue = true;
			notifPane.setVisible(true);
		} else {
			cbNotifValue = false;
			notifPane.setVisible(false);
		}
	}

	/**
	 * Select Craft
	 */
	@FXML
	private void craftSelect() {
		// Re-init macro file path
		craftFilePath = "";
		lbCraftFilePath.setText("...");
		// Init initial directory
		File initDir = null;
		String initDirPath = GlobalTools.formatDirPath(Settings.getCraftFavFile());
		if (initDirPath != null) {
			initDir = new File(initDirPath);
		}
		if (initDirPath == null || initDir == null || !initDir.exists() || !initDir.isDirectory() || !initDir.canRead() || !initDir.canWrite()) {
			initDir = new File(System.getProperty("user.home"));
		}
		// File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une macro *.mgt");
		fileChooser.setInitialDirectory(initDir);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MGT", "*.mgt"));
		File file = fileChooser.showOpenDialog(MyApp.getStage());
		// If file is valid, format path and init var and UI
		if (file != null) {
			craftFilePath = GlobalTools.formatFilePath(file.toPath().toAbsolutePath().toString());
			lbCraftFilePath.setText(craftFilePath);
		}
	}

	/**
	 * Get Fav Craft
	 */
	@FXML
	private void craftFavGet() {
		// Get file path from settings.cfg
		String favPath = Settings.getCraftFavFile();
		// If file path is valid, init var and UI
		if (favPath != null && !favPath.isEmpty()) {
			craftFilePath = favPath;
			lbCraftFilePath.setText(favPath);
		}
	}

	/**
	 * Set Fav Craft
	 */
	@FXML
	private void craftFavSet() {
		// If filename contains ".mgt"
		if (lbCraftFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			craftFilePath = lbCraftFilePath.getText();
			// Write settings.cfg if file path is valid
			if (craftFilePath != null && craftFilePath.length() >= 8 && !craftFilePath.startsWith("...")) {
				Settings.saveSettings(Settings.getAppFocus(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						String.valueOf(Settings.getGearMod()), String.valueOf(Settings.getGearFromX()), String.valueOf(Settings.getGearFromY()),
						String.valueOf(Settings.getGearOffsetX()), String.valueOf(Settings.getGearOffsetY()), craftFilePath,
						Settings.getSetUpFavFile(), Settings.getFoodFavFile(), Settings.getRepairFavFile(), Settings.getMateriaFavFile());
			}
		}
	}

	/**
	 * Select Set Up
	 */
	@FXML
	private void setUpSelect() {
		// Re-init macro file path
		setUpFilePath = "";
		lbSetUpFilePath.setText("...");
		// Init initial directory
		File initDir = null;
		String initDirPath = GlobalTools.formatDirPath(Settings.getSetUpFavFile());
		if (initDirPath != null) {
			initDir = new File(initDirPath);
		}
		if (initDirPath == null || initDir == null || !initDir.exists() || !initDir.isDirectory() || !initDir.canRead() || !initDir.canWrite()) {
			initDir = new File(System.getProperty("user.home"));
		}
		// File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une macro *.mgt");
		fileChooser.setInitialDirectory(initDir);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MGT", "*.mgt"));
		File file = fileChooser.showOpenDialog(MyApp.getStage());
		// If file is valid, format path and init var and UI
		if (file != null) {
			setUpFilePath = GlobalTools.formatFilePath(file.toPath().toAbsolutePath().toString());
			lbSetUpFilePath.setText(setUpFilePath);
		}
	}

	/**
	 * Get Fav Set Up
	 */
	@FXML
	private void setUpFavGet() {
		// Get file path from settings.cfg
		String favPath = Settings.getSetUpFavFile();
		// If file path is valid, init var and UI
		if (favPath != null && !favPath.isEmpty()) {
			setUpFilePath = favPath;
			lbSetUpFilePath.setText(favPath);
		}
	}

	/**
	 * Set Fav Set Up
	 */
	@FXML
	private void setUpFavSet() {
		// If filename contains ".mgt"
		if (lbSetUpFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			setUpFilePath = lbSetUpFilePath.getText();
			// Write settings.cfg if file path is valid
			if (setUpFilePath != null && setUpFilePath.length() >= 8 && !setUpFilePath.startsWith("...")) {
				Settings.saveSettings(Settings.getAppFocus(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						String.valueOf(Settings.getGearMod()), String.valueOf(Settings.getGearFromX()), String.valueOf(Settings.getGearFromY()),
						String.valueOf(Settings.getGearOffsetX()), String.valueOf(Settings.getGearOffsetY()), Settings.getCraftFavFile(),
						setUpFilePath, Settings.getFoodFavFile(), Settings.getRepairFavFile(), Settings.getMateriaFavFile());
			}
		}
	}

	/**
	 * Toggle Food
	 */
	@FXML
	private void toggleFood() {
		if (cbFood.isSelected()) {
			// Food ON
			cbFoodValue = true;
		} else {
			// Food OFF
			cbFoodValue = false;
		}
	}

	/**
	 * Select Food
	 */
	@FXML
	private void foodSelect() {
		// Re-init macro file path
		foodFilePath = "";
		lbFoodFilePath.setText("...");
		// Init initial directory
		File initDir = null;
		String initDirPath = GlobalTools.formatDirPath(Settings.getFoodFavFile());
		if (initDirPath != null) {
			initDir = new File(initDirPath);
		}
		if (initDirPath == null || initDir == null || !initDir.exists() || !initDir.isDirectory() || !initDir.canRead() || !initDir.canWrite()) {
			initDir = new File(System.getProperty("user.home"));
		}
		// File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une macro *.mgt");
		fileChooser.setInitialDirectory(initDir);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MGT", "*.mgt"));
		File file = fileChooser.showOpenDialog(MyApp.getStage());
		// If file is valid, format path and init var and UI
		if (file != null) {
			foodFilePath = GlobalTools.formatFilePath(file.toPath().toAbsolutePath().toString());
			lbFoodFilePath.setText(foodFilePath);
		}
	}

	/**
	 * Get Fav Food
	 */
	@FXML
	private void foodFavGet() {
		// Get file path from settings.cfg
		String favPath = Settings.getFoodFavFile();
		// If file path is valid, init var and UI
		if (favPath != null && !favPath.isEmpty()) {
			foodFilePath = favPath;
			lbFoodFilePath.setText(favPath);
		}
	}

	/**
	 * Set Fav Food
	 */
	@FXML
	private void foodFavSet() {
		// If filename contains ".mgt"
		if (lbFoodFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			foodFilePath = lbFoodFilePath.getText();
			// Write settings.cfg if file path is valid
			if (foodFilePath != null && foodFilePath.length() >= 8 && !foodFilePath.startsWith("...")) {
				Settings.saveSettings(Settings.getAppFocus(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						String.valueOf(Settings.getGearMod()), String.valueOf(Settings.getGearFromX()), String.valueOf(Settings.getGearFromY()),
						String.valueOf(Settings.getGearOffsetX()), String.valueOf(Settings.getGearOffsetY()), Settings.getCraftFavFile(),
						Settings.getSetUpFavFile(), foodFilePath, Settings.getRepairFavFile(), Settings.getMateriaFavFile());
			}
		}
	}

	/**
	 * Toggle Repair
	 */
	@FXML
	private void toggleRepair() {
		if (cbRepair.isSelected()) {
			// Repair ON
			cbRepairValue = true;
		} else {
			// Repair OFF
			cbRepairValue = false;
		}
	}

	/**
	 * Select Repair
	 */
	@FXML
	private void repairSelect() {
		// Re-init macro file path
		repairFilePath = "";
		lbRepairFilePath.setText("...");
		// Init initial directory
		File initDir = null;
		String initDirPath = GlobalTools.formatDirPath(Settings.getRepairFavFile());
		if (initDirPath != null) {
			initDir = new File(initDirPath);
		}
		if (initDirPath == null || initDir == null || !initDir.exists() || !initDir.isDirectory() || !initDir.canRead() || !initDir.canWrite()) {
			initDir = new File(System.getProperty("user.home"));
		}
		// File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une macro *.mgt");
		fileChooser.setInitialDirectory(initDir);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MGT", "*.mgt"));
		File file = fileChooser.showOpenDialog(MyApp.getStage());
		// If file is valid, format path and init var and UI
		if (file != null) {
			repairFilePath = GlobalTools.formatFilePath(file.toPath().toAbsolutePath().toString());
			lbRepairFilePath.setText(repairFilePath);
		}
	}

	/**
	 * Get Fav Repair
	 */
	@FXML
	private void repairFavGet() {
		// Get file path from settings.cfg
		String favPath = Settings.getRepairFavFile();
		// If file path is valid, init var and UI
		if (favPath != null && !favPath.isEmpty()) {
			repairFilePath = favPath;
			lbRepairFilePath.setText(favPath);
		}
	}

	/**
	 * Set Fav Repair
	 */
	@FXML
	private void repairFavSet() {
		// If filename contains ".mgt"
		if (lbRepairFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			repairFilePath = lbRepairFilePath.getText();
			// Write settings.cfg if file path is valid
			if (repairFilePath != null && repairFilePath.length() >= 8 && !repairFilePath.startsWith("...")) {
				Settings.saveSettings(Settings.getAppFocus(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						String.valueOf(Settings.getGearMod()), String.valueOf(Settings.getGearFromX()), String.valueOf(Settings.getGearFromY()),
						String.valueOf(Settings.getGearOffsetX()), String.valueOf(Settings.getGearOffsetY()), Settings.getCraftFavFile(),
						Settings.getSetUpFavFile(), Settings.getFoodFavFile(), repairFilePath, Settings.getMateriaFavFile());
			}
		}
	}

	/**
	 * Toggle Materia
	 */
	@FXML
	private void toggleMateria() {
		if (cbMateria.isSelected()) {
			// Materia ON
			cbMateriaValue = true;
		} else {
			// Materia OFF
			cbMateriaValue = false;
		}
	}

	/**
	 * Select Materia
	 */
	@FXML
	private void materiaSelect() {
		// Re-init macro file path
		materiaFilePath = "";
		lbMateriaFilePath.setText("...");
		// Init initial directory
		File initDir = null;
		String initDirPath = GlobalTools.formatDirPath(Settings.getMateriaFavFile());
		if (initDirPath != null) {
			initDir = new File(initDirPath);
		}
		if (initDirPath == null || initDir == null || !initDir.exists() || !initDir.isDirectory() || !initDir.canRead() || !initDir.canWrite()) {
			initDir = new File(System.getProperty("user.home"));
		}
		// File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une macro *.mgt");
		fileChooser.setInitialDirectory(initDir);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MGT", "*.mgt"));
		File file = fileChooser.showOpenDialog(MyApp.getStage());
		// If file is valid, format path and init var and UI
		if (file != null) {
			materiaFilePath = GlobalTools.formatFilePath(file.toPath().toAbsolutePath().toString());
			lbMateriaFilePath.setText(materiaFilePath);
		}
	}

	/**
	 * Get Fav Materia
	 */
	@FXML
	private void materiaFavGet() {
		// Get file path from settings.cfg
		String favPath = Settings.getMateriaFavFile();
		// If file path is valid, init var and UI
		if (favPath != null && !favPath.isEmpty()) {
			materiaFilePath = favPath;
			lbMateriaFilePath.setText(favPath);
		}
	}

	/**
	 * Set Fav Materia
	 */
	@FXML
	private void materiaFavSet() {
		// If filename contains ".mgt"
		if (lbMateriaFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			materiaFilePath = lbMateriaFilePath.getText();
			// Write settings.cfg if file path is valid
			if (materiaFilePath != null && materiaFilePath.length() >= 8 && !materiaFilePath.startsWith("...")) {
				Settings.saveSettings(Settings.getAppFocus(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						String.valueOf(Settings.getGearMod()), String.valueOf(Settings.getGearFromX()), String.valueOf(Settings.getGearFromY()),
						String.valueOf(Settings.getGearOffsetX()), String.valueOf(Settings.getGearOffsetY()), Settings.getCraftFavFile(),
						Settings.getSetUpFavFile(), Settings.getFoodFavFile(), Settings.getRepairFavFile(), materiaFilePath);
			}
		}
	}

	/**
	 * Display Menu
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displayMenu() throws IOException {
		// Stop task & release Keyboard Hook
		shutdown();
		// Switch scene
		MyApp.switchScene("menu");
	}

	// Keyboard Hook

	/**
	 * Init Keyboard Hook
	 */
	private void initKeyboardHook() {
		keyboardHook = new GlobalKeyboardHook(true);
		keyboardHook.addKeyListener(this);
	}

	/**
	 * Key Pressed
	 * 
	 * @param event
	 */
	@Override
	public void keyPressed(GlobalKeyEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (event.getVirtualKeyCode() == KB_MACRO_EXEC) {
					// Macro
					if (tbMacro.isSelected()) {
						tbMacro.setSelected(false);
					} else {
						tbMacro.setSelected(true);
					}
					// Action
					macroExec();
				}
				if (event.getVirtualKeyCode() == KB_MACRO_MOUSE_POS) {
					// Mouse Position
					lbMacroMousePosX.setText(String.valueOf(MouseInfo.getPointerInfo().getLocation().x));
					lbMacroMousePosY.setText(String.valueOf(MouseInfo.getPointerInfo().getLocation().y));
				}
			}
		});
	}

	/**
	 * Key Released
	 * 
	 * @param event
	 */
	@Override
	public void keyReleased(GlobalKeyEvent event) {}

	/**
	 * Release Keyboard Hook
	 */
	private void releaseKeyboardHook() {
		if (keyboardHook != null) {
			keyboardHook.shutdownHook();
		}
	}

	// Getters / Setter

	public static boolean getStopMacro() {
		return stopMacro;
	}

	public static void setStopMacro(boolean value) {
		stopMacro = value;
	}

	public static int getMacroDelay() {
		return macroDelay;
	}

	public static String getCraftFilePath() {
		return craftFilePath;
	}

	public static int getMacroStep() {
		return macroStep;
	}

	public static boolean getCbAdvanced() {
		return cbAdvancedValue;
	}

	public static boolean getCbAuto() {
		return cbAutoValue;
	}

	public static boolean getCbNotif() {
		return cbNotifValue;
	}

	public static double getSlVolume() {
		return slVolumeValue;
	}

	public static String getSetUpFilePath() {
		return setUpFilePath;
	}

	public static boolean getCbFood() {
		return cbFoodValue;
	}

	public static int getFoodStep() {
		return foodStep;
	}

	public static String getFoodFilePath() {
		return foodFilePath;
	}

	public static boolean getCbRepair() {
		return cbRepairValue;
	}

	public static int getRepairStep() {
		return repairStep;
	}

	public static String getRepairFilePath() {
		return repairFilePath;
	}

	public static boolean getCbMateria() {
		return cbMateriaValue;
	}

	public static int getMateriaStep() {
		return materiaStep;
	}

	public static String getMateriaFilePath() {
		return materiaFilePath;
	}

}
