package fr.my.home.ffxivgametime.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * KeyboardController
 * 
 * @version 1.1
 */
public class KeyboardController {
	private static Logger logger = LogManager.getLogger(KeyboardController.class);

	// Components

	@FXML
	private Label key_id;

	@FXML
	private ImageView k_escape;

	@FXML
	private ImageView k_f1;

	@FXML
	private ImageView k_f2;

	@FXML
	private ImageView k_f3;

	@FXML
	private ImageView k_f4;

	@FXML
	private ImageView k_f5;

	@FXML
	private ImageView k_f6;

	@FXML
	private ImageView k_f7;

	@FXML
	private ImageView k_f8;

	@FXML
	private ImageView k_f9;

	@FXML
	private ImageView k_f10;

	@FXML
	private ImageView k_f11;

	@FXML
	private ImageView k_f12;

	@FXML
	private ImageView k_1;

	@FXML
	private ImageView k_2;

	@FXML
	private ImageView k_3;

	@FXML
	private ImageView k_4;

	@FXML
	private ImageView k_5;

	@FXML
	private ImageView k_6;

	@FXML
	private ImageView k_7;

	@FXML
	private ImageView k_8;

	@FXML
	private ImageView k_9;

	@FXML
	private ImageView k_0;

	@FXML
	private ImageView k_right_parenthesis;

	@FXML
	private ImageView k_equals;

	@FXML
	private ImageView k_back_space;

	@FXML
	private ImageView k_tab;

	@FXML
	private ImageView k_a;

	@FXML
	private ImageView k_z;

	@FXML
	private ImageView k_e;

	@FXML
	private ImageView k_r;

	@FXML
	private ImageView k_t;

	@FXML
	private ImageView k_y;

	@FXML
	private ImageView k_u;

	@FXML
	private ImageView k_i;

	@FXML
	private ImageView k_o;

	@FXML
	private ImageView k_p;

	@FXML
	private ImageView k_enter;

	@FXML
	private ImageView k_caps_lock;

	@FXML
	private ImageView k_q;

	@FXML
	private ImageView k_s;

	@FXML
	private ImageView k_d;

	@FXML
	private ImageView k_f;

	@FXML
	private ImageView k_g;

	@FXML
	private ImageView k_h;

	@FXML
	private ImageView k_j;

	@FXML
	private ImageView k_k;

	@FXML
	private ImageView k_l;

	@FXML
	private ImageView k_m;

	@FXML
	private ImageView k_shift;

	@FXML
	private ImageView k_w;

	@FXML
	private ImageView k_x;

	@FXML
	private ImageView k_c;

	@FXML
	private ImageView k_v;

	@FXML
	private ImageView k_b;

	@FXML
	private ImageView k_n;

	@FXML
	private ImageView k_ctrl;

	@FXML
	private ImageView k_windows;

	@FXML
	private ImageView k_alt;

	@FXML
	private ImageView k_space;

	@FXML
	private ImageView k_up;

	@FXML
	private ImageView k_down;

	@FXML
	private ImageView k_left;

	@FXML
	private ImageView k_right;

	@FXML
	private ImageView k_np_decimal;

	@FXML
	private ImageView k_np_0;

	@FXML
	private ImageView k_np_1;

	@FXML
	private ImageView k_np_2;

	@FXML
	private ImageView k_np_3;

	@FXML
	private ImageView k_np_4;

	@FXML
	private ImageView k_np_5;

	@FXML
	private ImageView k_np_6;

	@FXML
	private ImageView k_np_7;

	@FXML
	private ImageView k_np_8;

	@FXML
	private ImageView k_np_9;

	/**
	 * Constructor
	 */
	public KeyboardController() {
		super();
	}

	// Methods

	/**
	 * Initialization
	 */
	@FXML
	private void initialize() {
		logger.info("-> Keyboard <-");
	}

