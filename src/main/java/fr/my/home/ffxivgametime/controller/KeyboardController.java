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
 * @version 1.0
 */
public class KeyboardController {
	private static Logger logger = LogManager.getLogger(KeyboardController.class);

	// Components

	@FXML
	private Label key_id;

	@FXML
	private ImageView k_echap;

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
	private ImageView k_ins;

	@FXML
	private ImageView k_suppr;

	@FXML
	private ImageView k_debut;

	@FXML
	private ImageView k_fin;

	@FXML
	private ImageView k_page_haut;

	@FXML
	private ImageView k_page_bas;

	@FXML
	private ImageView k_haut;

	@FXML
	private ImageView k_gauche;

	@FXML
	private ImageView k_bas;

	@FXML
	private ImageView k_droite;

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
	private ImageView k_degree;

	@FXML
	private ImageView k_egal;

	@FXML
	private ImageView k_retour;

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
	private ImageView k_entree;

	@FXML
	private ImageView k_verr_maj;

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
	private ImageView k_maj_gauche;

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
	private ImageView k_maj_droite;

	@FXML
	private ImageView k_ctrl_gauche;

	@FXML
	private ImageView k_win_gauche;

	@FXML
	private ImageView k_alt;

	@FXML
	private ImageView k_espace;

	@FXML
	private ImageView k_win_droite;

	@FXML
	private ImageView k_ctrl_droite;

	@FXML
	private ImageView k_pn_div;

	@FXML
	private ImageView k_pn_mult;

	@FXML
	private ImageView k_pn_moins;

	@FXML
	private ImageView k_pn_plus;

	@FXML
	private ImageView k_pn_entree;

	@FXML
	private ImageView k_pn_point;

	@FXML
	private ImageView k_pn_0;

	@FXML
	private ImageView k_pn_1;

	@FXML
	private ImageView k_pn_2;

	@FXML
	private ImageView k_pn_3;

	@FXML
	private ImageView k_pn_4;

	@FXML
	private ImageView k_pn_5;

	@FXML
	private ImageView k_pn_6;

	@FXML
	private ImageView k_pn_7;

	@FXML
	private ImageView k_pn_8;

	@FXML
	private ImageView k_pn_9;

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
	 * Key press : echap
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEchap() throws IOException {
		key_id.setText("27");
	}

