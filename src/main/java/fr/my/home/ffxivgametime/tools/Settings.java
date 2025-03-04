package fr.my.home.ffxivgametime.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Settings
 * 
 * @version 1.4
 */
public class Settings {
	private static Logger logger = LogManager.getLogger(Settings.class);

	private static final File SETTINGS_FILE = new File(System.getenv("APPDATA") + "\\ffxiv-gametime\\settings.cfg");
	private static final String APP_VERSION = "2.1";
	private static Properties properties;

	/**
	 * Load settings from file
	 */
	static {
		properties = new Properties();
		logger.info("Chargement des parametres ..");
		try {
			// Check settings file
			if (SETTINGS_FILE != null && SETTINGS_FILE.exists() && SETTINGS_FILE.isFile() && SETTINGS_FILE.canRead()) {
				// Load settings from file
				FileInputStream fis = new FileInputStream(SETTINGS_FILE.getAbsolutePath());
				properties.load(fis);
				fis.close();
				// Check settings from properties
				checkSettingsFromProperties();
				logger.info("-> fichier settings.cfg charge");
			} else {
				logger.error("-> fichier settings.cfg inexistant");
				throw new IOException();
			}
		} catch (Exception ex) {
			logger.error("-> fichier settings.cfg non valide");
			// Write default settings file
			try {
				writeSettings("FINAL FANTASY XIV", "F5", "Espace", "F6", "F7", "Echap", "Num 0", false, 1706, 897, 18, 6, "", "", "", "", "");
				logger.info("-> nouveau fichier settings.cfg cree");
			} catch (IOException ioe) {
				logger.error("-> impossible de creer le nouveau fichier settings.cfg");
			}
		}
	}

	/**
	 * Save settings
	 * 
	 * @param appFocus
	 * @param kbAntiAfkExec
	 * @param kbAntiAfkAction
	 * @param kbMacroExec
	 * @param kbMacroMousePos
	 * @param kbClose
	 * @param kbConfirm
	 * @param gearMod
	 * @param gearFromX
	 * @param gearFromY
	 * @param gearOffsetX
	 * @param gearOffsetY
	 * @param craftFavFile
	 * @param setUpFavFile
	 * @param foodFavFile
	 * @param repairFavFile
	 * @param materiaFavFile
	 * @return boolean
	 */
	public static boolean saveSettings(String appFocus, String kbAntiAfkExec, String kbAntiAfkAction, String kbMacroExec, String kbMacroMousePos,
			String kbClose, String kbConfirm, String gearMod, String gearFromX, String gearFromY, String gearOffsetX, String gearOffsetY,
			String craftFavFile, String setUpFavFile, String foodFavFile, String repairFavFile, String materiaFavFile) {
		boolean saved;
		try {
			// Check settings
			checkSettingsFromUser(gearFromX, gearFromY, gearOffsetX, gearOffsetY);
			// Write settings
			writeSettings(appFocus, kbAntiAfkExec, kbAntiAfkAction, kbMacroExec, kbMacroMousePos, kbClose, kbConfirm, Boolean.parseBoolean(gearMod),
					Integer.parseInt(gearFromX), Integer.parseInt(gearFromY), Integer.parseInt(gearOffsetX), Integer.parseInt(gearOffsetY),
					craftFavFile, setUpFavFile, foodFavFile, repairFavFile, materiaFavFile);
			saved = true;
		} catch (InvalidParameterException ipe) {
			logger.error("-> un ou plusieurs parametres non valides");
			saved = false;
		} catch (IOException ioe) {
			logger.error("-> impossible de modifier le fichier settings.cfg");
			saved = false;
		}
		return saved;
	}

