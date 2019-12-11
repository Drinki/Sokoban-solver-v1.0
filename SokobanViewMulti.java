import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

/**
 * Panel du mode multi
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class SokobanViewMulti extends JPanel implements ModelListener{
  public PartMulti partMulti;

  public int level = 1;

  protected BufferedImage imgNull;
  protected BufferedImage imgBox;
  protected BufferedImage imgPlayer;
  protected BufferedImage imgGoal;
  protected BufferedImage imgPlayerOnGoal;
  protected BufferedImage imgBoxOnGoal;
  protected BufferedImage imgWall;
  protected BufferedImage imgSpace;

  protected int space = 550;

  /**
  * Constructeur de SokobanViewMulti
  */
  public SokobanViewMulti(){
    super();
    partMulti = new PartMulti(level);
    loadImg();

    this.setBackground(Color.WHITE);
    this.setFocusable(true);
    this.requestFocusInWindow(true);
  }

  /**
  * Charge les images des éléments
  */
  public void loadImg(){
    try{
      imgNull = ImageIO.read(new File("images/null.png"));
      imgBox = ImageIO.read(new File("images/box.png"));
      imgPlayer = ImageIO.read(new File("images/player.png"));
      imgGoal = ImageIO.read(new File("images/goal.png"));
      imgPlayerOnGoal = ImageIO.read(new File("images/playerOnGoal.png"));
      imgBoxOnGoal = ImageIO.read(new File("images/boxOnGoal.png"));
      imgWall = ImageIO.read(new File("images/wall.png"));
      imgSpace = ImageIO.read(new File("images/space.png"));
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  @Override
  public void modelUpdated(Object o){
    repaint();
  }

  /**
  * Création des éléments dans le paintComponent
  * @param g, élement de la classe Graphics
  */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    //Affichage de la grille du joueur
    for(int i = 0; i<partMulti.state1.getPanel().length; i++){
      for(int j = 0; j<partMulti.state1.getPanel()[i].length; j++){
        switch(partMulti.state1.getPanel()[i][j]){
          case ' ' :
            g.drawImage(imgNull, j*32, i*32, null);
            break;
          case '$' :
            g.drawImage(imgBox, j*32, i*32, null);
            break;
          case '@' :
            g.drawImage(imgPlayer, j*32, i*32, null);
            break;
          case '.' :
            g.drawImage(imgGoal, j*32, i*32, null);
            break;
          case '!' :
            g.drawImage(imgPlayerOnGoal, j*32, i*32, null);
            break;
          case '*' :
            g.drawImage(imgBoxOnGoal, j*32, i*32, null);
            break;
          case '#' :
            g.drawImage(imgWall, j*32, i*32, null);
            break;
          case '+' :
            g.drawImage(imgSpace, j*32, i*32, null);
            break;
        }
      }
    }

    //Affichage de la grille de l'ordinateur
    for(int i = 0; i<partMulti.state2.getPanel().length; i++){
      for(int j = 0; j<partMulti.state2.getPanel()[i].length; j++){
        switch(partMulti.state2.getPanel()[i][j]){
          case ' ' :
            g.drawImage(imgNull, j*32 + space, i*32, null);
            break;
          case '$' :
            g.drawImage(imgBox, j*32 + space, i*32, null);
            break;
          case '@' :
            g.drawImage(imgPlayer, j*32 + space, i*32, null);
            break;
          case '.' :
            g.drawImage(imgGoal, j*32 + space, i*32, null);
            break;
          case '!' :
            g.drawImage(imgPlayerOnGoal, j*32 + space, i*32, null);
            break;
          case '*' :
            g.drawImage(imgBoxOnGoal, j*32 + space, i*32, null);
            break;
          case '#' :
            g.drawImage(imgWall, j*32 + space, i*32, null);
            break;
          case '+' :
            g.drawImage(imgSpace, j*32 + space, i*32, null);
            break;
        }
      }
    }
  }
}
