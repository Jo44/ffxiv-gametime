package fr.my.home.ffxivgametime.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * SettingsController
 * 
 * @version 1.3
 */
public class SettingsController {
	private static Logger logger = LogManager.getLogger(SettingsController.class);

	// Attributes

	private String focusApp;
	private String antiAfkExecValue;
	private String antiAfkActionValue;
	private String macroExecValue;
	private String macroMousePosValue;
	private String closeValue;
	private String confirmValue;
	private String craftFavFileValue;
	private String setUpFavFileValue;
	private String foodFavFileValue;
	private String repairFavFileValue;
	private String materiaFavFileValue;

	// Components

	@FXML
	private Pane pane;

	@FXML
	private TextField selectedFocus;

	@FXML
	private TextField antiAfkExec;

	@FXML
	private TextField antiAfkAction;

	@FXML
	private TextField macroExec;

	@FXML
	private TextField macroMousePos;

	@FXML
	private TextField close;

	@FXML
	private TextField confirm;

	@FXML
	private Button keyboard;

	@FXML
	private Button save;

	@FXML
	private Button reinit;

	@FXML
	private Label message;

	/**
	 * Constructor
	 */
	public SettingsController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Settings <-");

		// Init settings values
		focusApp = Settings.getFocusApp();
		antiAfkExecValue = Settings.getKeybindAntiAfkExec();
		antiAfkActionValue = Settings.getKeybindAntiAfkAction();
		macroExecValue = Settings.getKeybindMacroExec();
		macroMousePosValue = Settings.getKeybindMacroMousePos();
		closeValue = Settings.getKeybindClose();
		confirmValue = Settings.getKeybindConfirm();
		craftFavFileValue = Settings.getCraftFavFile();
		setUpFavFileValue = Settings.getSetUpFavFile();
		foodFavFileValue = Settings.getFoodFavFile();
		repairFavFileValue = Settings.getRepairFavFile();
		materiaFavFileValue = Settings.getMateriaFavFile();

		// Display settings values
		selectedFocus.setText(focusApp);
		antiAfkExec.setText(antiAfkExecValue);
		antiAfkAction.setText(antiAfkActionValue);
		macroExec.setText(macroExecValue);
		macroMousePos.setText(macroMousePosValue);
		close.setText(closeValue);
		confirm.setText(confirmValue);

		// Unfocus textfield
		Platform.runLater(() -> pane.requestFocus());

	}

	/**
	 * Display Keyboard
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displayKeyboard() throws IOException {
		Scene dialogScene = new Scene(MyApp.loadFXML("keyboard"), 600, 400);
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(MyApp.getStage());
		dialog.setTitle("FFXIV GameTime - Clavier");
		dialog.getIcons().add(new Image(MyApp.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/icon.png")));
		dialog.setResizable(false);
		dialog.setScene(dialogScene);
		// Calculate the center position of the parent Stage
		double centerXPosition = MyApp.getStage().getX() + MyApp.getStage().getWidth() / 2d;
		double centerYPosition = MyApp.getStage().getY() + MyApp.getStage().getHeight() / 2d;
		// Hide the pop-up stage before it is shown and becomes relocated
		dialog.setOnShowing(ev -> dialog.hide());
		// Relocate the pop-up Stage
		dialog.setOnShown(ev -> {
			dialog.setX(centerXPosition - dialog.getWidth() / 2d);
			dialog.setY(centerYPosition - dialog.getHeight() / 2d);
			dialog.show();
		});
		dialog.show();
	}

	/**
	 * Action Save
	 * 
	 * @throws IOException
	 */
	@FXML
	private void actionSave() throws IOException {
		// Save settings
		saveSettings();
		// Update message
		message.setText("Les paramètres ont été correctement sauvegardés");
	}

	/**
	 * Save settings
	 */
	private void saveSettings() {
		Settings.writeSettings(focusApp, antiAfkExecValue, antiAfkActionValue, macroExecValue, macroMousePosValue, closeValue, confirmValue,
				craftFavFileValue, setUpFavFileValue, foodFavFileValue, repairFavFileValue, materiaFavFileValue);
	}

	/**
	 * Action Reinit
	 * 
	 * @throws IOException
	 */
	@FXML
	private void actionReinit() throws IOException {
		// Reinit settings
		focusApp = "Final Fantasy XIV";
		antiAfkExecValue = "F5";
		antiAfkActionValue = "Espace";
		macroExecValue = "F6";
		macroMousePosValue = "F7";
		closeValue = "Echap";
		confirmValue = "Num 0";
		// Write settings
		Settings.writeSettings(focusApp, antiAfkExecValue, antiAfkActionValue, macroExecValue, macroMousePosValue, closeValue, confirmValue,
				craftFavFileValue, setUpFavFileValue, foodFavFileValue, repairFavFileValue, materiaFavFileValue);
		// Set UI
		selectedFocus.setText(focusApp);
		antiAfkExec.setText(antiAfkExecValue);
		antiAfkAction.setText(antiAfkActionValue);
		macroExec.setText(macroExecValue);
		macroMousePos.setText(macroMousePosValue);
		close.setText(closeValue);
		confirm.setText(confirmValue);
		message.setText("Les paramètres ont été correctement réinitialisés");
	}

}
