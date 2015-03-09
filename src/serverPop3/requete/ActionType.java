package serverPop3.requete;

import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Actions types r�alis�es par le serveur, classe m�re des r�actions au diff�rentes requetes 
 * @author Corinne & Laura
 *
 */
public abstract class ActionType {
	public static final String EXTENSION_MAIL = ".txt";
	public static final String MAIL_PATH = "./StockMail/";
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
			msg+= "\r\n";
			outDonnees.write(msg.getBytes(), 0, (int) msg.getBytes().length);
			outDonnees.flush();
			return true;
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("IOException : "+e.getMessage());
			return false;
		}
	}
	
	protected boolean PrecessingDefault(BufferedOutputStream outDonnees,String user){
		return true;
	}


}
