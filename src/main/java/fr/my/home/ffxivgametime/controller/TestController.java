package fr.my.home.ffxivgametime.controller;

import java.io.IOException;
import java.lang.Thread.State;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import fr.my.home.ffxivgametime.task.Test;
import fr.my.home.ffxivgametime.task.TestUpdater;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

/**
 * TestController
 * 
 * @version 1.0
 */
public class TestController {
	private static Logger logger = LogManager.getLogger(TestController.class);

	private Thread taskThread;
	private static boolean stopTest = true;

	// Components

	@FXML
	private ToggleButton tbTest;

	@FXML
	private TextArea taResult;

	@FXML
	private Button btnTestMenu;

	/**
	 * Constructor
	 */
	public TestController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Test <-");
		// Init textarea to append
		taResult.setText("");
	}

	/**
	 * Close
	 */
	public void shutdown() {
		// Stop task
		stopTest = true;
	}

	/**
	 * Exec Test
	 */
	@FXML
	private void testExec() {
		if (tbTest.isSelected()) {
			// Init Updater (for UI updates)
			TestUpdater testUpdater = new TestUpdater() {
				@Override
				public void run() {
					updateUI(message);
				}
			};
			// Thread execution controlled by boolean stopTest
			stopTest = false;
			// Check thread state
			if (taskThread == null || taskThread.getState() == State.TERMINATED) {
				// Launch dedicated daemon thread
				taskThread = new Thread(new Test(testUpdater));
				taskThread.setDaemon(true);
				taskThread.start();
			}
		} else {
			// Stop
			stopTest = true;
		}
	}

	/**
	 * Update UI
	 * 
	 * @param newMessage
	 */
	private void updateUI(String newMessage) {
		// Set new message
		taResult.appendText(newMessage + "\n");
		// Set toggle button
		tbTest.setSelected(!stopTest);
	}

	/**
	 * Display Menu
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displayMenu() throws IOException {
		// Stop task
		shutdown();
		// Switch scene
		MyApp.switchScene("menu");
	}

	// Getter / Setter

	public static boolean getStopTest() {
		return stopTest;
	}

	public static void setStopTest(boolean value) {
		stopTest = value;
	}

}
