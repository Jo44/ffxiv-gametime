package fr.my.home.ffxivgametime.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * HelpController
 * 
 * @version 1.3
 */
public class HelpController {
	private static Logger logger = LogManager.getLogger(HelpController.class);

	// Components

	@FXML
	private Label helpMessage1;

	@FXML
	private Label helpMessage2;

	@FXML
	private Label helpMessage3;

	@FXML
	private Label helpMessage4;

	@FXML
	private Label helpMessage5;

	@FXML
	private Label helpMessage6;

	@FXML
	private Label helpMessage7;

	@FXML
	private Label helpMessage8;

	@FXML
	private Label helpMessage9;

	@FXML
	private Label helpMessage10;

	@FXML
	private Label helpMessage11;

	@FXML
	private Label helpMessage12;

	@FXML
	private Button keyboard;

	/**
	 * Constructor
	 */
	public HelpController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Help <-");
		// Init Help Messages
		helpMessage1.setText("Les fichiers de macro doivent être enregistrés au format");
		helpMessage2.setText("*.mgt");
		helpMessage3.setText("et respecter la");
		helpMessage4.setText("syntaxe");
		helpMessage5.setText(".");
		StringBuilder sb = new StringBuilder("Les paramètres Final Fantasy XIV doivent être configurés en adéquation avec les raccourcis");
		sb.append("\ndu jeu pour pouvoir utiliser les fonctionnalitées avancées des macros de craft .");
		helpMessage6.setText(sb.toString());
		helpMessage7.setText("( Echap > Attribution des touches > Système > Fermer la fenêtre / Sélectionner )");
		helpMessage8.setText("Syntaxe :");
		sb = new StringBuilder("START");
		sb.append("\nmousemove(x,y)");
		sb.append("\nmouseclick(left/right)");
		sb.append("\nkeypress([m,]k)");
		sb.append("\nkeypresstime([m,]k,ms)");
		sb.append("\nsleep(s)");
		sb.append("\nsleeprng(s)");
		sb.append("\nSTOP");
		helpMessage9.setText(sb.toString());
		helpMessage10.setText("Paramètres :");
		sb = new StringBuilder("x / y  ►  coordonnées de la souris");
		sb.append("\nm / k  ►  labels touches clavier");
		sb.append("\ns / ms  ►  temps en secondes / millisecondes");
		helpMessage11.setText(sb.toString());
		helpMessage12.setText("Labels touches clavier :");
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

}
