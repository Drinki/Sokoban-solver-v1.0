import java.util.*;

/**
 * Les problemes qui font qu'un état n'est pas finissable
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class Problem{

	/**
	* Vérifie s'il y a un deadlock dans la grille
	* deadlock = une boxe que l'on ne peut plus bouger
	* @param state, un état du jeu.
	* @return true or false
	*/
	public boolean isDeadLock(State state){
		boolean dead = false;
		for(int i = 0; i < state.panel.length; i++){
			for(int j = 0; j < state.panel[i].length; j++){
				if(state.panel[i][j] == '$'){
					if(state.panel[i][j+1] == '#' && state.panel[i+1][j] == '#'){
						dead = true;
					}else if(state.panel[i+1][j] == '#' && state.panel[i][j-1] == '#'){
						dead = true;
					}else if(state.panel[i][j-1] == '#' && state.panel[i-1][j] == '#'){
						dead = true;
					}else if(state.panel[i-1][j] == '#' && state.panel[i][j+1] == '#'){
						dead = true;
					}
				}
			}
		}

		return dead;
	}
}
