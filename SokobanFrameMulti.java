import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Fenêtre du jeu multi
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class SokobanFrameMulti extends JFrame implements KeyListener{
  public static SokobanMenu bottomMenu;
  public static SokobanViewMulti panelGameMulti;
  public static boolean canMoveComp = false;

  /**
  * Constructeur de SokobanFrameMulti
  */
  public SokobanFrameMulti(){
    this.bottomMenu = new SokobanMenu(2);
    this.panelGameMulti = new SokobanViewMulti();


    //Création de la fenêtre
    this.setTitle("Sokoban Game Multi");
    this.setSize(1152, 448);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.setLocationRelativeTo(null);

    //Ajout des évènements au clavier
    this.addKeyListener(this);

    //Création du layout
		this.setLayout(new BorderLayout());

    //Ajout du panel qui affiche le niveau
    this.add(panelGameMulti, BorderLayout.CENTER);
    this.add(bottomMenu, BorderLayout.PAGE_END);

		setVisible(true);
  }

  //Utilistation des touches
  @Override
  public void keyPressed(KeyEvent key){
    ;
  }

  @Override
  public void keyTyped(KeyEvent key){
    ;
  }

  /**
  * Action d'une touche du clavier préssé
  * @param key, évèvement d'une action de touche
  */
  @Override
  public void keyReleased(KeyEvent key){
    char[][] panelBack = panelGameMulti.partMulti.state1.getPanelBack();
    switch(key.getKeyCode()){
      case KeyEvent.VK_UP: //Touche haut.
        panelGameMulti.partMulti.state1.up();
        canMoveComp = true;
        break;

      case KeyEvent.VK_DOWN: //Touche bas.
        panelGameMulti.partMulti.state1.down();
        canMoveComp = true;
        break;

      case KeyEvent.VK_LEFT: //Touche gauche.
        panelGameMulti.partMulti.state1.left();
        canMoveComp = true;
        break;

      case KeyEvent.VK_RIGHT: //Touche droite.
        panelGameMulti.partMulti.state1.right();
        canMoveComp = true;
        break;
    }
    canMoveComp = false;
    levelComplete();
    panelGameMulti.partMulti.playComp();
    panelGameMulti.repaint();
  }

  /**
  * Vérifie si une niveau est completé
  */
  public void levelComplete(){
    if(panelGameMulti.partMulti.state1.isOver()){
      panelGameMulti.partMulti.nextLevel();
    }

    if(panelGameMulti.partMulti.state2.isOver()){
      panelGameMulti.partMulti.nextLevel();
    }
  }
}
