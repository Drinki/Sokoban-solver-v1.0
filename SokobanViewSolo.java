import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

/**
 * Panel du mode solo
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class SokobanViewSolo extends JPanel implements ModelListener{
  public PartSolo partSolo;

  public int level = 1;

  protected BufferedImage imgNull;
  protected BufferedImage imgBox;
  protected BufferedImage imgPlayer;
  protected BufferedImage imgGoal;
  protected BufferedImage imgPlayerOnGoal;
  protected BufferedImage imgBoxOnGoal;
  protected BufferedImage imgWall;
  protected BufferedImage imgSpace;

  /**
  * Constructeur de SokobanViewSolo
  */
  public SokobanViewSolo(){
    super();
    partSolo = new PartSolo(level);
    loadImg();

    this.setBackground(Color.WHITE);
    //this.setPreferredSize(new Dimension(32 * partSolo.state.getPanel().length, 32 * partSolo.state.getPanel()[0].length));
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
    for(int i = 0; i<partSolo.state.getPanel().length; i++){
      for(int j = 0; j<partSolo.state.getPanel()[i].length; j++){
        switch(partSolo.state.getPanel()[i][j]){
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
  }
}
