package fr.my.home.ffxivgametime.tools;

import java.awt.event.KeyEvent;

/**
 * Keyboard Stroke Map
 * 
 * @version 1.0
 */
public class KeyboardStrokeMap {

	/**
	 * Get KeyEvent ID from String
	 * 
	 * @param value
	 * @return int
	 */
	public static int getKeyEvent(String value) {
		int key = 0;
		if (value != null && !value.trim().isEmpty()) {
			value = value.toUpperCase();
			switch (value) {
				case "ECHAP":
					key = KeyEvent.VK_ESCAPE;
					break;
				case "F1":
					key = KeyEvent.VK_F1;
					break;
				case "F2":
					key = KeyEvent.VK_F2;
					break;
				case "F3":
					key = KeyEvent.VK_F3;
					break;
				case "F4":
					key = KeyEvent.VK_F4;
					break;
				case "F5":
					key = KeyEvent.VK_F5;
					break;
				case "F6":
					key = KeyEvent.VK_F6;
					break;
				case "F7":
					key = KeyEvent.VK_F7;
					break;
				case "F8":
					key = KeyEvent.VK_F8;
					break;
				case "F9":
					key = KeyEvent.VK_F9;
					break;
				case "F10":
					key = KeyEvent.VK_F10;
					break;
				case "F11":
					key = KeyEvent.VK_F11;
					break;
				case "F12":
					key = KeyEvent.VK_F12;
					break;
				case "1":
					key = KeyEvent.VK_1;
					break;
				case "2":
					key = KeyEvent.VK_2;
					break;
				case "3":
					key = KeyEvent.VK_3;
					break;
				case "4":
					key = KeyEvent.VK_4;
					break;
				case "5":
					key = KeyEvent.VK_5;
					break;
				case "6":
					key = KeyEvent.VK_6;
					break;
				case "7":
					key = KeyEvent.VK_7;
					break;
				case "8":
					key = KeyEvent.VK_8;
					break;
				case "9":
					key = KeyEvent.VK_9;
					break;
				case "0":
					key = KeyEvent.VK_0;
					break;
				case "PARENTHESE":
					key = KeyEvent.VK_RIGHT_PARENTHESIS;
					break;
				case "EGAL":
					key = KeyEvent.VK_EQUALS;
					break;
				case "RETOUR":
					key = KeyEvent.VK_BACK_SPACE;
					break;
				case "TAB":
					key = KeyEvent.VK_TAB;
					break;
				case "A":
					key = KeyEvent.VK_A;
					break;
				case "Z":
					key = KeyEvent.VK_Z;
					break;
				case "E":
					key = KeyEvent.VK_E;
					break;
				case "R":
					key = KeyEvent.VK_R;
					break;
				case "T":
					key = KeyEvent.VK_T;
					break;
				case "Y":
					key = KeyEvent.VK_Y;
					break;
				case "U":
					key = KeyEvent.VK_U;
					break;
				case "I":
					key = KeyEvent.VK_I;
					break;
				case "O":
					key = KeyEvent.VK_O;
					break;
				case "P":
					key = KeyEvent.VK_P;
					break;
				case "ENTREE":
					key = KeyEvent.VK_ENTER;
					break;
				case "VERR MAJ":
					key = KeyEvent.VK_CAPS_LOCK;
					break;
				case "Q":
					key = KeyEvent.VK_Q;
					break;
				case "S":
					key = KeyEvent.VK_S;
					break;
				case "D":
					key = KeyEvent.VK_D;
					break;
				case "F":
					key = KeyEvent.VK_F;
					break;
				case "G":
					key = KeyEvent.VK_G;
					break;
				case "H":
					key = KeyEvent.VK_H;
					break;
				case "J":
					key = KeyEvent.VK_J;
					break;
				case "K":
					key = KeyEvent.VK_K;
					break;
				case "L":
					key = KeyEvent.VK_L;
					break;
				case "M":
					key = KeyEvent.VK_M;
					break;
				case "MAJ":
					key = KeyEvent.VK_SHIFT;
					break;
				case "W":
					key = KeyEvent.VK_W;
					break;
				case "X":
					key = KeyEvent.VK_X;
					break;
				case "C":
					key = KeyEvent.VK_C;
					break;
				case "V":
					key = KeyEvent.VK_V;
					break;
				case "B":
					key = KeyEvent.VK_B;
					break;
				case "N":
					key = KeyEvent.VK_N;
					break;
				case "CTRL":
					key = KeyEvent.VK_CONTROL;
					break;
				case "WINDOWS":
					key = KeyEvent.VK_WINDOWS;
					break;
				case "ALT":
					key = KeyEvent.VK_ALT;
					break;
				case "ESPACE":
					key = KeyEvent.VK_SPACE;
					break;
				case "HAUT":
					key = KeyEvent.VK_UP;
					break;
				case "BAS":
					key = KeyEvent.VK_DOWN;
					break;
				case "GAUCHE":
					key = KeyEvent.VK_LEFT;
					break;
				case "DROITE":
					key = KeyEvent.VK_RIGHT;
					break;
				case "NUM .":
					key = KeyEvent.VK_DECIMAL;
					break;
				case "NUM 0":
					key = KeyEvent.VK_NUMPAD0;
					break;
				case "NUM 1":
					key = KeyEvent.VK_NUMPAD1;
					break;
				case "NUM 2":
					key = KeyEvent.VK_NUMPAD2;
					break;
				case "NUM 3":
					key = KeyEvent.VK_NUMPAD3;
					break;
				case "NUM 4":
					key = KeyEvent.VK_NUMPAD4;
					break;
				case "NUM 5":
					key = KeyEvent.VK_NUMPAD5;
					break;
				case "NUM 6":
					key = KeyEvent.VK_NUMPAD6;
					break;
				case "NUM 7":
					key = KeyEvent.VK_NUMPAD7;
					break;
				case "NUM 8":
					key = KeyEvent.VK_NUMPAD8;
					break;
				case "NUM 9":
					key = KeyEvent.VK_NUMPAD9;
					break;
				default:
					key = 0;
			}
		}
		return key;
	}

}
