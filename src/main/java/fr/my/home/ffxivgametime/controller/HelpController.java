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
 * @version 1.2
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
		String str1 = "Les fichiers de macro doivent être enregistrés au format";
		helpMessage1.setText(str1.toString());
		String str2 = "*.mgt";
		helpMessage2.setText(str2.toString());
		String str3 = "et respecter la";
		helpMessage3.setText(str3.toString());
		String str4 = "syntaxe";
		helpMessage4.setText(str4.toString());
		String str5 = ".";
		helpMessage5.setText(str5.toString());
		StringBuilder sb1 = new StringBuilder();
		sb1.append("Les paramètres Final Fantasy XIV doivent être configurés en adéquation avec les raccourcis");
		sb1.append("\ndu jeu pour pouvoir utiliser les fonctionnalitées avancées des macros de craft .");
		helpMessage6.setText(sb1.toString());
		String str6 = "( Echap > Attribution des touches > Système > Fermer la fenêtre / Sélectionner )";
		helpMessage7.setText(str6.toString());
		String str7 = "Syntaxe :";
		helpMessage8.setText(str7.toString());
		StringBuilder sb2 = new StringBuilder();
		sb2.append("START");
		sb2.append("\nmousemove(x,y)");
		sb2.append("\nmouseclick(left/right)");
		sb2.append("\nkeypress([m,]k)");
		sb2.append("\nkeypresstime([m,]k,ms)");
		sb2.append("\nsleep(s)");
		sb2.append("\nsleeprng(s)");
		sb2.append("\nSTOP");
		helpMessage9.setText(sb2.toString());
		String str8 = "Paramètres :";
		helpMessage10.setText(str8.toString());
		StringBuilder sb3 = new StringBuilder();
		sb3.append("x / y  ►  coordonnées de la souris");
		sb3.append("\nm / k  ►  labels touches clavier");
		sb3.append("\ns / ms  ►  temps en secondes / millisecondes");
		helpMessage11.setText(sb3.toString());
		String str9 = "Labels touches clavier :";
		helpMessage12.setText(str9.toString());
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
