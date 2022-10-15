package fr.my.home.ffxivgametime.controller;

import java.awt.MouseInfo;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

/**
 * MacroController
 * 
 * @version 1.3
 */
public class MacroController implements GlobalKeyListener {
	private static Logger logger = LogManager.getLogger(MacroController.class);

	// Attributes

	@SuppressWarnings("unused")
	private MyApp myApp;
	private GlobalKeyboardHook keyboardHook;
	private static boolean stopMacro = true;
	private static int kbMacroExec = 0;
	private static int kbMacroMousePos = 0;
	private static int macroDelay = 3;
	private static String craftFilePath = "";
	private static int macroStep = 10;
	private static boolean cbAdvancedValue = false;
	private static String setUpFilePath = "";
	private static boolean cbFoodValue = false;
	private static int foodStep = 25;
	private static String foodFilePath = "";
	private static boolean cbRepairValue = false;
	private static int repairStep = 25;
	private static String repairFilePath = "";
	private static boolean cbMateriaValue = false;
	private static int materiaStep = 25;
	private static String materiaFilePath = "";

	// Components

	@FXML
	private ToggleButton tbMacro;

	@FXML
	private Spinner<Integer> spMacroDelay;

	@FXML
	private Button btnCraftSelect;

	@FXML
	private Button btnCraftFavGet;

	@FXML
	private Button btnCraftFavSet;

	@FXML
	private Label lbCraftFilePath;

	@FXML
	private Spinner<Integer> spMacroStep;

	@FXML
	private Label lbMacroIterationLeft;

	@FXML
	private Label lbMacroTimeLeft;

	@FXML
	private CheckBox cbAdvanced;

	@FXML
	private Button btnSetUpSelect;

	@FXML
	private Button btnSetUpFavGet;

	@FXML
	private Button btnSetUpFavSet;

	@FXML
	private Label lbSetUpFilePath;

	@FXML
	private CheckBox cbFood;

	@FXML
	private Spinner<Integer> spFoodStep;

	@FXML
	private Button btnFoodSelect;

	@FXML
	private Button btnFoodFavGet;

	@FXML
	private Button btnFoodFavSet;

	@FXML
	private Label lbFoodFilePath;

	@FXML
	private CheckBox cbRepair;

	@FXML
	private Spinner<Integer> spRepairStep;

	@FXML
	private Button btnRepairSelect;

	@FXML
	private Button btnRepairFavGet;

	@FXML
	private Button btnRepairFavSet;

	@FXML
	private Label lbRepairFilePath;

	@FXML
	private CheckBox cbMateria;

	@FXML
	private Spinner<Integer> spMateriaStep;

	@FXML
	private Button btnMateriaSelect;

	@FXML
	private Button btnMateriaFavGet;

	@FXML
	private Button btnMateriaFavSet;

