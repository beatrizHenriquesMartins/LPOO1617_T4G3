package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dkeep.cli.Menu;
import dkeep.logic.Club;
import dkeep.logic.Game;
import dkeep.logic.Guard;
import dkeep.logic.GuardDrunken;
import dkeep.logic.GuardRookie;
import dkeep.logic.GuardSuspicious;
import dkeep.logic.Map;
import dkeep.logic.Ogre;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;

public class window1 {
	
	private ImgPanel img;
	private JFrame frame;
	private JTextField textField;
	private JLabel lblGameStatus;
	private Game g;
	private Game g2;
	private int level;
	int[] j;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window1 window = new window1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 */
	public window1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 592, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(150, 28, 43, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("1");

		JLabel lblGameStatus = new JLabel("******* *DUNGEON'S KEEPER* *******");
		lblGameStatus.setBounds(20, 442, 252, 16);
		frame.getContentPane().add(lblGameStatus);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(366, 387, 117, 29);
		frame.getContentPane().add(btnExit);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Rookie", "Drunken", "Suspicious" }));
		comboBox.setBounds(150, 55, 127, 27);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(20, 31, 113, 16);
		frame.getContentPane().add(lblNumberOfOgres);

		JLabel lblGuardPersonality = new JLabel("Guard personality");
		lblGuardPersonality.setBounds(20, 59, 141, 16);
		frame.getContentPane().add(lblGuardPersonality);

		JButton btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.setBounds(387, 208, 78, 29);
		frame.getContentPane().add(btnUp);

		JButton btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.setBounds(346, 235, 79, 29);
		frame.getContentPane().add(btnLeft);

		JButton btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.setBounds(426, 235, 79, 29);
		frame.getContentPane().add(btnRight);

		JButton btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.setBounds(386, 265, 79, 29);
		frame.getContentPane().add(btnDown);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(366, 113, 117, 29);
		frame.getContentPane().add(btnNewGame);
		
		img = new ImgPanel();
		img.addKeyListener(img);
		img.setBounds(20, 113, 314, 317);
		img.setFocusable(true);
		frame.getContentPane().add(img);
		
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				g = new Game();
				level=1;
				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnRight.setEnabled(true);
				btnLeft.setEnabled(true);
				lblGameStatus.setText("-----LEVEL 1-----");

				if (Float.parseFloat(textField.getText()) > 5.0 || Float.parseFloat(textField.getText()) < 1.0
						|| (Float.parseFloat(textField.getText()) % 1 != 0)) {
					return;
				}

				g.loadElementsLevel1();
				for (int i = 0; i < g.getGuards().size(); i++) {
					g.eraseTrailC(g.getGuards().get(i));
				}
				g.getGuards().clear();

				if (comboBox.getSelectedItem() == "Rookie") {
					g.getGuards().add(new GuardRookie(1, 8));
				} else if (comboBox.getSelectedItem() == "Drunken") {
					g.getGuards().add(new GuardDrunken(1, 8));
				}
				if (comboBox.getSelectedItem() == "Suspicious") {
					g.getGuards().add(new GuardSuspicious(1, 8));
				}
				g.getMap().insertCharacter(g.getGuards().get(0));
				img.updateMap(g.getMap().getMap(),g,g2,level,j);
				img.repaint();

				g2 = new Game();

				g2.loadElementsLevel2();
				g2.eraseTrailC(g2.getClubs().get(0));
				for (int i = 0; i < g2.getOgres().size(); i++) {
					g2.eraseTrailC(g2.getOgres().get(i));
					g2.eraseTrailC(g2.getClubs().get(i + 1));
				}
				g2.getOgres().clear();
				g2.getClubs().clear();
				g2.getClubs().add(new Club(7, 5));
				g2.getMap().insertCharacter(g2.getClubs().get(0));
				g2.getMap().insertCharacter(g2.getClubs().get(0));
				for (int i = 0; i < Integer.parseInt(textField.getText()); i++) {
					g2.getOgres().add(new Ogre(1, 4));
					g2.getMap().insertCharacter(g2.getOgres().get(i));
				}
				
				
				for (int z=0;z<g2.getOgres().size();z++)
				{
					g2.getClubs().add(new Club(7, 3));
					g2.getClubs().get(z+1).movement(g2.getMap(), g2.getOgres().get(z).getX(),
							g2.getOgres().get(z).getY());
					g2.getMap().insertCharacter(g2.getClubs().get(z+1));
				}

				j = new int[g2.getOgres().size()];
				Arrays.fill(j, 0);
			}
		});
		
