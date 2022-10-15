package fr.my.home.ffxivgametime.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * MenuController
 * 
 * @version 1.0
 */
public class MenuController {
	private static Logger logger = LogManager.getLogger(MenuController.class);

	// Attributes

	@SuppressWarnings("unused")
	private MyApp myApp;

	// Components

	@FXML
	private Button btnAntiAfk;

	@FXML
	private Button btnMacro;

	@FXML
	private Button btnSettings;

	@FXML
	private Button btnHelp;

	@FXML
	private Button btnUpdate;

	/**
	 * Constructor
	 */
	public MenuController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Menu <-");
	}

	/**
	 * Display Anti-AFK
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displayAntiAfk() throws IOException {
		// Switch scene
		MyApp.switchScene("antiafk");
	}

	/**
	 * Display Macro
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displayMacro() throws IOException {
		// Switch scene
		MyApp.switchScene("macro");
	}

	/**
	 * Display Settings
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displaySettings() throws IOException {
		Scene dialogScene = new Scene(MyApp.loadFXML("settings"), 600, 400);
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(MyApp.getStage());
		dialog.setTitle("FFXIV GameTime - Paramètres");
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
	 * Display Help
	 * 
	 * @throws IOException
	 */
	@FXML
	private void displayHelp() throws IOException {
		Scene dialogScene = new Scene(MyApp.loadFXML("help"), 600, 400);
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(MyApp.getStage());
		dialog.setTitle("FFXIV GameTime - Aide");
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
	 * Display Update
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@FXML
	private void displayUpdate() throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URL("https://github.com/Jo44/ffxiv-gametime").toURI());
	}

	/**
	 * Called by main application to give reference back to itself
	 * 
	 * @param myApp
	 */
	public void setMainApp(MyApp myApp) {
		this.myApp = myApp;
	}

}
