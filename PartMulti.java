import java.util.*;

/**
 * Lancement d'une partie multijoueurs
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class PartMulti{
	protected State state1, state2;
	protected Heuristic heuristic;
	protected AbstractSolver solver;
	protected int level;
	public static List<Integer> resLevel;
	protected Random rand = new Random();

	/**
	* Constructeur du PartMulti
	* @param level, numéro du niveau
	*/
	public PartMulti(int level){
		this.level = level;

		//Création des instances
		state1 = new State(this.level);
		state2 = new State(this.level);
	}

	/**
	* Reinitialise le niveau
	*/
	public void reset(){
		state1 = new State(this.level);
		state2 = new State(this.level);
		reinitResLevel();
	}

	/**
	* Passe au prochain niveau
	*/
	public void nextLevel(){
		if(this.level != 8){
			this.level += 1;
		}

		state1 = new State(this.level);
		state2 = new State(this.level);
		reinitResLevel();
	}

	/**
	* Passe au niveau précedent
	*/
	public void previousLevel(){
		if(this.level != 1){
			this.level -= 1;
		}

		state1 = new State(this.level);
		state2 = new State(this.level);
		reinitResLevel();
	}

	/**
	* Reinitialise la solution du niveau
	*/
	public void reinitResLevel(){
		resLevel = null;
	}

	/**
	* Fait jouer l'ordinateur
	*/
	public void playComp(){
		heuristic = new Heuristic();
		solver = new AStarSolver(state2, heuristic);
		int res = 0;
		if(SokobanFrameMulti.canMoveComp == false){
			//resLevel = solver.search();
			//solver.queue.clear();
			res = rand.nextInt(4 - 1 + 1) + 1;

		}
		switch(res){
			case 1 : state2.up(); break;
			case 2 : state2.right(); break;
			case 3 : state2.down(); break;
			case 4 : state2.left(); break;
		}
		reinitResLevel();
	}
}
