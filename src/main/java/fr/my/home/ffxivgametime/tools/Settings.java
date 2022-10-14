package fr.my.home.ffxivgametime.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Settings
 * 
 * @version 1.1
 */
public class Settings {
	private static Logger logger = LogManager.getLogger(Settings.class);

	// Attributes

	private static final File SETTINGS_FILE = new File(System.getenv("APPDATA") + "\\ffxiv-gametime\\settings.cfg");
	private static Properties properties;

	/**
	 * Load settings from file
	 */
	static {
		properties = new Properties();
		logger.info("Chargement des parametres ..");
		try {
			if (SETTINGS_FILE != null && SETTINGS_FILE.exists() && SETTINGS_FILE.isFile() && SETTINGS_FILE.canRead()) {
				// Load settings from file
				FileInputStream fis = new FileInputStream(SETTINGS_FILE.getAbsolutePath());
				properties.load(fis);
				fis.close();
				// Check properties
				checkProperties();
				logger.info("-> fichier settings.cfg charge avec succes");
			} else {
				throw new IOException();
			}
		} catch (IOException ioe) {
			// Write default settings in file
			writeSettings("Final Fantasy XIV", 116, 32, 117, 118, 27, 96, "", "", "", "", "");
		}
	}

	/**
	 * Check properties
	 * 
	 * @throws IOException
	 */
	private static void checkProperties() throws IOException {
		if (properties.getProperty("keybind.antiafk.exec") == null || properties.getProperty("keybind.antiafk.exec").trim().isEmpty()
				|| properties.getProperty("keybind.antiafk.action") == null || properties.getProperty("keybind.antiafk.action").trim().isEmpty()
				|| properties.getProperty("keybind.macro.exec") == null || properties.getProperty("keybind.macro.exec").trim().isEmpty()
				|| properties.getProperty("keybind.macro.mousepos") == null || properties.getProperty("keybind.macro.mousepos").trim().isEmpty()
				|| properties.getProperty("keybind.close") == null || properties.getProperty("keybind.close").trim().isEmpty()
				|| properties.getProperty("keybind.confirm") == null || properties.getProperty("keybind.confirm").trim().isEmpty()) {
			throw new IOException();
		}
	}

	/**
	 * Write settings in file
	 * 
	 * @param focusApp
	 * @param kbAntiAfkExec
	 * @param kbAntiAfkAction
	 * @param kbMacroExec
	 * @param kbMacroMousePos
	 * @param kbClose
	 * @param kbConfirm
	 * @param craftFavFile
	 * @param setUpFavFile
	 * @param foodFavFile
	 * @param repairFavFile
	 * @param materiaFavFile
	 */
	public static void writeSettings(String focusApp, int kbAntiAfkExec, int kbAntiAfkAction, int kbMacroExec, int kbMacroMousePos, int kbClose,
			int kbConfirm, String craftFavFile, String setUpFavFile, String foodFavFile, String repairFavFile, String materiaFavFile) {
		try {
			// Create file settings.cfg
			FileWriter fileWriter = new FileWriter(SETTINGS_FILE.getAbsoluteFile());
			fileWriter.write("### FFXIV GameTime - Fichier de configuration ###");
			fileWriter.append("\nfocus.app=" + focusApp);
			fileWriter.append("\nkeybind.antiafk.exec=" + String.valueOf(kbAntiAfkExec));
			fileWriter.append("\nkeybind.antiafk.action=" + String.valueOf(kbAntiAfkAction));
			fileWriter.append("\nkeybind.macro.exec=" + String.valueOf(kbMacroExec));
			fileWriter.append("\nkeybind.macro.mousepos=" + String.valueOf(kbMacroMousePos));
			fileWriter.append("\nkeybind.close=" + String.valueOf(kbClose));
			fileWriter.append("\nkeybind.confirm=" + String.valueOf(kbConfirm));
			fileWriter.append("\ncraft.fav.file=" + craftFavFile);
			fileWriter.append("\nset.up.fav.file=" + setUpFavFile);
			fileWriter.append("\nfood.fav.file=" + foodFavFile);
			fileWriter.append("\nrepair.fav.file=" + repairFavFile);
			fileWriter.append("\nmateria.fav.file=" + materiaFavFile);
			fileWriter.close();
			logger.info("-> fichier settings.cfg cree/modifie avec succes");
		} catch (IOException ioe) {
			// Can't create/update file settings.cfg
			logger.error("-> impossible de creer/modifier le fichier settings.cfg");
		} finally {
			// Load values
			properties.setProperty("focus.app", focusApp);
			properties.setProperty("keybind.antiafk.exec", String.valueOf(kbAntiAfkExec));
			properties.setProperty("keybind.antiafk.action", String.valueOf(kbAntiAfkAction));
			properties.setProperty("keybind.macro.exec", String.valueOf(kbMacroExec));
			properties.setProperty("keybind.macro.mousepos", String.valueOf(kbMacroMousePos));
			properties.setProperty("keybind.close", String.valueOf(kbClose));
			properties.setProperty("keybind.confirm", String.valueOf(kbConfirm));
			properties.setProperty("craft.fav.file", craftFavFile);
			properties.setProperty("set.up.fav.file", setUpFavFile);
			properties.setProperty("food.fav.file", foodFavFile);
			properties.setProperty("repair.fav.file", repairFavFile);
			properties.setProperty("materia.fav.file", materiaFavFile);
		}
	}

