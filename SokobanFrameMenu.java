import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenêtre du lancement du jeu (Menu start)
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class SokobanFrameMenu extends JFrame{
  protected JPanel panel = new JPanel();
  protected JButton soloPlay = new JButton("Jouer (solo)");
  protected JButton multiPlay = new JButton("Jouer (multi)");

  /**
  * Constructeur de SokobanFrameMenu
  */
  public SokobanFrameMenu(){
    //Création de la fenêtre
    this.setTitle("Sokoban Game");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setFocusable(true);
    this.requestFocusInWindow(true);
    this.setLocationRelativeTo(null);

    //Création du layout
    this.setLayout(new BorderLayout());

    //Ajout des panels et des boutons (au panel)
    this.add(panel, BorderLayout.CENTER);
    panel.add(soloPlay);
    panel.add(multiPlay);

    this.setVisible(true);

    //Action du bouton solo
    soloPlay.setFocusable(false);
    soloPlay.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        new SokobanFrameSolo();
        setVisible(false);
      }
    });

    //Action du bouton multi
    multiPlay.setFocusable(false);
    multiPlay.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        new SokobanFrameMulti();
        setVisible(false);
      }
    });
  }
}
