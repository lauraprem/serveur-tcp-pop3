package serverPop3.requete;

/**
 * Actions types r�allis�es par le serveur, classe m�re des r�actions au diff�rentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {

	protected static final String MAIL_PATH = "./StockMail/";
	
	protected String reponseOk(String msg){
		//TODO construire r�ponse ok
		return "+OK "+msg;
	}
	
	protected String reponseKo(String msg){
		//TODO construire r�ponse ko
		return "-KO "+msg;
	}

}