	/**
	 * Check all settings from properties
	 * 
	 * @throws InvalidParameterException
	 */
	private static void checkSettingsFromProperties() throws InvalidParameterException {
		// Check app version
		String appVersion = properties.getProperty("app.version");
		if (appVersion == null || !appVersion.trim().equals(APP_VERSION)) {
			throw new InvalidParameterException();
		}
		// Check integer parameters
		try {
			Integer.parseInt(properties.getProperty("gear.from.x"));
			Integer.parseInt(properties.getProperty("gear.from.y"));
			Integer.parseInt(properties.getProperty("gear.offset.x"));
			Integer.parseInt(properties.getProperty("gear.offset.y"));
		} catch (NumberFormatException nfe) {
			throw new InvalidParameterException();
		}
	}

	/**
	 * Check all settings from user
	 * 
	 * @param gearFromX
	 * @param gearFromY
	 * @param gearOffsetX
	 * @param gearOffsetY
	 * @throws InvalidParameterException
	 */
	private static void checkSettingsFromUser(String gearFromX, String gearFromY, String gearOffsetX, String gearOffsetY)
			throws InvalidParameterException {
		// Check integer parameters
		try {
			Integer.parseInt(gearFromX);
			Integer.parseInt(gearFromY);
			Integer.parseInt(gearOffsetX);
			Integer.parseInt(gearOffsetY);
		} catch (NumberFormatException nfe) {
			throw new InvalidParameterException();
		}
	}

	/**
	 * Write settings in file
	 * 
	 * @param appFocus
	 * @param kbAntiAfkExec
	 * @param kbAntiAfkAction
	 * @param kbMacroExec
	 * @param kbMacroMousePos
	 * @param kbClose
	 * @param kbConfirm
	 * @param gearMod
	 * @param gearFromX
	 * @param gearFromY
	 * @param gearOffsetX
	 * @param gearOffsetY
	 * @param craftFavFile
	 * @param setUpFavFile
	 * @param foodFavFile
	 * @param repairFavFile
	 * @param materiaFavFile
	 * @throws IOException
	 */
	private static void writeSettings(String appFocus, String kbAntiAfkExec, String kbAntiAfkAction, String kbMacroExec, String kbMacroMousePos,
			String kbClose, String kbConfirm, boolean gearMod, int gearFromX, int gearFromY, int gearOffsetX, int gearOffsetY, String craftFavFile,
			String setUpFavFile, String foodFavFile, String repairFavFile, String materiaFavFile) throws IOException {
		// Create file settings.cfg
		FileWriter fileWriter = new FileWriter(SETTINGS_FILE.getAbsoluteFile());
		fileWriter.write("### FFXIV GameTime - Fichier de configuration ###");
		fileWriter.append("\napp.version=" + APP_VERSION);
		fileWriter.append("\napp.focus=" + appFocus);
		fileWriter.append("\nkeybind.antiafk.exec=" + kbAntiAfkExec);
		fileWriter.append("\nkeybind.antiafk.action=" + kbAntiAfkAction);
		fileWriter.append("\nkeybind.macro.exec=" + kbMacroExec);
		fileWriter.append("\nkeybind.macro.mousepos=" + kbMacroMousePos);
		fileWriter.append("\nkeybind.close=" + kbClose);
		fileWriter.append("\nkeybind.confirm=" + kbConfirm);
		fileWriter.append("\ngear.mod=" + String.valueOf(gearMod));
		fileWriter.append("\ngear.from.x=" + String.valueOf(gearFromX));
		fileWriter.append("\ngear.from.y=" + String.valueOf(gearFromY));
		fileWriter.append("\ngear.offset.x=" + String.valueOf(gearOffsetX));
		fileWriter.append("\ngear.offset.y=" + String.valueOf(gearOffsetY));
		fileWriter.append("\ncraft.fav.file=" + craftFavFile);
		fileWriter.append("\nset.up.fav.file=" + setUpFavFile);
		fileWriter.append("\nfood.fav.file=" + foodFavFile);
		fileWriter.append("\nrepair.fav.file=" + repairFavFile);
		fileWriter.append("\nmateria.fav.file=" + materiaFavFile);
		fileWriter.close();
		logger.info("-> fichier settings.cfg cree/modifie avec succes");
		// Load values
		properties.setProperty("app.version", APP_VERSION);
		properties.setProperty("app.focus", appFocus);
		properties.setProperty("keybind.antiafk.exec", kbAntiAfkExec);
		properties.setProperty("keybind.antiafk.action", kbAntiAfkAction);
		properties.setProperty("keybind.macro.exec", kbMacroExec);
		properties.setProperty("keybind.macro.mousepos", kbMacroMousePos);
		properties.setProperty("keybind.close", kbClose);
		properties.setProperty("keybind.confirm", kbConfirm);
		properties.setProperty("gear.mod", String.valueOf(gearMod));
		properties.setProperty("gear.from.x", String.valueOf(gearFromX));
		properties.setProperty("gear.from.y", String.valueOf(gearFromY));
		properties.setProperty("gear.offset.x", String.valueOf(gearOffsetX));
		properties.setProperty("gear.offset.y", String.valueOf(gearOffsetY));
		properties.setProperty("craft.fav.file", craftFavFile);
		properties.setProperty("set.up.fav.file", setUpFavFile);
		properties.setProperty("food.fav.file", foodFavFile);
		properties.setProperty("repair.fav.file", repairFavFile);
		properties.setProperty("materia.fav.file", materiaFavFile);
	}

