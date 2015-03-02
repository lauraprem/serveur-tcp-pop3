package serverPop3.requete;

/**
 * Actions types réallisées par le serveur, classe mère des réactions au différentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {
	
	public String reponseOk(String message){
		//TODO construire réponse ok
		return "+OK " + message;
	}
	
	public String reponseKo(String message){
		//TODO construire réponse ko
		return "-KO " + message;
	}

}
