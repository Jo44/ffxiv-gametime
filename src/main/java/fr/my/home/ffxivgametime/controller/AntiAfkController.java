package fr.my.home.ffxivgametime.controller;

import java.io.IOException;
import java.lang.Thread.State;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import fr.my.home.ffxivgametime.controller.type.AntiAfkMethod;
import fr.my.home.ffxivgametime.task.AntiAfk;
import fr.my.home.ffxivgametime.tools.KeyboardStrokeMap;
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
 * @version 1.4
 */
public class AntiAfkController implements GlobalKeyListener {
	private static Logger logger = LogManager.getLogger(AntiAfkController.class);

	private Thread taskThread;
	private GlobalKeyboardHook keyboardHook;
	private static boolean stopAntiAfk = true;
	private static final int KB_ANTI_AFK_EXEC = KeyboardStrokeMap.getKeyEvent(Settings.getKeybindAntiAfkExec());
	private static int antiAfkDelay = 3;
	private static int antiAfkMin = 2;
	private static int antiAfkMax = 5;
	private static AntiAfkMethod antiAfkMethod = AntiAfkMethod.ACTION;

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
				// Thread execution controlled by boolean stopAntiAfk
				stopAntiAfk = false;
				// Check thread state
				if (taskThread == null || taskThread.getState() == State.TERMINATED) {
					// Launch dedicated daemon thread
					taskThread = new Thread(new AntiAfk());
					taskThread.setDaemon(true);
					taskThread.start();
				}
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
	 */
	@FXML
	private void antiAfkMethod() {
		switch (cbAntiAfkMethod.getValue()) {
			case "Action":
				antiAfkMethod = AntiAfkMethod.ACTION;
				break;
			case "Mixte":
				antiAfkMethod = AntiAfkMethod.MIX;
				break;
			case "Déplacement":
				antiAfkMethod = AntiAfkMethod.MOVE;
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
				if (event.getVirtualKeyCode() == KB_ANTI_AFK_EXEC) {
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

	public static AntiAfkMethod getAntiAfkMethod() {
		return antiAfkMethod;
	}

}