	/**
	 * Key press : escape
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEscape() throws IOException {
		key_id.setText("Echap");
	}

	/**
	 * Key press : f1
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF1() throws IOException {
		key_id.setText("F1");
	}

	/**
	 * Key press : f2
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF2() throws IOException {
		key_id.setText("F2");
	}

	/**
	 * Key press : f3
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF3() throws IOException {
		key_id.setText("F3");
	}

	/**
	 * Key press : f4
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF4() throws IOException {
		key_id.setText("F4");
	}

	/**
	 * Key press : f5
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF5() throws IOException {
		key_id.setText("F5");
	}

	/**
	 * Key press : f6
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF6() throws IOException {
		key_id.setText("F6");
	}

	/**
	 * Key press : f7
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF7() throws IOException {
		key_id.setText("F7");
	}

	/**
	 * Key press : f8
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF8() throws IOException {
		key_id.setText("F8");
	}

	/**
	 * Key press : f9
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF9() throws IOException {
		key_id.setText("F9");
	}

	/**
	 * Key press : f10
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF10() throws IOException {
		key_id.setText("F10");
	}

	/**
	 * Key press : f11
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF11() throws IOException {
		key_id.setText("F11");
	}

	/**
	 * Key press : f12
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF12() throws IOException {
		key_id.setText("F12");
	}

	/**
	 * Key press : 1
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress1() throws IOException {
		key_id.setText("1");
	}

	/**
	 * Key press : 2
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress2() throws IOException {
		key_id.setText("2");
	}

	/**
	 * Key press : 3
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress3() throws IOException {
		key_id.setText("3");
	}

	/**
	 * Key press : 4
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress4() throws IOException {
		key_id.setText("4");
	}

	/**
	 * Key press : 5
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress5() throws IOException {
		key_id.setText("5");
	}

	/**
	 * Key press : 6
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress6() throws IOException {
		key_id.setText("6");
	}

	/**
	 * Key press : 7
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress7() throws IOException {
		key_id.setText("7");
	}

	/**
	 * Key press : 8
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress8() throws IOException {
		key_id.setText("8");
	}

	/**
	 * Key press : 9
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress9() throws IOException {
		key_id.setText("9");
	}

	/**
	 * Key press : 0
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress0() throws IOException {
		key_id.setText("0");
	}

	/**
	 * Key press : right parenthesis
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressRightParenthesis() throws IOException {
		key_id.setText("Parenthese");
	}

	/**
	 * Key press : equals
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEquals() throws IOException {
		key_id.setText("Egal");
	}

	/**
	 * Key press : back space
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressBackSpace() throws IOException {
		key_id.setText("Retour");
	}

	/**
	 * Key press : tab
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressTab() throws IOException {
		key_id.setText("Tab");
	}

	/**
	 * Key press : a
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressA() throws IOException {
		key_id.setText("A");
	}

	/**
	 * Key press : z
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressZ() throws IOException {
		key_id.setText("Z");
	}

	/**
	 * Key press : e
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressE() throws IOException {
		key_id.setText("E");
	}

	/**
	 * Key press : r
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressR() throws IOException {
		key_id.setText("R");
	}

	/**
	 * Key press : t
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressT() throws IOException {
		key_id.setText("T");
	}

	/**
	 * Key press : y
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressY() throws IOException {
		key_id.setText("Y");
	}

	/**
	 * Key press : u
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressU() throws IOException {
		key_id.setText("U");
	}

	/**
	 * Key press : i
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressI() throws IOException {
		key_id.setText("I");
	}

	/**
	 * Key press : o
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressO() throws IOException {
		key_id.setText("O");
	}

	/**
	 * Key press : p
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressP() throws IOException {
		key_id.setText("P");
	}

	/**
	 * Key press : enter
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEnter() throws IOException {
		key_id.setText("Entree");
	}

	/**
	 * Key press : caps lock
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressCapsLock() throws IOException {
		key_id.setText("Verr Maj");
	}

	/**
	 * Key press : q
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressQ() throws IOException {
		key_id.setText("Q");
	}

	/**
	 * Key press : s
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressS() throws IOException {
		key_id.setText("S");
	}

	/**
	 * Key press : d
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressD() throws IOException {
		key_id.setText("D");
	}

	/**
	 * Key press : f
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF() throws IOException {
		key_id.setText("F");
	}

	/**
	 * Key press : g
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressG() throws IOException {
		key_id.setText("G");
	}

	/**
	 * Key press : h
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressH() throws IOException {
		key_id.setText("H");
	}

	/**
	 * Key press : j
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressJ() throws IOException {
		key_id.setText("J");
	}

	/**
	 * Key press : k
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressK() throws IOException {
		key_id.setText("K");
	}

	/**
	 * Key press : l
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressL() throws IOException {
		key_id.setText("L");
	}

	/**
	 * Key press : m
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressM() throws IOException {
		key_id.setText("M");
	}

	/**
	 * Key press : shift
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressShift() throws IOException {
		key_id.setText("Maj");
	}

	/**
	 * Key press : w
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressW() throws IOException {
		key_id.setText("W");
	}

	/**
	 * Key press : x
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressX() throws IOException {
		key_id.setText("X");
	}

	/**
	 * Key press : c
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressC() throws IOException {
		key_id.setText("C");
	}

	/**
	 * Key press : v
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressV() throws IOException {
		key_id.setText("V");
	}

	/**
	 * Key press : b
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressB() throws IOException {
		key_id.setText("B");
	}

	/**
	 * Key press : n
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressN() throws IOException {
		key_id.setText("N");
	}

	/**
	 * Key press : ctrl
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressCtrl() throws IOException {
		key_id.setText("Ctrl");
	}

	/**
	 * Key press : windows
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressWindows() throws IOException {
		key_id.setText("Windows");
	}

	/**
	 * Key press : alt
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressAlt() throws IOException {
		key_id.setText("Alt");
	}

	/**
	 * Key press : space
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressSpace() throws IOException {
		key_id.setText("Espace");
	}

	/**
	 * Key press : up
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressUp() throws IOException {
		key_id.setText("Haut");
	}

	/**
	 * Key press : down
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressDown() throws IOException {
		key_id.setText("Bas");
	}

	/**
	 * Key press : left
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressLeft() throws IOException {
		key_id.setText("Gauche");
	}

	/**
	 * Key press : right
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressRight() throws IOException {
		key_id.setText("Droite");
	}

	/**
	 * Key press : NP .
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNPDecimal() throws IOException {
		key_id.setText("Num .");
	}

	/**
	 * Key press : NP 0
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP0() throws IOException {
		key_id.setText("Num 0");
	}

	/**
	 * Key press : NP 1
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP1() throws IOException {
		key_id.setText("Num 1");
	}

	/**
	 * Key press : NP 2
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP2() throws IOException {
		key_id.setText("Num 2");
	}

	/**
	 * Key press : NP 3
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP3() throws IOException {
		key_id.setText("Num 3");
	}

	/**
	 * Key press : NP 4
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP4() throws IOException {
		key_id.setText("Num 4");
	}

	/**
	 * Key press : NP 5
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP5() throws IOException {
		key_id.setText("Num 5");
	}

	/**
	 * Key press : NP 6
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP6() throws IOException {
		key_id.setText("Num 6");
	}

	/**
	 * Key press : NP 7
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP7() throws IOException {
		key_id.setText("Num 7");
	}

	/**
	 * Key press : NP 8
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP8() throws IOException {
		key_id.setText("Num 8");
	}

	/**
	 * Key press : NP 9
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressNP9() throws IOException {
		key_id.setText("Num 9");
	}

}
