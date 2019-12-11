import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Barre menu en bas au lancement d'une partie
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class SokobanMenu extends JPanel{
	protected JButton buttonReset;
	protected JButton buttonBack;
	protected JButton buttonNextLvl;
	protected JButton buttonPreviousLvl;
	protected JButton buttonAutoRes;
	protected String res;
	protected int nbPlayer;

	/**
	* Constructeur de SokobanMenu
	*/
	public SokobanMenu(int nbPlayer){
		this.nbPlayer = nbPlayer;

		//Création des boutons
		buttonReset = new JButton("Reset");
		buttonBack = new JButton("Back");
		buttonNextLvl = new JButton("Next");
		buttonPreviousLvl = new JButton("Previous");
		buttonAutoRes = new JButton("Solver");

		/**
		* Style des boutons
		*/
		//Bouton RESET
		buttonReset.setBorderPainted(false);
		buttonReset.setToolTipText( "Reinitialise le niveau" );
		buttonReset.setIcon( new ImageIcon("images/button/reset.png"));

		//Bouton BACK
		buttonBack.setBorderPainted(false);
		buttonBack.setToolTipText( "Retourne en arrière" );
		buttonBack.setIcon( new ImageIcon("images/button/back.png"));

		//Bouton NEXT_LEVEL
		buttonNextLvl.setBorderPainted(false);
		buttonNextLvl.setToolTipText( "Niveau suivant" );
		buttonNextLvl.setIcon( new ImageIcon("images/button/next.png"));

		//Bouton PREVIOUS_LEVEL
		buttonPreviousLvl.setBorderPainted(false);
		buttonPreviousLvl.setToolTipText( "Niveau précédent" );
		buttonPreviousLvl.setIcon( new ImageIcon("images/button/previous.png"));

		//Bouton AUTO_RES
		buttonAutoRes.setBorderPainted(false);
		buttonAutoRes.setToolTipText( "Complete automatiquement le niveau" );
		buttonAutoRes.setIcon( new ImageIcon("images/button/auto.png"));

		res = "";

		//Ajout des boutons au panel
		if(nbPlayer == 1){
			this.add(buttonReset);
			this.add(buttonBack);
			this.add(buttonPreviousLvl);
			this.add(buttonNextLvl);
			this.add(buttonAutoRes);
		}else if(nbPlayer == 2){
			this.add(buttonReset);
			this.add(buttonPreviousLvl);
			this.add(buttonNextLvl);
		}

		this.setBackground(Color.GRAY);

		/**
		* Action des boutons
		*/
		//Bouton RESET
		buttonReset.setFocusable(false);
		buttonReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(nbPlayer == 1){
					SokobanFrameSolo.panelGameSolo.partSolo.reset();
					SokobanFrameSolo.panelGameSolo.repaint();
				}else if(nbPlayer == 2){
					SokobanFrameMulti.panelGameMulti.partMulti.reset();
					SokobanFrameMulti.panelGameMulti.repaint();
				}
			}
		});

		//Bouton BACK
		buttonBack.setFocusable(false);
		buttonBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SokobanFrameSolo.panelGameSolo.partSolo.backMove();
				SokobanFrameSolo.panelGameSolo.repaint();
			}
		});

		//Bouton NEXT_LEVEL
		buttonNextLvl.setFocusable(false);
		buttonNextLvl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(nbPlayer == 1){
					SokobanFrameSolo.panelGameSolo.partSolo.nextLevel();
					SokobanFrameSolo.panelGameSolo.repaint();
				}else if(nbPlayer == 2){
					SokobanFrameMulti.panelGameMulti.partMulti.nextLevel();
					SokobanFrameMulti.panelGameMulti.repaint();
				}
			}
		});

		//Bouton PREVIOUS_LEVEL
		buttonPreviousLvl.setFocusable(false);
		buttonPreviousLvl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(nbPlayer == 1){
					SokobanFrameSolo.panelGameSolo.partSolo.previousLevel();
					SokobanFrameSolo.panelGameSolo.repaint();
				}else if(nbPlayer == 2){
					SokobanFrameMulti.panelGameMulti.partMulti.previousLevel();
					SokobanFrameMulti.panelGameMulti.repaint();
				}
			}
		});

		//Bouton AUTO_RES
		buttonAutoRes.setFocusable(false);
		buttonAutoRes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				res = SokobanFrameSolo.panelGameSolo.partSolo.stringWay();
				if(e.getSource() == buttonAutoRes){
					JOptionPane.showMessageDialog(buttonReset, res, "Solution du niveau", JOptionPane.INFORMATION_MESSAGE);
					SokobanFrameSolo.panelGameSolo.partSolo.executeWay();
					SokobanFrameSolo.panelGameSolo.repaint();
				}
			}
		});
	}
}
