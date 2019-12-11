import java.util.*;

/**
 * Algorithme de recherche AStar
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class AStarSolver extends AbstractSolver{
  protected Heuristic heuristic;
  protected Problem prob;

  /**
  * Constructeur de AStarSolver
  * @param state, un état du jeu.
  */
  protected AStarSolver(State state){
    super(state);
    queue = new PriorityQueue<State>();
  }

  /**
  * Constructeur de AStarSolver
  * @param state, un état du jeu.
  * @param heuristic, un heuristique
  */
  public AStarSolver(State state, Heuristic heuristic){
    this(state);
    this.heuristic = heuristic;
    this.prob = new Problem();
  }

  /**
  * Méthode qui vérifie tous les coups valides et récupère les plus efficaces
  * grâce à l'heuristique
  * @param validMoves, liste de coups de valides.
  */
  protected void searchFunction(List<State> validMoves){
    for(State move : validMoves){
      if(!prob.isDeadLock(move)){
        heuristic.score(move);
        queue.add(move);
      }
    }
  }
}
