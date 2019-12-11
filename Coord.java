
/**
 * Classe de coordonnée d'un élément
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class Coord{
  //x = col, y = row
  public int x, y;

  /**
  * Constructeur de Coord
  * @param x, coordonnée x de l'élément
  * @param y, coordonnée y de l'élément
  */
  public Coord(int x, int y){
    this.x = x;
    this.y = y;
  }

  /**
  * Accesseur de x
  */
  public int getX(){
    return this.x;
  }

  /**
  * Accesseur de x
  */
  public int getY(){
    return this.y;
  }
}
