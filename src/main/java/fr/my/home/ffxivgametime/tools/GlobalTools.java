package fr.my.home.ffxivgametime.tools;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

/**
 * GlobalTools
 * 
 * @version 1.1
 */
public class GlobalTools {

	/**
	 * Random float between min and max
	 * 
	 * @param min
	 * @param max
	 * @return float
	 */
	public static float getRandomFloat(float min, float max) {
		return min + (float) Math.random() * (max - min);
	}

	/**
	 * Random int between 1 and 2
	 * 
	 * @return int
	 */
	public static int getRandomIntBetween1and2() {
		return (Math.random() <= 0.5) ? 1 : 2;
	}

	/**
	 * Random int between 1 and 4
	 * 
	 * @return int
	 */
	public static int getRandomIntBetween1and4() {
		int value = 1;
		double random = Math.random();
		if (random < 0.25) {
			value = 1;
		} else if (random < 0.5) {
			value = 2;
		} else if (random < 0.75) {
			value = 3;
		} else {
			value = 4;
		}
		return value;
	}

	/**
	 * Random float in the range of value (-100 <> +100)
	 * 
	 * @param value
	 * @return float
	 */
	public static float getRangeFloat(float value) {
		value *= 1000;
		float min = (value < 100) ? 0 : value - 100;
		float max = value + 100;
		return min + (float) Math.random() * (max - min);
	}

	/**
	 * Format filepath to windows standard # "C:\\User\\my-file.mgt" -> "C:/User/my-file.mgt"
	 * 
	 * @param rawPath
	 * @return String
	 */
	public static String formatFilePath(String rawPath) {
		String formattedPath = "";
		if (rawPath != null && !rawPath.trim().isEmpty()) {
			formattedPath = rawPath.replaceAll("\\\\", "/");
		}
		return formattedPath;
	}

	/**
	 * Format dirpath from filepath to windows standard # "C:\\User\\my-file.mgt" -> "C:/User/"
	 * 
	 * @param rawPath
	 * @return String
	 */
	public static String formatDirPath(String rawPath) {
		String formattedPath = "";
		if (rawPath != null && !rawPath.trim().isEmpty()) {
			formattedPath = rawPath.replaceAll("\\\\", "/");
			formattedPath = formattedPath.substring(0, formattedPath.lastIndexOf("/") + 1);
		}
		return formattedPath;
	}

	/**
	 * Format duration to display time left
	 * 
	 * @param duration
	 * @return String
	 */
	public static String formatTimeLeft(long duration) {
		String formattedTime = "";
		if (duration >= 172800) {
			formattedTime = String.valueOf(duration / 86400) + " jours";
		} else if (duration >= 86400) {
			formattedTime = "1 jour";
		} else if (duration >= 3600) {
			formattedTime = String.valueOf(duration / 3600) + " h";
		} else {
			formattedTime = String.valueOf((duration / 60) + 1) + " min";
		}
		return formattedTime;
	}

	/**
	 * Set focus to windows app
	 * 
	 * @param focusValue
	 */
	public static void setFocusToWindowsApp(String focusValue) {
		if (focusValue != null && !focusValue.trim().isEmpty()) {
			User32 user32 = User32.INSTANCE;
			WinDef.HWND hWnd = user32.FindWindow(null, focusValue);
			if (user32.IsWindowVisible(hWnd)) {
				user32.ShowWindow(hWnd, User32.SW_SHOWMAXIMIZED);
				user32.SetForegroundWindow(hWnd);
				user32.SetFocus(hWnd);
			}
		}
	}

}
