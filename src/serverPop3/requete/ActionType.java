package serverPop3.requete;

import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Actions types r�alis�es par le serveur, classe m�re des r�actions au diff�rentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {
	protected static final String EXTENSION_MAIL = ".txt";
	protected static final String MAIL_PATH = "./StockMail/";
	protected String user;
	
	protected String reponseOk(String msg){
		//TODO construire r�ponse ok
		return "+OK "+msg;
	}
	
	protected String reponseKo(String msg){
		//TODO construire r�ponse ko
		return "-KO "+msg;
	}
	
	protected boolean sendMsg(String msg, BufferedOutputStream outDonnees){
		
		try {
			outDonnees.write(msg.getBytes(), 0, (int) msg.getBytes().length);
			outDonnees.flush();
			return true;
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("IOException : "+e.getMessage());
			return false;
		}
	}
	
	// enlever verrou (fonction)


}
