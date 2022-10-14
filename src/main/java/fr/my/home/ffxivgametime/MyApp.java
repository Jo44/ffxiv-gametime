package fr.my.home.ffxivgametime;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.controller.AntiAfkController;
import fr.my.home.ffxivgametime.controller.MacroController;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FFXIV GameTime (JavaFX App)
 * 
 * @version 1.1
 */
public class MyApp extends Application {
	private static Logger logger = LogManager.getLogger(MyApp.class);

	//////////
	// TODO //
	//////////
	// Next Updates :
	// - Real key name from keyboard instead of ID
	/////////////

	// Attributes

	private static Stage stage;
	private static FXMLLoader fxmlLoader;

	/**
	 * Constructor
	 */
	public MyApp() {
		super();
	}

	/**
	 * Launch
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Start
	 * 
	 * @param stage
	 * @throws IOException
	 */
	@Override
	public void start(Stage stage) throws IOException {
		logger.info("#####   FFXIV GameTime   #####");
		// Init settings
		new Settings();
		// Init scene
		Scene scene = new Scene(loadFXML("menu"), 350, 400);
		stage.setTitle("FFXIV GameTime - Menu");
		stage.getIcons().add(new Image(MyApp.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/icon.png")));
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		MyApp.stage = stage;
	}

	/**
	 * Switch scene
	 * 
	 * @param scene
	 * @throws IOException
	 */
	public static void switchScene(String scene) throws IOException {
		stage.hide();
		Scene newScene = null;
		// Init scene
		switch (scene) {
			case "antiafk":
				// Anti-AFK
				newScene = new Scene(loadFXML("antiafk"), 350, 400);
				stage.setTitle("FFXIV GameTime - Anti-AFK");
				AntiAfkController antiAfkController = fxmlLoader.getController();
				stage.setOnCloseRequest(e -> antiAfkController.shutdown());
				break;
			case "macro":
				// Macro
				newScene = new Scene(loadFXML("macro"), 600, 600);
				stage.setTitle("FFXIV GameTime - Macro");
				MacroController macroController = fxmlLoader.getController();
				stage.setOnCloseRequest(e -> macroController.shutdown());
				break;
			case "menu":
				// Menu
				newScene = new Scene(loadFXML("menu"), 350, 400);
				stage.setTitle("FFXIV GameTime - Menu");
				break;
		}
		stage.setScene(newScene);
		stage.show();
	}

	/**
	 * Load FXML
	 * 
	 * @param fxml
	 * @return Parent
	 * @throws IOException
	 */
	public static Parent loadFXML(String fxml) throws IOException {
		fxmlLoader = new FXMLLoader(MyApp.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * Stop
	 * 
	 * @throws Exception
	 */
	@Override
	public void stop() throws Exception {
		logger.info("#####   Fermeture   #####");
	}

	/**
	 * Return Stage
	 * 
	 * @return Stage
	 */
	public static Stage getStage() {
		return stage;
	}

}
