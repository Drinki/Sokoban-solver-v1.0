import java.util.*;

/**
 * Heuristique (distance Manhattan)
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class Heuristic{

  /**
  * Méthode qui définie le coût d'un état en fonction
  * de la distance entre ses éléments (boxes, goals)
  * On parcourt toute les boxes et pour chaque box on cherche
  * le goal le plus proche avec la distance Manhattan.
  * pour cela on a un min cost qui a une valeur max au début et qui prend
  * la valeur de dist (distance Manhattan) si dist est inférieur à minCost.
  * Le coût correspond à la somme des distances entre chaque boxes et chaque goals.
  * @param state, un état du jeu
  */
  public void score(State state){
    Set<Coord> goals = state.getGoals();
    Set<Coord> boxes = state.getBoxes();
    Coord player = state.getPlayer();

    Set<Coord> inter = new HashSet<Coord>(goals);
    inter.retainAll(boxes);
    goals.removeAll(inter);
    boxes.removeAll(inter);


    int cost = 0;
    for(Coord box : boxes){
      int minCost = Integer.MAX_VALUE;
      for(Coord goal : goals){
        int dist = getManhattanDist(box, goal);
        if(dist < minCost){
          minCost = dist;
        }
      }
      cost += minCost;
    }

    state.setCost(cost);
  }

  /**
  * Calcule la distance Manhattan de deux ékéments (avec leur coordonnée)
  * manDist = |y1 - y2| + |x1 - x2|
  * @param c1, coordonnée élément 1.
  * @param c2, coordonnée élément 2
  * @return la distance sous forme d'entier.
  */
  public int getManhattanDist(Coord c1, Coord c2){
    return Math.abs(c1.getY()-c2.getY() + Math.abs(c1.getX()-c2.getX()));
  }
}
