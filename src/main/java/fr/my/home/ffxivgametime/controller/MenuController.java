package fr.my.home.ffxivgametime.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.my.home.ffxivgametime.MyApp;
import fr.my.home.ffxivgametime.tools.Settings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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

	private static final String TMP_DIR = "tmp";
	private static final String VERSION_FILE = "ffxiv-gametime.version";
	private static final String VERSION_LINK = "https://raw.githubusercontent.com/Jo44/ffxiv-gametime/main/distrib/ffxiv-gametime.version";
	private static final String UPDATER_FILE = "FFXIV-GameTime-Updater.exe";
	private static String currentVersion;
	private static String newerVersion;

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
		Stage dialog = new Stage();
		dialog.getIcons().add(new Image(MyApp.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/icon.png")));
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(MyApp.getStage());
		dialog.setResizable(false);
		dialog.setScene(new Scene(MyApp.loadFXML("settings"), 600, 400));
		dialog.setTitle("FFXIV GameTime - Paramètres");
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
		Stage dialog = new Stage();
		dialog.getIcons().add(new Image(MyApp.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/icon.png")));
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(MyApp.getStage());
		dialog.setResizable(false);
		dialog.setScene(new Scene(MyApp.loadFXML("help"), 600, 400));
		dialog.setTitle("FFXIV GameTime - Aide");
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
	 * Process Update
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@FXML
	private void processUpdate() throws IOException, URISyntaxException {

		logger.info("-> Update <-");

		// Prepare update
		prepareUpdate();

		try {
			// Check update
			logger.info("Vérification de la version ...");
			boolean updateAvailable = checkUpdate();

			logger.info("-> Version actuelle : v" + currentVersion);
			logger.info("-> Version disponible : v" + newerVersion);

			// If update available
			if (updateAvailable) {

				// Dialog confirm update
				if (displayConfirmDialog("Une nouvelle version est disponible (v" + newerVersion
						+ ") ! Voulez-vous relancer l'application pour télécharger et installer la mise à jour ?")) {

					// Cleanup
					cleanup();

					// Start updater
					logger.info("Lancement de l'updater ...");
					startUpdater();

					// Close updater
					logger.info("Fermeture de l'application.");
					System.exit(0);

				}

			} else {

				// Dialog Up-to-date
				String message = "L'application est à jour (v" + currentVersion + ").";
				logger.info(message);
				displayMessageDialog(message);

			}

		} catch (IOException | InterruptedException ex) {

			logger.error("Une erreur est survenue pendant la mise à jour !");
			logger.error(ex.getMessage());

		} finally {
			// Cleanup
			cleanup();
		}

	}

	/**
	 * Prepare update
	 */
	private void prepareUpdate() {

		// Create tmp dir
		File tmpDir = new File(TMP_DIR);
		if (tmpDir != null && !tmpDir.exists()) {
			tmpDir.mkdir();
		}

	}

	/**
	 * Check if update available
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private boolean checkUpdate() throws IOException {

		boolean updateRequired = false;
		// Download version file
		File versionFile = downloadFile(VERSION_LINK, VERSION_FILE);
		// Get update version
		newerVersion = getNewerVersion(versionFile);
		// Compare to current version
		updateRequired = needUpdate(newerVersion);
		return updateRequired;

	}

	/**
	 * Start updater
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void startUpdater() throws IOException, InterruptedException {

		// Exec runtime
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(UPDATER_FILE);
		process.waitFor();

	}

	/**
	 * Download file from url
	 * 
	 * @param link
	 * @param filename
	 * @return File
	 * @throws IOException
	 */
	private File downloadFile(String link, String filename) throws IOException {

		File downloadFile = new File(TMP_DIR + "\\" + filename);
		// Create connection
		URL url = new URL(link);
		URLConnection con = url.openConnection();
		con.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703)");
		// Get input stream
		InputStream input = con.getInputStream();
		byte[] buffer = new byte[4096];
		int n = -1;
		// Write into output stream
		OutputStream output = new FileOutputStream(downloadFile);
		while ((n = input.read(buffer)) != -1) {
			if (n > 0) {
				output.write(buffer, 0, n);
			}
		}
		output.close();
		// Return file
		return downloadFile;

	}

	/**
	 * Get newer version
	 * 
	 * @param updateFile
	 * @return String
	 * @throws IOException
	 */
	private String getNewerVersion(File updateFile) throws IOException {
		newerVersion = "";
		Path path = Paths.get(updateFile.getAbsolutePath());
		String read = Files.readAllLines(path).get(0);
		if (read != null && !read.trim().isEmpty()) {
			newerVersion = read.substring(read.indexOf("app.version=") + 12);
		}
		return newerVersion;
	}

	/**
	 * Check if new update available
	 * 
	 * @param newVersion
	 * @return boolean
	 * @throws IOException
	 */
	private boolean needUpdate(String newVersion) throws IOException {
		boolean updateAvailable = false;
		currentVersion = Settings.getAppVersion();
		if (!newVersion.equals(currentVersion)) {
			updateAvailable = true;
		}
		return updateAvailable;
	}

	/**
	 * Cleanup
	 */
	private void cleanup() {

		// Delete tmp dir & files
		File tmpDir = new File(TMP_DIR);
		if (tmpDir != null && tmpDir.exists()) {
			File[] files = tmpDir.listFiles();
			if (files != null) {
				for (File file : files) {
					file.delete();
				}
			}
			tmpDir.delete();
		}

	}

	/**
	 * Display Confirm Dialog
	 * 
	 * @param message
	 * @return boolean
	 * @throws IOException
	 */
	private boolean displayConfirmDialog(String message) throws IOException {

		boolean confirmed = false;

		// Dialog
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setContentText(message);
		dialog.setResizable(false);
		dialog.setTitle("Confirmation");

		// Custom DialogPane
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().add(new ButtonType("Oui", ButtonData.YES));
		dialogPane.getButtonTypes().add(new ButtonType("Non", ButtonData.NO));
		dialogPane.getStylesheets().add(MenuController.class.getResource("/fr/my/home/ffxivgametime/style.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");
		((Stage) dialogPane.getScene().getWindow()).getIcons()
				.add(new Image(MenuController.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/icon.png")));

		// Result
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (result.get().getButtonData() == ButtonData.YES) {
				confirmed = true;
			} else {
				confirmed = false;
			}
		}
		return confirmed;

	}

	/**
	 * Display Message Dialog
	 * 
	 * @param message
	 * @throws IOException
	 */
	private void displayMessageDialog(String message) throws IOException {

		// Dialog
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setContentText(message);
		dialog.setResizable(false);
		dialog.setTitle("Information");

		// Custom DialogPane
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().add(new ButtonType("Ok", ButtonData.OK_DONE));
		dialogPane.getStylesheets().add(MenuController.class.getResource("/fr/my/home/ffxivgametime/style.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");
		((Stage) dialogPane.getScene().getWindow()).getIcons()
				.add(new Image(MenuController.class.getResourceAsStream("/fr/my/home/ffxivgametime/img/icon.png")));

		// Show
		dialog.showAndWait();

	}

}
