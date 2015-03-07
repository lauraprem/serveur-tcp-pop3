package serverPop3.requete;

import java.io.BufferedOutputStream;

/**
*
* @author Corinne & Laura
*/
public class ActionQUIT extends ActionType {
	
	public ActionQUIT() {
		super();
	}
	
	public boolean ProcessingQUIT(BufferedOutputStream outDonnees) {
		// TODO Lib�ration du verrou s'il est � lib�rer
		
		// Envoi du message au client
		String msg = super.reponseOk("POP3 server signing off");
		if(sendMsg(msg, outDonnees)){
			return true;
		}
		
		return false;
	}
}
