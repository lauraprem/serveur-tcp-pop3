package serverPop3.requete;

/**
 * Actions types réallisées par le serveur, classe mère des réactions au différentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {

	protected static final String MAIL_PATH = "./StockMail/";
	
	protected String reponseOk(String msg){
		//TODO construire réponse ok
		return "+OK "+msg;
	}
	
	protected String reponseKo(String msg){
		//TODO construire réponse ko
		return "-KO "+msg;
	}

}