	/**
	 * Getters
	 */

	/**
	 * Get app version
	 * 
	 * @return String
	 */
	public static String getAppVersion() {
		return properties.getProperty("app.version");
	}

	/**
	 * Get app focus
	 * 
	 * @return String
	 */
	public static String getAppFocus() {
		return properties.getProperty("app.focus");
	}

	/**
	 * Get keybind antiafk : exec
	 * 
	 * @return String
	 */
	public static String getKeybindAntiAfkExec() {
		return properties.getProperty("keybind.antiafk.exec");
	}

	/**
	 * Get keybind antiafk : action
	 * 
	 * @return String
	 */
	public static String getKeybindAntiAfkAction() {
		return properties.getProperty("keybind.antiafk.action");
	}

	/**
	 * Get keybind macro : exec
	 * 
	 * @return String
	 */
	public static String getKeybindMacroExec() {
		return properties.getProperty("keybind.macro.exec");
	}

	/**
	 * Get keybind macro : mousepos
	 * 
	 * @return String
	 */
	public static String getKeybindMacroMousePos() {
		return properties.getProperty("keybind.macro.mousepos");
	}

	/**
	 * Get keybind close
	 * 
	 * @return String
	 */
	public static String getKeybindClose() {
		return properties.getProperty("keybind.close");
	}

	/**
	 * Get keybind confirm
	 * 
	 * @return String
	 */
	public static String getKeybindConfirm() {
		return properties.getProperty("keybind.confirm");
	}

	/**
	 * Get gear mod
	 * 
	 * @return boolean
	 */
	public static boolean getGearMod() {
		return Boolean.parseBoolean(properties.getProperty("gear.mod"));
	}

	/**
	 * Get gear from X
	 * 
	 * @return int
	 */
	public static int getGearFromX() {
		return Integer.parseInt(properties.getProperty("gear.from.x"));
	}

	/**
	 * Get gear from Y
	 * 
	 * @return int
	 */
	public static int getGearFromY() {
		return Integer.parseInt(properties.getProperty("gear.from.y"));
	}

	/**
	 * Get gear offset X
	 * 
	 * @return int
	 */
	public static int getGearOffsetX() {
		return Integer.parseInt(properties.getProperty("gear.offset.x"));
	}

	/**
	 * Get gear offset Y
	 * 
	 * @return int
	 */
	public static int getGearOffsetY() {
		return Integer.parseInt(properties.getProperty("gear.offset.y"));
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
