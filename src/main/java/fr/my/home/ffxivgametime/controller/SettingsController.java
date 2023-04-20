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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * SettingsController
 * 
 * @version 1.4
 */
public class SettingsController {
	private static Logger logger = LogManager.getLogger(SettingsController.class);

	private static String appFocus = Settings.getAppFocus();
	private static String antiAfkExecValue = Settings.getKeybindAntiAfkExec();
	private static String antiAfkActionValue = Settings.getKeybindAntiAfkAction();
	private static String macroExecValue = Settings.getKeybindMacroExec();
	private static String macroMousePosValue = Settings.getKeybindMacroMousePos();
	private static String closeValue = Settings.getKeybindClose();
	private static String confirmValue = Settings.getKeybindConfirm();
	private static String gearModValue = String.valueOf(Settings.getGearMod());
	private static String gearFromXValue = String.valueOf(Settings.getGearFromX());
	private static String gearFromYValue = String.valueOf(Settings.getGearFromY());
	private static String gearOffsetXValue = String.valueOf(Settings.getGearOffsetX());
	private static String gearOffsetYValue = String.valueOf(Settings.getGearOffsetY());
	private static final String CRAFT_FAV_FILE = Settings.getCraftFavFile();
	private static final String SET_UP_FAV_FILE = Settings.getSetUpFavFile();
	private static final String FOOD_FAV_FILE = Settings.getFoodFavFile();
	private static final String REPAIR_FAV_FILE = Settings.getRepairFavFile();
	private static final String MATERIA_FAV_FILE = Settings.getMateriaFavFile();

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
	private CheckBox gearMod;

	@FXML
	private TextField gearFromX;

	@FXML
	private TextField gearFromY;

	@FXML
	private TextField gearOffsetX;

	@FXML
	private TextField gearOffsetY;

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
		// Set UI
		setUI();
	}

	/**
	 * Set UI values
	 */
	private void setUI() {
		// Set UI
		selectedFocus.setText(appFocus);
		antiAfkExec.setText(antiAfkExecValue);
		antiAfkAction.setText(antiAfkActionValue);
		macroExec.setText(macroExecValue);
		macroMousePos.setText(macroMousePosValue);
		close.setText(closeValue);
		confirm.setText(confirmValue);
		if (gearModValue.equals("true")) {
			gearMod.setSelected(true);
		} else {
			gearMod.setSelected(false);
		}
		gearFromX.setText(gearFromXValue);
		gearFromY.setText(gearFromYValue);
		gearOffsetX.setText(gearOffsetXValue);
		gearOffsetY.setText(gearOffsetYValue);
		// Trick to unfocus textfield
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
	 */
	@FXML
	private void actionSave() {
		// Update settings
		appFocus = selectedFocus.getText().trim();
		antiAfkExecValue = antiAfkExec.getText().trim();
		antiAfkActionValue = antiAfkAction.getText().trim();
		macroExecValue = macroExec.getText().trim();
		macroMousePosValue = macroMousePos.getText().trim();
		closeValue = close.getText().trim();
		confirmValue = confirm.getText().trim();
		gearModValue = String.valueOf(gearMod.isSelected());
		gearFromXValue = gearFromX.getText().trim();
		gearFromYValue = gearFromY.getText().trim();
		gearOffsetXValue = gearOffsetX.getText().trim();
		gearOffsetYValue = gearOffsetY.getText().trim();
		// Save settings
		if (Settings.saveSettings(appFocus, antiAfkExecValue, antiAfkActionValue, macroExecValue, macroMousePosValue, closeValue, confirmValue,
				gearModValue, gearFromXValue, gearFromYValue, gearOffsetXValue, gearOffsetYValue, CRAFT_FAV_FILE, SET_UP_FAV_FILE, FOOD_FAV_FILE,
				REPAIR_FAV_FILE, MATERIA_FAV_FILE)) {
			message.setText("Les paramètres ont été correctement sauvegardés");
		} else {
			message.setText("Les paramètres actuels ne sont pas valides");
		}
	}

	/**
	 * Action Reinit
	 */
	@FXML
	private void actionReinit() {
		// Reinit settings
		appFocus = "Final Fantasy XIV";
		antiAfkExecValue = "F5";
		antiAfkActionValue = "Espace";
		macroExecValue = "F6";
		macroMousePosValue = "F7";
		closeValue = "Echap";
		confirmValue = "Num 0";
		gearModValue = "false";
		gearFromXValue = String.valueOf(1706);
		gearFromYValue = String.valueOf(897);
		gearOffsetXValue = String.valueOf(18);
		gearOffsetYValue = String.valueOf(6);
		// Save settings
		if (Settings.saveSettings(appFocus, antiAfkExecValue, antiAfkActionValue, macroExecValue, macroMousePosValue, closeValue, confirmValue,
				gearModValue, gearFromXValue, gearFromYValue, gearOffsetXValue, gearOffsetYValue, CRAFT_FAV_FILE, SET_UP_FAV_FILE, FOOD_FAV_FILE,
				REPAIR_FAV_FILE, MATERIA_FAV_FILE)) {
			// Set UI
			setUI();
			message.setText("Les paramètres ont été correctement réinitialisés");
		} else {
			message.setText("Une erreur est survenue");
		}
	}

}
