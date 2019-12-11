import java.util.*;
import java.io.*;

/**
 * Classe de l'état du jeu
 *
 * @author Valentin DUMAS, Tomy DEMAZEAU, Yassine Gourirane, Fatima ezzahraa Elbardi
 * @version 1.0
 */
public class State implements Comparable<State>{
  protected char[][] panel;

  protected char[][] panelBack;
  protected State stateBack;

  protected Coord player;
  protected Set<Coord> goals;
  protected Set<Coord> boxes;

  protected String nameFile;
  protected int level;

  protected int nbLevel, nbLine = 0;
  protected int nbInitGoal = 0;
  protected int cost;
  protected int direction = 0;

  protected boolean boxMoved;

  /**
  * Constructeur State
  * @param level, numéro du niveau
  */
  public State(int level){
    this.level = level;
    initPanel();

    this.panelBack = new char[panel.length][panel[0].length];
    setPanelBack();

    goals = new HashSet<Coord>();
    boxes = new HashSet<Coord>();
    cost = 0;
    boxMoved = true;

    load();
  }

  /**
  * Initialise le niveau du jeu avec le fichier texte
  * On ouvre le fichier texte du niveau correspondant et on parcourt
  * les caractères du fichier. On ajoute au panel le caractères à la
  * bonne case.
  */
  public void initPanel(){
    try{
      InputStream file = new FileInputStream("level/lvl" + level + ".txt");
      InputStreamReader reading = new InputStreamReader(file);
      BufferedReader buffFile = new BufferedReader(reading);

      //Création du tableau
      int height = Integer.parseInt(buffFile.readLine());
      int width = Integer.parseInt(buffFile.readLine());
      panel = new char[height][width];
      //panelBack = new char[height][width];

      //Lecture des lignes
      String line = buffFile.readLine();
      while(line.charAt(0) != 'S'){
        for(int i = 0; i < line.length(); i++){
          switch(line.charAt(i)){
            case ' ' :
              panel[nbLine][i] = ' '; //Floor
              break;
            case '@' :
              panel[nbLine][i] = '@'; //Player
              break;
            case '#' :
              panel[nbLine][i] = '#'; //Wall
              break;
            case '$' :
              panel[nbLine][i] = '$'; //Box
              break;
            case '.' :
              panel[nbLine][i] = '.'; //Goal
              nbInitGoal++;
              break;
            case '*' :
              panel[nbLine][i] = '*'; //Box on goal
              nbInitGoal++;
              break;
            case '+' :
              panel[nbLine][i] = '+'; //Space
              break;
          }
        }
        nbLine ++;
        line = buffFile.readLine();
      }
      buffFile.close();
    }catch(Exception e){}
  }

  /**
  * Charge les set des éléments  (goals, boxes et player)
  * en ajoutant les coordonnées des éléments aux différents set.
  */
  public void load(){
    //goals.clear();
    //boxes.clear();
    player = null;
    for(int i = 0; i < panel.length; i++){
      for(int j = 0; j < panel[i].length; j++){
        if(panel[i][j] == '.' ){
          goals.add(new Coord(i, j));
        }else if(panel[i][j] == '$' ){
          boxes.add(new Coord(i, j));
        }else if(panel[i][j] == '@' || panel[i][j] == '!'){
          player = new Coord(i, j);
        }
      }
    }
  }