////////////////////////////////////////MOVEMENT/////////////////////////////////////////////////
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (level == 1 && !g.getGameOver()) {
					if (g.getHero().wall(g.getMap(), 'w')) {

						lblGameStatus.setText("-----LEVEL 1-----");
						g.logicLevel1('w');
						img.updateMap(g.getMap().getMap(),g,g2,level,j);
						img.repaint();
						if (g.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
							level = 2;
						}
					}
				}
				if (level == 2) {
					if (g2.getHero().wall(g2.getMap(), 'w')) {
						lblGameStatus.setText("-----LEVEL 2-----");
						g2.logicLevel2('w', j);
						img.updateMap(g2.getMap().getMap(),g,g2,level,j);
						img.repaint();
						for (int w = 0; w < g2.getOgres().size(); w++) {
							if (!g2.getOgres().get(w).getStunned()) {
								j[w] = 0;
							} else {
								j[w]++;
							}
						}

						if (g2.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g2.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
						}
					}
					if (g.getGameOver() || g2.getGameOver())
					{
						btnUp.setEnabled(false);
						btnDown.setEnabled(false);
						btnRight.setEnabled(false);
						btnLeft.setEnabled(false);
					}
				}
			}
		});
		
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (level == 1 && !g.getGameOver()) {
					if (g.getHero().wall(g.getMap(), 'a')) {

						lblGameStatus.setText("-----LEVEL 1-----");
						g.logicLevel1('a');
						img.updateMap(g.getMap().getMap(),g,g2,level,j);
						img.repaint();
						if (g.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
							level = 2;
						}
					}
				}

				if (level == 2) {
					if (g2.getHero().wall(g2.getMap(), 'a')) {
						lblGameStatus.setText("-----LEVEL 2-----");
						g2.logicLevel2('a', j);
						img.updateMap(g2.getMap().getMap(),g,g2,level,j);
						img.repaint();
						for (int w = 0; w < g2.getOgres().size(); w++) {
							if (!g2.getOgres().get(w).getStunned()) {
								j[w] = 0;
							} else {
								j[w]++;
							}
						}

						if (g2.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g2.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
						}
					}
				}
				if (g.getGameOver() || g2.getGameOver())
				{
					btnUp.setEnabled(false);
					btnDown.setEnabled(false);
					btnRight.setEnabled(false);
					btnLeft.setEnabled(false);
				}
			}
		});
		
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (level == 1 && !g.getGameOver()) {
					if (g.getHero().wall(g.getMap(), 'd')) {
						lblGameStatus.setText("-----LEVEL 1-----");
						g.logicLevel1('d');
						img.updateMap(g.getMap().getMap(),g,g2,level,j);
						img.repaint();
						if (g.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
							level=2;
						}
					}
				}
				if (level == 2) {
					if (g2.getHero().wall(g2.getMap(), 'd')) {
						lblGameStatus.setText("-----LEVEL 2-----");
						g2.logicLevel2('d', j);
						img.updateMap(g2.getMap().getMap(),g,g2,level,j);
						img.repaint();
						for (int w = 0; w < g2.getOgres().size(); w++) {
							if (!g2.getOgres().get(w).getStunned()) {
								j[w] = 0;
							} else {
								j[w]++;
							}
						}

						if (g2.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g2.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
						}
					}
				}
				if (g.getGameOver() || g2.getGameOver())
				{
					btnUp.setEnabled(false);
					btnDown.setEnabled(false);
					btnRight.setEnabled(false);
					btnLeft.setEnabled(false);
				}
			}
		});
	
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (level == 1 && !g.getGameOver()) {
					if (g.getHero().wall(g.getMap(), 's')) {
						lblGameStatus.setText("-----LEVEL 1-----");
						g.logicLevel1('s');
						img.updateMap(g.getMap().getMap(),g,g2,level,j);
						img.repaint();
						if (g.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
							level =2;
						}
					}
				}
				if (level == 2) {
					if (g2.getHero().wall(g2.getMap(), 's')) {
						lblGameStatus.setText("-----LEVEL 2-----");
						g2.logicLevel2('s', j);
						img.updateMap(g2.getMap().getMap(),g,g2,level,j);
						img.repaint();
						for (int w = 0; w < g2.getOgres().size(); w++) {
							if (!g2.getOgres().get(w).getStunned()) {
								j[w] = 0;
							} else {
								j[w]++;
							}
						}
						if (g2.getGameOver()) {
							lblGameStatus.setText("*********** *GAME OVER* ***********");
						} else if (g2.checkVictory()) {
							lblGameStatus.setText("YOU WIN");
						}
					}
				}
				if (g.getGameOver() || g2.getGameOver())
				{
					btnUp.setEnabled(false);
					btnDown.setEnabled(false);
					btnRight.setEnabled(false);
					btnLeft.setEnabled(false);
				}
			}
			
		});

	}
	///////////////////////////////////////// USEFUL//////////////////////////////////////
	public static String printMap(Map map) {
		String p = "";
		for (int i = 0; i < map.getMap().length; i++) {
			for (int j = 0; j < map.getMap()[i].length; j++) {
				p += map.getMap()[i][j] + " ";
			}
			p += "\n";
		}
		return p;
	}
}