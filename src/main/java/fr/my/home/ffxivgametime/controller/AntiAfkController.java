package fr.my.home.ffxivgametime.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import fr.my.home.ffxivgametime.task.AntiAfk;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

/**
 * AntiAfkController
 * 
 * @version 1.2
 */
public class AntiAfkController implements GlobalKeyListener {
	private static Logger logger = LogManager.getLogger(AntiAfkController.class);

	// Attributes

	@SuppressWarnings("unused")
	private MyApp myApp;
	private GlobalKeyboardHook keyboardHook;
	private static boolean stopAntiAfk = true;
	private static int kbAfkExec = 0;
	private static int antiAfkDelay = 3;
	private static int antiAfkMin = 2;
	private static int antiAfkMax = 5;
	private static int antiAfkMethod = 0;

	// Components

	@FXML
	private ToggleButton tbAntiAfk;

	@FXML
	private Spinner<Integer> spAntiAfkDelay;

	@FXML
	private Spinner<Integer> spAntiAfkMin;

	@FXML
	private Spinner<Integer> spAntiAfkMax;

	@FXML
	private ComboBox<String> cbAntiAfkMethod;

	@FXML
	private Button btnAntiAfkMenu;

	/**
	 * Constructor
	 */
	public AntiAfkController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Anti-AFK <-");
		// Init Keyboard Hook
		initKeyboardHook();
		// Init settings
		kbAfkExec = Settings.getKeybindAntiAfkExec();
		// Init spinners listeners
		spAntiAfkDelay.editorProperty().get().setAlignment(Pos.CENTER);
		spAntiAfkMin.editorProperty().get().setAlignment(Pos.CENTER);
		spAntiAfkMax.editorProperty().get().setAlignment(Pos.CENTER);
		spAntiAfkDelay.valueProperty().addListener((obs, oldValue, newValue) -> antiAfkDelay = newValue);
		spAntiAfkMin.valueProperty().addListener((obs, oldValue, newValue) -> antiAfkMin = newValue);
		spAntiAfkMax.valueProperty().addListener((obs, oldValue, newValue) -> antiAfkMax = newValue);
	}

	/**
	 * Close
	 */
	public void shutdown() {
		// Stop task
		stopAntiAfk = true;
		// Release Keyboard Hook
		releaseKeyboardHook();
	}

	/**
	 * Exec Anti-Afk
	 */
	@FXML
	private void antiAfkExec() {
		if (tbAntiAfk.isSelected()) {
			// If minDelay <= maxDelay
			if (spAntiAfkMin.getValue() <= spAntiAfkMax.getValue()) {
				// Launch dedicated thread, execution controlled by boolean stopAntiAfk
				stopAntiAfk = false;
				Thread threadAntiAfk = new Thread(new AntiAfk());
				threadAntiAfk.start();
			} else {
				// Turn off toggle button
				tbAntiAfk.setSelected(false);
			}
		} else {
			// Stop
			stopAntiAfk = true;
		}
	}

	/**
	 * Update Method Afk
	 * 
	 * @throws IOException
	 */
	@FXML
	private void antiAfkMethod() throws IOException {
		String value = cbAntiAfkMethod.getValue();
		switch (value) {
			case "Action":
				antiAfkMethod = 0;
				break;
			case "Mixte":
				antiAfkMethod = 1;
				break;
			case "Déplacement":
				antiAfkMethod = 2;
				break;
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
				if (event.getVirtualKeyCode() == kbAfkExec) {
					// Anti-Afk
					if (tbAntiAfk.isSelected()) {
						tbAntiAfk.setSelected(false);
					} else {
						tbAntiAfk.setSelected(true);
					}
					// Action
					antiAfkExec();
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

	// Getters

	public static boolean getStopAntiAfk() {
		return stopAntiAfk;
	}

	public static int getAntiAfkDelay() {
		return antiAfkDelay;
	}

	public static int getAntiAfkMin() {
		return antiAfkMin;
	}

	public static int getAntiAfkMax() {
		return antiAfkMax;
	}

	public static int getAntiAfkMethod() {
		return antiAfkMethod;
	}

}