	/**
	 * Getters
	 */

	/**
	 * Get focus app
	 * 
	 * @return String
	 */
	public static String getFocusApp() {
		return properties.getProperty("focus.app");
	}

	/**
	 * Get keybind antiafk : exec
	 * 
	 * @return int
	 */
	public static int getKeybindAntiAfkExec() {
		return Integer.parseInt(properties.getProperty("keybind.antiafk.exec"));
	}

	/**
	 * Get keybind antiafk : action
	 * 
	 * @return int
	 */
	public static int getKeybindAntiAfkAction() {
		return Integer.parseInt(properties.getProperty("keybind.antiafk.action"));
	}

	/**
	 * Get keybind macro : exec
	 * 
	 * @return int
	 */
	public static int getKeybindMacroExec() {
		return Integer.parseInt(properties.getProperty("keybind.macro.exec"));
	}

	/**
	 * Get keybind macro : mousepos
	 * 
	 * @return int
	 */
	public static int getKeybindMacroMousePos() {
		return Integer.parseInt(properties.getProperty("keybind.macro.mousepos"));
	}

	/**
	 * Get keybind close
	 * 
	 * @return int
	 */
	public static int getKeybindClose() {
		return Integer.parseInt(properties.getProperty("keybind.close"));
	}

	/**
	 * Get keybind confirm
	 * 
	 * @return int
	 */
	public static int getKeybindConfirm() {
		return Integer.parseInt(properties.getProperty("keybind.confirm"));
	}

	/**
	 * Get craft fav file
	 * 
	 * @return String
	 */
	public static String getCraftFavFile() {
		return properties.getProperty("craft.fav.file");
	}

	/**
	 * Get set up fav file
	 * 
	 * @return String
	 */
	public static String getSetUpFavFile() {
		return properties.getProperty("set.up.fav.file");
	}

	/**
	 * Get food fav file
	 * 
	 * @return String
	 */
	public static String getFoodFavFile() {
		return properties.getProperty("food.fav.file");
	}

	/**
	 * Get repair fav file
	 * 
	 * @return String
	 */
	public static String getRepairFavFile() {
		return properties.getProperty("repair.fav.file");
	}

	/**
	 * Get materia fav file
	 * 
	 * @return String
	 */
	public static String getMateriaFavFile() {
		return properties.getProperty("materia.fav.file");
	}

}