	@FXML
	private Label lbMateriaFilePath;

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
		// Init settings
		kbMacroExec = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindMacroExec());
		kbMacroMousePos = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindMacroMousePos());
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
		// Init fav filepath
		try {
			craftFavGet();
			setUpFavGet();
			foodFavGet();
			repairFavGet();
			materiaFavGet();
		} catch (IOException ioe) {
			logger.error(ioe);
			ioe.printStackTrace();
		}
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
				lbMacroIterationLeft.setStyle("-fx-text-fill: #5df921;");
				lbMacroTimeLeft.setText("calcul en cours ...");
				// Init Updater (for UI updates)
				MacroUpdater macroUpdater = new MacroUpdater() {
					@Override
					public void run() {
						updateUI(iterationLeft, timeLeft);
					}
				};
				// Launch dedicated thread, execution controlled by boolean stopMacro
				stopMacro = false;
				Thread threadMacro = new Thread(new Macro(macroUpdater));
				threadMacro.start();
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
	 */
	private void updateUI(int iterationLeft, String timeLeft) {
		// Set values
		lbMacroIterationLeft.setText(String.valueOf(iterationLeft));
		lbMacroTimeLeft.setText(timeLeft);
		// Set colors
		if (iterationLeft < 1) {
			lbMacroIterationLeft.setStyle("-fx-text-fill: #d32121;");
		} else {
			lbMacroIterationLeft.setStyle("-fx-text-fill: #5df921;");
		}
		// Set toggle button
		tbMacro.setSelected(!stopMacro);
	}

	/**
	 * Select Craft
	 * 
	 * @throws IOException
	 */
	@FXML
	private void craftSelect() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void craftFavGet() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void craftFavSet() throws IOException {
		// If filename contains ".mgt"
		if (lbCraftFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			craftFilePath = lbCraftFilePath.getText();
			// Write settings.cfg if file path is valid
			if (craftFilePath != null && craftFilePath.length() >= 8 && !craftFilePath.startsWith("...")) {
				Settings.writeSettings(Settings.getFocusApp(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						craftFilePath, Settings.getSetUpFavFile(), Settings.getFoodFavFile(), Settings.getRepairFavFile(),
						Settings.getMateriaFavFile());
			}
		}
	}

	/**
	 * Toggle Advanced
	 */
	@FXML
	private void toggleAdvanced() {
		if (cbAdvanced.isSelected()) {
			// Advanced ON
			cbAdvancedValue = true;
			// Enable food / repair / materia
			cbFood.setDisable(false);
			cbRepair.setDisable(false);
			cbMateria.setDisable(false);
		} else {
			// Advanced OFF
			cbAdvancedValue = false;
			// Disable food / repair / materia
			cbFoodValue = false;
			cbRepairValue = false;
			cbMateriaValue = false;
			cbFood.setSelected(false);
			cbFood.setDisable(true);
			cbRepair.setSelected(false);
			cbRepair.setDisable(true);
			cbMateria.setSelected(false);
			cbMateria.setDisable(true);
		}
	}

	/**
	 * Select Set Up
	 * 
	 * @throws IOException
	 */
	@FXML
	private void setUpSelect() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void setUpFavGet() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void setUpFavSet() throws IOException {
		// If filename contains ".mgt"
		if (lbSetUpFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			setUpFilePath = lbSetUpFilePath.getText();
			// Write settings.cfg if file path is valid
			if (setUpFilePath != null && setUpFilePath.length() >= 8 && !setUpFilePath.startsWith("...")) {
				Settings.writeSettings(Settings.getFocusApp(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						Settings.getCraftFavFile(), setUpFilePath, Settings.getFoodFavFile(), Settings.getRepairFavFile(),
						Settings.getMateriaFavFile());
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void foodSelect() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void foodFavGet() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void foodFavSet() throws IOException {
		// If filename contains ".mgt"
		if (lbFoodFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			foodFilePath = lbFoodFilePath.getText();
			// Write settings.cfg if file path is valid
			if (foodFilePath != null && foodFilePath.length() >= 8 && !foodFilePath.startsWith("...")) {
				Settings.writeSettings(Settings.getFocusApp(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						Settings.getCraftFavFile(), Settings.getSetUpFavFile(), foodFilePath, Settings.getRepairFavFile(),
						Settings.getMateriaFavFile());
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void repairSelect() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void repairFavGet() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void repairFavSet() throws IOException {
		// If filename contains ".mgt"
		if (lbRepairFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			repairFilePath = lbRepairFilePath.getText();
			// Write settings.cfg if file path is valid
			if (repairFilePath != null && repairFilePath.length() >= 8 && !repairFilePath.startsWith("...")) {
				Settings.writeSettings(Settings.getFocusApp(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						Settings.getCraftFavFile(), Settings.getSetUpFavFile(), Settings.getFoodFavFile(), repairFilePath,
						Settings.getMateriaFavFile());
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void materiaSelect() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void materiaFavGet() throws IOException {
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void materiaFavSet() throws IOException {
		// If filename contains ".mgt"
		if (lbMateriaFilePath.getText().indexOf(".mgt") != -1) {
			// Get file path
			materiaFilePath = lbMateriaFilePath.getText();
			// Write settings.cfg if file path is valid
			if (materiaFilePath != null && materiaFilePath.length() >= 8 && !materiaFilePath.startsWith("...")) {
				Settings.writeSettings(Settings.getFocusApp(), Settings.getKeybindAntiAfkExec(), Settings.getKeybindAntiAfkAction(),
						Settings.getKeybindMacroExec(), Settings.getKeybindMacroMousePos(), Settings.getKeybindClose(), Settings.getKeybindConfirm(),
						Settings.getCraftFavFile(), Settings.getSetUpFavFile(), Settings.getFoodFavFile(), Settings.getRepairFavFile(),
						materiaFilePath);
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
				if (event.getVirtualKeyCode() == kbMacroExec) {
					// Macro
					if (tbMacro.isSelected()) {
						tbMacro.setSelected(false);
					} else {
						tbMacro.setSelected(true);
					}
					// Action
					macroExec();
				}
				if (event.getVirtualKeyCode() == kbMacroMousePos) {
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

	/**
	 * Called by main application to give reference back to itself
	 * 
	 * @param myApp
	 */
	public void setMainApp(MyApp myApp) {
		this.myApp = myApp;
	}

	// Getters / Setters

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