	/**
	 * Key press : f1
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF1() throws IOException {
		key_id.setText("112");
	}

	/**
	 * Key press : f2
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF2() throws IOException {
		key_id.setText("113");
	}

	/**
	 * Key press : f3
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF3() throws IOException {
		key_id.setText("114");
	}

	/**
	 * Key press : f4
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF4() throws IOException {
		key_id.setText("115");
	}

	/**
	 * Key press : f5
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF5() throws IOException {
		key_id.setText("116");
	}

	/**
	 * Key press : f6
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF6() throws IOException {
		key_id.setText("117");
	}

	/**
	 * Key press : f7
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF7() throws IOException {
		key_id.setText("118");
	}

	/**
	 * Key press : f8
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF8() throws IOException {
		key_id.setText("119");
	}

	/**
	 * Key press : f9
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF9() throws IOException {
		key_id.setText("120");
	}

	/**
	 * Key press : f10
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF10() throws IOException {
		key_id.setText("121");
	}

	/**
	 * Key press : f11
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF11() throws IOException {
		key_id.setText("122");
	}

	/**
	 * Key press : f12
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF12() throws IOException {
		key_id.setText("123");
	}

	/**
	 * Key press : ins
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressIns() throws IOException {
		key_id.setText("155");
	}

	/**
	 * Key press : suppr
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressSuppr() throws IOException {
		key_id.setText("127");
	}

	/**
	 * Key press : debut
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressDebut() throws IOException {
		key_id.setText("36");
	}

	/**
	 * Key press : fin
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressFin() throws IOException {
		key_id.setText("35");
	}

	/**
	 * Key press : page haut
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPageHaut() throws IOException {
		key_id.setText("33");
	}

	/**
	 * Key press : page bas
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPageBas() throws IOException {
		key_id.setText("34");
	}

	/**
	 * Key press : haut
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressHaut() throws IOException {
		key_id.setText("38");
	}

	/**
	 * Key press : gauche
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressGauche() throws IOException {
		key_id.setText("37");
	}

	/**
	 * Key press : bas
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressBas() throws IOException {
		key_id.setText("40");
	}

	/**
	 * Key press : droite
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressDroite() throws IOException {
		key_id.setText("39");
	}

	/**
	 * Key press : 1
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress1() throws IOException {
		key_id.setText("49");
	}

	/**
	 * Key press : 2
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress2() throws IOException {
		key_id.setText("50");
	}

	/**
	 * Key press : 3
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress3() throws IOException {
		key_id.setText("51");
	}

	/**
	 * Key press : 4
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress4() throws IOException {
		key_id.setText("52");
	}

	/**
	 * Key press : 5
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress5() throws IOException {
		key_id.setText("53");
	}

	/**
	 * Key press : 6
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress6() throws IOException {
		key_id.setText("54");
	}

	/**
	 * Key press : 7
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress7() throws IOException {
		key_id.setText("55");
	}

	/**
	 * Key press : 8
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress8() throws IOException {
		key_id.setText("56");
	}

	/**
	 * Key press : 9
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress9() throws IOException {
		key_id.setText("57");
	}

	/**
	 * Key press : 0
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPress0() throws IOException {
		key_id.setText("48");
	}

	/**
	 * Key press : degree
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressDegree() throws IOException {
		key_id.setText("522");
	}

	/**
	 * Key press : egal
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEgal() throws IOException {
		key_id.setText("61");
	}

	/**
	 * Key press : retour
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressRetour() throws IOException {
		key_id.setText("8");
	}

	/**
	 * Key press : tab
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressTab() throws IOException {
		key_id.setText("9");
	}

	/**
	 * Key press : a
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressA() throws IOException {
		key_id.setText("65");
	}

	/**
	 * Key press : z
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressZ() throws IOException {
		key_id.setText("90");
	}

	/**
	 * Key press : e
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressE() throws IOException {
		key_id.setText("69");
	}

	/**
	 * Key press : r
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressR() throws IOException {
		key_id.setText("82");
	}

	/**
	 * Key press : t
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressT() throws IOException {
		key_id.setText("84");
	}

	/**
	 * Key press : y
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressY() throws IOException {
		key_id.setText("89");
	}

	/**
	 * Key press : u
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressU() throws IOException {
		key_id.setText("85");
	}

	/**
	 * Key press : i
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressI() throws IOException {
		key_id.setText("73");
	}

	/**
	 * Key press : o
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressO() throws IOException {
		key_id.setText("79");
	}

	/**
	 * Key press : p
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressP() throws IOException {
		key_id.setText("80");
	}

	/**
	 * Key press : entree
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEntree() throws IOException {
		key_id.setText("10");
	}

	/**
	 * Key press : verr maj
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressVerrMaj() throws IOException {
		key_id.setText("20");
	}

	/**
	 * Key press : q
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressQ() throws IOException {
		key_id.setText("81");
	}

	/**
	 * Key press : s
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressS() throws IOException {
		key_id.setText("83");
	}

	/**
	 * Key press : d
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressD() throws IOException {
		key_id.setText("68");
	}

	/**
	 * Key press : f
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressF() throws IOException {
		key_id.setText("70");
	}

	/**
	 * Key press : g
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressG() throws IOException {
		key_id.setText("71");
	}

	/**
	 * Key press : h
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressH() throws IOException {
		key_id.setText("72");
	}

	/**
	 * Key press : j
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressJ() throws IOException {
		key_id.setText("74");
	}

	/**
	 * Key press : k
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressK() throws IOException {
		key_id.setText("75");
	}

	/**
	 * Key press : l
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressL() throws IOException {
		key_id.setText("76");
	}

	/**
	 * Key press : m
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressM() throws IOException {
		key_id.setText("77");
	}

	/**
	 * Key press : maj
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressMaj() throws IOException {
		key_id.setText("16");
	}

	/**
	 * Key press : w
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressW() throws IOException {
		key_id.setText("87");
	}

	/**
	 * Key press : x
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressX() throws IOException {
		key_id.setText("88");
	}

	/**
	 * Key press : c
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressC() throws IOException {
		key_id.setText("67");
	}

	/**
	 * Key press : v
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressV() throws IOException {
		key_id.setText("86");
	}

	/**
	 * Key press : b
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressB() throws IOException {
		key_id.setText("66");
	}

	/**
	 * Key press : n
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressN() throws IOException {
		key_id.setText("78");
	}

	/**
	 * Key press : ctrl gauche
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressCtrl() throws IOException {
		key_id.setText("17");
	}

	/**
	 * Key press : win
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressWin() throws IOException {
		key_id.setText("524");
	}

	/**
	 * Key press : alt
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressAlt() throws IOException {
		key_id.setText("18");
	}

	/**
	 * Key press : espace
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressEspace() throws IOException {
		key_id.setText("32");
	}

	/**
	 * Key press : PN /
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPNDiv() throws IOException {
		key_id.setText("111");
	}

	/**
	 * Key press : PN *
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPNMult() throws IOException {
		key_id.setText("106");
	}

	/**
	 * Key press : PN -
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPNMoins() throws IOException {
		key_id.setText("109");
	}

	/**
	 * Key press : PN +
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPNPlus() throws IOException {
		key_id.setText("107");
	}

	/**
	 * Key press : PN .
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPNPoint() throws IOException {
		key_id.setText("110");
	}

	/**
	 * Key press : PN 0
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN0() throws IOException {
		key_id.setText("96");
	}

	/**
	 * Key press : PN 1
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN1() throws IOException {
		key_id.setText("97");
	}

	/**
	 * Key press : PN 2
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN2() throws IOException {
		key_id.setText("98");
	}

	/**
	 * Key press : PN 3
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN3() throws IOException {
		key_id.setText("99");
	}

	/**
	 * Key press : PN 4
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN4() throws IOException {
		key_id.setText("100");
	}

	/**
	 * Key press : PN 5
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN5() throws IOException {
		key_id.setText("101");
	}

	/**
	 * Key press : PN 6
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN6() throws IOException {
		key_id.setText("102");
	}

	/**
	 * Key press : PN 7
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN7() throws IOException {
		key_id.setText("103");
	}

	/**
	 * Key press : PN 8
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN8() throws IOException {
		key_id.setText("104");
	}

	/**
	 * Key press : PN 9
	 * 
	 * @throws IOException
	 */
	@FXML
	private void keyPressPN9() throws IOException {
		key_id.setText("105");
	}

}
