package serverPop3.requete;

/**
 * Actions types r�allis�es par le serveur, classe m�re des r�actions au diff�rentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {
	
	public String reponseOk(String message){
		//TODO construire r�ponse ok
		return "+OK " + message;
	}
	
	public String reponseKo(String message){
		//TODO construire r�ponse ko
		return "-KO " + message;
	}

}