  @Override
  public int compareTo(State other) {
    if (getCost() < other.getCost()){
      return -1;
    }else if (getCost() > other.getCost()){
      return 1;
    }else{
      return 0;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.deepHashCode(panel);
    result = prime * result + ((goals == null) ? 0 : goals.hashCode());
    result = prime * result + ((player == null) ? 0 : player.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj){
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    State other = (State) obj;
    if (!Arrays.deepEquals(panel, other.panel))
      return false;
    if (goals == null) {
      if (other.goals != null)
        return false;
    } else if (!goals.equals(other.goals))
      return false;
    if (player == null) {
      if (other.player != null)
        return false;
    } else if (!player.equals(other.player))
      return false;
    return true;
  }

  /**
  * Renvoie l'état précedent de l'état actuel
  */
  public State getStateBack(){
    return stateBack;
  }

  /**
  * Définie le coût
  */
  public void setCost(int cost){
    this.cost = cost;
  }

  /**
  * Renvoie le coup
  * @return cost
  */
  public int getCost(){
    return cost;
  }

  /**
  * Définie le la grille du coup prècedent
  */
  public void setPanelBack(){
    for(int i = 0; i < panel.length; i++){
      for(int j = 0; j < panel[i].length; j++){
        panelBack[i][j] = panel[i][j];
      }
    }
  }

  /**
  * Renvoie la grille du coup précedent
  * @return panelBack
  */
  public char[][] getPanelBack(){
    return panelBack;
  }

  /**
  * Définit la grille principale
  * @param otherPanel, grille de réference
  */
  public void setPanel(char[][] otherPanel){
    for(int i = 0; i < otherPanel.length; i++){
      for(int j = 0; j < otherPanel[i].length; j++){
        panel[i][j] = otherPanel[i][j];
      }
    }
  }

  /**
  * Renvoie la grille principale
  * @return panel
  */
  public char[][] getPanel(){
    return panel;
  }

  /**
  * Renvoie le hashSet des goals sous forme de coordonnées
  * @return goals
  */
  public HashSet<Coord> getGoals(){
    return new HashSet<Coord>(goals);
  }

  /**
  * Renvoie les coordonnée du player
  * @return player
  */
  public Coord getPlayer(){
    return this.player;
  }

  /**
  * Renvoie le hashSet des boxes sous forme de coordonnées
  * @return boxes
  */
  public HashSet<Coord> getBoxes(){
    return new HashSet<Coord>(boxes);
  }

  /**
  * Renvoie la direction qu'a prit le joueur
  * @return direction
  */
  public int getDirection(){
    return direction;
  }

  /**
  * Renvoie true si le niveau est fini, false sinon
  * @return true or false
  */
  public boolean isOver(){
    int nbBoxOnGoal = 0;
    for(int i = 0; i < panel.length; i++){
      for(int j = 0; j < panel[i].length; j++){
        if(panel[i][j] == '*' ){
          nbBoxOnGoal++;
        }
      }
    }

    //Vérification si tous les goals sont validé
    if(nbBoxOnGoal == nbInitGoal){
      return true;
    }else{
      return false;
    }
  }

  /**
  * Affiche pour la console l'état de la grille
  */
  public void situationToString() {
    for(int i = 0; i < panel.length; i++) {
      for (int j = 0; j < panel[i].length; j++) {
        System.out.print(panel[i][j]);
      }
      System.out.print("\n");
    }
    System.out.println("");
  }

  /**
  * Renvoie la liste des coups valides,
  * les coups que le joueur peut potentiellement jouer
  * (sans mur devant ni deux caisse en même temps devant)
  * et cela pour les 4 directions.
  * @return list, liste des coups valides (entier)
  */
  public List<State> validMoves(){
    List<State> list = new ArrayList<State>();
    load();

    //UP
    if(panel[player.getX()-1][player.getY()] == ' ' || panel[player.getX()-1][player.getY()] == '.'){
      if(boxMoved){
        list.add(stateMoved(1));
      }
    }else if(panel[player.getX()-1][player.getY()] == '$' || panel[player.getX()-1][player.getY()] == '*'){
      if(panel[player.getX()-2][player.getY()] == ' ' || panel[player.getX()-2][player.getY()] == '.'){
        if(boxMoved){
          list.add(stateMoved(1));
        }
      }
    }

    //RIGHT
    if(panel[player.getX()][player.getY()+1] == ' ' || panel[player.getX()][player.getY()+1] == '.'){
      if(boxMoved){
        list.add(stateMoved(2));
      }
    }else if(panel[player.getX()][player.getY()+1] == '$' || panel[player.getX()][player.getY()+1] == '*'){
      if(panel[player.getX()][player.getY()+2] == ' ' || panel[player.getX()][player.getY()+2] == '.'){
        if(boxMoved){
          list.add(stateMoved(2));
        }
      }
    }

    //DOWN
    if(panel[player.getX()+1][player.getY()] == ' ' || panel[player.getX()+1][player.getY()] == '.'){
      if(boxMoved){
        list.add(stateMoved(3));
      }
    }else if(panel[player.getX()+1][player.getY()] == '$' || panel[player.getX()+1][player.getY()] == '*'){
      if(panel[player.getX()+2][player.getY()] == ' ' || panel[player.getX()+2][player.getY()] == '.'){
        if(boxMoved){
          list.add(stateMoved(3));
        }
      }
    }

    //LEFT
    if(panel[player.getX()][player.getY()-1] == ' ' || panel[player.getX()][player.getY()-1] == '.'){
      if(boxMoved){
        list.add(stateMoved(4));
      }
    }else if(panel[player.getX()][player.getY()-1] == '$' || panel[player.getX()][player.getY()-1] == '*'){
      if(panel[player.getX()][player.getY()-2] == ' ' || panel[player.getX()][player.getY()-2] == '.'){
        if(boxMoved){
          list.add(stateMoved(4 ));
        }
      }
    }

    return list;
  }

  /**
  * Déplace le joueur vers le haut
  * en vérifiant que cela est possible
  */
  public void up(){
    setPanelBack();
    stateBack = getCopy();
    direction = 1;

    //On réupère les coordonnées du player
    load();

    //Si la case suivante est du vide
    if(panel[player.getX()-1][player.getY()] == ' '){
      panel[player.getX()-1][player.getY()] = '@'; //déplacement du player
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }

    //Si la case suivante est une box
    else if(panel[player.getX()-1][player.getY()] == '$'){
      if(panel[player.getX()-2][player.getY()] == ' '){ //Si c'est vide après la box
        panel[player.getX()-2][player.getY()] = '$'; //On déplace la box et le player
        panel[player.getX()-1][player.getY()] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()-2][player.getY()] == '.'){ //Si c'est un Goal après la box
        panel[player.getX()-2][player.getY()] = '*'; //On déplace la box dans le Goal et le player
        panel[player.getX()-1][player.getY()] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est une boxGoal
    else if(panel[player.getX()-1][player.getY()] == '*'){
      if(panel[player.getX()-2][player.getY()] == ' '){ //Si c'est du vide après la boxGoal
        panel[player.getX()-2][player.getY()] = '$'; //On déplace la box et le player
        panel[player.getX()-1][player.getY()] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()-2][player.getY()] == '.'){ //Si c'est un Goal après on déplace la caisse dans le Goal suivant
        panel[player.getX()-2][player.getY()] = '*'; //On déplace la box et le player dans le Goal
        panel[player.getX()-1][player.getY()] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est un Goal
    else if(panel[player.getX()-1][player.getY()] == '.'){
      panel[player.getX()-1][player.getY()] = '!'; //On déplace le player dans le Goal
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }
  }

  /**
  * Déplace le joueur vers la droite
  * en vérifiant que cela est possible
  */
  public void right(){
    setPanelBack();
    stateBack = getCopy();
    direction = 2;

    //On réupère les coordonnées du player
    load();

    //Si la case suivante est du vide
    if(panel[player.getX()][player.getY()+1] == ' '){
      panel[player.getX()][player.getY()+1] = '@'; //déplacement du player
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }

    //Si la case suivante est une box
    else if(panel[player.getX()][player.getY()+1] == '$'){
      if(panel[player.getX()][player.getY()+2] == ' '){ //Si c'est vide après la box
        panel[player.getX()][player.getY()+2] = '$'; //On déplace la box et le player
        panel[player.getX()][player.getY()+1] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()][player.getY()+2] == '.'){ //Si c'est un Goal après la box
        panel[player.getX()][player.getY()+2] = '*'; //On déplace la box dans le Goal et le player
        panel[player.getX()][player.getY()+1] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est une boxGoal
    else if(panel[player.getX()][player.getY()+1] == '*'){
      if(panel[player.getX()][player.getY()+2] == ' '){ //Si c'est du vide après la boxGoal
        panel[player.getX()][player.getY()+2] = '$'; //On déplace la box et le player
        panel[player.getX()][player.getY()+1] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()][player.getY()+2] == '.'){ //Si c'est un Goal après on déplace la caisse dans le Goal suivant
        panel[player.getX()][player.getY()+2] = '*'; //On déplace la box et le player dans le Goal
        panel[player.getX()][player.getY()+1] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est un Goal
    else if(panel[player.getX()][player.getY()+1] == '.'){
      panel[player.getX()][player.getY()+1] = '!'; //On déplace le player dans le Goal
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }
  }

  /**
  * Déplace le joueur vers le bas
  * en vérifiant que cela est possible
  */
  public void down(){
    setPanelBack();
    stateBack = getCopy();
    direction = 3;

    //On réupère les coordonnées du player
    load();

    //Si la case suivante est du vide
    if(panel[player.getX()+1][player.getY()] == ' '){
      panel[player.getX()+1][player.getY()] = '@'; //déplacement du player
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }

    //Si la case suivante est une box
    else if(panel[player.getX()+1][player.getY()] == '$'){
      if(panel[player.getX()+2][player.getY()] == ' '){ //Si c'est vide après la box
        panel[player.getX()+2][player.getY()] = '$'; //On déplace la box et le player
        panel[player.getX()+1][player.getY()] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()+2][player.getY()] == '.'){ //Si c'est un Goal après la box
        panel[player.getX()+2][player.getY()] = '*'; //On déplace la box dans le Goal et le player
        panel[player.getX()+1][player.getY()] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est une boxGoal
    else if(panel[player.getX()+1][player.getY()] == '*'){
      if(panel[player.getX()+2][player.getY()] == ' '){ //Si c'est du vide après la boxGoal
        panel[player.getX()+2][player.getY()] = '$'; //On déplace la box et le player
        panel[player.getX()+1][player.getY()] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()+2][player.getY()] == '.'){ //Si c'est un Goal après on déplace la caisse dans le Goal suivant
        panel[player.getX()+2][player.getY()] = '*'; //On déplace la box et le player dans le Goal
        panel[player.getX()+1][player.getY()] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est un Goal
    else if(panel[player.getX()+1][player.getY()] == '.'){
      panel[player.getX()+1][player.getY()] = '!'; //On déplace le player dans le Goal
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }
  }

  /**
  * Déplace le joueur vers la gauche
  * en vérifiant que cela est possible
  */
  public void left(){
    setPanelBack();
    stateBack = getCopy();
    direction = 4;

    //On réupère les coordonnées du player
    load();

    //Si la case suivante est du vide
    if(panel[player.getX()][player.getY()-1] == ' '){
      panel[player.getX()][player.getY()-1] = '@'; //déplacement du player
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }

    //Si la case suivante est une box
    else if(panel[player.getX()][player.getY()-1] == '$'){
      if(panel[player.getX()][player.getY()-2] == ' '){ //Si c'est vide après la box
        panel[player.getX()][player.getY()-2] = '$'; //On déplace la box et le player
        panel[player.getX()][player.getY()-1] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()][player.getY()-2] == '.'){ //Si c'est un Goal après la box
        panel[player.getX()][player.getY()-2] = '*'; //On déplace la box dans le Goal et le player
        panel[player.getX()][player.getY()-1] = '@';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est une boxGoal
    else if(panel[player.getX()][player.getY()-1] == '*'){
      if(panel[player.getX()][player.getY()-2] == ' '){ //Si c'est du vide après la boxGoal
        panel[player.getX()][player.getY()-2] = '$'; //On déplace la box et le player
        panel[player.getX()][player.getY()-1] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }else if(panel[player.getX()][player.getY()-2] == '.'){ //Si c'est un Goal après on déplace la caisse dans le Goal suivant
        panel[player.getX()][player.getY()-2] = '*'; //On déplace la box et le player dans le Goal
        panel[player.getX()][player.getY()-1] = '!';
        boxMoved = true;
        if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
          panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
        }else{ //Sinon c'est que c'était un Goal
          panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
        }
      }
    }

    //Si la case suivante est un Goal
    else if(panel[player.getX()][player.getY()-1] == '.'){
      panel[player.getX()][player.getY()-1] = '!'; //On déplace le player dans le Goal
      if(panel[player.getX()][player.getY()] == '@'){ //Si le player n'était pas dans un Goal
        panel[player.getX()][player.getY()] = ' '; //Vide à la place du player
      }else{ //Sinon c'est que c'était un Goal
        panel[player.getX()][player.getY()] = '.'; //Goal à la place du player
      }
    }
  }

  /**
  * Renvoie un état du jeu avec un coup joué
  * @return newState, nouvel état avec un coup joué
  */
  public State stateMoved(int dir){
    State newState = getCopy();
    switch(dir){
      case 1 :
        newState.up();
        break;
      case 2 :
        newState.right();
        break;
      case 3 :
        newState.down();
        break;
      case 4 :
        newState.left();
        break;
    }

    return newState;
  }

  /**
  * Fait une copie de l'état du jeu
  */
  public State getCopy(){
    State newState = new State(this.level);
    for(int i = 0; i < panel.length; i++){
      for(int j = 0; j < panel[i].length; j++){
        newState.panel[i][j] = panel[i][j];
      }
    }

    newState.stateBack = stateBack;
    newState.direction = direction;
    newState.boxMoved = boxMoved;

    return newState;
  }
}
