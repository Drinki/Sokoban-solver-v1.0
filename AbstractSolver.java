import java.util.*;
import java.util.HashMap;

/**
 * Classe abstraite du solver
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public abstract class AbstractSolver{
  protected State currentState;
  protected Queue<State> queue;

  /**
  * Constructeur de AbstractSolver
  * @param state, état du jeu
  */
  public AbstractSolver(State state){
    currentState = state;
  }

  /**
  * Méthode search qui va chercher dans la queue l'état qui mène
  * à la résolution du niveau
  * @return solution, une liste d'entier qui correspondent au mouvement du player
  */
  public List<Integer> search(){
    List<Integer> solution = new ArrayList<Integer>();
    queue.clear();
    searchStart();
    while(queue.isEmpty() == false){
      currentState = queue.poll();
      if(currentState.isOver()){
        solution = backtrackMoves(currentState);
        break;
      }

      if(SokobanFrameMulti.canMoveComp){
        solution = backtrackMoves(currentState);
        break;
      }

      List<State> validMoves = currentState.validMoves();
      searchFunction(validMoves);
    }
    return solution;
  }

  /**
  * Initialise la recherche en ajoutant le première état du jeu à la queue
  */
  public void searchStart(){
    queue.add(currentState);
  }

  /**
  * La fonction qui va varier en fonction des différente méthode de recherche
  * @param validMoves, une liste de coup valide
  */
  protected abstract void searchFunction(List<State> validMoves);

  /**
  * Renvoie la liste des mouvements à partir d'un état de jeu final (résolut)
  * @param state, un état du jeu
  * @return solution, une liste d'entier qui correspondent au mouvement du player
  */
  public List<Integer> backtrackMoves(State state){
    LinkedList<Integer> solution = new LinkedList<Integer>();
    State current = state;

    while(current.stateBack != null){
      int move = current.getDirection();
      solution.push(move);

      current = current.stateBack;
    }
    return solution;
  }
}
