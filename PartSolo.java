import java.util.*;

/**
 * Lancement d'une partie solo
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class PartSolo{
	protected State state;
	protected Heuristic heuristic;
	protected AbstractSolver solver;
	protected int level;
	public static List<Integer> resLevel;

	/**
	* Constructeur du PartSolo
	* @param level, numéro du niveau
	*/
	public PartSolo(int level){
		this.level = level;

		//Création des instances
		state = new State(this.level);
	}

	public char[][] getPanel(){
		return state.getPanel();
	}

	/**
	* Reinitialise le niveau
	*/
	public void reset(){
		state = new State(this.level);
		reinitResLevel();
	}

	/**
	* Remet le joueur à sa position prècedente
	*/
	public void backMove(){
		if(state.stateBack != null){
			state = state.getStateBack();
		}
	}

	/**
	* Passe au prochain niveau
	*/
	public void nextLevel(){
		if(this.level != 8){
			this.level += 1;
		}

		state = new State(this.level);
		reinitResLevel();
	}

	/**
	* Passe au niveau précedent
	*/
	public void previousLevel(){
		if(this.level != 1){
			this.level -= 1;
		}

		state = new State(this.level);
		reinitResLevel();
	}

	/**
	* Reinitialise la solution du niveau
	*/
	public void reinitResLevel(){
		resLevel = null;
	}

	/**
	* Affiche la solution du niveau sous forme d'une chaine de caractère
	* @return strWay, chemin sous forme de u, r, d, l
	*/
	public String stringWay(){
		String strWay = "";
		if(this.level <= 3){
			reset();
			heuristic = new Heuristic();
			solver = new AStarSolver(state, heuristic);
			if(resLevel == null){
				resLevel = solver.search();
			}
			for(int i = 0; i < resLevel.size(); i++){
				switch(resLevel.get(i)){
					case 1 : strWay += "u"; break;
					case 2 : strWay += "r"; break;
					case 3 : strWay += "d"; break;
					case 4 : strWay += "l"; break;
				}
				strWay += ", ";
			}
		}else{
			strWay = "Le solver ne peut pas resoudre ce niveau";
		}
		return strWay;
	}

	/**
	* Execute la solution du niveau
	*/
	public void executeWay(){
		for(int move : resLevel){
			switch(move){
				case 1 : state.up(); break;
				case 2 : state.right(); break;
				case 3 : state.down(); break;
				case 4 : state.left(); break;
			}
		}
	}
}
