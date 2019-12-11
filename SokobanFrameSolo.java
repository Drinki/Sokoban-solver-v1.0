import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Fenêtre du jeu solo
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class SokobanFrameSolo extends JFrame implements KeyListener{
  public static SokobanMenu bottomMenu;
  public static SokobanViewSolo panelGameSolo;

  /**
  * Constructeur de SokobanFrameSolo
  */
  public SokobanFrameSolo(){
    this.bottomMenu = new SokobanMenu(1);
    this.panelGameSolo = new SokobanViewSolo();


    //Création de la fenêtre
    this.setTitle("Sokoban Game Solo");
    this.setSize(576, 448);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.setLocationRelativeTo(null);

    //Ajout des évènements au clavier
    this.addKeyListener(this);

    //Création du layout
		this.setLayout(new BorderLayout());

    //Ajout du panel qui affiche le niveau
    this.add(panelGameSolo, BorderLayout.CENTER);
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
    char[][] panelBack = panelGameSolo.partSolo.state.getPanelBack();
    switch(key.getKeyCode()){
      case KeyEvent.VK_UP:
        panelGameSolo.partSolo.state.up();
        break;

      case KeyEvent.VK_DOWN:
        panelGameSolo.partSolo.state.down();
        break;

      case KeyEvent.VK_LEFT:
        panelGameSolo.partSolo.state.left();
        break;

      case KeyEvent.VK_RIGHT:
        panelGameSolo.partSolo.state.right();
        break;
    }
    levelComplete();
    panelGameSolo.repaint();
  }

  /**
  * Vérifie si une niveau est completé
  */
  public void levelComplete(){
    if(panelGameSolo.partSolo.state.isOver()){
      panelGameSolo.partSolo.nextLevel();
    }
  }
}
