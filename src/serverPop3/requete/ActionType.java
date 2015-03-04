package serverPop3.requete;

/**
 * Actions types réallisées par le serveur, classe mère des réactions au différentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {

	protected static final String MAIL_PATH = "./StockMail/";
	
	public String reponseOk(){
		//TODO construire réponse ok
		return "+OK ";
	}
	
	public String reponseKo(){
		//TODO construire réponse ko
		return "-KO ";
	}

}
